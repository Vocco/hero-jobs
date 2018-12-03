package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.ServiceConfiguration;
import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestStateDto;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.QuestService;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.facade.QuestFacadeImpl;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * @author Vojtech Krajnansky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestFacadeImplTest {

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private QuestService service;

    @InjectMocks
    private QuestFacadeImpl facade;

    private Quest questEntity;

    private QuestDto questDto;

    private List<Quest> entityList;

    private List<QuestDto> dtoList;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        questEntity = new Quest("Forest of Woes", "The Dark Forest", 20, 1);
        questDto = new QuestDto();
        questDto.setId(new Long("1"));
        questDto.setName("Forest of Woes");
        questDto.setLocation("The Dark Forest");
        questDto.setReward(20);
        questDto.setHeroLimit(1);

        when(beanMappingService.mapTo(QuestStateDto.NEW, QuestState.class))
            .thenReturn(QuestState.NEW);
        when(beanMappingService.mapTo(QuestStateDto.ONGOING, QuestState.class))
            .thenReturn(QuestState.ONGOING);

        when(beanMappingService.mapTo(questDto, Quest.class))
            .thenReturn(questEntity);
        when(beanMappingService.mapTo(questEntity, QuestDto.class))
            .thenReturn(questDto);

        entityList = new ArrayList<>();
        entityList.add(questEntity);

        dtoList = new ArrayList<>();
        dtoList.add(questDto);

        when(beanMappingService.mapTo(entityList, QuestDto.class))
            .thenReturn(dtoList);
    }

    @Test
    public void findAll() {
        when(service.findAll()).thenReturn(entityList);

        assertEquals(facade.findAll(), dtoList);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        when(service.findById(questDto.getId()))
            .thenReturn(questEntity);

        assertEquals(facade.findById(questDto.getId()), questDto);
    }

    @Test
    public void findByIdNotFound() throws EntityNotFoundException {
        when(service.findById(questDto.getId()))
            .thenThrow(new EntityNotFoundException());
        assertNull(facade.findById(questDto.getId()));
    }

    @Test
    public void update() throws EntityValidationException {
        when(service.update(questEntity))
            .thenReturn(questEntity);

        assertEquals(facade.update(questDto), questDto);
    }

    @Test
    public void updateInvalid() throws EntityValidationException {
        when(service.update(questEntity))
            .thenThrow(new EntityValidationException());

        assertNull(facade.update(questDto));
    }

    @Test
    public void save() throws EntityValidationException {
        when(service.save(questEntity))
            .thenReturn(true);

        assertTrue(facade.save(questDto));

        when(service.save(questEntity))
            .thenReturn(false);

        assertFalse(facade.save(questDto));
    }

    @Test
    public void saveInvalid() throws EntityValidationException {
        when(service.save(questEntity))
            .thenThrow(new EntityValidationException());

        assertFalse(facade.save(questDto));
    }

    @Test
    public void delete() {
        when(service.delete(questEntity))
            .thenReturn(true);

        assertTrue(facade.delete(questDto));

        when(service.delete(questEntity))
            .thenReturn(false);

        assertFalse(facade.delete(questDto));
    }

    @Test
    public void deleteById() {
        when(service.deleteById(questDto.getId()))
            .thenReturn(true);

        assertTrue(facade.deleteById(questDto.getId()));

        when(service.deleteById(questDto.getId()))
            .thenReturn(false);

        assertFalse(facade.deleteById(questDto.getId()));
    }

    @Test
    public void findByState() throws EntityNotFoundException {
        when(service.findByState(QuestState.NEW))
            .thenReturn(entityList);

        assertEquals(facade.findByState(QuestStateDto.NEW), dtoList);

        when(service.findByState(QuestState.ONGOING))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findByState(QuestStateDto.ONGOING));
    }

    @Test
    public void findByLocation() throws EntityNotFoundException {
        when(service.findByLocation("Dark Forest"))
            .thenReturn(entityList);

        assertEquals(facade.findByLocation("Dark Forest"), dtoList);

        when(service.findByLocation("Happy Unicorn Meadows"))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findByLocation("Happy Unicorn Meadows"));
    }

    @Test public void findByMonster() throws EntityNotFoundException {
        Monster mEntity = new Monster(
            "Direwolf", 4, 2, "small", new ArrayList<>(), new ArrayList<>()
        );

        MonsterDto mDto = new MonsterDto();
        mDto.setId(new Long("100"));
        mDto.setName("Direwolf");
        mDto.setHitpoints(4);
        mDto.setDamage(2);
        mDto.setSize("small");
        mDto.setWeaknesses(new ArrayList<>());
        mDto.setStrengths(new ArrayList<>());

        when(beanMappingService.mapTo(mDto, Monster.class))
            .thenReturn(mEntity);

        when(service.findByMonster(mEntity)).thenReturn(entityList);

        assertEquals(facade.findByMonster(mDto), dtoList);

        when(service.findByMonster(mEntity))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findByMonster(mDto));
    }

    @Test public void findByAssignedHero() throws EntityNotFoundException {
        Hero hEntity = new Hero(
            "Awesome Guy", 100, 100, 10000, 10, 10, 10, new ArrayList<>()
        );

        HeroDto hDto = new HeroDto();
        hDto.setId(new Long("10"));
        hDto.setName("Awesome Guy");
        hDto.setHitpoints(100);
        hDto.setDamage(100);
        hDto.setGold(10000);
        hDto.setMight(10);
        hDto.setAgility(10);
        hDto.setMagic(10);
        hDto.setSkills(new ArrayList<>());

        when(beanMappingService.mapTo(hDto, Hero.class))
            .thenReturn(hEntity);

        when(service.findByAssignedHero(hEntity)).thenReturn(entityList);

        assertEquals(facade.findByAssignedHero(hDto), dtoList);

        when(service.findByAssignedHero(hEntity))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findByAssignedHero(hDto));
    }
}
