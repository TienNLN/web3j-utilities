package com.tiennln.testaquariux.repositories;

import com.tiennln.testaquariux.entities.User;
import com.tiennln.testaquariux.entities.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author TienNLN on 02/04/2023
 */
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {
    List<UserWallet> findAllByUser(User user);
}
