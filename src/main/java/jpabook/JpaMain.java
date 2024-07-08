package jpabook;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            System.out.println();
            System.out.println("=======================");
            System.out.println();

            Member member1 = new Member();
            member1.setName("안녕안녕 1");

            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());

            // 영속성 컨텍스트를 먼저 조회하기 때문에 엔티티가 영속성 컨텍스트에 있다면 그걸 반환해줌
            // JPA는 한 트랜잭션, 영속성 컨텍스트에서 조회한 엔티티의 동일성을 보장해줌
//            Member reference1 = em.getReference(Member.class, m1.getId());
//            System.out.println(reference1.getClass());

            em.flush();
            em.clear();

            Member ref = em.getReference(Member.class, member1.getId()); // proxy
            System.out.println("ref.getClass() = " + ref.getClass());

            /*
            em.detach(ref);

            ref.getName(); // could not initialize proxy exception

            // 프록시 객체가 영속성 컨텍스트에서 관리되지 않는다면
            // 해당 객체를 참조해서 타켓 객체를 초기화하려 할때 예외가 발생한다.
            */


            // 프록시 관련 유틸 기능
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(ref)); // 프록시 초기화 여부

            // 프록시 클래스 확인: class jpabook.Member$HibernateProxy$1W1cl9JZ
            System.out.println(ref.getClass());

            // 강제 초기화
            Hibernate.initialize(ref);

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
