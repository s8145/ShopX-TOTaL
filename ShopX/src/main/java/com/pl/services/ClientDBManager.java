package com.pl.services;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.pl.projectfiles.*;

public class ClientDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addCustomerStmt;
	private PreparedStatement getCustomerStmt;
	private PreparedStatement deleteAllCustomerStmt;
	private PreparedStatement deleteCustomerStmt;
	private PreparedStatement findCustomerStmt;

	
	public ClientDBManager() {
		
		
		Properties props = new Properties();
		try {
			
			try {		
				props.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			
			conn = DriverManager.getConnection(props.getProperty("url"));
			stmt = conn.createStatement();
			boolean customerTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while(rs.next()) {
				if("client".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					customerTableExists = true;
					break;
				}
			}


			if(!customerTableExists) {
				stmt.executeUpdate("" +
						"CREATE TABLE client(" +
						"id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
						"name varchar(20)," +
						"surname varchar(20)," +
						")");
			}

			addCustomerStmt = conn.prepareStatement("" +
					"INSERT INTO customer (name, surname) VALUES (?, ?)" +
					"");

			getCustomerStmt = conn.prepareStatement("" +
					"SELECT * FROM client" +
					"");

			deleteAllCustomerStmt = conn.prepareStatement("" +
					"DELETE FROM client" +
					"");

			findCustomerStmt = conn.prepareStatement("" +
					"SELECT id FROM client WHERE surname = ?" +
					"");

			deleteCustomerStmt = conn.prepareStatement("" +
					"DELETE FROM customer WHERE id = ?" +
					"");



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
		}
	
	public void addCustomer(Client customer) {
		try {
			addCustomerStmt.setString(1, customer.getName());
			addCustomerStmt.setString(2, customer.getSurname());
			addCustomerStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Client> getAllCustomers() {
		
		List<Client> customers = new ArrayList<Client>();

		try {
			ResultSet rs = getCustomerStmt.executeQuery();

			while(rs.next()) {
				customers.add(new Client(rs.getString("name"), rs.getString("surname")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
	public void deleteAllCustomers() {
		try {
			deleteAllCustomerStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> searchCustomerbySurname(String surname) {
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findCustomerStmt.setString(1, surname);
			ResultSet rs = findCustomerStmt.executeQuery();
			
			while (rs.next())
				result.add(rs.getInt("ID"));
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;			
	}

	public void deleteCustomer(List<Integer> list) {
		try {
			for (Integer id : list) {
				deleteCustomerStmt.setInt(1, id);
				deleteCustomerStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	


}