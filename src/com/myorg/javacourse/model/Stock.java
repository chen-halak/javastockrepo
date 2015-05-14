package com.myorg.javacourse.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMANDATION;

/**
 * An instance of this class represent a stock
 * 
 * @author Chen Halak
 * @JDK 1.7 @ 5.3.2015
 */
public class Stock {
	private String symbol;
	private float ask, bid;
	private Date date;
	private ALGO_RECOMMANDATION recommendation;
	private int stockQuantity;

	/**
	 * @param stock -instance of the class Stock 
	 * returns stock that same to the parameter
	 * 
	 */
	public Stock(Stock stock) {
		this(new String(stock.getSymbol()), stock.getAsk(), stock.getBid(),
				new Date(stock.getDate().getTime()), stock.getRecommendation());
	}

	/**
	 * @param symbol-name of stock
	 * @param ask-the buying price
	 * @param bid-the selling buying
	 * @param date
	 * returns instance of class Stock with the parameters
	 */
	public Stock(String symbol, float ask, float bid, Date date,
			ALGO_RECOMMANDATION recommandation) {
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.date = date;
		this.setRecommendation(recommandation);
	}

	/**
	 * returned is not simplified 
	 * create istance of Stock
	 */
	public Stock() {
		recommendation = ALGO_RECOMMANDATION.HOLD;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public float getAsk() {
		return ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	public float getBid() {
		return bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String newdate) {
		try {
			DateFormat format = new SimpleDateFormat("MM/d/yyyy");
			Date tempDate = null;
			tempDate = format.parse(newdate);
			setDate(tempDate);
		} catch (ParseException e) {
		}
	}
	public ALGO_RECOMMANDATION getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ALGO_RECOMMANDATION recommendation) {
		this.recommendation = recommendation;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	/**
	 * returns details of stock that used in html code
	 */
	public String getHtmlDescription() {
		DateFormat format = new SimpleDateFormat("MM/d/yyyy");
		String stockDate = format.format(getDate());
		return "<b>Stocksymbol</b>:" + getSymbol() + ", <b>Ask</b>:" + getAsk()
				+ ", <b>Bid</b>:" + getBid() + ", <b>Date</b>:" + stockDate
				+ ", <b>Quantity</b>:" + stockQuantity;
	}

	

}
