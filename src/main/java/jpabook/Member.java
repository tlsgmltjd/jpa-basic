package jpabook;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

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
