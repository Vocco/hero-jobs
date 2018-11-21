package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Test suite for HeroDAO
 *
 * @author Michal Pavúk
 */
@Transactional
public class JpaHeroDAOTest {

    private ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");

    private Hero warrior;
    private Hero rogue;
    private Hero mage;

    private Quest mageQuest;

    private Skill heal;

    private EntityManager em;

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Affinity fire = new Affinity("Fire", 2);
        Skill fireSlash = new Skill("Fire Slash", Arrays.asList(fire), 30);

        Affinity light = new Affinity("Light", 4);
        Affinity destruction = new Affinity("Destruction", 2);
        Skill holyBomb = new Skill("Holy Hand Grenade", Arrays.asList(light, destruction), 100);

        warrior = new Hero("Beowulf", 300, 20, 142, 10, 7, 2, Arrays.asList(fireSlash, holyBomb));

        Affinity shadow = new Affinity("Shadow", 3);
        Affinity alteration = new Affinity("Alteration", 4);
        Skill decoy = new Skill("Decoy", Arrays.asList(shadow, alteration), 35);

        Affinity metal = new Affinity("Metal", 2);
        Skill knives = new Skill("Thousand Knives", Arrays.asList(metal, destruction), 110);

        rogue = new Hero("Cicero", 200, 17, 321, 5, 9, 3, Arrays.asList(decoy, knives));
        rogue.setAlive(false);

        Affinity earth = new Affinity("Earth", 1);
        heal = new Skill("Heal", Arrays.asList(light, earth), -50);

        Affinity ice = new Affinity("Ice", 4);
        Affinity destruction1 = new Affinity("Destruction", 1);
        Skill spike = new Skill("Ice Spike", Arrays.asList(ice, destruction1), 50);

        mage = new Hero("Merlin", 150, 14, 193, 3, 4, 9, Arrays.asList(heal, spike));

        mageQuest = new Quest("Advanced Magic Training", "Wizard Academy", 100, 1);
        mage.setQuest(mageQuest);

        em.persist(fire);
        em.persist(light);
        em.persist(destruction);
        em.persist(destruction1);
        em.persist(shadow);
        em.persist(alteration);
        em.persist(metal);
        em.persist(earth);
        em.persist(ice);

        em.persist(fireSlash);
        em.persist(holyBomb);
        em.persist(decoy);
        em.persist(knives);
        em.persist(heal);
        em.persist(spike);

        em.persist(mageQuest);

        em.persist(warrior);
        em.persist(rogue);
        em.persist(mage);

        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @After
    public void tearDown() {
        em.close();
    }


    @Test
    @Transactional
    public void testFindAll() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> heroes = heroDAO.findAll();

        assertEquals(3, heroes.size());

        Set<String> names = new HashSet<>(Arrays.asList("Beowulf", "Cicero", "Merlin"));
        assertTrue(heroes.stream().map(Hero::getName).collect(Collectors.toSet()).containsAll(names));
    }

    /**
     * Test for the findById method.
     */
    @Test
    @Transactional
    public void testFindById() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        Hero war = heroDAO.findById(warrior.getId());
        Hero mag = heroDAO.findById(mage.getId());
        Hero invalid = heroDAO.findById(-1L);

        assertEquals(war.getName(), warrior.getName());
        assertEquals(mag.getGold(), mage.getGold());
        assertNull(invalid);
    }

    /**
     * Test for the update method.
     */
    @Test
    @Transactional
    public void testUpdate() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        warrior.setName("Dovahkin");

        heroDAO.update(warrior);

        Hero found = heroDAO.findById(warrior.getId());
        assertEquals("Dovahkin", found.getName());
    }

    /**
     * Test for the update method.
     */
    @Test
    @Transactional
    public void testSave() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        Hero hunter = new Hero("Gabriel van Hellsing", 250, 20, 563, 6, 6, 6, new ArrayList<Skill>());

        heroDAO.save(hunter);

        Hero found = heroDAO.findById(hunter.getId());
        assertEquals("Gabriel van Hellsing", found.getName());
        assertEquals(563, found.getGold());
    }

    @Test
    @Transactional
    public void testDelete() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);

        assertTrue(heroDAO.delete(rogue));
        assertEquals(2, heroDAO.findAll().size());
        assertNull(heroDAO.findById(rogue.getId()));
    }

    @Test
    @Transactional
    public void testDeleteById() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);

        assertTrue(heroDAO.deleteById(rogue.getId()));
        assertEquals(2, heroDAO.findAll().size());
        assertNull(heroDAO.findById(rogue.getId()));
    }

    @Test
    public void testFindAvailable() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> available = heroDAO.findAvailable();

        assertEquals(1, available.size());
        assertEquals("Beowulf", available.get(0).getName());
    }

    @Test
    @Transactional
    public void testFindAlive() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> alive = heroDAO.findAlive();

        assertEquals(2, alive.size());
        assertFalse(alive.contains(rogue));

        Set<String> names = new HashSet<>(Arrays.asList("Beowulf", "Merlin"));
        assertTrue(alive.stream().map(Hero::getName).collect(Collectors.toSet()).containsAll(names));
    }

    @Test
    public void testFindByName() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> found = heroDAO.findByName("Beowulf");

        assertEquals(1, found.size());
        assertEquals(warrior.getName(), found.get(0).getName());
        assertEquals(warrior.getAgility(), found.get(0).getAgility());
    }

    @Test
    public void testFindDead() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> dead = heroDAO.findDead();

        assertEquals(1, dead.size());
        assertFalse(rogue.isAlive());
        assertEquals(rogue.getName(), dead.get(0).getName());
        assertEquals(rogue.getHitpoints(), dead.get(0).getHitpoints());
    }

    @Test
    public void testFindOnQuest() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> onQuest = heroDAO.findOnQuest(mageQuest);

        assertEquals(1, onQuest.size());
        assertEquals("Merlin", onQuest.get(0).getName());
    }

    @Test
    public void testFindWithSkill() {
        HeroDAO heroDAO = context.getBean("jpaHeroDAO", JpaHeroDAO.class);
        List<Hero> found = heroDAO.findWithSkill(heal);

        assertEquals(1, found.size());
        Hibernate.initialize(found.get(0).getSkills());
        assertEquals("Heal", found.get(0).getSkills().get(0).getName());
    }
}
