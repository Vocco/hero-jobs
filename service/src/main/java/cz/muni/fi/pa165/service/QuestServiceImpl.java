package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.QuestDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.QuestService;

import javax.inject.Inject;
import java.util.List;

public class QuestServiceImpl implements QuestService {

	@Inject
	private QuestDAO questDAO;

	@Override
	public Quest findById(Long id) throws EntityNotFoundException {
		Quest quest = questDAO.findById(id);

		if (quest == null) {
			throw new EntityNotFoundException("Quest with id: " + id + " not found.");
		}

		return quest;
	}

	@Override
	public List<Quest> findAll() {
		return questDAO.findAll();
	}

	@Override
	public boolean save(Quest quest) throws EntityValidationException {
		validate(quest);
		return questDAO.save(quest);
	}

	@Override
	public Quest update(Quest quest) throws EntityValidationException {
		validate(quest);
		return questDAO.update(quest);
	}

	@Override
	public boolean delete(Quest quest) {
		return questDAO.delete(quest);
	}

	@Override
	public boolean deleteById(Long id) {
		return questDAO.deleteById(id);
	}

	@Override
	public List<Quest> findByState(QuestState state) throws EntityNotFoundException {
		List<Quest> quests = questDAO.findByState(state);

		if (quests.isEmpty()) {
			throw new EntityNotFoundException("Quests with state: " + state + " not found.");
		}

		return quests;
	}

	@Override
	public List<Quest> findByLocation(String location) throws EntityNotFoundException {
		List<Quest> quests = questDAO.findByLocation(location);

		if (quests.isEmpty()) {
			throw new EntityNotFoundException("Quests in location: " + location + " not found.");
		}

		return quests;
	}

	@Override
	public List<Quest> findByMonster(Monster monster) throws EntityNotFoundException {
		List<Quest> quests = questDAO.findByMonster(monster);

		if (quests.isEmpty()) {
			throw new EntityNotFoundException("Quests with monster: " + monster + " not found.");
		}

		return quests;
	}

	@Override
	public List<Quest> findByAssignedHero(Hero hero) throws EntityNotFoundException {
		List<Quest> quests = questDAO.findByAssignedHero(hero);

		if (quests.isEmpty()) {
			throw new EntityNotFoundException("Quests with hero: " + hero + " not found.");
		}

		return quests;
	}

	private void validate(Quest quest) throws EntityValidationException {
		if (quest == null
				|| quest.getName() == null
				|| quest.getLocation() == null
				|| quest.getReward() <= 0
				|| quest.getHeroLimit() <= 0) {
			throw new EntityValidationException("Quest is not in valid state.");
		}
	}
}
