package jpabook.InheritanceMapping;

import jakarta.persistence.*;

// 상속관계 매핑 -> 슈퍼타입 서브타입과 매핑이 된다.

// JOIN 자식 엔티티들이 부모 엔티티의 PK를 FK로 가지고 있는 형태
// 장점: 정규화가 잘 되어있다. 외래키 참조 무결성 제약조건 활용가능 (다른 엔티티에서 참조하려고 할때 슈퍼타입 엔티티만 볼 수 있다.), 정규화로 저장공간 효율화
// 단점: 조회시 조인 사용, insert sql 2번 호출

// SINGLE_TABLE 모든 서브타입 엔티티의 필드를 슈퍼타입이 가지고 있는 형태
// 장점: 조인 필요 없다. -> 조회 성능 빠름
// 단점: 자식 엔티티가 매핑한 컬럼은 모두 NUULABLE 해야함, 단일 테이블이 모든 것을 저장하므로 테이블이 커져서 임계치를 넘으면 오히려 성능 저하가 될 수 있다.

// TABLE_PER_CLASS (구현 클래스마다 테이블 전략) 서브타입이 슈퍼타입의 필드를 모두 갖는 형태
// 장점: 서브타입을 명확하게 구분할 때 효과적, NOTNULL 가능
// 단점: 여러 자식 테이블을 함께 조회할 때 성능이 느리다. (UNION sql이 나감), 자식 테이블을 통합해서 쿼리하기 어렵다.

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DDTYPE") // Join 전략에서 Dtype이 생긴다.
public abstract class Gift {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
