package com.myorg.javacourse.service;

import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;

import com.myorg.javacourse.model.Stock;
import com.myorg.javacourse.model.Portfolio;

/**
 * An instance of this class represents portfolio
 * 
 * @author Chen Halak JDK 1.7 4/23/2015
 */
public class PortfolioManager implements PortfolioManagerInterface {

	private DatastoreService datastoreService;
	Portfolio portfolio;

	/**
	 * @return portfolio with with data of stocks that in it
	 */
	public PortfolioInterface getPortfolio() {
		if (portfolio == null)
			init();

		return portfolio;
	}

	/**
	 * 
	 */
	private void init() {
		portfolio = new Portfolio();
		portfolio.setTitle("Exercise 8 portfolio");
		portfolio.updateBalance(10000);
		Stock PIH = new Stock();
		Stock AAL = new Stock();
		Stock CAAS = new Stock();
		PIH.setSymbol("PIH");
		AAL.setSymbol("AAL");
		CAAS.setSymbol("CAAS");
		PIH.setAsk(10.0F);
		AAL.setAsk(30.0F);
		CAAS.setAsk(20.0F);
		PIH.setBid(8.5F);
		AAL.setBid(25.5F);
		CAAS.setBid(15.5F);

		PIH.setDate("12/15/2014");
		AAL.setDate("12/15/2014");
		CAAS.setDate("12/15/2014");

		portfolio.addStock(PIH);
		portfolio.addStock(AAL);
		portfolio.addStock(CAAS);

		portfolio.buyStock("PIH", 20);
		portfolio.buyStock("AAL", 30);
		portfolio.buyStock("CAAS", 40);

		portfolio.sellStock("AAL", -1);

		portfolio.removeStock("CAAS");
	}

	@Override
	public void update() {
		
	}

	@Override
	public void setTitle(String title) {
		portfolio.setTitle(title);

	}

	@Override
	public void updateBalance(float value) throws PortfolioException {
		portfolio.updateBalance(value);

	}

	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStock(String symbol) throws PortfolioException {
		try {
			StockDto stock = MarketService.getInstance().getStock(symbol);
			portfolio.addStock(new Stock(stock));

		} catch (SymbolNotFoundInNasdaq e) {

		}
	}

	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException {
		portfolio.buyStock(symbol, quantity);
	}

	@Override
	public void sellStock(String symbol, int quantity)
			throws PortfolioException {
		portfolio.sellStock(symbol, quantity);

	}

	@Override
	public void removeStock(String symbol) throws PortfolioException {
		portfolio.removeStock(symbol);

	}
}
