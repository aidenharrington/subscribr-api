package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    
}
