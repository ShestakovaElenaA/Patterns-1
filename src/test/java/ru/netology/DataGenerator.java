package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

public class DataGenerator {
    private Faker faker;

    @BeforeEach
    void setUppAll() {
        faker = new Faker(new Locale("ru"));
    }

    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {}

        public static RegistrationByOrderingCardinfo generateByCard(String locale) {
            Faker faker = new Faker(new Locale("ru"));
            return new RegistrationByOrderingCardinfo(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }
}
