package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import cz.muni.fi.pa165.heroes.entity.Skill;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * A {@link Hero} JPA DAO implementation.
 *
 * @author Vojtech Krajnansky (423126)
 */
public class JpaHeroDAO extends JpaDAO<Hero> implements HeroDAO {


    // HERODAO INTERFACE IMPLEMENTATION

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findAvailable() {
        return entityManager
            .createQuery(
                "SELECT h FROM Hero h, Quest q WHERE h.isAlive = TRUE "
                + "AND (h.quest IS NULL OR (h.quest = q.id AND q.state <> :state))"
                , Hero.class
            )
            .setParameter("state", QuestState.ONGOING)
            .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findAlive() {
        return entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = TRUE", Hero.class)
            .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findByName(String name) {
        if (name == null) throw new IllegalArgumentException("Cannot find by null Name.");

        return entityManager.createQuery("SELECT h FROM Hero h WHERE h.name = :name", Hero.class)
            .setParameter("name", name)
            .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findDead() {
        return entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = FALSE", Hero.class)
          .getResultList();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findOnQuest(Quest quest) {
        if (quest == null) throw new IllegalArgumentException("Cannot find by null Quest.");

        return entityManager
            .createQuery("SELECT h FROM Hero h WHERE h.quest = :questId", Hero.class)
            .setParameter("questId", quest.getId())
            .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Hero> findWithSkill(Skill skill) {
        if (skill == null) throw new IllegalArgumentException("Cannot find by null Skill.");

        return entityManager
            .createQuery("SELECT h FROM Hero h JOIN h.skills s WHERE s.id = :sid", Hero.class)
            .setParameter("sid", skill.getId())
            .getResultList();
    }
}
