package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AffinityDto;

import java.util.List;

public interface AffinityFacade {

    /**
     * Gets all the affinities available.
     *
     * @return A {@link List} of all existing affinities.
     */
    List<AffinityDto> findAll();

    /**
     * Gets a single affinity by its ID, or null if such an affinity doesn't exist.
     *
     * @param id The ID of the affinity being accessed.
     *
     * @return The affinity with given ID, or null if not found.
     */
    AffinityDto findById(Long id);

    /**
     * Updates the affinity's attributes.
     *
     * @param affinity The updated affinity.
     *
     * @return The updated affinity.
     */
    AffinityDto update(AffinityDto affinity);

    /**
     * Persists a new affinity.
     *
     * @param affinity The affinity to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(AffinityDto affinity);

    /**
     * Deletes an affinity.
     *
     * @param affinity The affinity to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(AffinityDto affinity);

    /**
     * Deletes an affinity with given ID.
     *
     * @param id The ID of the affinity to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);
}
