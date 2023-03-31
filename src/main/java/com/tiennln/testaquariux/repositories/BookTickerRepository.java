package com.tiennln.testaquariux.repositories;

import com.tiennln.testaquariux.entities.BookTicker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author TienNLN on 31/03/2023
 */
public interface BookTickerRepository extends JpaRepository<BookTicker, String> {

}
