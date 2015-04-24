package com.myorg.javacourse.model;

import com.myorg.javacourse.Stock;
/**
 *An instance of this class represent portfilo-manage stocks on one  place
 * @author Chen Halak
 *since JDK 1.7
 *4/23/15
 */
public class Portfolio {
	/**
	 * the maximum size of stocks in the portpolio
	 */
	private final static int MAX_PORTFOLIO_SIZE = 5;
	private final static int BUY = 0;
	private final static int SELL = 1;
	private final static int REMOVE = 2;
	private final static int HOLD = 3;
	/**
	 * the name of the portfolio
	 */
	private String title;
	/**
	 * array that include instances of stocks 
	 */
	private Stock[] stocks;
	/**
	 * the physical size of the array that include instances of stocks
	 */
	private int portfolioSize = 0;
	private int recommendation;
	private int stockQuantity;
/**
 * create instance of stocks
 * returned not simplified
 */
	public Portfolio() {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
/**
 * add sotock to the portfolio
 * returned not simplified
 * @param stock the stock that added to the portfolio
 */
	public void addStock(Stock stock) {
		if (portfolioSize < MAX_PORTFOLIO_SIZE) {
			stocks[portfolioSize] = stock;
			portfolioSize++;
		} else {
			System.out.println("lack of memory");
		}
	}
/**
 * @return stocks array
 */
	public Stock[] getStocks() {
		return stocks;
	}
/**
 * @return string with portfolio html code
 */
	public String getHtmlString() {
		String result = "<h1>porfolio title</h1>";
		for (int i = 0; i < portfolioSize; i++) {
			result += stocks[i].getHtmlDescription();
		}
		return result;
	}
}
