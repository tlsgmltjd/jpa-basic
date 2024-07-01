package jpabook;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MemberProduct { // 중간 테이블을 엔티티로 승격 (N:M -> 1:N M:1)

    @Id @GeneratedValue
    private Long id; // 왠만하면 PK는 인조값을 사용하는 것이 좋다.
    // -> 유연하게 사용할 수 있다.

    // 매핑 정보
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 주문에 대한 메타 데이터들...
    private int count;
    private int price;
    private LocalDateTime orderDateTime;
}
