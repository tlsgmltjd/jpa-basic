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

            // 페치조인 특징과 한계
            // 페치조인 대상에게 alias를 주면 안된다
            // -> 페치조인 대상에게 alias를 줘서 조건절로 걸러서 가져오거나 하면, 객체 그래프의 사상과 맞지 않다.
            // -> 페치조인은 연관된 엔티티를 한번에 조회한다 라는 의미인데 어떤 연관된 엔티티는 가져와지고 어떤건 안가져와지면 예상된 설계가 아니다.

            // 둘 이상의 컬렉션은 페치조인 불가능
            // -> 데이터 정합성이 예상할 수 없게 안맞을 수 있다.

            // 컬렉션을 페치조인하면 페이징 API 사용 불가
            // -> 데이터가 뻥튀기되어 나오기 때문에 정상적으로 페이징을 디비단에서 적용시킬 수 없다.
            // -> 그렇기 때문에 모든 데이터를 퍼올린 다음 메모리에서 페이징을 적용하여 성능이 저하된다.
            // 일대다 관계에서 일을 기준으로 페이징을 하고 싶은데, 페치 조인의 결과로 생성되는 row는 다가 기준이 되기 때문이다.

            em.flush();
            em.clear();

            List<Team> resultList3 = em.createQuery("select t from Team t join fetch t.members", Team.class)
                    .setMaxResults(1)
                    .setFirstResult(0)
                    .getResultList();

            // WARN: HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory

            em.flush();
            em.clear();

            List<Team> resultList4 = em.createQuery("select t from Team t", Team.class)
                    .setMaxResults(2)
                    .setFirstResult(0)
                    .getResultList();

            for (Team team : resultList4) {
                List<Member> members1 = team.getMembers();
                for (Member member : members1) {
                    System.out.println("member.getUsername() = " + member.getUsername());
                }
            }

            // 페치조인은 연관된 엔티티를 한번 쿼리로 조회한다.
            // 엔티티 컬럼의 글로벌 로딩 전략보다 우선시다
            // 모든 글로벌 로딩 전략은 지연로딩으로 설정하자
            // 최적화가 꼭 필요한 곳에만 페치조인을 적용시키자

            // 하지만 모든것을 페치조인으로 해결할 순 없다
            // TODO -> 페치조인은 객체 그래프를 유지할 때 사용하면 효과적이다.

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
