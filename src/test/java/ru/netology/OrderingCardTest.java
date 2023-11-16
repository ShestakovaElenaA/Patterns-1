package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.files.DownloadActions.click;

public class OrderingCardTest {
    private String generateMeetingDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldOrderingCard() {

        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String meetingDate = generateMeetingDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("BackSpace");
        $("[data-test-id=date] input").setValue(String.valueOf(meetingDate));
        $("[data-test-id=name] input").setValue("Иванова Олеся");
        $("[data-test-id=phone] input").setValue("+79420044521");
        $(By.className("checkbox__box")).click();
        $(".grid-row button").click();
        $("[data-test-id=success-notification]> div.notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + meetingDate));

    }
    @Test
    void shouldRescheduleOrderingCard() {

        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String meetingDate = generateMeetingDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("BackSpace");
        $("[data-test-id=date] input").setValue(String.valueOf(meetingDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79220012581");
        $(By.className("checkbox__box")).click();
        $(By.className("button")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] > div.notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Необходимо подтверждение"));
        $("[data-test-id=replan-notification]").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(50)).shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n" +
                "\n" +
                "Перепланировать"));
    }
}

