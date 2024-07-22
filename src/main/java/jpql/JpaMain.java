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
            member.setType(MemberType.ADMIN);

            Team team = new Team();
            team.setName("gg");

            member.changeTeam(team);

            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 기타 표현과 기타식
            // 문자, 숫자, boolean, ENUM(페키지명까지 포함해야함)
            // type(e) = Entity 상속 관계에서 사용
            Query query = em.createQuery("select 'HELLO', true, 10L from Member m");

            List<Member> members = em.createQuery("select m from Member m " +
                            "where jpql.MemberType.ADMIN = m.type", Member.class)
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
