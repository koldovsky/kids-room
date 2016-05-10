package ua.softserveinc.tc.mapper;

/**
 * Created by dima- on 07.05.2016.
 */
public interface GenericMapper<T, V> {
    /*
     * Map entity to DTO
     */

    V toDto(T entity);

    /*
     * Map DTO to entity
     */
    T toEntity(V dto);
}
