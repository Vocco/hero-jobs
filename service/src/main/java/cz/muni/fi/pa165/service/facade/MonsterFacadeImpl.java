package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.AffinityDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.facade.MonsterFacade;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.BeanMappingService;
import cz.muni.fi.pa165.service.interfaces.MonsterService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class MonsterFacadeImpl implements MonsterFacade {

    @Inject
    private MonsterService monsterService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<MonsterDto> findAll() {
        return beanMappingService
                .mapTo(monsterService.findAll(), MonsterDto.class);
    }

    @Override
    public MonsterDto findById(Long id) {
        try {
            Monster monster = monsterService.findById(id);
            return beanMappingService.mapTo(monster, MonsterDto.class);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MonsterDto update(MonsterDto monster) {
        try {
            Monster updated = monsterService
                    .update(beanMappingService.mapTo(monster, Monster.class));

            return beanMappingService.mapTo(updated, MonsterDto.class);
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(MonsterDto monster) {
        try {
            return monsterService
                    .save(beanMappingService.mapTo(monster, Monster.class));
        } catch (EntityValidationException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(MonsterDto monster) {
        return monsterService
                .delete(beanMappingService.mapTo(monster, Monster.class));
    }

    @Override
    public boolean deleteById(Long id) {
        return monsterService.deleteById(id);
    }

    @Override
    public List<MonsterDto> findWithStrength(AffinityDto strength) {
        List<Monster> monsters = monsterService
                .findWithStrength(
                        beanMappingService.mapTo(strength, Affinity.class)
                );

        return beanMappingService
                .mapTo(monsters, MonsterDto.class);
    }

    @Override
    public List<MonsterDto> findWithWeakness(AffinityDto weakness) {
        List<Monster> monsters = monsterService
                .findWithWeakness(
                        beanMappingService.mapTo(weakness, Affinity.class)
                );

        return beanMappingService
                .mapTo(monsters, MonsterDto.class);
    }

    @Override
    public List<MonsterDto> findWithSize(String size) {
        return beanMappingService
                .mapTo(monsterService.findWithSize(size), MonsterDto.class);
    }

    @Override
    public List<MonsterDto> findByQuest(QuestDto quest) {
        List<Monster> monsters = monsterService
                .findByQuest(beanMappingService.mapTo(quest, Quest.class));

        return beanMappingService.mapTo(monsters, MonsterDto.class);
    }

    @Override
    public List<AffinityDto> findAllWeaknessesOfMonstersOnQuest(QuestDto quest) {
        List<Affinity> weaknesses = monsterService
                .findAllWeaknessesOfMonstersOnQuest(
                        beanMappingService.mapTo(quest, Quest.class)
                );

        return beanMappingService.mapTo(weaknesses, AffinityDto.class);
    }

    @Override
    public List<AffinityDto> findAllStrengthsOfMonstersOnQuest(QuestDto quest) {
        List<Affinity> strengths = monsterService
                .findAllStrengthsOfMonstersOnQuest(
                        beanMappingService.mapTo(quest, Quest.class)
                );

        return beanMappingService.mapTo(strengths, AffinityDto.class);
    }
}