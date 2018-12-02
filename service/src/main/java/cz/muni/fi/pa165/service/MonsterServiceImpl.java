package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.service.interfaces.MonsterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtech Krajnansky
 */
@Service
public class MonsterServiceImpl implements MonsterService {

    private final MonsterDAO monsterDao;

    @Autowired
    public MonsterServiceImpl(MonsterDAO monsterDao) {
        this.monsterDao = monsterDao;
    }

    @Override
    public Monster findById(Long id) {
        return monsterDao.findById(id);
    }

    @Override
    public List<Monster> findAll() {
        return monsterDao.findAll();
    }

    @Override
    public boolean save(Monster monster) {
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
    public Monster update(Monster monster) {
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
}