package tests.domain;

import com.ubb.postuniv.domain.Car;
import com.ubb.postuniv.domain.CarWithSumOfLaborPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarWithSumOfLaborPriceTest {

    Car car = new Car("1", "yaris turbo", 2023, 0, true);
    CarWithSumOfLaborPrice carWithSumOfLaborPrice = new CarWithSumOfLaborPrice(car, 100d);

    @Test
    void getCar() {
        assertEquals(car, carWithSumOfLaborPrice.getCar());
    }

    @Test
    void getTotalLaborPrice() {
        assertEquals(100d, carWithSumOfLaborPrice.getTotalLaborPrice());
    }

    @Test
    void testToString() {
        assertEquals("CarWithSumOfLaborPrice{car=Car{id='1', model='yaris turbo', yearOfPurchase=2023, km=0, warranty=true}, sumOfLaborPrice=100.0}", carWithSumOfLaborPrice.toString());
    }
}