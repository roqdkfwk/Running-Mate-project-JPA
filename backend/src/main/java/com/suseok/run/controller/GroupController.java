package com.suseok.run.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@Tag(name = "GroupRestController", description = "그룹CRUD")
@RequiredArgsConstructor
public class GroupController {

}
