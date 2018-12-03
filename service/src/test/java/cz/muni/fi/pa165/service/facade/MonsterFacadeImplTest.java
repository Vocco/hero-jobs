package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.ServiceConfiguration;
import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestMonsterDto;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.TestUtils;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.MonsterService;
import org.dozer.Mapper;
import org.junit.Before;
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
import static org.mockito.Mockito.when;


/**
 * @author Michal Pavúk
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MonsterFacadeImplTest {

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private MonsterService service;

    @InjectMocks
    private MonsterFacadeImpl facade;

    private Monster entity;

    private MonsterDto dto;

    private Affinity strength;
    private AffinityDto strengthDto;

    private Affinity weakness;
    private AffinityDto weaknessDto;

    private List<Monster> entityList;

    private List<MonsterDto> dtoList;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        strength = new Affinity("Darkness", 2);
        strengthDto = new AffinityDto();
        strengthDto.setName("Darkness");
        strengthDto.setLevel(2);

        weakness = new Affinity("Light", 2);
        weaknessDto = new AffinityDto();
        weaknessDto.setName("Light");
        weaknessDto.setLevel(2);

        entity = new Monster("Evil Mage", 200, 20, "Regular",
                Collections.singletonList(strength),
                Collections.singletonList(weakness));
        TestUtils.setId(entity, 1L);
        dto = new MonsterDto();
        dto.setId(1L);
        dto.setName("Evil Mage");
        dto.setHitpoints(200);
        dto.setDamage(20);
        dto.setSize("Regular");

        dto.setStrengths(Collections.singletonList(strengthDto));
        dto.setWeaknesses(Collections.singletonList(weaknessDto));

        when(beanMappingService.mapTo(dto, Monster.class))
                .thenReturn(entity);
        when(beanMappingService.mapTo(entity, MonsterDto.class))
                .thenReturn(dto);

        entityList = Collections.singletonList(entity);
        dtoList = Collections.singletonList(dto);

        when(beanMappingService.mapTo(entityList, MonsterDto.class))
                .thenReturn(dtoList);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        when(service.findById(dto.getId())).thenReturn(entity);
        assertEquals(facade.findById(dto.getId()), dto);
    }

    @Test
    public void findByIdNotFound() {
        assertNull(facade.findById(dto.getId()));
    }

    @Test
    public void update() throws EntityValidationException {
        when(service.update(entity)).thenReturn(entity);
        assertEquals(facade.update(dto), dto);
    }

    @Test
    public void updateInvalid() throws EntityValidationException {
        when(service.update(entity)).thenThrow(new EntityValidationException());
        assertNull(facade.update(dto));
    }

    @Test
    public void save() throws EntityValidationException {
        when(service.save(entity)).thenReturn(true);
        assertTrue(facade.save(dto));

        when(service.save(entity)).thenReturn(false);
        assertFalse(facade.save(dto));
    }

    @Test
    public void saveInvalid() throws EntityValidationException {
        when(service.save(entity)).thenThrow(new EntityValidationException());
        assertFalse(facade.save(dto));
    }

    @Test
    public void delete() {
        when(service.delete(entity)).thenReturn(true);
        assertTrue(facade.delete(dto));

        when(service.delete(entity)).thenReturn(false);
        assertFalse(facade.delete(dto));
    }

    @Test
    public void deleteById() {
        when(service.deleteById(dto.getId())).thenReturn(true);
        assertTrue(facade.deleteById(dto.getId()));

        when(service.deleteById(dto.getId())).thenReturn(false);
        assertFalse(facade.deleteById(dto.getId()));
    }

    @Test
    public void findWithStrength() {
        when(service.findWithStrength(strength)).thenReturn(entityList);
        assertEquals(facade.findWithStrength(strengthDto), dtoList);
    }

    @Test
    public void findWithWeakness() {
        when(service.findWithWeakness(weakness)).thenReturn(entityList);
        assertEquals(facade.findWithWeakness(weaknessDto), dtoList);
    }

    @Test
    public void findWithSize() {
        when(service.findWithSize("Regular")).thenReturn(entityList);
        assertEquals(facade.findWithSize("Regular"), dtoList);
    }

    @Test
    public void findByQuest() {
        Quest quest = new Quest("quest", "location", 100, 1);
        QuestDto questDto = new QuestDto();
        questDto.setId(1L);
        questDto.setName("quest");
        questDto.setLocation("location");
        questDto.setReward(100);
        questDto.setHeroLimit(1);
        when(service.findByQuest(quest)).thenReturn(entityList);
        assertEquals(facade.findByQuest(questDto), dtoList);
    }

    @Test
    public void findAllWeaknessesOfMonstersOnQuest() throws NoSuchFieldException, IllegalAccessException {
        Quest quest = new Quest("quest", "location", 100, 1);
        TestUtils.setId(quest, 1L);
        quest.addMonster(entity);

        QuestDto questDto = new QuestDto();
        questDto.setId(1L);
        questDto.setName("quest");
        questDto.setLocation("location");
        questDto.setReward(100);
        questDto.setHeroLimit(1);

        QuestMonsterDto questMonsterDto = new QuestMonsterDto();
        questMonsterDto.setQuest(questDto);
        questMonsterDto.setMonster(dto);
        questMonsterDto.setMonsterCount(5);

        questDto.setMonsters(Collections.singletonList(questMonsterDto));

        when(service.findAllWeaknessesOfMonstersOnQuest(quest)).thenReturn(entityList.get(0).getWeaknesses());
        assertEquals(facade.findByQuest(questDto), dtoList);
    }

    @Test
    public void findAllStrengthsOfMonstersOnQuest() throws NoSuchFieldException, IllegalAccessException {
        Quest quest = new Quest("quest", "location", 100, 1);
        TestUtils.setId(quest, 1L);
        quest.addMonster(entity);

        QuestDto questDto = new QuestDto();
        questDto.setId(1L);
        questDto.setName("quest");
        questDto.setLocation("location");
        questDto.setReward(100);
        questDto.setHeroLimit(1);

        QuestMonsterDto questMonsterDto = new QuestMonsterDto();
        questMonsterDto.setQuest(questDto);
        questMonsterDto.setMonster(dto);
        questMonsterDto.setMonsterCount(5);

        questDto.setMonsters(Collections.singletonList(questMonsterDto));

        when(service.findAllWeaknessesOfMonstersOnQuest(quest)).thenReturn(entityList.get(0).getStrengths());
        assertEquals(facade.findByQuest(questDto), dtoList);
    }
}