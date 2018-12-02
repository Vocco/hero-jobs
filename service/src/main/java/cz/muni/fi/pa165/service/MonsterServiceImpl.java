package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.exception.EntityNotFoundException;
import cz.muni.fi.pa165.service.exception.EntityValidationException;
import cz.muni.fi.pa165.service.interfaces.MonsterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * @author Vojtech Krajnansky
 */
@Service
public class MonsterServiceImpl implements MonsterService {

    @Inject
    private MonsterDAO monsterDao;

    @Override
    public Monster findById(Long id) throws EntityNotFoundException {
        Monster monster = monsterDao.findById(id);

        if (monster == null) throw new EntityNotFoundException("Monster with given ID was not found");

        return monster;
    }

    @Override
    public List<Monster> findAll() {
        return monsterDao.findAll();
    }

    @Override
    public boolean save(Monster monster) throws EntityValidationException {
        validate(monster);
        return monsterDao.save(monster);
    }

    @Override
    public boolean delete(Monster monster) {
        return monsterDao.delete(monster);
    }

    @Override
    public boolean deleteById(Long id) {
        return monsterDao.deleteById(id);
    }

    @Override
    public Monster update(Monster monster) throws EntityValidationException {
        validate(monster);
        return monsterDao.update(monster);
    }

    @Override
    public List<Monster> findWithStrength(Affinity strength) {
        return monsterDao.findWithStrength(strength);
    }

    @Override
    public List<Monster> findWithWeakness(Affinity weakness) {
        return monsterDao.findWithWeakness(weakness);
    }

    @Override
    public List<Monster> findWithSize(String size) {
        return monsterDao.findWithSize(size);
    }

    @Override
    public List<Monster> findByQuest(Quest quest) {
        return monsterDao.findByQuest(quest);
    }

    @Override
    public List<Affinity> findAllWeaknessesOfMonstersOnQuest(Quest quest) {
        List<Monster> onQuest = monsterDao.findByQuest(quest);
        List<Affinity> weaknesses = new ArrayList<>();

        for (Monster m : onQuest) {
            for (Affinity monsterWeakness : m.getWeaknesses()) {
                if (!weaknesses.contains(monsterWeakness)) {
                    weaknesses.add(monsterWeakness);
                }
            }
        }

        return weaknesses;
    }

    @Override
    public List<Affinity> findAllStrengthsOfMonstersOnQuest(Quest quest) {
        List<Monster> onQuest = monsterDao.findByQuest(quest);
        List<Affinity> strengths = new ArrayList<>();

        for (Monster m : onQuest) {
            for (Affinity monsterStrength : m.getStrengths()) {
                if (!strengths.contains(monsterStrength)) {
                    strengths.add(monsterStrength);
                }
            }
        }

        return strengths;
    }

    private void validate(Monster monster) throws EntityValidationException {
        if (monster.getName() == null
            || monster.getHitpoints() <= 0
            || monster.getDamage() <= 0
            || monster.getSize() == null
            || monster.getStrengths() == null
            || monster.getWeaknesses() == null) {
            throw new EntityValidationException("Monster is not in valid state.");
        }

        Set<Affinity> weaknessesSet = new HashSet<>(monster.getWeaknesses());
        Set<Affinity> strengthsSet = new HashSet<>(monster.getStrengths());

        if (weaknessesSet.size() < monster.getWeaknesses().size()
            || strengthsSet.size() < monster.getStrengths().size()) {
            throw new EntityValidationException("Monster is not in valid state.");
        }
    }
}