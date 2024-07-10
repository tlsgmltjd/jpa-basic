package jpabook;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();

            Address address1 = new Address("A", "B", "C");
            Address address2 = new Address("A", "B", "C");
            Period period = new Period(LocalDateTime.now(), LocalDateTime.now());

            member.setPeriod(period);
            member.setHomeAddress(address1);
            member.setWorkAddress(address1);

            em.persist(member);

            // 동일성 비교: 인스턴스의 참조값 비교 ==, 동등성 비교 인스턴스의 값 비교 equals()
            // 값 타입은 equals()로 동등성 비교를 해서 값이 동일한지 판별해야한다.
            // -> 값 비교가 필요할때 임베디드 타입과 같은 객체 값 타입은 equals()를 재정의해서 동등성 비교를 가능하게 해야한다.

            System.out.println(address1.equals(address2));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
