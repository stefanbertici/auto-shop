package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<TEntity extends Entity> implements Repository<TEntity>{
    private Map<String, TEntity> entities;

    public InMemoryRepository() {
        entities = new HashMap<>();
    }

    @Override
    public void create(TEntity entity) {
        if (entities.containsKey(entity.getId())) {
            throw new RuntimeException("Error: There already is an entity with id " + entity.getId());
        } else {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public List<TEntity> readAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public TEntity read(String idEntity) {
        return entities.getOrDefault(idEntity, null);
    }

    @Override
    public void update(TEntity entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        } else {
            throw new RuntimeException("Error: There is no entity with id " + entity.getId());
        }
    }

    @Override
    public void delete(String idEntity) {
        if (entities.containsKey(idEntity)) {
            entities.remove(idEntity);
        } else {
            throw new RuntimeException("Error: There is no entity with id " + idEntity);
        }
    }
}
