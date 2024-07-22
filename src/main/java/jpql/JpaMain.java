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

            Member member2 = new Member();
            member2.setUsername("fffff");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);

            Team team = new Team();
            team.setName("gg");

            member.changeTeam(team);

            em.persist(team);
            em.persist(member);
            em.persist(member2);

            em.flush();
            em.clear();

            // concat, substring, trim, lower, upper, length, locate, abs, sort, mod, size, index
            List resultList = em.createQuery("select substring('abc', 2, 1) from Member m")
                    .getResultList();

            // 컬렉션의 size
            List<Integer> resultList1 = em.createQuery("select size(t.members) from Team t", Integer.class)
                    .getResultList();

            for (Integer i : resultList1) {
                System.out.println("i = " + i);
            }

            TypedQuery<String> query = em.createQuery("select function('group_concat', m.username) from Member m", String.class);
            List<String> resultList2 = query.getResultList();

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
