package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.entity.*;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/*
 * @author Michal Pavuk
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HeroServiceImplTest {

    @Mock
    private HeroDAO dao;

    @InjectMocks
    private HeroServiceImpl service;

    private Hero entity = createHero();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public HeroServiceImplTest() throws NoSuchFieldException, IllegalAccessException {
    }

    @BeforeClass
    public static void setUpClass() throws NoSuchFieldException, IllegalAccessException {
    }

    private static Hero createHero() throws NoSuchFieldException, IllegalAccessException {
        Skill s = new Skill("n", Collections.singletonList(new Affinity("w", 2)), 10);
        Hero h = new Hero("Beowulf", 300, 20, 142, 10, 7, 2,
                Collections.singletonList(s));

        TestUtils.setId(h, 1L);
        TestUtils.setId(s, 1L);

        return h;
    }

    @Test
    public void findById() {
        when(dao.findById(entity.getId())).thenReturn(entity);
        Hero result = null;
        try {
            result = service.findById(entity.getId());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(entity, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdError() throws EntityNotFoundException {
        service.findById(null);
    }

    @Test
    public void findAll() {
        when(dao.findAll()).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findAll();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void save() {
        try {
            service.save(entity);
        } catch (EntityValidationException e) {
            e.printStackTrace();
        }
        verify(dao, times(1)).save(entity);
    }

    @Test(expected = EntityValidationException.class)
    public void saveError() throws EntityValidationException {
        service.save(null);
    }

    @Test
    public void delete() {
        service.delete(entity);
        verify(dao, times(1)).delete(entity);
    }

    @Test
    public void deleteById() {
        service.deleteById(entity.getId());
        verify(dao, times(1)).deleteById(entity.getId());
    }


    @Test
    public void update() {
        try {
            service.update(entity);
        } catch (EntityValidationException e) {
            e.printStackTrace();
        }
        verify(dao, times(1)).update(entity);
    }

    @Test(expected = EntityValidationException.class)
    public void updateError() throws EntityValidationException {
        service.update(null);
    }

    @Test
    public void findAvailable() {
        when(dao.findAvailable()).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findAvailable();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findAlive() {
        when(dao.findAlive()).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findAlive();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findByName() {
        when(dao.findByName(entity.getName())).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findByName(entity.getName());

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findDead() {
        when(dao.findDead()).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findDead();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findOnQuest() {
        Quest quest = new Quest("quest", "location", 1, 1);
        when(dao.findOnQuest(quest)).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findOnQuest(quest);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithSkill() {
        Skill skill = new Skill("name", Collections.singletonList(new Affinity("n", 1)), 1);
        when(dao.findWithSkill(skill)).thenReturn(Collections.singletonList(entity));
        List<Hero> result = service.findWithSkill(skill);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findAllSkillsOfHeroesOnQuest() {
        Quest quest = new Quest("quest", "location", 1, 1);
        when(dao.findOnQuest(quest)).thenReturn(Collections.singletonList(entity));
        List<Skill> result = service.findAllSkillsOfHeroesOnQuest(quest);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result, entity.getSkills());
    }

    @Test
    public void rateAgainstMonsterType() {
        Monster monster = new Monster("name", 100, 10, "t",
                Collections.singletonList(new Affinity("s", 1)),
                Collections.singletonList(new Affinity("w", 1)));
        int result = service.rateAgainstMonsterType(entity, monster);

        assertTrue(result > 0);
    }
}