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

            // 조인 내부, 외부, 세타(연관관계가 없는 엔티티끼리 조인)조인 가능
            // on절을 사용해서 조인 조건 걸 수 있음, on절로 연관관계가 없는 엔티티를 외부 조인할 수도 있음

            List<Member> members = em.createQuery("select m from Member m left join m.team t on t.name = :name", Member.class)
                    .setParameter("name", "gmg")
                    .getResultList();


//            List<Member> members = em.createQuery("select m from Member m left join m.team t on t.name = :name", Member.class)
//                    .setParameter("name", "gmg")
//                    .getResultList();

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
