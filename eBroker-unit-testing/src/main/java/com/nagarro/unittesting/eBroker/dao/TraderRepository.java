package com.nagarro.unittesting.eBroker.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.unittesting.eBroker.model.TraderDetailsBE;

@Repository
public interface TraderRepository extends JpaRepository<TraderDetailsBE, Long> {

	Optional<TraderDetailsBE> findByTraderId(Integer traderId);

}
