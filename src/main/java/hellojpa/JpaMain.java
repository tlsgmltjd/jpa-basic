package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // flush

            Member member = new Member(200L, "나는 플러시에요");
            em.persist(member);

//            em.flush();

            System.out.println("==================");

            String jpql = "SELECT m FROM Member m WHERE m.id = :id";
            Member fm = em.createQuery(jpql, Member.class)
                    .setParameter("id", 200)
                    .getSingleResult();

            System.out.println(fm.getName());

            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
