package jpabook;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            System.out.println();
            System.out.println("=======================");
            System.out.println();

            Member member1 = new Member();
            member1.setName("안녕안녕 1");

            Member member2 = new Member();
            member2.setName("안녕안녕 2");

            Team team1 = new Team();
            team1.setTeamName("팀팀 1");

            Team team2 = new Team();
            team2.setTeamName("팀팀 2");

            member1.setTeam(team1);
            member2.setTeam(team2);

            em.persist(team1);
            em.persist(member1);

            em.persist(team2);
            em.persist(member2);

            em.flush();
            em.clear();


            // JPQL은 SQL로 바로 번역되기 때문에 아래 내용과 같이 Member 엔티티 조회 후
            // TEAM 필드가 EAGER로 설정되어있는것을 확인하고 쿼리를 한번 더 날린 후 엔티티를 초기화한다.
            // -> EAGER여도 N + 1이 발생함
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            // select * from member;
            // select * from Team where Team.memberId = memberId; // Team이 EAGER로 설정되어있기 때문에 팀 조회 쿼리가 따로 발생

            // 앤간해서는 지연로딩으로 설정해주는것이 좋음
            // @XXXToOne 시리즈는 기본 패치 타입이 EAGER여서 꼭 LAZY로 설정해주자
            // 즉시로딩은 예측하기 어려운 쿼리가 나간다


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
