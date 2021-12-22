package com.nagarro.unittesting.eBroker.dao;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.unittesting.eBroker.model.EquityDetailsBE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EquityRepositoryTest {

	@Autowired
	private EquityRepository equityRepository;

	@Test
	void testBuySell() {
		EquityDetailsBE equity = new EquityDetailsBE(1, 1, 1000);
		equityRepository.save(equity);

		MatcherAssert.assertThat(equityRepository.findAll().size(), CoreMatchers.is(1));

		equityRepository.delete(equity);

		MatcherAssert.assertThat(equityRepository.findAll().size(), CoreMatchers.is(0));

	}

}
