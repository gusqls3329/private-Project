package com.example.beenproject.user.jpa;

import com.example.beenproject.eneities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
