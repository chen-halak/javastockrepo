package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * An instance of this class represent exception that it thrown when adding more stocks
 * than max stocks limitation
 * 
 * @author Chen Halak since JDK 1.7 6/5/15
 */
public class PortfolioFullException extends PortfolioException {
	public PortfolioFullException(int MAX_PORTFOLIO_SIZE) {
		super("Can't add new stock,portfolio can have only "
				+ MAX_PORTFOLIO_SIZE + " stocks");
	}

}
