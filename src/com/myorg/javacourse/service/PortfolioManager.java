package com.myorg.javacourse.service;

import com.myorg.javacourse.model.Stock;
import com.myorg.javacourse.model.Portfolio;
/**
 * An instance of this class represents portfolio
 * @author Chen Halak
 *JDK 1.7
 *4/23/2015
 */
public class PortfolioManager {
	/**
	 * @return portfolio with with data of stocks that in it
	 */
	public Portfolio getPortfolio() {
		Portfolio portfolio=new Portfolio();
		portfolio.setTitle("Exercise 7 portfolio");
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
		
		return portfolio;
	}
}
