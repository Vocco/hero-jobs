package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HeroDto;
import cz.muni.fi.pa165.dto.MonsterDto;
import cz.muni.fi.pa165.dto.QuestDto;
import cz.muni.fi.pa165.dto.QuestState;

import java.util.List;

public interface QuestFacade {

    /**
     * Gets all the quests available.
     *
     * @return A {@link List} of all existing quests.
     */
    List<QuestDto> findAll();

    /**
     * Gets a single quest by its ID, or null if such a quest doesn't exist.
     *
     * @param id The ID of the quest being accessed.
     *
     * @return The quest with given ID, or null if not found.
     */
    QuestDto findById(Long id);

    /**
     * Updates the quest's attributes.
     *
     * @param quest The updated quest.
     *
     * @return The updated quest.
     */
    QuestDto update(QuestDto quest);

    /**
     * Persists a new quest.
     *
     * @param quest The quest to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(QuestDto quest);

    /**
     * Deletes a quest.
     *
     * @param quest The quest to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(QuestDto quest);

    /**
     * Deletes a quest with given ID.
     *
     * @param id The ID of the quest to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);

    /**
     * Finds all quests with certain state.
     *
     * @param state The stte of a quest.
     *
     * @return A {@link List} of quests with given state.
     */
    List<QuestDto> findByState(QuestState state);

    /**
     * Finds all quests in certain location.
     *
     * @param location The {@link String} representing location.
     *
     * @return A {@link List} of quests in given location.
     */
    List<QuestDto> findByLocation(String location);

    /**
     * Finds all quests with certain monster.
     *
     * @param monster The monster in a quest.
     *
     * @return A {@link List} of quests with given monster.
     */
    List<QuestDto> findByMonster(MonsterDto monster);

    /**
     * Finds all quests with certain hero assigned.
     *
     * @param hero The hero on a quest.
     *
     * @return A {@link List} of quests with given hero assigned.
     */
    List<QuestDto> findByAssignedHero(HeroDto hero);
}
