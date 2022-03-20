package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;
import com.ubb.postuniv.exceptions.IdProblemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<TEntity extends Entity> implements Repository<TEntity>{
    private final Map<String, TEntity> entities;

    public InMemoryRepository() {
        entities = new HashMap<>();
    }

    @Override
    public void create(TEntity entity) throws IdProblemException {
        if (entities.containsKey(entity.getId())) {
            throw new IdProblemException("Error: There already is an entity with id " + entity.getId());
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
    public void update(TEntity entity) throws IdProblemException {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        } else {
            throw new IdProblemException("Error: There is no entity with id " + entity.getId());
        }
    }

    @Override
    public void delete(String idEntity) throws IdProblemException {
        if (entities.containsKey(idEntity)) {
            entities.remove(idEntity);
        } else {
            throw new IdProblemException("Error: There is no entity with id " + idEntity);
        }
    }
}
