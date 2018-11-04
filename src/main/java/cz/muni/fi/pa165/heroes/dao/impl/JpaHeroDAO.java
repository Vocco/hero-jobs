package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.HeroDAO;
import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.QuestState;
import cz.muni.fi.pa165.heroes.entity.Skill;

import java.util.List;

import javax.persistence.Query;


/**
 * A {@link Hero} JPA DAO implementation.
 *
 * @author Vojtech Krajnansky (423126)
 */
public class JpaHeroDAO extends JpaDAO<Hero> implements HeroDAO {


    // HERODAO INTERFACE IMPLEMENTATION

    @Override
    public List<Hero> findAvailable() {
        entityManager.getTransaction().begin();

        List<Hero> availableHeroes = entityManager
            .createQuery(
                "SELECT h FROM Hero h, Quest q WHERE h.isAlive = TRUE "
                + "AND (h.quest IS NULL OR (h.quest = q.id AND q.state <> :state))"
                , Hero.class
            )
            .setParameter("state", QuestState.ONGOING)
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return availableHeroes;
    }

    @Override
    public List<Hero> findAlive() {
        entityManager.getTransaction().begin();

        List<Hero> livingHeroes = entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = TRUE", Hero.class)
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return livingHeroes;
    }

    @Override
    public List<Hero> findByName(String name) {
        if (name == null) throw new IllegalArgumentException("Cannot find by null Name.");
        entityManager.getTransaction().commit();

        List<Hero> heroesWithName = entityManager.createQuery("SELECT h FROM Hero h WHERE h.name = :name", Hero.class)
            .setParameter("name", name)
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return heroesWithName;
    }

    @Override
    public List<Hero> findDead() {
        entityManager.getTransaction().begin();

        List<Hero> deadHeroes = entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = FALSE", Hero.class)
          .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return deadHeroes;
    }
    
    @Override
    public List<Hero> findOnQuest(Quest quest) {
        if (quest == null) throw new IllegalArgumentException("Cannot find by null Quest.");
        entityManager.getTransaction().begin();

        List<Hero> heroesOnQuest = entityManager
            .createQuery("SELECT h FROM Hero h WHERE h.quest = :questId", Hero.class)
            .setParameter("questId", quest.getId())
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return heroesOnQuest;
    }

    @Override
    public List<Hero> findWithSkill(Skill skill) {
        if (skill == null) throw new IllegalArgumentException("Cannot find by null Skill.");
        entityManager.getTransaction().begin();

        List<Hero> heroesWithSkill = entityManager
            .createQuery("SELECT h FROM Hero h JOIN h.skills s WHERE s.id = :sid", Hero.class)
            .setParameter("sid", skill.getId())
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return heroesWithSkill;
    }
}
