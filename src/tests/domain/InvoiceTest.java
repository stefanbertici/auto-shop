package tests.domain;

import com.ubb.postuniv.domain.Invoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    Invoice invoice = new Invoice(100d, 50d, 100d, 5d, 0d, 45d);

    @Test
    void getPartPrice() {
        assertEquals(100d, invoice.getPartPrice());
    }

    @Test
    void getLaborPrice() {
        assertEquals(50d, invoice.getLaborPrice());
    }

    @Test
    void getPartDiscount() {
        assertEquals(100d, invoice.getPartDiscount());
    }

    @Test
    void getLaborDiscount() {
        assertEquals(5d, invoice.getLaborDiscount());
    }

    @Test
    void getFinalPartPrice() {
        assertEquals(0d, invoice.getFinalPartPrice());
    }

    @Test
    void getFinalLaborPrice() {
        assertEquals(45d, invoice.getFinalLaborPrice());
    }

    @Test
    void testToString() {
        assertEquals("Invoice{partPrice=100.0, laborPrice=50.0, partDiscount=100.0, laborDiscount=5.0, finalPartPrice=0.0, finalLaborPrice=45.0}", invoice.toString());
    }
}