package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.SkillDto;

import java.util.List;

public interface HeroFacade {


    /**
     * Gets all the heroes available.
     *
     * @return A {@link List} of all existing heroes.
     */
    List<HeroDto> findAll();

    /**
     * Gets a single hero by its ID, or null if such a hero doesn't exist.
     *
     * @param id The ID of the hero being accessed.
     *
     * @return The hero with given ID, or null if not found.
     */
    HeroDto findById(Long id);

    /**
     * Updates the hero's attributes.
     *
     * @param hero The updated hero.
     *
     * @return The updated hero.
     */
    HeroDto update(HeroDto hero);

    /**
     * Persists a new hero.
     *
     * @param hero The hero to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(HeroDto hero);

    /**
     * Deletes a hero.
     *
     * @param hero The hero to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(HeroDto hero);

    /**
     * Deletes a hero with given ID.
     *
     * @param id The ID of the hero to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);

    /**
     * Finds all living heroes who are not currently assigned to an ongoing quest.
     *
     * @return A {@link List} of heroes who are available to join a new quest.
     */
    List<HeroDto> findAvailable();

    /**
     * Finds all living heroes.
     *
     * @return A {@link List} of heroes who are still alive.
     */
    List<HeroDto> findAlive();

    /**
     * Finds all heroes with the given name.
     *
     * @param name The name to search by.
     *
     * @return A {@link List} of heroes who have the given name.
     */
    List<HeroDto> findByName(String name);

    /**
     * Finds all dead heroes.
     *
     * @return A {@link List} of heroes who are not alive anymore.
     */
    List<HeroDto> findDead();

    /**
     * Finds all heroes that are currently assigned to a given quest.
     *
     * @param quest The quest to search by.
     *
     * @return A {@link List} of heroes that are currently assigned to a given quest.
     */
    List<HeroDto> findOnQuest(QuestDto quest);

    /**
     * Finds all {@link Hero}es who have a given skill.
     *
     * @param skill The skill to search by.
     *
     * @return A {@link List} of {@link Hero}es with the given skill.
     */
    List<HeroDto> findWithSkill(SkillDto skill);
}
