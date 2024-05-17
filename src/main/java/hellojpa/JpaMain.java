package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // 비영속
            Member member = new Member();
            member.setId(1L);
            member.setName("USER_A");

            // 영속
            // em.persist() 가 디비에 저장하는게 아니다. em의 영속성 컨텍스트 안에 넣어서 관리되는 것이다.
            System.out.println("=============== BEFORE");
            em.persist(member);
            System.out.println("=============== AFTER");

            // 준영속
            // 엔티티 객체를 영속성 컨텍스트에서 분리시킨다.
//            em.detach(member);

            tx.commit(); // 트랜잭션이 커밋되는 순간 영속성 컨텍스트에서 관리되는 엔티티가 디비에 반영된다.
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // 애플리케이션과 데이터베이스 사이에서 중간자 역할을 해주는 영속성 컨텍스트는
        // 1차캐시, 동일성 보장, 쓰기 지연, 변경 감지, 지연 로딩 기능을 지원해준다.

        emf.close();
    }
}
