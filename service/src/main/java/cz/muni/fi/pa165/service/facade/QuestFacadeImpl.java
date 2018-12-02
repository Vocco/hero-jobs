package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestState;
import cz.muni.fi.pa165.facade.QuestFacade;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.QuestService;

import javax.inject.Inject;
import java.util.List;


/**
 * @author Metodej Klang
 */
public class QuestFacadeImpl implements QuestFacade {

	@Inject
	private QuestService questService;

	@Inject
	private BeanMappingService beanMappingService;

	@Override
	public List<QuestDto> findAll() {
		return beanMappingService.mapTo(questService.findAll(), QuestDto.class);
	}

	@Override
	public QuestDto findById(Long id) {
		try {
			Quest quest = questService.findById(id);
			return beanMappingService.mapTo(quest, QuestDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public QuestDto update(QuestDto dto) {
		try {
			Quest quest = questService.update(beanMappingService.mapTo(dto, Quest.class));
			return beanMappingService.mapTo(quest, QuestDto.class);
		} catch (EntityValidationException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean save(QuestDto dto) {
		try {
			return questService.save(beanMappingService.mapTo(dto, Quest.class));
		} catch (EntityValidationException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(QuestDto dto) {
		return questService.delete(beanMappingService.mapTo(dto, Quest.class));
	}

	@Override
	public boolean deleteById(Long id) {
		return questService.deleteById(id);
	}

	@Override
	public List<QuestDto> findByState(QuestState state) {
		try {
			return beanMappingService.mapTo(questService.findByState(beanMappingService.mapTo(state, cz.muni.fi.pa165.heroes.entity.QuestState.class)), QuestDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<QuestDto> findByLocation(String location) {
		try {
			return beanMappingService.mapTo(questService.findByLocation(location), QuestDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<QuestDto> findByMonster(MonsterDto monster) {
		try {
			return beanMappingService.mapTo(questService.findByMonster(beanMappingService.mapTo(monster, Monster.class)), QuestDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<QuestDto> findByAssignedHero(HeroDto hero) {
		try {
			return beanMappingService.mapTo(questService.findByAssignedHero(beanMappingService.mapTo(hero, Hero.class)), QuestDto.class);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
