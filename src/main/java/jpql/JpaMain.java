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

            // 경로 표현식
            // 상태 필드, 단일 값 연관 필드, 컬렉션 값 연관 필드
            // 상태 필드 - 단순히 값을 저장하는 필드, 경로 탐색의 끝이다
            // 단일 값 연관 필드 - 묵시적 내부 조인이 발생한다, 이후 조회한 엔티티에서 또 탐색이 가능하다.
            // 컬렉션 값 연관 필드 - 묵시적 내부 조인이 발생한다, 이후 조회한 컬렉션에 대한 탐색이 불가능하다.

            // 단일 값 연관 필드 - 묵시적 내부 조인이 발생 -> 조심히 써야함, 묵시적 조인은 가급적 안 써야한다.
            em.createQuery("select m.team from Member m")
                    .getResultList();

            // 컬렉션 값 연관 필드 - 묵시적 조인 발생, 컬렉션을 탐색했다면 해당 컬렉션 엔티티에 대한 탐색이 불가능하다!
            em.createQuery("select t.members from Team t")
                    .getResultList();

            // from 절에서 명시적 조인을 통해 alias를 얻어서 탐색은 가능하다.
            List<String> resultList = em.createQuery("select m.username from Team t join t.members m", String.class)
                    .getResultList();
            for (String s : resultList) {
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
