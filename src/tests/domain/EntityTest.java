package tests.domain;

import com.ubb.postuniv.domain.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    Entity entity = new Entity("1");

    @Test
    void getId() {
        assertEquals("1", entity.getId());
    }
}