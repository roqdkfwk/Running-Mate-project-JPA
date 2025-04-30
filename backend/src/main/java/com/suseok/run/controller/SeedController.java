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

    @PostMapping
    public ResponseEntity<String> seed(int count) {
        dummyDataService.seed(count);
        return ResponseEntity.ok(count + "개의 데이터가 생성되었습니다.");
    }
}
