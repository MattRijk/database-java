package com.natickweb.database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DataSourceJNDI
 */
public class DataSourceJNDI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataSourceJNDI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initContext = new InitialContext();

			Context env = (Context) initContext.lookup("java:comp/env");

			// Database
			ds = (DataSource) env.lookup("jdbc/explorecalifornia");

		} catch (NamingException e) {
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		// use connection
		PrintWriter out = response.getWriter();

		out.println("Connected to Database/explorecalifonia");

		try {

			stmt = conn.createStatement();

			set = stmt.executeQuery("SELECT * FROM `tours`");

			while (set.next()) {
				System.out.println(set.getString("tourName") + ": ID: "
						+ set.getShort("tourId") + " Price: "
						+ set.getShort("price"));

			}

		} catch (Exception e) {
			System.err.println(e);

		}
	}

	/*****/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
