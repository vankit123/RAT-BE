package com.rat.backend.service;

import java.util.List;

public interface CrudService<REQ, RES> {

    List<RES> getAll();

    RES getById(Long id);

    RES create(REQ request);

    RES update(Long id, REQ request);

    void delete(Long id);
}
