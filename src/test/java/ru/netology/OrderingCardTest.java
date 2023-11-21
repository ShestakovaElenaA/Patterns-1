package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class OrderingCardTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }
    @Test
    void shouldPlanAndReplanMeetingByOrderingCard() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 5;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 6;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("BackSpace");
        $("[data-test-id=date] input").setValue(String.valueOf(firstMeetingDate));
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $(By.className("checkbox__box")).click();
        $(".grid-row button").click();
        $("[data-test-id=success-notification]> div.notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("BackSpace");
        $("[data-test-id=date] input").setValue(String.valueOf(secondMeetingDate));
        $(".grid-row button").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] > div.notification__title").shouldBe(visible).shouldHave(exactText("Необходимо подтверждение"));
        $("[data-test-id=replan-notification]").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}

