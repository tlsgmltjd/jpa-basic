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

            Member member = new Member();
            member.setUsername("gg");
            member.setAge(10);

            Team team = new Team();
            team.setName("gg");

            member.changeTeam(team);

            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 서브쿼리 - 네이티브 쿼리와 같이 사용 가능
            // JPA 표준 스펙에서는 where, having 절에서만 서브쿼리 가능
            // 하이버네이트에서는 select 절 서브쿼리도 지원해줌
            // FROM 절 서브쿼리는 JPQL에서 지원하지 않음.. 조인으로 풀어서 해결 -> 최신버전은 가능하다고는 함
            em.createQuery("select m from Member m where m.age > (select avg(m.age) from Member m)")
                    .getResultList();

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
