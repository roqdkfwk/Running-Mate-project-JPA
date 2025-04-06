package com.suseok.run.model.repository;

import com.suseok.run.model.dto.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
