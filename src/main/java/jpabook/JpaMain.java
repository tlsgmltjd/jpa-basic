package jpabook;

import jakarta.persistence.*;
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

            Member member = new Member();
            member.setName("ASd");
            member.setAddressHistory(List.of(new Address("Asd", "ASd", "Asd"),
                    new Address("Aㅇㄴㅁㅇㄴㅇsd", "ASd", "Asd")));
            member.getFavoriteFoods().add("A");
            member.getFavoriteFoods().add("B");
            member.getFavoriteFoods().add("C");

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("------------");
            Member findMember = em.find(Member.class, member.getId()); // 컬렉션 값 타입은 기본적으로 지연로딩이 됨
            System.out.println("------------");

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println(favoriteFood);
            }

            // 값 타입을 수정하고 싶으면 각각의 값 타입은 불변해야하기 때문에 갈아끼워야함
            // 하지만 값 타입은 추적하기 어렵다 (모든 컬럼을 PK로 잡아야함)
            // 그렇기 때문에 컬렉션을 수정한다면... 연관되어있는 엔티티가 가지고 있는 모든 컬렉션을 삭제하고 현재 있는 모든 컬렉션 데이터를 다시 insert 한다.
            // -> 결론. 쓰지 말자. OrderColumn으로 해결 가능하긴 하지만 이것도 예측 불가능하게 동작한다. 일대다 관계를 고려

            // equals 로 비교해서 동일한 것이라면 삭제 (equals, hashcode를 잘 구현해두었기 때문)
            findMember.getAddressHistory().remove(new Address("Asd", "ASd", "Asd"));
            findMember.getAddressHistory().add(new Address("NEW", "ASd", "Asd"));

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
