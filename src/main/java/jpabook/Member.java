package jpabook;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

//    @ManyToMany // 다대다 매핑 -> 객체 관점에서는 가능하지만 테이블 관점에서는 불가능한 매핑, 중간 테이블로 일대다 다대일로 풀어서 매핑됨
    // 편리해보이지만 실무에서 사용하면 안된다.. 중간 테이블은 연결 목적으로만 존재하지 않는다.
    // 생성시간, 수정시간 등등 같은 메타 데이터를 저장할 수가 없다. (매핑 정보만 담긴다.)
    // 쿼리가 예상할 수 없다. 중간 테이블 때문에 조인 쿼리 같은게 예상치 못하게 나갈 수 있다.
//    @JoinTable(name = "member_product")
//    private List<Product> products = new ArrayList<>();

    // 다대다 한계 극복 방법: 연결용 중간 테이블을 엔티티로 승격시킨다. (매핑 정보 외에도 여러 정보들을 저장 가능)

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    @OneToMany // 일대다 매핑 (1쪽이 연관관계의 주인..)
    @JoinColumn(name = "member_id") // @JoinColumn을 꼭 해야함 -> 중간테이블이 생겨버림(JoinTable)
    private List<Order> orders = new ArrayList<>();

    // 객체 상으로는 1쪽이 주인이지만 테이블 상으로는 다대일 관계와 동일하다.
    // 연관관계 설정시 update 쿼리가 추가로 발생한다.

    // 엔티티가 관리하는 왜래키가 다른 테이블에 있다는 크나큰 단점이 있다.
    // + 추가 update query
    // 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자..

    public Member() {
    }

    public Member(Long id, String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
