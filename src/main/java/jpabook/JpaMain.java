package jpabook;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();

            Address address = new Address();
            address.setCity("Asd");
            address.setZipcode("Asd");
            address.setStreet("Asd");

            Period period = new Period();
            period.setStateDate(LocalDateTime.now());
            period.setEndDate(LocalDateTime.now());

            member.setPeriod(period);
            member.setHomeAddress(address);
            member.setWorkAddress(address);

            em.persist(member);

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
