package jpabook;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member("kim ju en");
            em.persist(member);

//            List<Member> resultList = em.createQuery("select m from Member m where m.name LIKE '%kim%'")
//                    .getResultList();
//
//            for (Member m : resultList) {
//                System.out.println(m.getName());
//            }

            // criteria
            // String이 아닌 자바코드로 JPQL을 작성할 수 있다. JPQL 빌더 역할임
            // 하지만 너무 복잡하고 실용성이 없다 가독성이 낮고 유지보수성 낮음
            // QueryDSL 쓰자
            // QueryDSL도 자바 코드로 JPQL을 작성할 수 있고 컴파일시 문법 오류를 찾을 수 있다.
            // 동적 쿼리 작성이 편하고 단순하다! 실무사용권장
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim ju en"));
            List<Member> members = em.createQuery(cq)
                    .getResultList();

            for (Member member1 : members) {
                System.out.println("member1 = " + member1);
            }

            // JPQL로는 해결할 수 없는 특정 디비에 의존적인 문법을 사용할때 네이티브 SQL을 날릴 수 있도록 지원해준다.

            em.createNativeQuery("select NAME from member", Member.class)
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
