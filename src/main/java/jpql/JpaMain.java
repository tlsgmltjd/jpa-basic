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

            // 프로젝션 - select 절에 조회할 대상을 지정하는것
            // 엔티티, 임베디드, 스칼라 타입이 프로젝션 대상이될 수 있음
            Member member = new Member();
            member.setUsername("123");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 엔티티 타입 프로젝션으로 엔티티를 조회해오면 영속성 컨텍스트에 관리된다.
            List<Member> members1 = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            members1.get(0).setAge(100);

            // 임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            // Query 타입으로 조회(Object), Dto 객체로 바로 조회하기 (new 키워드 사용해서)
            List<MemberDto> resultList = em.createQuery("select distinct new jpql.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            for (MemberDto memberDto : resultList) {
                System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
                System.out.println("memberDto.getAge() = " + memberDto.getAge());
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
