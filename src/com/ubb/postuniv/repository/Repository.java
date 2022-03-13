package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;

import java.util.List;

public interface Repository<TEntity extends Entity> {
    void create(TEntity entity);

    List<TEntity> readAll();

    TEntity read(String idEntity);

    void update(TEntity entity);

    void delete(String idEntity);
}
