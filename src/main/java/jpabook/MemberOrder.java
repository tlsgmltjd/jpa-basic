package jpabook;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MemberOrder {
    @Id @GeneratedValue
    private Long id;
}
