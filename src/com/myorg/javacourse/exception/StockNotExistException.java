package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * An instance of this class represent exception that it thrown when stock not 
 * exist in portfolio
 * @author Chen Halak since JDK 1.7 6/5/15
 */
public class StockNotExistException extends PortfolioException {

	public StockNotExistException(String symbol) {
		super("Sorry, The stock " + symbol + " not exist in portfolio");
	}

}
