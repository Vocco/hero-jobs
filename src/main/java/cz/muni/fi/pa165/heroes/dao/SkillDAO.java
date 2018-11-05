package cz.muni.fi.pa165.heroes.dao;


import cz.muni.fi.pa165.heroes.entity.Affinity;
import cz.muni.fi.pa165.heroes.entity.Skill;

import java.util.List;

/**
 * Skill-specific DAO interface
 *
 * @author Jakub Strmen
 */
public interface SkillDAO {

    /**
     * Finds all {@link Skill}s with given name - note that name doesn't have to be unique
     * @param name
     * @return Finds all {@link Skill}s containing given {@link Affinity}
     */
    List<Skill> findByName(String name);

    /**
     * Finds all {@link Skill}s containing given {@link Affinity}
     *
     * @param affinity - {@link Affinity} needed
     *
     * @return - c which contains needed {@link Affinity}
     */
    List<Skill> findWithAffinity(Affinity affinity);

    /**
     * Finds all {@link Skill}s with given base damage
     *
     * @param baseDamage - int base damage of {@link Skill}
     *
     * @return @return - {@link List} of {@link Skill}s with given base damage
     */
    List<Skill> findWithBaseDamage(int baseDamage);

    /**
     * Finds all {@link Skill}s with base damage greater or equal to given parameter
     *
     * @param minBaseDamage - int base damage of {@link Skill}
     *
     * @return @return - {@link List} of {@link Skill}s
     */
    List<Skill> findWithGreaterBaseDamage(int minBaseDamage);

}
