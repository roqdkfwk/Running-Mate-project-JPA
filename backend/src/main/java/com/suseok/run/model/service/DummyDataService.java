package com.suseok.run.model.service;

import com.github.javafaker.Faker;
import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.CommentRepository;
import com.suseok.run.model.repository.GroupRepository;
import com.suseok.run.model.repository.PostRepository;
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
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Faker faker = new Faker(new Locale("ko"));

    @Transactional
    public void seedUser(int count) {
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

    @Transactional
    public void seedGroup(int count) {
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

    @Transactional
    public void seedPost(int count) {
        List<Post> batch = new ArrayList<>(1000);
        User user = userRepository.findById(1L).get();
        Group group = groupRepository.findById(1L).get();
        for (int i = 0; i < count; i++) {
            Post post = new Post();
            post.setTitle("content" + i);
            post.setContent("content" + i);
            post.setAuthor(user);
            post.setGroup(group);
            post.setCreatedAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0, 365)));

            batch.add(post);
            if (batch.size() == 1000) {
                postRepository.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            postRepository.saveAll(batch);
        }
    }

    @Transactional
    public void seedComment(int count) {
        User user = userRepository.findById(1L).get();
        Post post = postRepository.findById(1L).get();
        List<Comment> batch = new ArrayList<>(1000);
        for (int i = 0; i < count; i++) {
            Comment comment = new Comment();
            comment.setContent("content" + i);
            comment.setCreatedAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0, 365)));
            comment.setAuthor(user);
            comment.setPost(post);

            batch.add(comment);
            if (batch.size() == 1000) {
                commentRepository.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            commentRepository.saveAll(batch);
        }
    }
}
