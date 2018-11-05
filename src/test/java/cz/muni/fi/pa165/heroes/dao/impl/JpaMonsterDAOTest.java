package cz.muni.fi.pa165.heroes.dao.impl;


import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.DAO;
import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertTrue;
import org.junit.Before;

import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Unit tests for Monster JPA DAO implementation.
 *
 * @author Vojtech Krajnansky (423126)
 */
@Transactional
public class JpaMonsterDAOTest {

    private ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");

    private Affinity fire;
    private Affinity ice;
    private Affinity lightHigh;
    private Affinity lightLow;
    private Affinity water;

    private Monster iceOgre;
    private Monster fireOgre;
    private Monster efreet;


    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        fire = new Affinity("Fire", 2);
        ice = new Affinity("Ice", 3);
        lightHigh = new Affinity("Light", 4);
        lightLow = new Affinity("Light", 1);
        water = new Affinity("Water", 5);

        em.persist(fire);
        em.persist(ice);
        em.persist(lightHigh);
        em.persist(lightLow);
        em.persist(water);

        iceOgre = new Monster("Ice Ogre", 150, 30, "medium", Arrays.asList(ice), Arrays.asList(lightLow));
        fireOgre = new Monster("Fire Ogre", 180, 40, "medium", Arrays.asList(fire), Arrays.asList(lightHigh));
        efreet = new Monster("Efreet", 180, 40, "small", Arrays.asList(fire), Arrays.asList(lightLow, water));

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
     * Test for the save method.
     */
    @Transactional
    @Test
    public void testSave() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);
        Monster newMonster = new Monster("New Guy", 110, 10, "small", new ArrayList<Affinity>(), new ArrayList<Affinity>());

        monsterDAO.save(newMonster);

        assertTrue(monsterDAO.findAll().size() == 4);
        Monster found = (Monster) monsterDAO.findById(newMonster.getId());
        assertTrue(found.getName().equals("New Guy"));
    }

    /**
     * Test for the delete method.
     */
    @Test
    public void testDelete() {
      MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

      assertTrue(monsterDAO.delete(fireOgre));
      assertTrue(monsterDAO.findAll().size() == 2);
      assertTrue(monsterDAO.findById(fireOgre.getId()) == null);
    }

    /**
     * Test for the deleteById method.
     */
    @Test
    public void testDeleteById() {
      MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

      assertTrue(monsterDAO.deleteById(fireOgre.getId()));
      assertTrue(monsterDAO.findAll().size() == 2);
      assertTrue(monsterDAO.findById(fireOgre.getId()) == null);
    }

    /**
     * Test for the findWithStrength method.
     */
    @Test
    public void testFindWithStrength() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

        List<Monster> withFire = monsterDAO.findWithStrength(fire);
        assertTrue(withFire.size() == 2);

        List<Monster> withIce = monsterDAO.findWithStrength(ice);
        assertTrue(withIce.size() == 1);

        assertTrue(withIce.get(0).getName().equals("Ice Ogre"));

        List<Monster> withWater = monsterDAO.findWithStrength(water);
        assertTrue(withWater.size() == 0);
    }

    /**
     * Test findWithWeakness method.
     */
    @Test
    public void testFindWithWeakness() {
      MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

      List<Monster> withLightLow = monsterDAO.findWithWeakness(lightLow);
      assertTrue(withLightLow.size() == 2);

      List<Monster> withWater = monsterDAO.findWithWeakness(water);
      assertTrue(withWater.size() == 1);

      assertTrue(withWater.get(0).getName().equals("Efreet"));

      List<Monster> withLightHigh = monsterDAO.findWithWeakness(lightHigh);
      assertTrue(withLightHigh.size() == 1);

      assertTrue(withLightHigh.get(0).getName().equals("Fire Ogre"));

      List<Monster> withIce = monsterDAO.findWithWeakness(ice);
      assertTrue(withIce.size() == 0);
    }

    /**
     * Test findWithSize method.
     */
    @Test
    public void testFindWithSize() {
        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

        List<Monster> small = monsterDAO.findWithSize("small");
        List<Monster> medium = monsterDAO.findWithSize("medium");
        List<Monster> large = monsterDAO.findWithSize("large");

        assertTrue(large.size() == 0);
        assertTrue(small.size() == 1);
        assertTrue(small.get(0).getName().equals("Efreet"));
        assertTrue(medium.size() == 2);
    }

    /**
     * Test findByQuest method.
     */
    @Test
    public void testFindByQuest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Quest darkCave = new Quest("Dark Cave", "Cave of Doom", 100, 2);
        Quest happyLands = new Quest("Happy Lands of Lovelandia", "Lovelandia", 1, 1);

        em.persist(darkCave);
        em.persist(happyLands);

        darkCave.addMonster(fireOgre);
        darkCave.addMonster(fireOgre);
        darkCave.addMonster(efreet);

        em.merge(darkCave);


        em.flush();
        em.getTransaction().commit();

        em.close();

        MonsterDAO monsterDAO = context.getBean("jpaMonsterDAO", JpaMonsterDAO.class);

        List<Monster> inDarkCave = monsterDAO.findByQuest(darkCave);
        assertTrue(inDarkCave.size() == 2);

        List<String> names = new ArrayList<>();

        for (Monster m : inDarkCave) {
            names.add(m.getName());
        }

        assertTrue(names.contains("Fire Ogre"));
        assertTrue(names.contains("Efreet"));

        List<Monster> inHappyLands = monsterDAO.findByQuest(happyLands);
        assertTrue(inHappyLands.size() == 0);
    }
}
