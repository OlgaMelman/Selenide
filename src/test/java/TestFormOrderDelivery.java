import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class TestFormOrderDelivery {
    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldPositivePathForOrderDeliveryCard() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Оренбург");
        String currentDay = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Петров Иван");
        $("[data-test-id=phone] input").setValue("+79226789877");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//*[contains(text(),'Успешно')]").should(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + currentDay), Duration.ofSeconds(15));

    }
}
