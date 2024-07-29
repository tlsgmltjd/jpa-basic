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

            Team teamA = new Team();
            teamA.setName("팀A");
            Team teamB = new Team();
            teamB.setName("팀B");

            Test test1 = new Test();
            test1.setName("asd");
            Test test2 = new Test();
            test2.setName("adfg");

            teamA.setTest(test1);
            teamB.setTest(test2);

            em.persist(test1);
            em.persist(test2);
            em.persist(teamA);
            em.persist(teamB);

            em.flush();
            em.clear();

            List<Team> resultList = em.createQuery("select t from Team t", Team.class)
                    .getResultList();

            for (Team team : resultList) {
                System.out.println("team.getTest() = " + team.getTest().getName());
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
