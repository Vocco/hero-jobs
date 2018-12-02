package cz.muni.fi.pa165.heroes.service;

import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HeroService {

	Hero findById(Long id);
	void create(Hero hero);
	void remove(Hero hero);
	List<Hero> findAvailable();
	List<Hero> findAlive();
	List<Hero> findByName(String name);
	List<Hero> findDead();
	List<Hero> findOnQuest(Quest quest);
	List<Hero> findWithSkill(Skill skill);
	List<Affinity> findAllStrengthsOfHeroesOnQuest(Quest quest);

}
