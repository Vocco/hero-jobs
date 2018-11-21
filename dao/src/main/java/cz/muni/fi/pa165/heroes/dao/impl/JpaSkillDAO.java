package cz.muni.fi.pa165.heroes.dao.impl;


import cz.muni.fi.pa165.heroes.dao.SkillDAO;
import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A {@link Skill} JPA DAO implementation.
 *
 * @author Jakub Strmen
 */
@Repository
public class JpaSkillDAO extends JpaDAO<Skill> implements SkillDAO {


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Skill> findByName(String name) {
        return entityManager.createQuery("SELECT s FROM Skill s WHERE s.name = :name"
                , Skill.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Skill> findWithAffinity(Affinity affinity) {
        return entityManager.createQuery("SELECT s FROM Skill s JOIN s.affinities a WHERE a.id = :aid"
                , Skill.class)
                .setParameter("aid", affinity.getId())
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Skill> findWithBaseDamage(int baseDamage) {
        return findByBaseDamage(baseDamage, "=");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Skill> findWithGreaterBaseDamage(int minBaseDamage) {
        return findByBaseDamage(minBaseDamage, ">=");
    }

    /**
     * @param baseDamage - boundary value
     * @param operator - operator for comparison (e. g. =, >=)
     *
     * @return Finds and returns {@link List} of {@link Skill}s using specific value and operator for comparison
     */
    private List<Skill> findByBaseDamage(int baseDamage, String operator) {
        return entityManager.createQuery("SELECT s FROM Skill s WHERE s.baseDamage " + operator + " :basedamage"
                , Skill.class)
                .setParameter("basedamage", baseDamage)
                .getResultList();
    }
}
