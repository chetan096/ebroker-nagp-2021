package com.nagarro.unittesting.eBroker.services.impl;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarro.unittesting.eBroker.dao.EquityRepository;
import com.nagarro.unittesting.eBroker.dao.TraderRepository;
import com.nagarro.unittesting.eBroker.dtos.requests.BuyEquityRequstDO;
import com.nagarro.unittesting.eBroker.model.EquityDetailsBE;
import com.nagarro.unittesting.eBroker.model.TraderDetailsBE;
import com.nagarro.unittesting.eBroker.utils.EquityUtililty;

@ExtendWith(MockitoExtension.class)
public class EquityServiceImplTest {

	@InjectMocks
	private EquityServiceImpl target;

	@Mock
	private EquityRepository equityRepo;

	@Mock
	private TraderRepository traderRepo;

	@Captor
	private ArgumentCaptor<TraderDetailsBE> trader;

	@Test
	@DisplayName("should throw exception when invalid day and time on buying equity")
	void testBuyEquity_InValidTime() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.FALSE);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
			Exception exception = Assertions.assertThrows(Exception.class,
					() -> this.target.buyEquity(1, new BuyEquityRequstDO()));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("validation.failed"));
		}
	}

	@Test
	@DisplayName("should throw exception when trader not in db on buying equity")
	void testBuyEquity_InvalidTraderId() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
			Exception exception = Assertions.assertThrows(Exception.class,
					() -> this.target.buyEquity(1, new BuyEquityRequstDO(1, 100)));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("trader.notfound"));
		}
	}

	@Test
	@DisplayName("should throw exception when funds are insufficient on buying equity")
	void testBuyEquity_FundsInsufficient() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt()))
					.thenReturn(Optional.of(new TraderDetailsBE(1, 100)));
			Exception exception = Assertions.assertThrows(Exception.class,
					() -> this.target.buyEquity(1, new BuyEquityRequstDO(1, 200)));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("fund.notavailable"));
		}
	}

	@Test
	@DisplayName("should buy equity when each input is valid")
	void testBuyEquity() throws Exception {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt()))
					.thenReturn(Optional.of(new TraderDetailsBE(1, 100)));
			MatcherAssert.assertThat(this.target.buyEquity(1, new BuyEquityRequstDO(1, 50)),
					CoreMatchers.is("Success"));
			Mockito.verify(this.traderRepo, Mockito.times(1)).save(trader.capture());
			MatcherAssert.assertThat(trader.getValue().getFundsAvailable(), CoreMatchers.is(50));
		}
	}

	@Test
	@DisplayName("should throw exception when invalid day and time on selling equity")
	void testSellEquity_InValidTime() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.FALSE);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
			Exception exception = Assertions.assertThrows(Exception.class, () -> this.target.sellEquity(1, 1));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("validation.failed"));
		}
	}

	@Test
	@DisplayName("should throw exception when trader not in db on selling equity")
	void testSellEquity_InvalidTraderId() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
			Exception exception = Assertions.assertThrows(Exception.class, () -> this.target.sellEquity(1, 1));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("trader.notfound"));
		}
	}

	@Test
	@DisplayName("should throw exception when trader and equity are not mapped in db on selling equity")
	void testSellEquity_EquityIdNotMapped() {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt()))
					.thenReturn(Optional.of(new TraderDetailsBE(1, 100)));
			Mockito.when(this.equityRepo.findByEquityIdAndTraderId(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(Optional.ofNullable(null));
			Exception exception = Assertions.assertThrows(Exception.class, () -> this.target.sellEquity(1, 1));
			MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("equity.notfound"));
		}
	}

	@Test
	@DisplayName("should sell equity on each valid input")
	void testSellEquity() throws Exception {
		try (MockedStatic<EquityUtililty> utilites = Mockito.mockStatic(EquityUtililty.class)) {
			utilites.when(EquityUtililty::checkForValidDayAndTime).thenReturn(Boolean.TRUE);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
			TraderDetailsBE valueTrader = new TraderDetailsBE(1, 100);
			Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt()))
					.thenReturn(Optional.of(valueTrader));
			EquityDetailsBE value = new EquityDetailsBE(1, 1, 100);
			Mockito.when(this.equityRepo.findByEquityIdAndTraderId(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(Optional.of(value));
			MatcherAssert.assertThat(this.target.sellEquity(1, 1), CoreMatchers.is("Success"));
			Mockito.verify(this.traderRepo, Mockito.times(1)).save(valueTrader);
			Mockito.verify(this.equityRepo, Mockito.times(1)).delete(value);
		}
	}

	@Test
	@DisplayName("should throw exception when trader not in db on adding funds")
	void testAddFunds_InvalidTraderId() {
		Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
		Exception exception = Assertions.assertThrows(Exception.class, () -> this.target.addFunds(1, 100));
		MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("trader.notfound"));
	}

	@Test
	@DisplayName("should add funds to trader details")
	void testAddFunds() throws Exception {
		Mockito.when(this.traderRepo.findByTraderId(Mockito.anyInt()))
				.thenReturn(Optional.of(new TraderDetailsBE(1, 100)));
		MatcherAssert.assertThat(this.target.addFunds(1, 100),CoreMatchers.is("Success"));
		Mockito.verify(this.traderRepo, Mockito.times(1)).save(trader.capture());
		MatcherAssert.assertThat(trader.getValue().getFundsAvailable(), CoreMatchers.is(200));

	}
}
