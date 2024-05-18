package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // 비영속
            Member member = new Member();
            member.setId(1L);
            member.setName("USER_A");

            // 영속
            // em.persist() 가 디비에 저장하는게 아니다. em의 영속성 컨텍스트 안에 넣어서 관리되는 것이다.
            System.out.println("=============== BEFORE");
            em.persist(member);
            System.out.println("=============== AFTER");

            // 준영속
            // 엔티티 객체를 영속성 컨텍스트에서 분리시킨다.
//            em.detach(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
            // 변경감지 -> 쓰기지연 저장소에 등록, 쓰기지연 저장소의 내용 데이터베이스에 쿼리
            // 직접호출, 트랜잭션 커밋시 자동호출, JPQL 쿼리 실행시 자동 호출

            tx.commit(); // 트랜잭션이 커밋되는 순간 영속성 컨텍스트에서 관리되는 엔티티가 디비에 반영된다.
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // 애플리케이션과 데이터베이스 사이에서 중간자 역할을 해주는 영속성 컨텍스트는
        // 1차캐시, 동일성 보장, 쓰기 지연, 변경 감지, 지연 로딩 기능을 지원해준다.

        // 1차 캐시: 동일 트랜잭션 내에서 영속성 컨텍스트에 보관된 엔티티를 조회시 1차캐시에서 반환
        // -> 중복된 조회 쿼리가 나가지 않음, 동일한 엔티티 조회시 두 엔티티의 동일성이 보장됨

        // 쓰기 지연: 트랜잭션 내부에서 발생한 CUD 쿼리를 모아둔 후(쓰지 지연 저장소) 해당 트랜잭션 커밋 순간에 데이터베이스에 한번에 쿼리한다.

        // 변경 감지: 트랜잭션 내에서 영속 엔티티의 수정이 일어났다면 트랜잭션 커밋 순간에 1차 캐시에 있는 영속 엔티티의 스냅샷을 비교하여 변경 부분에 대한
        // UPDATE 쿼리를 만들어 쓰기 지연 저장소에 저장 후 함께 쿼리한다.

        // 지연 로딩: 엔티티 조회시 엔티티와 연관관계로 매핑되어 있는 엔티티가 있디면, 해당 엔티티에 대한 조회 쿼리를 바로 날리지 않고
        // 해당 엔티티에 대한 참조가 일어나는 순간에 조회 쿼리를 날린다.

        emf.close();
    }
}
