package cz.muni.fi.pa165.service.interfaces;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HeroService {

	  Hero findById(Long id) throws EntityNotFoundException;
	  List<Hero> findAll();

	  boolean save(Hero hero) throws EntityValidationException;
	  Hero update(Hero hero) throws EntityValidationException;
	  boolean delete(Hero hero);
	  boolean deleteById(Long id);

	  List<Hero> findAvailable();
	  List<Hero> findAlive();
	  List<Hero> findByName(String name);
	  List<Hero> findDead();
	  List<Hero> findOnQuest(Quest quest);
	  List<Hero> findWithSkill(Skill skill);

	  List<Skill> findAllSkillsOfHeroesOnQuest(Quest quest);
    int rateAgainstMonsterType(Hero hero, Monster monster);
}