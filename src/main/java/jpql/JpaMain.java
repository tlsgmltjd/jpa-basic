package jpql;

import jakarta.persistence.*;
import jpql.dto.MemberDto;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("123");
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            // JPA는 페이징을 setFirstResult, setMaxResults이 두 API로 추상화함
            // 각각의 데이터베이스의 방언에 맞게 알아서 쿼리를 짜주어서 코드 상에서는 아래처럼 추상화된 API만 사용하면 됨

            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member1 : members) {
                System.out.println("member1 = " + member1);
            }


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
