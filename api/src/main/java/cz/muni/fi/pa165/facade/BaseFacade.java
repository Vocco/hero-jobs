package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.BaseDto;

import java.util.List;

public interface BaseFacade<D extends BaseDto> {

    /**
     * Gets all the DTOs available.
     *
     * @return A {@link List} of all existing DTOs.
     */
    List<D> findAll();

    /**
     * Gets a single DTO by its ID, or null if such a DTO doesn't exist.
     *
     * @param id The ID of the DTO being accessed.
     *
     * @return The DTO with given ID, or null if not found.
     */
    D findById(Long id);

    /**
     * Updates the DTO's attributes.
     *
     * @param dto The updated DTO.
     *
     * @return The updated DTO.
     */
    D update(D dto);

    /**
     * Persists a new DTO.
     *
     * @param dto The DTO to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(D dto);

    /**
     * Deletes a DTO.
     *
     * @param dto The DTO to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(D dto);

    /**
     * Deletes a DTO with given ID.
     *
     * @param id The ID of the DTO to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);
}
