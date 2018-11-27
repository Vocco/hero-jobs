package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.MonsterDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Monster;
import cz.muni.fi.pa165.heroes.entity.Quest;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JpaMonsterDAO extends JpaDAO<Monster> implements MonsterDAO {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Monster> findWithStrength(Affinity strength) {
        return entityManager.createQuery("SELECT m FROM Monster m JOIN m.strengths s WHERE s.id = :sid"
                , Monster.class)
                .setParameter("sid", strength.getId())
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Monster> findWithWeakness(Affinity weakness) {
        return entityManager.createQuery("SELECT m FROM Monster m JOIN m.weaknesses w WHERE w.id = :wid", Monster.class)
                .setParameter("wid", weakness.getId())
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Monster> findWithSize(String size) {
        return entityManager.createQuery("SELECT m FROM Monster m WHERE m.size = :sz", Monster.class)
                .setParameter("sz", size)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Monster> findByQuest(Quest quest) {
      return entityManager.createQuery("SELECT m FROM Monster m JOIN m.assignedToQuests q WHERE q.quest.id = :qid",
          Monster.class)
          .setParameter("qid", quest.getId())
          .getResultList();

        
    }
}
