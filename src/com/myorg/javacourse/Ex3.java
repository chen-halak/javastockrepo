package com.myorg.javacourse;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Ex3 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		int radius = 50;
		double radiousSquared = java.lang.Math.pow(radius, 2);
		double area = radiousSquared * Math.PI;
		String line1 = new String("calculation 1: Area of circle with radius "
				+ radius + " is " + area + " squre-cm");
		resp.getWriter().print(line1 + "<BR>");

		int b = 30;
		double angle = Math.toRadians(b);
		int hypotenuse = 50;
		double sin = Math.sin(angle);
		double opposite = sin * hypotenuse;
		String line2 = new String(
				"calculation 2: Length of opposite where angle B is 30 degrees and Hypotenuse length is 50 cm is: "
						+ opposite + " cm");
		resp.getWriter().print(line2 + "<BR>");

		double solution = java.lang.Math.pow(20, 13);
		String line3 = new String("calculation 3: Power of 20 with exp 13 is: " + solution);
		resp.getWriter().print(line3 + "<BR>");

	}
}
