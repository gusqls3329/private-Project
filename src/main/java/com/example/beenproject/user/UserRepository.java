package com.example.beenproject.user;

import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.ProvideType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);
    Optional<User> findByUidAndProvideType(String uid, ProvideType provideType);
}
