package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;
import com.ubb.postuniv.exceptions.IdProblemException;

import java.util.List;

public interface Repository<TEntity extends Entity> {
    void create(TEntity entity) throws IdProblemException;

    List<TEntity> readAll();

    TEntity read(String idEntity);

    void update(TEntity entity) throws IdProblemException;

    void delete(String idEntity) throws IdProblemException;
}
