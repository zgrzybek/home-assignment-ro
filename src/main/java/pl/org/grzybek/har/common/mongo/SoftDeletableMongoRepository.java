package pl.org.grzybek.har.common.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.org.grzybek.har.common.audit.SoftDeletable;

import java.util.Optional;

/**
 * Repository simplifying work with SoftDeletable objects.
 * There are better ways to approach it, but this is the fastest to implement
 */
public interface SoftDeletableMongoRepository<T extends SoftDeletable, ID> extends MongoRepository<T, ID> {

    /**
     * Retrieves non deleted entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Query(value = "{ 'id' : ?0, 'deleted': false }")
    Optional<T> findActiveById(ID id);

    /**
     * Returns a {@link Page} of active entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Query(value = "{ 'deleted': false }")
    Page<T> findAllActive(Pageable pageable);

    /**
     * Soft Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    default void softDelete(T entity) {
        entity.setDeleted(true);
        save(entity);
    }

}
