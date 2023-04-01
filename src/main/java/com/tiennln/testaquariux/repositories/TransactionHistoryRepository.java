package com.tiennln.testaquariux.repositories;

import com.tiennln.testaquariux.entities.TransactionHistory;
import com.tiennln.testaquariux.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author TienNLN on 02/04/2023
 */
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

    List<TransactionHistory> findAllByUser(User user);
}
