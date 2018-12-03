package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.SkillDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
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

public class SkillServiceImplTest {

    @Mock
    private SkillDAO dao;

    @InjectMocks
    private SkillServiceImpl service;

    private Skill entity = createSkill();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    public SkillServiceImplTest() throws NoSuchFieldException, IllegalAccessException {
    }

    @BeforeClass
    public static void setUpClass() throws NoSuchFieldException, IllegalAccessException {
    }

    private static Skill createSkill() throws NoSuchFieldException, IllegalAccessException {
        Skill s = new Skill("name", Collections.singletonList(new Affinity("aff", 1)), 10);

        TestUtils.setId(s, 1L);

        return s;
    }

    @Test
    public void findById() {
        when(dao.findById(entity.getId())).thenReturn(entity);
        Skill result = null;
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
        List<Skill> result = service.findAll();

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
    public void findByName() {
        when(dao.findByName(entity.getName())).thenReturn(Collections.singletonList(entity));
        List<Skill> result = null;
        try {
            result = service.findByName(entity.getName());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithAffinity() {
        when(dao.findWithAffinity(entity.getAffinities().get(0))).thenReturn(Collections.singletonList(entity));
        List<Skill> result = null;
        try {
            result = service.findWithAffinity(entity.getAffinities().get(0));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithBaseDamage() {
        when(dao.findWithBaseDamage(entity.getBaseDamage())).thenReturn(Collections.singletonList(entity));
        List<Skill> result = null;
        try {
            result = service.findWithBaseDamage(entity.getBaseDamage());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }

    @Test
    public void findWithGreaterBaseDamage() {
        when(dao.findWithGreaterBaseDamage(entity.getBaseDamage() - 1)).thenReturn(Collections.singletonList(entity));
        List<Skill> result = null;
        try {
            result = service.findWithGreaterBaseDamage(entity.getBaseDamage() - 1);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(entity));
    }
}