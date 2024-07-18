package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("신희성");
            member.setAge(18);
            em.persist(member);

            // 반환 타입이 명확할 때
            TypedQuery<Member> tQuery = em.createQuery("select m from Member m", Member.class);
            // 반환 타입이 명확하지 않을 때
            Query query = em.createQuery("select m.username, m.age from Member m where m.id = 1");

            List<Member> resultList = tQuery.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            Object singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            // setParameter로 파라미터 지정가능, 메서드 체이닝하면 좋다
            List<Member> resultList1 = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "신희성")
                    .getResultList();
            for (Member member1 : resultList1) {
                System.out.println("asdasd = " + member1.getUsername());
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
