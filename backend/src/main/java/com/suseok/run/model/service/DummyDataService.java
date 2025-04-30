package com.suseok.run.model.service;

import com.github.javafaker.Faker;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DummyDataService {

    private final UserRepository userRepository;
    private final Faker faker = new Faker(new Locale("ko"));

    @Transactional
    public void seed(int count) {
        List<User> batch = new ArrayList<>(1000);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setAddress(faker.address().fullAddress());
            user.setCreatedAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0, 365)));
            user.setEmail(String.format("dummy%05d@example.com", i));
            user.setExposure(false);
            user.setImg(null);
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUserId(String.format("user%05d", i));
            user.setUserName(faker.name().fullName());
            user.setUserNick(faker.name().firstName() + i);
            user.setUserPw("string");

            batch.add(user);
            if (batch.size() == 1000) {
                userRepository.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            userRepository.saveAll(batch);
        }
    }
}
