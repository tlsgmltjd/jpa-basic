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

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            Member member2 = new Member();
            member2.setTeam(teamA);
            member2.setUsername("회원2");
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);

            em.persist(teamA);
            em.persist(teamB);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            for (Member member : members) {
                System.out.println("member = " + member);
                System.out.println("t n = " + member.getTeam().getName());
                // 1 -> 팀A SQL 쿼리
                // 2 -> 팀A 영속성 컨텍스트 1차 캐시
                // 3 -> 팀B SQL 쿼리
            }

            System.out.println("==================");

            em.flush();
            em.clear();

            List<Member> members2 = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();
            for (Member member : members2) {
                System.out.println("member = " + member);
                System.out.println("t n = " + member.getTeam().getName());
            }

            em.flush();
            em.clear();

            // 하이버네이트 6 이전 버전에서는 컬렉션 페치조인을 하면 데이터가 뻥튀기 되는 현상이 있었다.
            // 아래와 같이 조인하면 쿼리 결과로 팀A 팀A 팀B 이렇게 로우를 주니까 size가 3줄이나왔다.
            // 하이버네이트 6 버전에서부터 중복을 자동으로 제거해준다.

            // DISTINCT
            // 1. sql에 distinct, 2. 애플리케이션에서 엔티티 중복을 제거

            List<Team> resultList = em.createQuery("select t from Team t join fetch t.members", Team.class)
                    .getResultList();

            for (Team team : resultList) {
                System.out.println("data = " + team.getName() + " " + team.getMembers().size());
            }


            // 페치조인, 이너조인 차이
            // 일반 조인은 실행시 연관된 엔티티를 함께 조회하지 않는다.
            // 페치조인으르 사용할때만 연관된 엔티티도 함께 조회한다. 즉시로딩처럼
            // 페치조인은 객체 그래프를 SQL 한번에 조회하는것


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
