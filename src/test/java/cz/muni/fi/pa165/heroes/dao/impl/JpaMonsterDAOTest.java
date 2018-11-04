package cz.muni.fi.pa165.heroes.dao.impl;


import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.DAO;
import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Unit tests for Monster JPA DAO implementation.
 */
@Transactional
public class JpaMonsterDAOTest {
  
  private static ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");
  
  private static Monster iceOgre;
  private static Monster fireOgre;
  private static Monster efreet;


  @BeforeClass
  public static void setUp() {
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();

      Affinity fire = new Affinity("Fire", 2);
      Affinity fireEfreet = new Affinity("Fire", 2);
      Affinity ice = new Affinity("Ice", 3);
      Affinity lightHigh = new Affinity("Light", 4);
      Affinity lightLow = new Affinity("Light", 1);
      Affinity lightMedium = new Affinity("Light", 2);
      Affinity water = new Affinity("Water", 5);

      em.persist(fire);
      em.persist(fireEfreet);
      em.persist(ice);
      em.persist(lightHigh);
      em.persist(lightLow);
      em.persist(lightMedium);
      em.persist(water);

      iceOgre = new Monster("Ice Ogre", 150, 30, "medium", Arrays.asList(ice), Arrays.asList(lightLow));
      fireOgre = new Monster("Fire Ogre", 180, 40, "medium", Arrays.asList(fire), Arrays.asList(lightHigh));
      efreet = new Monster("Efreet", 180, 40, "small", Arrays.asList(fireEfreet), Arrays.asList(lightMedium, water));

      em.persist(iceOgre);
      em.persist(fireOgre);
      em.persist(efreet);

      em.flush();
      em.getTransaction().commit();

      em.close();
  }

    /**
     * Test for the findAll method.
     */
    @Test
    public void testFindAll() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        List<Monster> allMonsters = monsterDAO.findAll();

        assertTrue(allMonsters.size() == 3);

        List<String> names = new ArrayList<>();

        for (Monster m : allMonsters) {
            names.add(m.getName());
        }

        assertTrue(names.contains("Ice Ogre"));
        assertTrue(names.contains("Fire Ogre"));
        assertTrue(names.contains("Efreet"));
    }

    /**
     * Test for the findById method.
     */
    @Test
    public void testFindById() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        Monster fo = monsterDAO.findById(fireOgre.getId());
        Monster io = monsterDAO.findById(iceOgre.getId());
        Monster ef = monsterDAO.findById(efreet.getId());
        Monster nonexistent = (Monster) monsterDAO.findById(new Long("-1"));

        assertTrue(fo.getName().equals(fireOgre.getName()));
        assertTrue(io.getName().equals(iceOgre.getName()));
        assertTrue(ef.getName().equals(efreet.getName()));
        assertTrue(nonexistent == null);
    }

    /**
     * Test for the update method.
     */
    @Transactional
    @Test
    public void testUpdate() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        fireOgre.setName("New Fire Ogre");

        monsterDAO.update(fireOgre);

        Monster found = (Monster) monsterDAO.findById(fireOgre.getId());
        assertTrue(found.getName().equals("New Fire Ogre"));
    }

    /**
     * Test for the update method.
     */
    @Transactional
    @Test
    public void testSave() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        Monster newMonster = new Monster("New Guy", 110, 10, "small", new ArrayList<Affinity>(), new ArrayList<Affinity>());

        monsterDAO.save(newMonster);

        Monster found = (Monster) monsterDAO.findById(newMonster.getId());
        assertTrue(found.getName().equals("New Guy"));
    }

    @Test
    public void testDelete() {
      // TODO: Implement me.
      assertTrue(true);
    }

    @Test
    public void testDeleteById() {
      // TODO: Implement me.
      assertTrue(true);
    }
    
    @Test
    public void testFindWithStrength() {
      // TODO: Implement me.
      assertTrue(true);
    }
    
    @Test
    public void testFindWithWeakness() {
      // TODO: Implement me.
      assertTrue(true);
    }
    
    @Test
    public void testFindWithSize() {
      // TODO: Implement me.
      assertTrue(true);
    }
    
    @Test
    public void testFindByQuest() {
      // TODO: Implement me.
      assertTrue(true);
    }
}
