package com.myorg.javacourse.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock {
	private String symbol;
	private float ask, bid;
	private Date date;

	public Stock(Stock stock){
		this(stock.symbol,stock.ask,stock.bid,stock.date);

	}	


	public Stock(String symbol, float ask, float bid, Date date) {
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.date = date;
	}

	public Stock() {
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

	public String getHtmlDescription() {
		DateFormat format = new SimpleDateFormat("MM/d/yyyy");
		String stockDate = format.format(getDate());
		return "<b>Stocksymbol</b>:" + getSymbol() + ", <b>Ask</b>:" + getAsk()
				+ ", <b>Bid</b>:" + getBid() + ", <b>Date</b>:" + stockDate
				+ "<BR>";
	}

}
