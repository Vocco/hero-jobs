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

        Query query = entityManager
            .createQuery(
                "SELECT h FROM Hero h, Quest q WHERE h.isAlive = TRUE "
                + "AND (h.quest IS NULL OR (h.quest = q.id AND q.state <> :state))");

        query.setParameter("state", QuestState.ONGOING);

        List<Hero> availableHeroes = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return availableHeroes;
    }

    @Override
    public List<Hero> findAlive() {
        entityManager.getTransaction().begin();

        List<Hero> livingHeroes = entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = TRUE")
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return livingHeroes;
    }
    
    @Override
    public List<Hero> findDead() {
        entityManager.getTransaction().begin();

        List<Hero> deadHeroes = entityManager.createQuery("SELECT h FROM Hero h WHERE h.isAlive = FALSE")
          .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return deadHeroes;
    }
    
    @Override
    public List<Hero> findOnQuest(Quest quest) {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT h FROM Hero h WHERE h.quest = :questId");

        query.setParameter("questId", quest.getId());

        List<Hero> heroesOnQuest = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return heroesOnQuest;
    }

    @Override
    public List<Hero> findWithSkill(Skill skill) {
        // TODO: Implement me.
        return null;
    }
}
