package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }


    @Test
    void shouldSendFormDeliveryCardChangeDate() {
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.generateUser().getCity());
        $("[data-test-id= date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id= date] input").sendKeys(DataGenerator.Registration.getDate(3));
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateUser().getName());
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateUser().getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(" [data-test-id='success-notification']").waitUntil(visible, 150000)
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + DataGenerator.Registration.getDate(3)));
    }
}
