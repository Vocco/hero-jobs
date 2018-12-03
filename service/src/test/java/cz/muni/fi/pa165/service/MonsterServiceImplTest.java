package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/*
 * @author Michal Pavuk
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MonsterServiceImplTest {

    @Mock
    private MonsterDAO dao;

    @InjectMocks
    private MonsterServiceImpl service;

    private Monster entity = createMonster();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public MonsterServiceImplTest() throws NoSuchFieldException, IllegalAccessException {
    }

    private static Monster createMonster() throws NoSuchFieldException, IllegalAccessException {
        Monster m = new Monster("name", 100, 10, "big",
                Collections.singletonList(new Affinity("s", 1)),
                Collections.singletonList(new Affinity("w", 1)));

        // It's better to use Reflection than to introduce an id mutator. As id should be immutable for the application.
        Class<? extends Monster> cl = m.getClass();
        Field id = cl.getSuperclass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(m, 1L);
        id.setAccessible(false);

        return m;
    }

    @Test
    public void findById() {
        when(dao.findById(entity.getId())).thenReturn(entity);
        Monster result = null;
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
        List<Monster> result = service.findAll();

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
    public void findWithStrength() {
        Affinity strength = new Affinity("s", 1);
        when(dao.findWithStrength(strength)).thenReturn(Collections.singletonList(entity));
        List<Monster> result = service.findWithStrength(strength);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithWeakness() {
        Affinity weakness = new Affinity("w", 1);
        when(dao.findWithWeakness(weakness)).thenReturn(Collections.singletonList(entity));
        List<Monster> result = service.findWithWeakness(weakness);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithSize() {
        when(dao.findWithSize(entity.getSize())).thenReturn(Collections.singletonList(entity));
        List<Monster> result = service.findWithSize(entity.getSize());

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findByQuest() {
        Quest quest = new Quest("quest", "location", 1, 1);
        when(dao.findByQuest(quest)).thenReturn(Collections.singletonList(entity));
        List<Monster> result = service.findByQuest(quest);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findAllWeaknessesOfMonstersOnQuest() {
        Quest quest = new Quest("quest", "location", 1, 1);
        when(dao.findByQuest(quest)).thenReturn(Collections.singletonList(entity));
        List<Affinity> result = service.findAllWeaknessesOfMonstersOnQuest(quest);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.containsAll(entity.getWeaknesses()));
    }

    @Test
    public void findAllStrengthsOfMonstersOnQuest() {
        Quest quest = new Quest("quest", "location", 1, 1);
        when(dao.findByQuest(quest)).thenReturn(Collections.singletonList(entity));
        List<Affinity> result = service.findAllStrengthsOfMonstersOnQuest(quest);

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.containsAll(entity.getStrengths()));
    }
}