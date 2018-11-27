package cz.muni.fi.pa165.heroes.dao.impl;


import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit tests for Monster JPA DAO implementation.
 *
 * @author Vojtech Krajnansky (423126)
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JpaMonsterDAOTest {

    @Autowired
    private MonsterDAO monsterDAO;

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
        List<Monster> allMonsters = monsterDAO.findAll();

        assertEquals(3, allMonsters.size());

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
        Monster fo = monsterDAO.findById(fireOgre.getId());
        Monster io = monsterDAO.findById(iceOgre.getId());
        Monster ef = monsterDAO.findById(efreet.getId());
        Monster nonexistent = (Monster) monsterDAO.findById(new Long("-1"));

        assertEquals(fo.getName(), fireOgre.getName());
        assertEquals(io.getName(), iceOgre.getName());
        assertEquals(ef.getName(), efreet.getName());
        assertNull(nonexistent);
    }

    /**
     * Test for the update method.
     */
    @Test
    public void testUpdate() {
        fireOgre.setName("New Fire Ogre");

        monsterDAO.update(fireOgre);

        Monster found = (Monster) monsterDAO.findById(fireOgre.getId());
        assertEquals("New Fire Ogre", found.getName());
    }

    /**
     * Test for the save method.
     */
    @Test
    public void testSave() {
        Monster newMonster = new Monster("New Guy", 110, 10, "small", new ArrayList<Affinity>(), new ArrayList<Affinity>());

        monsterDAO.save(newMonster);

        assertEquals(4, monsterDAO.findAll().size());
        Monster found = (Monster) monsterDAO.findById(newMonster.getId());
        assertEquals("New Guy", found.getName());
    }

    /**
     * Test for the delete method.
     */
    @Test
    public void testDelete() {
      assertTrue(monsterDAO.delete(fireOgre));
        assertEquals(2, monsterDAO.findAll().size());
        assertNull(monsterDAO.findById(fireOgre.getId()));
    }

    /**
     * Test for the deleteById method.
     */
    @Test
    public void testDeleteById() {
      assertTrue(monsterDAO.deleteById(fireOgre.getId()));
        assertEquals(2, monsterDAO.findAll().size());
        assertNull(monsterDAO.findById(fireOgre.getId()));
    }

    /**
     * Test for the findWithStrength method.
     */
    @Test
    public void testFindWithStrength() {
        List<Monster> withFire = monsterDAO.findWithStrength(fire);
        assertEquals(2, withFire.size());

        List<Monster> withIce = monsterDAO.findWithStrength(ice);
        assertEquals(1, withIce.size());

        assertEquals("Ice Ogre", withIce.get(0).getName());

        List<Monster> withWater = monsterDAO.findWithStrength(water);
        assertEquals(0, withWater.size());
    }

    /**
     * Test findWithWeakness method.
     */
    @Test
    public void testFindWithWeakness() {
      List<Monster> withLightLow = monsterDAO.findWithWeakness(lightLow);
        assertEquals(2, withLightLow.size());

      List<Monster> withWater = monsterDAO.findWithWeakness(water);
        assertEquals(1, withWater.size());

        assertEquals("Efreet", withWater.get(0).getName());

      List<Monster> withLightHigh = monsterDAO.findWithWeakness(lightHigh);
        assertEquals(1, withLightHigh.size());

        assertEquals("Fire Ogre", withLightHigh.get(0).getName());

      List<Monster> withIce = monsterDAO.findWithWeakness(ice);
        assertEquals(0, withIce.size());
    }

    /**
     * Test findWithSize method.
     */
    @Test
    public void testFindWithSize() {
        List<Monster> small = monsterDAO.findWithSize("small");
        List<Monster> medium = monsterDAO.findWithSize("medium");
        List<Monster> large = monsterDAO.findWithSize("large");

        assertEquals(0, large.size());
        assertEquals(1, small.size());
        assertEquals("Efreet", small.get(0).getName());
        assertEquals(2, medium.size());
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

        List<Monster> inDarkCave = monsterDAO.findByQuest(darkCave);
        assertEquals(2, inDarkCave.size());

        List<String> names = new ArrayList<>();

        for (Monster m : inDarkCave) {
            names.add(m.getName());
        }

        assertTrue(names.contains("Fire Ogre"));
        assertTrue(names.contains("Efreet"));

        List<Monster> inHappyLands = monsterDAO.findByQuest(happyLands);
        assertEquals(0, inHappyLands.size());
    }
}
