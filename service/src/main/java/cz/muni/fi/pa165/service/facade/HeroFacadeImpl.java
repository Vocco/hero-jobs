package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.SkillDto;
import cz.muni.fi.pa165.facade.HeroFacade;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.HeroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    @Inject
    private HeroService heroService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<HeroDto> findAll() {
        return beanMappingService
            .mapTo(heroService.findAll(), HeroDto.class);
    }

    @Override
    public HeroDto findById(Long id) {
        try {
            Hero hero = heroService.findById(id);
            return beanMappingService.mapTo(hero, HeroDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HeroDto update(HeroDto hero) {
        try {
            Hero updated = heroService
                .update(beanMappingService.mapTo(hero, Hero.class));

            return beanMappingService.mapTo(updated, HeroDto.class);
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean save(HeroDto hero) {
        try {
            return heroService
                .save(beanMappingService.mapTo(hero, Hero.class));
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(HeroDto hero) {
        return heroService
            .delete(beanMappingService.mapTo(hero, Hero.class));
    }

    @Override
    public boolean deleteById(Long id) {
        return heroService.deleteById(id);
    }

    @Override
    public List<HeroDto> findAvailable() {
        return beanMappingService
            .mapTo(heroService.findAvailable(), HeroDto.class);
    }

    @Override
    public List<HeroDto> findAlive() {
        return beanMappingService
            .mapTo(heroService.findAlive(), HeroDto.class);
    }

    @Override
    public List<HeroDto> findByName(String name) {
        return beanMappingService
            .mapTo(heroService.findByName(name), HeroDto.class);
    }

    @Override
    public List<HeroDto> findDead() {
        return beanMappingService
            .mapTo(heroService.findDead(), HeroDto.class);
    }

    @Override
    public List<HeroDto> findOnQuest(QuestDto quest) {
        List<Hero> heroes = heroService
            .findOnQuest(beanMappingService.mapTo(quest, Quest.class));

        return beanMappingService.mapTo(heroes, HeroDto.class);
    }

    @Override
    public List<HeroDto> findWithSkill(SkillDto skill) {
        List<Hero> heroes = heroService
            .findWithSkill(beanMappingService.mapTo(skill, Skill.class));

        return beanMappingService.mapTo(heroes, HeroDto.class);
    }

    @Override
    public List<SkillDto> findAllSkillsOfHeroesOnQuest(QuestDto quest) {
        List<Skill> skills = heroService
            .findAllSkillsOfHeroesOnQuest(
                beanMappingService.mapTo(quest, Quest.class)
            );

        return beanMappingService.mapTo(skills, SkillDto.class);
    }

    @Override
    public int rateAgainstMonsterType(HeroDto hero, MonsterDto monster) {
        return heroService
            .rateAgainstMonsterType(
                beanMappingService.mapTo(hero, Hero.class),
                beanMappingService.mapTo(monster, Monster.class)
            );
    }
}