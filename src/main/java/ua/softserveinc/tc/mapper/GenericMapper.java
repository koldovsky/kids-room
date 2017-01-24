package ua.softserveinc.tc.mapper;


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
