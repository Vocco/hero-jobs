package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.ServiceConfiguration;
import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.SkillDto;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.HeroService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HeroFacadeImplTest {

	@Mock
	private Mapper dozerBeanMapper;

	@Mock
	private BeanMappingService beanMappingService;

	@Mock
	private HeroService heroService;

	@InjectMocks
	private HeroFacadeImpl heroFacade;

	private Hero hero;

	private HeroDto heroDto;

	private List<Hero> heroList;

	private List<HeroDto> heroDtoList;

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Before
	public void setUp() throws Exception {
		hero = new Hero("Myspulin", 5, 10, 0, 3, 18, 14, new ArrayList<>());
		heroDto = new HeroDto();
		heroDto.setId(1L);
		heroDto.setName("Myspulin");
		heroDto.setHitpoints(5);
		heroDto.setDamage(10);
		heroDto.setGold(0);
		heroDto.setMight(3);
		heroDto.setAgility(18);
		heroDto.setMagic(14);
		heroDto.setSkills(new ArrayList<>());

		heroList = new ArrayList<>();
		heroList.add(hero);

		heroDtoList = new ArrayList<>();
		heroDtoList.add(heroDto);

		when(beanMappingService.mapTo(heroDto, Hero.class))
				.thenReturn(hero);
		when(beanMappingService.mapTo(hero, HeroDto.class))
				.thenReturn(heroDto);
		when(beanMappingService.mapTo(heroList, HeroDto.class))
				.thenReturn(heroDtoList);
		when(beanMappingService.mapTo(heroDtoList, Hero.class))
				.thenReturn(heroList);
	}

	@Test
	public void findAll() {
		when(heroService.findAll()).thenReturn(heroList);

		assertEquals(heroFacade.findAll(), heroDtoList);
	}

	@Test
	public void findById() throws EntityNotFoundException {
		when(heroService.findById(heroDto.getId())).thenReturn(hero);

		assertEquals(heroFacade.findById(heroDto.getId()), heroDto);
	}

	@Test
	public void notFoundById() throws EntityNotFoundException {
		when(heroService.findById(heroDto.getId())).thenThrow(new EntityNotFoundException());

		assertNull(heroFacade.findById(heroDto.getId()));
	}

	@Test
	public void update() throws EntityValidationException {
		when(heroService.update(hero)).thenReturn(hero);

		assertEquals(heroFacade.update(heroDto), heroDto);
	}

	@Test
	public void invalidUpdate() throws EntityValidationException {
		when(heroService.update(hero)).thenThrow(new EntityValidationException());

		assertNull(heroFacade.update(heroDto));
	}

	@Test
	public void save() throws EntityValidationException {
		when(heroService.save(hero)).thenReturn(true);

		assertTrue(heroFacade.save(heroDto));

		when(heroService.save(hero)).thenReturn(false);

		assertFalse(heroFacade.save(heroDto));
	}

	@Test
	public void invalidSave() throws EntityValidationException {
		when(heroService.save(hero)).thenThrow(new EntityValidationException());

		assertFalse(heroFacade.save(heroDto));
	}

	@Test
	public void delete() {
		when(heroService.delete(hero)).thenReturn(true);

		assertTrue(heroFacade.delete(heroDto));

		when(heroService.delete(hero)).thenReturn(false);

		assertFalse(heroFacade.delete(heroDto));
	}

	@Test
	public void deleteById() {
		when(heroService.deleteById(heroDto.getId())).thenReturn(true);

		assertTrue(heroFacade.deleteById(heroDto.getId()));

		when(heroService.deleteById(heroDto.getId())).thenReturn(false);

		assertFalse(heroFacade.deleteById(heroDto.getId()));
	}

	@Test
	public void findAvailable() {
		when(heroService.findAvailable()).thenReturn(heroList);

		assertEquals(heroFacade.findAvailable(), heroDtoList);
	}

	@Test
	public void findAlive() {
		when(heroService.findAlive()).thenReturn(heroList);

		assertEquals(heroFacade.findAlive(), heroDtoList);
	}

	@Test
	public void findByName() {
		when(heroService.findByName("Myspulin")).thenReturn(heroList);

		assertEquals(heroFacade.findByName("Myspulin"), heroDtoList);
	}

	@Test
	public void findDead() {
		when(heroService.findDead()).thenReturn(heroList);

		assertEquals(heroFacade.findDead(), heroDtoList);
	}

	@Test
	public void findOnQuest() {
		Quest quest = new Quest("Strawberry hunt", "Forest", 1, 2);
		QuestDto questDto = new QuestDto();
		questDto.setId(1L);
		questDto.setName("Strawberry hunt");
		questDto.setLocation("Forest");
		questDto.setReward(1);
		questDto.setHeroLimit(2);

		when(beanMappingService.mapTo(questDto, Quest.class)).thenReturn(quest);
		when(heroService.findOnQuest(quest)).thenReturn(heroList);

		assertEquals(heroFacade.findOnQuest(questDto), heroDtoList);
	}

	@Test
	public void findWithSkill() {
		Skill skill = new Skill("Meteor Shower", Collections.singletonList(new Affinity("Fire", 1)), 5);
		AffinityDto affinityDto = new AffinityDto();
		affinityDto.setName("Fire");
		affinityDto.setLevel(1);
		SkillDto skillDto = new SkillDto();
		skillDto.setName("Meteor Shower");
		skillDto.setAffinities(Collections.singletonList(affinityDto));
		skillDto.setBaseDamage(5);

		when(beanMappingService.mapTo(skillDto, Skill.class)).thenReturn(skill);
		when(heroService.findWithSkill(skill)).thenReturn(heroList);

		assertEquals(heroFacade.findWithSkill(skillDto), heroDtoList);
	}

	@Test
	public void findAllSkillsOfHeroesOnQuest() {
		Skill skill = new Skill("Meteor Shower", Collections.singletonList(new Affinity("Fire", 1)), 5);
		AffinityDto affinityDto = new AffinityDto();
		affinityDto.setName("Fire");
		affinityDto.setLevel(1);
		SkillDto skillDto = new SkillDto();
		skillDto.setName("Meteor Shower");
		skillDto.setAffinities(Collections.singletonList(affinityDto));
		skillDto.setBaseDamage(5);

		Quest quest = new Quest("Strawberry hunt", "Forest", 1, 2);
		QuestDto questDto = new QuestDto();
		questDto.setId(1L);
		questDto.setName("Strawberry hunt");
		questDto.setLocation("Forest");
		questDto.setReward(1);
		questDto.setHeroLimit(2);

		when(beanMappingService.mapTo(questDto, Quest.class)).thenReturn(quest);
		when(beanMappingService.mapTo(Collections.singletonList(skill), SkillDto.class)).thenReturn(Collections.singletonList(skillDto));
		when(heroService.findAllSkillsOfHeroesOnQuest(quest)).thenReturn(Collections.singletonList(skill));

		assertEquals(heroFacade.findAllSkillsOfHeroesOnQuest(questDto), Collections.singletonList(skillDto));
	}

	@Test
	public void rateAgainstMonsterType() {
		Monster monster = new Monster("Rat", 1, 1, "XS", new ArrayList<>(), new ArrayList<>());
		MonsterDto monsterDto = new MonsterDto();
		monsterDto.setName("Rat");
		monsterDto.setHitpoints(1);
		monsterDto.setDamage(1);
		monsterDto.setSize("XS");

		when(beanMappingService.mapTo(monsterDto, Monster.class)).thenReturn(monster);
		when(heroService.rateAgainstMonsterType(hero, monster)).thenReturn(1);

		assertEquals(heroFacade.rateAgainstMonsterType(heroDto, monsterDto), 1);

	}
}
