package com.leslie2019;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SqlConnector {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public SqlConnector() {
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=devDWH;";
        try {
        	// �������ݿ�
            conn = DriverManager.getConnection(url, "sa", "123456");
            conn.setAutoCommit(false);
            // ����Statement����
            stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println("Database connect failed.");
		}
    }
	
	/**
	 * ����
	 * @param sql
	 * @throws SQLException
	 */
	public void insertOneRecord(String sql) throws SQLException {
		stmt.executeUpdate(sql);
	}
	
	/**
	 * �ضϱ�
	 * @param sql
	 */
	public void truncateTable(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Truncate table failure, please check your database!");
			e.printStackTrace();
		}
	}
	
	/**
	 * �ύ
	 * @throws SQLException
	 */
	public void close() {
		try {
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ع�
	 * @throws SQLException 
	 */
	public void rollBack() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��
	 * @param sql
	 */
	public void delete(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Delete data failure, please check your database!");
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ѯ
	 */
	public ResultSet select(String sql){
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
