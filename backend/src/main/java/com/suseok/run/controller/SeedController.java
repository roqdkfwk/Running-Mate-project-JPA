package com.suseok.run.controller;

import com.suseok.run.model.service.DummyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class SeedController {

    private final DummyDataService dummyDataService;

    @PostMapping("/users")
    public ResponseEntity<String> seedUser(int count) {
        dummyDataService.seedUser(count);
        return ResponseEntity.ok(count + "개의 데이터가 생성되었습니다.");
    }

    @PostMapping("/groups")
    public ResponseEntity<String> seedGroup(int count) {
        dummyDataService.seedGroup(count);
        return ResponseEntity.ok(count + "개의 데이터가 생성되었습니다.");
    }

    @PostMapping("/posts")
    public ResponseEntity<String> seedPost(int count) {
        dummyDataService.seedPost(count);
        return ResponseEntity.ok(count + "개의 데이터가 생성되었습니다.");
    }

    @PostMapping("/comments")
    public ResponseEntity<String> seedComment(int count) {
        dummyDataService.seedComment(count);
        return ResponseEntity.ok(count + "개의 데이터가 생성되었습니다.");
    }
}
