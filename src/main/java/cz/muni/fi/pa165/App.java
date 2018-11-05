package cz.muni.fi.pa165;

import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.dao.impl.JpaMonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Affinity fire = new Affinity("Fire", 3);
        em.persist(fire);

        Affinity light = new Affinity("Light", 1);
        em.persist(light);

        Monster ogre = new Monster("Ogre", 150, 30, "medium", Arrays.asList(fire), Arrays.asList(light));
        em.persist(ogre);

        em.flush();
        em.getTransaction().commit();
        
        em.close();

        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        List<Monster> found = monsterDAO.findWithStrength(fire);

        for (Monster m : found) {
          System.out.println(m.getName());
        }

        em = emf.createEntityManager();
        System.out.println(em.createQuery("SELECT m FROM Monster m").getResultList());
        em.close();

        emf.close();
    }
}
