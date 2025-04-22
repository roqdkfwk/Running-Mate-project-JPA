package com.suseok.run.model.repository;

import com.suseok.run.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(String userId);

    @Query("SELECT u FROM User u WHERE u.userNick = :userNick")
    Optional<User> findByUserNick(String userNick);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET " +
            "u.userName = :userName, " +
            "u.userNick = :userNick, " +
            "u.address = :address " +
            "WHERE u.userSeq = :userSeq")
    int one(Long userSeq, String userName, String userNick, String address);
}
