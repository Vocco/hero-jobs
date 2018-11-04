package cz.muni.fi.pa165.heroes.dao;

import cz.muni.fi.pa165.heroes.entity.Hero;
import cz.muni.fi.pa165.heroes.entity.Quest;
import cz.muni.fi.pa165.heroes.entity.Skill;

import java.util.List;


/**
 * Hero-specific DAO.
 *
 * @author Vojtech Krajnansky (423126)
 */
public interface HeroDAO {

    /**
     * Finds all living {@link Hero}es who are not currently assigned to an ongoing quest.
     *
     * @return A {@link List} of {@link Hero}es who are available to join a new quest.
     */
    List<Hero> findAvailable();

    /**
     * Finds all living {@link Hero}es.
     *
     * @return A {@link List} of {@link Hero}es who are still alive.
     */
    List<Hero> findAlive();

    /**
     * Finds all {@link Hero}es with the given name.
     *
     * @param name The name to search by.
     *
     * @return A {@link List} of {@link Hero}es who have the given name.
     */
    List<Hero> findByName(String name);

    /**
     * Finds all dead {@link Hero}es.
     *
     * @return A {@link List} of {@link Hero}es who are not alive anymore.
     */
    List<Hero> findDead();

    /**
     * Finds all heroes that are currently assigned to a given {@link Quest}.
     *
     * @param quest The {@link Quest} to search by.
     *
     * @return A {@link List} of {@link Hero}es that are currently assigned to a given {@link Quest}.
     */
    List<Hero> findOnQuest(Quest quest);

    /**
     * Finds all {@link Hero}es who have a given {@link Skill}.
     *
     * @param skill The {@link Skill} to search by.
     *
     * @return A {@link List} of {@link Hero}es with the given {@link Skill}.
     */
    List<Hero> findWithSkill(Skill skill);
}
