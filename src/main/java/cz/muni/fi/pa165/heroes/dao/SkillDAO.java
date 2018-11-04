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

    // findByName ???
    // List<Skill> findByName();

    /**
     * Finds all {@link Skill}s containing given {@link Affinity}
     *
     * @param affinity - {@link Affinity} needed
     *
     * @return - {@link List} of {@link Skill}s which contains needed {@link Affinity}
     */
    List<Skill> findWithAffinity(Affinity affinity);

    /**
     * Finds all {@link Skill}s containing given {@link Affinity}-ies
     *
     * @param affinities - {@link List} of {@link Affinity}-ies needed
     *
     * @return - {@link List} of {@link Skill}s which contains needed {@link Affinity}-ies
     */
    List<Skill> findWithAffinities(List<Affinity> affinities);

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
