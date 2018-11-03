package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaMonsterDAO extends JpaDAO<Monster> implements MonsterDAO {

    @Override
    public List<Monster> findWithStrength(Affinity strength) {
        entityManager.getTransaction().begin();

        List<Monster> monsters = entityManager.createQuery("SELECT m FROM Monster m JOIN m.strengths s WHERE s.id = :sid"
                , Monster.class)
                .setParameter("sid", strength.getId())
                .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return monsters;
    }

    @Override
    public List<Monster> findWithWeakness(Affinity weakness) {
        entityManager.getTransaction().begin();

        List<Monster> monsters = entityManager.createQuery("SELECT m FROM Monster m JOIN m.weaknesses w WHERE w.id = :wid"
                , Monster.class)
                .setParameter("wid", weakness.getId())
                .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return monsters;
    }

    @Override
    public List<Monster> findWithSize(String size) {
        entityManager.getTransaction().begin();

        List<Monster> monsters = entityManager.createQuery("SELECT m FROM Monster m WHERE m.size = :sz"
                , Monster.class)
                .setParameter("sz", size)
                .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return monsters;
    }

    @Override
    public List<Monster> findByQuest(Quest quest) {
        entityManager.getTransaction().begin();

        List<Monster> monsters = entityManager.createQuery("SELECT m FROM Monster m JOIN m.assignedToQuests q WHERE q.quest.id = :qid"
                , Monster.class)
                .setParameter("qid", quest.getId())
                .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return monsters;
    }
}
