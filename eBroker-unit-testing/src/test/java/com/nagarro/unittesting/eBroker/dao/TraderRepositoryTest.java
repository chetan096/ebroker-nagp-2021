package com.nagarro.unittesting.eBroker.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.unittesting.eBroker.model.TraderDetailsBE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TraderRepositoryTest {

	@Autowired
	private TraderRepository traderRepository;

	@Test
	void testCreatTraderAndAddFund() {
		// 2 traders already exist by import.sql
		TraderDetailsBE trader = new TraderDetailsBE(3, 1000);
		traderRepository.save(trader);
		MatcherAssert.assertThat(traderRepository.findAll().size(), CoreMatchers.is(3));

//		traderRepository.addFund(2000, 2);

		TraderDetailsBE traderDetailsBE = traderRepository.findByTraderId(3).get();
		traderDetailsBE.setFundsAvailable(2000);
		traderRepository.save(traderDetailsBE);
		traderDetailsBE = traderRepository.findByTraderId(3).get();
		MatcherAssert.assertThat(traderDetailsBE.getFundsAvailable(), CoreMatchers.is(2000));

	}

}
