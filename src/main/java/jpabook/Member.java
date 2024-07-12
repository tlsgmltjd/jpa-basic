package jpabook;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "favorite_food", joinColumns =
        @JoinColumn(name = "member_id")
    )
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "address", joinColumns =
        @JoinColumn(name = "member_id")
    )
    private List<Address> addressHistory = new ArrayList<>();

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

    // 임베디드 타입은 그냥 값 타입일 뿐이다. 엔티티의 값! 엔티티와 라이프사이클이 같다.
    // 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
    // @Embeddable / @Embedded
    // 재사용, 추상화가 가능하고 응집도을 높힐 수 있다.
    // 잘 설계한 ORM 애플리케이션은 매핑한 테이블 수보다 클래스의 수가 더 많다.

    // Period
//    @Embedded
//    private Period period;
//
//    // Address
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
//            @AttributeOverride(name = "street", column = @Column(name = "home_street")),
//            @AttributeOverride(name = "zipcode", column = @Column(name = "home_zipcode")),
//    })
//    private Address homeAddress;
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
//            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
//            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode")),
//    })
//    private Address WorkAddress;

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
}