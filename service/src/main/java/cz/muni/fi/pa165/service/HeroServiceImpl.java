package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.interfaces.HeroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vojtech Krajnansky
 */
@Service
public class HeroServiceImpl implements HeroService {

    private final HeroDAO heroDao;

    @Autowired
    public HeroServiceImpl(HeroDAO heroDao) {
        this.heroDao = heroDao;
    }

    @Override
    public Hero findById(Long id) {
        return heroDao.findById(id);
    }

    @Override
    public List<Hero> findAll() {
        return heroDao.findAll();
    }

    @Override
    public boolean save(Hero hero) {
        return heroDao.save(hero);
    }

    @Override
    public boolean delete(Hero hero) {
        return heroDao.delete(hero);
    }

    @Override
    public boolean deleteById(Long id) {
        return heroDao.deleteById(id);
    }

    @Override
    public Hero update(Hero hero) {
        return heroDao.update(hero);
    }

    @Override
    public List<Hero> findAvailable() {
        return heroDao.findAvailable();
    }

    @Override
	  public List<Hero> findAlive() {
	      return heroDao.findAlive();
	  }

	  @Override
	  public List<Hero> findByName(String name) {
	      return heroDao.findByName(name);
	  }

	  @Override
	  public List<Hero> findDead() {
	      return heroDao.findDead();
	  }

	  @Override
	  public List<Hero> findOnQuest(Quest quest) {
	      return heroDao.findOnQuest(quest);
	  }

    @Override
	  public List<Hero> findWithSkill(Skill skill) {
	      return heroDao.findWithSkill(skill);
	  }

	  @Override
	  public List<Skill> findAllSkillsOfHeroesOnQuest(Quest quest) {
	      List<Hero> heroes =  heroDao.findOnQuest(quest);
	      List<Skill> skills = new ArrayList<>();

	      for (Hero h : heroes) {
	          for (Skill skill : h.getSkills()) {
	              if (!skills.contains(skill)) {
	                  skills.add(skill);
	              }
	          }
	      }

	      return skills;
	  }

    @Override
    public int rateAgainstMonsterType(Hero hero, Monster monster) {
        int baseRating = getBaseRating(hero, monster);

        int skillsModifier = getSkillsModifier(
            hero.getSkills(), monster.getWeaknesses(), monster.getStrengths()
        );

        return baseRating + skillsModifier;
    }

    private int getBaseRating(Hero hero, Monster monster) {
        return getTurnsToKill(hero.getHitpoints(), monster.getDamage())
            - getTurnsToKill(monster.getHitpoints(), hero.getDamage());
    }

    private int getTurnsToKill(int hitpoints, int damage) {
        int turnsToKill = hitpoints / damage;
        if (hitpoints % damage != 0) {
            turnsToKill++;
        }
        return turnsToKill;
    }

    private int getSkillsModifier(List<Skill> skills, List<Affinity> weaknesses, List<Affinity> strengths) {
        int finalModifier = 0;

        List<String> weaknessNames = weaknesses.stream().map(w -> w.getName())
            .collect(Collectors.toList());

        List<String> strengthNames = strengths.stream().map(s -> s.getName())
            .collect(Collectors.toList());

        for (Skill skill : skills) {
            int baseDamage = skill.getBaseDamage();
            int skillModifier = 0;

            // Skills with negative base damage are healing.
            if (baseDamage >= 0) {
                for (Affinity affinity : skill.getAffinities()) {
                    int wIndex = weaknessNames.indexOf(affinity.getName());
                    int sIndex = strengthNames.indexOf(affinity.getName());
                    int affLevel = affinity.getLevel();

                    // Check if the monster is weak or strong against this.
                    if (wIndex != -1) {
                        int wLevel = weaknesses.get(wIndex).getLevel();
                        skillModifier += wLevel + affLevel;
                    } else if (sIndex != -1) {
                        int sLevel = strengths.get(sIndex).getLevel();
                        skillModifier -= sLevel + affLevel;
                    }
                }
                finalModifier += skillModifier * baseDamage;
            }
        }

        return finalModifier;
    }
}
