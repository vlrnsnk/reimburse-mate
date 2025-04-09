package com.vlrnsnk.reimbursemate.repository;

import com.vlrnsnk.reimbursemate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username
     *
     * @param username the username to search for
     * @return the user if found
     */
    Optional<User> findByUsername(String username);

}
