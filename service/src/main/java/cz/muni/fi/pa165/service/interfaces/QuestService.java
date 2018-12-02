package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestService {

	Quest findById(Long id);
	void create(Quest quest);
	void remove(Quest quest);
	List<Quest> findByState(QuestState state);
	List<Quest> findByLocation(String location);
	List<Quest> findByMonster(Monster monster);
	List<Quest> findByAssignedHero(Hero hero);
}