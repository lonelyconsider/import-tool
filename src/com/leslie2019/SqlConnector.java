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
        	// 连接数据库
            conn = DriverManager.getConnection(url, "sa", "123456");
            conn.setAutoCommit(false);
            // 建立Statement对象
            stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println("Database connect failed.");
		}
    }
	
	/**
	 * 插入
	 * @param sql
	 * @throws SQLException
	 */
	public void insertOneRecord(String sql) throws SQLException {
		stmt.executeUpdate(sql);
	}
	
	/**
	 * 截断表
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
	 * 提交
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
	 * 回滚
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
	 * 删除
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
	 * 查询
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
