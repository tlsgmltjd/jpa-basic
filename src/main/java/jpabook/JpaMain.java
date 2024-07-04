package jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.InheritanceMapping.Movie;

import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Movie movie = new Movie();
            movie.setDirector("A");
            movie.setActor("ㅎㅎ");
            movie.setPrice(10000);
            movie.setName("B");

            em.persist(movie);

            em.flush();
            em.clear();

            em.find(Movie.class, movie.getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
