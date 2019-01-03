package ro.ubb.socketCinema.server.repository;

import ro.ubb.socketCinema.common.domain.BaseEntity;

import java.util.Optional;

/**
 * Interface on CRUD operations for books
 *
 * @author Liviu
 */
public interface Repository<ID, Entities extends BaseEntity<ID>> {

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Optional<Entities> save(Entities entity);

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional delete(ID id);

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional findOne(ID id);

    /**
     * @return all entities.
     */
    Iterable<Entities> findAll();

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Optional<Entities> update(Entities entity);
}
