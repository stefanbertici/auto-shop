package tests.domain;

import com.ubb.postuniv.domain.ClientCard;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardTest {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    ClientCard clientCard = new ClientCard("1", "Yari", "Motomo", "199922", LocalDate.parse("12.03.1993", dateFormatter), LocalDate.parse("01.01.2022", dateFormatter));

    @Test
    void getFirstName() {
        assertEquals("Yari", clientCard.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Motomo", clientCard.getLastName());
    }

    @Test
    void getCnp() {
        assertEquals("199922", clientCard.getCnp());
    }

    @Test
    void setCnp() {
        clientCard.setCnp("-1");
        assertEquals("-1", clientCard.getCnp());
    }

    @Test
    void getBirthDate() {
        assertEquals("12.03.1993", clientCard.getBirthDate().format(dateFormatter));
    }

    @Test
    void getRegistrationDate() {
        assertEquals("01.01.2022", clientCard.getRegistrationDate().format(dateFormatter));
    }

    @Test
    void testToString() {
        assertEquals("ClientCard{id='1', firstName='Yari', lastName='Motomo', cnp='199922', birthDate='12.03.1993', registrationDate='01.01.2022'}", clientCard.toString());
    }
}