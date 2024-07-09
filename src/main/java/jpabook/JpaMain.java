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

            Parent parent = new Parent();

            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            // 영속성 전이: cascade = PERSIST로 설정시 부모 엔티티가 영속화되면 자식엔티티들이 설정되어있으면 자동으로 영속화됨

            em.flush(); em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            // findParent.getChildren().remove(0); // orphanRemoval 속성으로 부모 엔티티와 연관관계가 끊어진 자식 엔티는 자동으로 delete

            // 위의 고아객체 삭제 기능을 활성화하면 부모를 제거해도 자식은 사라진다. <- cascade = REMOVE 처럼 동작한다.
            em.remove(findParent);

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
