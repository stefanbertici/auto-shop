package tests.repository;

import com.ubb.postuniv.domain.Entity;
import com.ubb.postuniv.exceptions.IdProblemException;
import com.ubb.postuniv.repository.InMemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    InMemoryRepository<Entity> getRepository() {
        InMemoryRepository<Entity> entityRepository = new InMemoryRepository<>();
        Entity entity = new Entity("1");
        entityRepository.create(entity);
        return entityRepository;
    }

    @org.junit.jupiter.api.Test
    void create() {
        InMemoryRepository<Entity> entityRepository = getRepository();
        Entity entity = new Entity("1");

        try {
            entityRepository.create(entity);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There already is an entity with id " + entity.getId(), ipex.getMessage());
        }

        Entity newEntity = new Entity("2");

        try {
            entityRepository.create(newEntity);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @org.junit.jupiter.api.Test
    void readAll() {
        InMemoryRepository<Entity> entityRepository = getRepository();
        Entity entity = new Entity("2");

        entityRepository.create(entity);

        List<Entity> entities = entityRepository.readAll();
        assertEquals(2, entities.size());
    }

    @org.junit.jupiter.api.Test
    void read() {
        InMemoryRepository<Entity> entityRepository = getRepository();
        Entity entity = new Entity("2");

        entityRepository.create(entity);

        assertEquals("1", entityRepository.read("1").getId());
        assertEquals("2", entityRepository.read("2").getId());
    }

    @org.junit.jupiter.api.Test
    void update() {
        InMemoryRepository<Entity> entityRepository = getRepository();
        Entity entity = new Entity("2");

        try {
            entityRepository.update(entity);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id " + entity.getId(), ipex.getMessage());
        }

        Entity newEntity = new Entity("1");

        try {
            entityRepository.update(newEntity);
        } catch (IdProblemException ipex) {
            fail();
        }
    }

    @org.junit.jupiter.api.Test
    void delete() {
        InMemoryRepository<Entity> entityRepository = getRepository();
        String id = "2";

        try {
            entityRepository.delete(id);
            fail();
        } catch (IdProblemException ipex) {
            assertEquals("Error: There is no entity with id " + id, ipex.getMessage());
        }

        id = "1";

        try {
            entityRepository.delete(id);
        } catch (IdProblemException ipex) {
            fail();
        }
    }
}