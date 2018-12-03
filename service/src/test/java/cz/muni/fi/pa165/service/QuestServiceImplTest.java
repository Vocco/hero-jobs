package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.QuestDAO;
import cz.muni.fi.pa165.heroes.entity.*;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuestServiceImplTest {

    @Mock
    private QuestDAO dao;

    @InjectMocks
    private QuestServiceImpl service;

    private Quest entity = createQuest();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public QuestServiceImplTest() throws NoSuchFieldException, IllegalAccessException {
    }

    @BeforeClass
    public static void setUpClass() throws NoSuchFieldException, IllegalAccessException {
    }

    private static Quest createQuest() throws NoSuchFieldException, IllegalAccessException {
        Quest q = new Quest("quest", "location", 100, 1);

        TestUtils.setId(q, 1L);

        return q;
    }

    @Test
    public void findById() {
        when(dao.findById(entity.getId())).thenReturn(entity);
        Quest result = null;
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
        List<Quest> result = service.findAll();

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
    public void findByState() {
        when(dao.findByState(QuestState.NEW)).thenReturn(Collections.singletonList(entity));
        List<Quest> result = null;
        try {
            result = service.findByState(entity.getQuestState());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findByLocation() {
        when(dao.findByLocation(entity.getLocation())).thenReturn(Collections.singletonList(entity));
        List<Quest> result = null;
        try {
            result = service.findByLocation(entity.getLocation());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findByMonster() {
        Monster monster = new Monster("name", 100, 10, "t",
                Collections.singletonList(new Affinity("s", 1)),
                Collections.singletonList(new Affinity("w", 1)));
        when(dao.findByMonster(monster)).thenReturn(Collections.singletonList(entity));
        List<Quest> result = null;
        try {
            result = service.findByMonster(monster);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findByAssignedQuest() {
        Hero hero = new Hero("Beowulf", 300, 20, 142, 10, 7, 2,
                Collections.singletonList(new Skill("n", Collections.singletonList(new Affinity("w", 2)), 10)));
        when(dao.findByAssignedHero(hero)).thenReturn(Collections.singletonList(entity));
        List<Quest> result = null;
        try {
            result = service.findByAssignedHero(hero);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }
}