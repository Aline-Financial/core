package com.aline.core.service;

/**
 * Service interface for CRUD operations.
 * @param <ResponseDTO> Returned object type.
 * @param <IdType> Id type.
 * @param <CreateRequestDTO> Create object DTO type.
 * @param <UpdateRequestDTO> Update object DTO type.
 */
public interface CrudService<ResponseDTO, IdType, CreateRequestDTO, UpdateRequestDTO> {

    /**
     * Get object by id.
     * @param id Id of the retrieved object.
     * @return The retrieved object.
     */
    ResponseDTO getById(IdType id);

    /**
     * Create an object using a dto.
     * @param creatDto DTO used to create the object.
     * @return The created object.
     */
    ResponseDTO create(CreateRequestDTO creatDto);

    /**
     * Update an object by id using a dto.
     * @param id Id of the object to be updated.
     * @param newValues The new values to update the object with.
     */
    void update(IdType id, UpdateRequestDTO newValues);

    /**
     * Delete an object by id.
     * @param id Id of object to be deleted.
     */
    void delete(IdType id);
}
