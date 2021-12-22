package com.nagarro.unittesting.eBroker.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.unittesting.eBroker.model.EquityDetailsBE;

@Repository
public interface EquityRepository extends JpaRepository<EquityDetailsBE,Long>{

	Optional<EquityDetailsBE> findByEquityIdAndTraderId(Integer equityId, Integer traderId);

}
