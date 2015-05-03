package com.myorg.javacourse.servelt;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.service.PortfolioManager;

@SuppressWarnings("serial")
public class PortfolioServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		PortfolioManager portfolioManager = new PortfolioManager();
		Portfolio portfolio = portfolioManager.getPortfolio();
		Portfolio portfolio2 = new Portfolio(portfolio);
		portfolio2.setTitle("Portpolio # 2");
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
		portfolio.removeFirstStock();
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
		portfolio2.getLastStock().setBid(55.55f);
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
		
	}
}
