package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * An instance of this class represent expception that it thrown 
 * when the user give amount of stocks that bigger from the the amount stocks
 * that he have
 * @author Chen Halak since JDK 1.7 6/5/15
 */
public class LackOfStocksToSellException extends PortfolioException {

	public LackOfStocksToSellException() {
		super("Sorry, Not enough stocks to sell");
	}
}
