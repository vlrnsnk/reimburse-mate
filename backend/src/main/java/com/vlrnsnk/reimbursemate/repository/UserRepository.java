package com.vlrnsnk.reimbursemate.repository;

import com.vlrnsnk.reimbursemate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
