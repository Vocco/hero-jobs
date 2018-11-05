package cz.muni.fi.pa165.heroes.dao.impl;


import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.QuestDAO;
import cz.muni.fi.pa165.heroes.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Quest JPA DAO implementation.
 *
 * @author Jakub Strmen
 */
@Transactional
public class JpaQuestDAOTest {

    private ApplicationContext context = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");


    private Quest easyQuest;
    private Quest hardQuest;

    private Hero warrior;

    private Monster iceOgre;

    //before
    @Before
    public void before(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Affinity fire = new Affinity("Fire", 2);
        Affinity ice = new Affinity("Ice", 3);

        Skill fireSlash = new Skill("Fire Slash", Arrays.asList(fire), 30);

        Affinity light = new Affinity("Light", 4);
        Affinity destruction = new Affinity("Destruction", 2);
        Skill holyBomb = new Skill("Holy Hand Grenade", Arrays.asList(light, destruction), 100);
        warrior = new Hero("Beowulf", 300, 20, 142, 10, 7, 2, Arrays.asList(fireSlash, holyBomb));

        iceOgre = new Monster("Ice Ogre", 150, 30, "medium", Arrays.asList(ice), Arrays.asList(fire));

        easyQuest = new Quest("Easy quest", "Wonderland", 500, 2);
        hardQuest = new Quest("Hard quest", "Magic land", 2000, 8);


        em.persist(fire);
        em.persist(ice);
        em.persist(fireSlash);
        em.persist(light);
        em.persist(destruction);
        em.persist(holyBomb);

        em.persist(warrior);
        em.persist(iceOgre);

        em.persist(easyQuest);
        em.persist(hardQuest);

        hardQuest.addMonster(iceOgre);

        em.merge(hardQuest);

        em.flush();
        em.getTransaction().commit();

        em.close();
    }

    @Test
    public void testFindAll(){
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        List<Quest> allQuests = questDAO.findAll();

        assertEquals(2, allQuests.size());

        List<String> names = new ArrayList<>();

        for (Quest q : allQuests) {
            names.add(q.getName());
        }

        assertTrue(names.contains("Easy quest"));
        assertTrue(names.contains("Hard quest"));
    }


    @Test
    public void testFindById(){
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        Quest wantedQuest1 = questDAO.findById(easyQuest.getId());
        Quest wantedQuest2 = questDAO.findById(hardQuest.getId());


        assertEquals("Easy quest", wantedQuest1.getName());
        assertEquals("Hard quest", wantedQuest2.getName());
    }

    @Test
    public void testUpdate() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        easyQuest.setName("Maybe little harder than expected");

        questDAO.update(easyQuest);

        Quest updatedQuest = questDAO.findById(easyQuest.getId());
        assertEquals("Maybe little harder than expected", updatedQuest.getName());
    }

    @Test
    public void testSave() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        Quest quest = new Quest("testQuest", "Magic land", 1000, 4);

        questDAO.save(quest);

        assertTrue(questDAO.findAll().size() == 3);
        Quest found = (Quest) questDAO.findById(quest.getId());
        assertTrue(found.getName().equals("testQuest"));
    }

    @Test
    public void testDelete() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);

        assertTrue(questDAO.delete(easyQuest));
        assertTrue(questDAO.findAll().size() == 1);
        assertTrue(questDAO.findById(easyQuest.getId()) == null);
    }

    @Test
    public void testDeleteById() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);

        assertTrue(questDAO.deleteById(easyQuest.getId()));
        assertTrue(questDAO.findAll().size() == 1);
        assertTrue(questDAO.findById(easyQuest.getId()) == null);
    }

    @Test
    public void testfindByState() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);

        List<Quest> allQuests = questDAO.findByState(QuestState.NEW);

        assertEquals(2, allQuests.size());

        easyQuest.setHeroLimit(1);
        easyQuest.addHero(warrior);

        questDAO.update(easyQuest);

        List<Quest> ongoingQuests= questDAO.findByState(QuestState.ONGOING);

        assertEquals(1, ongoingQuests.size());
    }

    @Test
    public void testfindByLocation() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        List<Quest> byLocation = questDAO.findByLocation("Wonderland");

        assertEquals(1, byLocation.size());

        hardQuest.setLocation("Wonderland");
        questDAO.update(hardQuest);

        byLocation.clear();
        byLocation = questDAO.findByLocation("Wonderland");

        assertEquals(2, byLocation.size());
    }

    @Test
    public void testfindByMonster() {
        // TODO - fix questDAO.findByMonster
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        List<Quest> byMonster = questDAO.findByMonster(iceOgre);

        assertEquals(1, byMonster.size());

        easyQuest.addMonster(iceOgre);
        questDAO.update(easyQuest);

        byMonster.clear();
        byMonster = questDAO.findByMonster(iceOgre);

        assertEquals(2, byMonster.size());
    }

    // findByAssignedHero
    @Test
    public void testfindByHero() {
        QuestDAO questDAO = context.getBean("jpaQuestDAO", JpaQuestDAO.class);
        List<Quest> byAssignedHero = questDAO.findByAssignedHero(warrior);

        assertEquals(0, byAssignedHero.size());

        easyQuest.setHeroLimit(1);
        easyQuest.addHero(warrior);

        questDAO.update(easyQuest);

        byAssignedHero.clear();
        byAssignedHero = questDAO.findByAssignedHero(warrior);

        assertEquals(1, byAssignedHero.size());
    }


}
