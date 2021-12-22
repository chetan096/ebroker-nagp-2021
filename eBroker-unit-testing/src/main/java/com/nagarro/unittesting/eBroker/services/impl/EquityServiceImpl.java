package com.nagarro.unittesting.eBroker.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.unittesting.eBroker.dao.EquityRepository;
import com.nagarro.unittesting.eBroker.dao.TraderRepository;
import com.nagarro.unittesting.eBroker.dtos.requests.BuyEquityRequstDO;
import com.nagarro.unittesting.eBroker.model.EquityDetailsBE;
import com.nagarro.unittesting.eBroker.model.TraderDetailsBE;
import com.nagarro.unittesting.eBroker.services.IEquityService;
import com.nagarro.unittesting.eBroker.utils.EquityUtililty;

@Service
public class EquityServiceImpl implements IEquityService {

	@Autowired
	private EquityRepository equityRepo;

	@Autowired
	private TraderRepository traderRepo;

	@Override
	@Transactional
	public String buyEquity(Integer equityId, BuyEquityRequstDO request) throws Exception {
		if (!EquityUtililty.checkForValidDayAndTime()) {
			throw new Exception("validation.failed");
		}
		Optional<TraderDetailsBE> optionalTrader = this.traderRepo.findByTraderId(request.getTraderId());
		if (optionalTrader.isEmpty()) {
			throw new Exception("trader.notfound");
		} else {
			TraderDetailsBE traderDetails = optionalTrader.get();
			if (traderDetails.getFundsAvailable() < request.getFunds()) {
				throw new Exception("fund.notavailable");
			} else {
				traderDetails.setFundsAvailable(traderDetails.getFundsAvailable() - request.getFunds());
				EquityDetailsBE equityDetails = new EquityDetailsBE(traderDetails.getTraderId(), equityId,
						request.getFunds());
				equityRepo.save(equityDetails);
				traderRepo.save(traderDetails);
			}
		}

		return "Success";
	}

	@Override
	@Transactional
	public String sellEquity(Integer equityId, Integer traderId) throws Exception {
		if (!EquityUtililty.checkForValidDayAndTime()) {
			throw new Exception("validation.failed");
		}
		Optional<TraderDetailsBE> optionalTrader = this.traderRepo.findByTraderId(traderId);
		if (optionalTrader.isEmpty()) {
			throw new Exception("trader.notfound");
		} else {
			Optional<EquityDetailsBE> optionalEquity = this.equityRepo.findByEquityIdAndTraderId(equityId, traderId);
			if (optionalEquity.isEmpty()) {
				throw new Exception("equity.notfound");
			} else {
				optionalTrader.get().setFundsAvailable(optionalEquity.get().getFund() + optionalTrader.get().getFundsAvailable());
				this.traderRepo.save(optionalTrader.get());
				this.equityRepo.delete(optionalEquity.get());
			}
		}
		return "Success";
	}

	@Override
	public String addFunds(Integer traderId, Integer fundsToBeAdded) throws Exception {
		Optional<TraderDetailsBE> optionalTrader = this.traderRepo.findByTraderId(traderId);
		if (optionalTrader.isEmpty()) {
			throw new Exception("trader.notfound");
		} else {
			TraderDetailsBE trader = optionalTrader.get();
			trader.setFundsAvailable(trader.getFundsAvailable() + fundsToBeAdded);
			this.traderRepo.save(trader);
		}

		return "Success";
	}

}
