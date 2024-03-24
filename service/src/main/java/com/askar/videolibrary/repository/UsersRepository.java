package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {

}
