package jpabook;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Locker {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker") // 일대일 양방향 매핑
    private Member member;
}
