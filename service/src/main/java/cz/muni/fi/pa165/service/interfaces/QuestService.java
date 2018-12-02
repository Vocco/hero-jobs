package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestService {

	Quest findById(Long id) throws EntityNotFoundException;
	List<Quest> findAll();

	boolean save(Quest quest) throws EntityValidationException;
	Quest update(Quest quest) throws EntityValidationException;
	boolean delete(Quest quest);
	boolean deleteById(Long id);

	List<Quest> findByState(QuestState state) throws EntityNotFoundException;
	List<Quest> findByLocation(String location) throws EntityNotFoundException;
	List<Quest> findByMonster(Monster monster) throws EntityNotFoundException;
	List<Quest> findByAssignedHero(Hero hero) throws EntityNotFoundException;
}