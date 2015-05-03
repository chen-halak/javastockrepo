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
		portfolio.setTitle("Portpolio # 1");
		Stock PIH = new Stock();
		Stock AAL = new Stock();
		Stock CAAS = new Stock();
		PIH.setSymbol("PIH");
		AAL.setSymbol("AAL");
		CAAS.setSymbol("CAAS");
		PIH.setAsk(13.1F);
		AAL.setAsk(5.78F);
		CAAS.setAsk(32.2F);
		PIH.setBid(12.4F);
		AAL.setBid(5.5F);
		CAAS.setBid(31.5F);

		PIH.setDate("11/15/2014");
		AAL.setDate("11/15/2014");
		CAAS.setDate("11/15/2014");
		portfolio.addStock(PIH);
		portfolio.addStock(AAL);
		portfolio.addStock(CAAS);
		return portfolio;
	}
}
