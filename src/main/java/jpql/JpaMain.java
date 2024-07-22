package jpql;

import jakarta.persistence.*;
import jpql.dto.MemberDto;

import java.util.Iterator;
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

            // case 식
            TypedQuery<String> query = em.createQuery("select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "when m.age >= 60 then '경로요금' " +
                    "else '일반요금' end " +
                    "from Member m", String.class);

            List<String> resultList = query.getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            em.flush();
            em.clear();

            // COALESCE null이 아니면 첫번째 인자값, null이면 두번째 인자값
            TypedQuery<String> query2 = em.createQuery("select coalesce(m.username, '???') as username from Member m", String.class);
            List<String> resultList1 = query2.getResultList();
            for (String s : resultList1) {
                System.out.println("s = " + s);
            }

            em.flush();
            em.clear();

            // NULLIF 두 인자 값이 같으면 null, 다르면 첫번째 인자값 반환
            TypedQuery<String> query3 = em.createQuery("select nullif(m.username, 'gg') as username from Member m", String.class);
            List<String> resultList2 = query3.getResultList();
            for (String s : resultList2) {
                System.out.println("s = " + s);
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
