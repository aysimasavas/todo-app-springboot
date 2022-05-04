package com.aysimasavas.mytodo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aysimasavas.mytodo.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}
