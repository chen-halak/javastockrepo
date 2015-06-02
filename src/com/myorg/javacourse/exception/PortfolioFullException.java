package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class PortfolioFullException extends PortfolioException {
	public PortfolioFullException(int MAX_PORTFOLIO_SIZE) {
		super("Can't add new stock,portfolio can have only "
				+ MAX_PORTFOLIO_SIZE + " stocks");
	}

}
