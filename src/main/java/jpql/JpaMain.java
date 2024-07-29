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

            // 벌크 연산
            // pk를 집어서 각각의 튜플을 업데이트, 딜리트하는것을 제외한 모든 SQL UPDATE, DELETE문이라고 보면 된다.
            // executeUpdate 쓰면 됨

            // rc -> 영향 받은 row 수
            int rc = em.createQuery("update Member m set m.age = 10")
                    .executeUpdate();

            em.createQuery("delete from Member m")
                    .executeUpdate();

            // 벌크 연산은 영속성 컨텍스트를 무시하고 디비에 직접 쿼리한다.
            // 벌크 연산 처리 후 영속성 컨텍스트 초기화가 필요하다. jpql은 실행 전 플러쉬 자동 호출

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
