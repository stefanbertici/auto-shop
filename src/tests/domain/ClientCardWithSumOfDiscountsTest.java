package tests.domain;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardWithSumOfDiscounts;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardWithSumOfDiscountsTest {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    ClientCard clientCard = new ClientCard("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));
    ClientCardWithSumOfDiscounts clientCardWithSumOfDiscounts = new ClientCardWithSumOfDiscounts(clientCard, 100d);

    @Test
    void getClientCard() {
        assertEquals(clientCard, clientCardWithSumOfDiscounts.getClientCard());
    }

    @Test
    void getSumOfDiscounts() {
        assertEquals(100d, clientCardWithSumOfDiscounts.getSumOfDiscounts());
    }

    @Test
    void testToString() {
        assertEquals("ClientCardWithSumOfDiscounts{clientCard=ClientCard{id='1', firstName='Yari', lastName='Motomo', cnp='199922', birthDate='12.03.1993', registrationDate='01.01.2022'}, sumOfDiscounts=100.0}"
                , clientCardWithSumOfDiscounts.toString());
    }
}