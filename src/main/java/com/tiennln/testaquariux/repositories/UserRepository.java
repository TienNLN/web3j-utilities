package com.tiennln.testaquariux.repositories;

import com.tiennln.testaquariux.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 *
 * @author TienNLN on 01/04/2023
 */
public interface UserRepository extends JpaRepository<User, String> {

}
