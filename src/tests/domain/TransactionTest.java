package tests.domain;

import com.ubb.postuniv.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    Transaction transaction = new Transaction("1", "2", "1", 1500d, 550d, LocalDateTime.parse("21.07.2016 09:50", dateTimeFormatter), 100d);

    @Test
    void getCarId() {
        assertEquals("2", transaction.getCarId());
    }

    @Test
    void getClientCardId() {
        assertEquals("1", transaction.getClientCardId());
    }

    @Test
    void getPartPrice() {
        assertEquals(1500d, transaction.getPartPrice());
    }

    @Test
    void getLaborPrice() {
        assertEquals(550d, transaction.getLaborPrice());
    }

    @Test
    void getDateAndTime() {
        assertEquals("21.07.2016 09:50", transaction.getDateAndTime().format(dateTimeFormatter));
    }

    @Test
    void getAppliedDiscount() {
        assertEquals(100d, transaction.getAppliedDiscount());
    }

    @Test
    void testToString() {
        assertEquals("Transaction{id='1', carId='2', clientCardId='1', partPrice=1500.0, laborPrice=550.0, dateAndTime='21.07.2016 09:50', appliedDiscount=100.0}", transaction.toString());
    }
}