package tests.domain;

import com.ubb.postuniv.domain.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    Car car = new Car("1", "yaris turbo", 2023, 0, true);

    @Test
    void getModel() {
        assertEquals("yaris turbo", car.getModel());
    }

    @Test
    void getYearOfPurchase() {
        assertEquals(2023, car.getYearOfPurchase());
    }

    @Test
    void getKm() {
        assertEquals(0, car.getKm());
    }

    @Test
    void isWarranty() {
        assertTrue(car.isWarranty());
    }

    @Test
    void testToString() {
        assertEquals("Car{id='1', model='yaris turbo', yearOfPurchase=2023, km=0, warranty=true}", car.toString());
    }
}