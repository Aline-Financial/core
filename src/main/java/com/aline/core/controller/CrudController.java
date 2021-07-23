package com.aline.core.controller;

import com.aline.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;

/**
 * Abstract controller class to be extended by more specific class to use base CRUD functionalities.
 * @param <ResponseDTO>
 * @param <IdType>
 * @param <CreateRequestDTO>
 * @param <UpdateRequestDTO>
 */
public abstract class CrudController<MainService extends CrudService<ResponseDTO, IdType, CreateRequestDTO, UpdateRequestDTO>, ResponseDTO, IdType, CreateRequestDTO, UpdateRequestDTO> {

    protected MainService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable IdType id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getById(id));
    }

    public abstract URI createLocation(ResponseDTO t);

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid CreateRequestDTO createDto) {
        ResponseDTO created = service.create(createDto);
        return ResponseEntity
                .created(createLocation(created))
                .contentType(MediaType.APPLICATION_JSON)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable IdType id, @RequestBody @Valid UpdateRequestDTO updateDTO) {
        service.update(id, updateDTO);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable IdType id) {
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
