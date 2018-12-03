package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.ServiceConfiguration;
import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.SkillDto;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.SkillService;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.facade.SkillFacadeImpl;

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
public class SkillFacadeImplTest {

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private SkillService service;

    @InjectMocks
    private SkillFacadeImpl facade;

    private Skill skillEntity;

    private Affinity affinityEntity;

    private SkillDto skillDto;

    private AffinityDto affinityDto;

    private List<Skill> entityList;

    private List<SkillDto> dtoList;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        List<Affinity> affinities = new ArrayList<>();
        List<AffinityDto> affinityDtos = new ArrayList<>();

        affinityEntity = new Affinity("ice", 1);
        affinityDto = new AffinityDto();
        affinityDto.setId(new Long("17"));
        affinityDto.setName("ice");
        affinityDto.setLevel(1);

        affinities.add(affinityEntity);
        affinityDtos.add(affinityDto);

        skillEntity = new Skill("Ice Bolt", affinities, 4);
        skillDto = new SkillDto();
        skillDto.setId(new Long("1"));
        skillDto.setName("Forest of Woes");
        skillDto.setAffinities(affinityDtos);
        skillDto.setBaseDamage(4);

        when(beanMappingService.mapTo(skillDto, Skill.class))
            .thenReturn(skillEntity);
        when(beanMappingService.mapTo(skillEntity, SkillDto.class))
            .thenReturn(skillDto);

        when(beanMappingService.mapTo(affinityDto, Affinity.class))
            .thenReturn(affinityEntity);
        when(beanMappingService.mapTo(affinityEntity, AffinityDto.class))
            .thenReturn(affinityDto);


        entityList = new ArrayList<>();
        entityList.add(skillEntity);

        dtoList = new ArrayList<>();
        dtoList.add(skillDto);

        when(beanMappingService.mapTo(entityList, SkillDto.class))
            .thenReturn(dtoList);
    }

    @Test
    public void findAll() {
        when(service.findAll()).thenReturn(entityList);

        assertEquals(facade.findAll(), dtoList);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        when(service.findById(skillDto.getId()))
            .thenReturn(skillEntity);

        assertEquals(facade.findById(skillDto.getId()), skillDto);
    }

    @Test
    public void findByIdNotFound() throws EntityNotFoundException {
        when(service.findById(skillDto.getId()))
            .thenThrow(new EntityNotFoundException());
        assertNull(facade.findById(skillDto.getId()));
    }

    @Test
    public void update() throws EntityValidationException {
        when(service.update(skillEntity))
            .thenReturn(skillEntity);

        assertEquals(facade.update(skillDto), skillDto);
    }

    @Test
    public void updateInvalid() throws EntityValidationException {
        when(service.update(skillEntity))
            .thenThrow(new EntityValidationException());

        assertNull(facade.update(skillDto));
    }

    @Test
    public void save() throws EntityValidationException {
        when(service.save(skillEntity))
            .thenReturn(true);

        assertTrue(facade.save(skillDto));

        when(service.save(skillEntity))
            .thenReturn(false);

        assertFalse(facade.save(skillDto));
    }

    @Test
    public void saveInvalid() throws EntityValidationException {
        when(service.save(skillEntity))
            .thenThrow(new EntityValidationException());

        assertFalse(facade.save(skillDto));
    }

    @Test
    public void delete() {
        when(service.delete(skillEntity))
            .thenReturn(true);

        assertTrue(facade.delete(skillDto));

        when(service.delete(skillEntity))
            .thenReturn(false);

        assertFalse(facade.delete(skillDto));
    }

    @Test
    public void deleteById() {
        when(service.deleteById(skillDto.getId()))
            .thenReturn(true);

        assertTrue(facade.deleteById(skillDto.getId()));

        when(service.deleteById(skillDto.getId()))
            .thenReturn(false);

        assertFalse(facade.deleteById(skillDto.getId()));
    }

    @Test
    public void findByName() throws EntityNotFoundException {
        when(service.findByName(skillDto.getName()))
            .thenReturn(entityList);

        assertEquals(facade.findByName(skillDto.getName()), dtoList);

        when(service.findByName("Unlucky Luke"))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findByName("Unlucky Luke"));
    }

    @Test
    public void findWithAffinity() throws EntityNotFoundException {
        when(service.findWithAffinity(affinityEntity))
            .thenReturn(entityList);

        assertEquals(facade.findWithAffinity(affinityDto), dtoList);

        AffinityDto notFound = new AffinityDto();

        when(service.findWithAffinity(affinityEntity))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findWithAffinity(affinityDto));
    }

    @Test
    public void findWithBaseDamage() throws EntityNotFoundException {
        when(service.findWithBaseDamage(4)).thenReturn(entityList);

        assertEquals(facade.findWithBaseDamage(4), dtoList);

        when(service.findWithBaseDamage(2))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findWithBaseDamage(2));
    }

    @Test
    public void findWithGreaterBaseDamage() throws EntityNotFoundException {
        when(service.findWithGreaterBaseDamage(3)).thenReturn(entityList);

        assertEquals(facade.findWithGreaterBaseDamage(3), dtoList);

        when(service.findWithGreaterBaseDamage(100))
            .thenThrow(new EntityNotFoundException());

        assertNull(facade.findWithGreaterBaseDamage(100));
    }
}