package com.leslie2019;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ReadCsvFile {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("");
			
			int n = in.nextInt(); //��һ��ѡ����
			int n1 = 0; // �ڶ���ѡ����
			if (n == 7) {
				System.out.println("\nPlease input a number to choose...");
				n1 = in.nextInt();
			}
			// in.close();
			// ѡ���ļ�����ȡ�ļ���
			MyFileChooser chooser = new MyFileChooser();
			String filename = chooser.getFileName();
			// ���Գ���ִ��ʱ��
			long startTime = System.currentTimeMillis(); // ��ʼʱ��
			long endTime; // ����ʱ��
			// ���ļ�
			ArrayList<String[]> dataList = CsvUtils.ReadCsv(filename, false);
			switch (n) {
			case 1:
				transferData(dataList);
				break;
			default:
				System.out.println("Incorrect input, please enter again!");
				break;
			}
			endTime = System.currentTimeMillis();
			System.out.println("The time cost is: " + (endTime - startTime) / 1000.0 + "s");
		}
	}

	private static void transferData(ArrayList<String[]> dataList) {
		// �������ݿ��������
		SqlConnector sqlConnector = new SqlConnector();
		// ɾ��������
		sqlConnector.truncateTable("truncate table xxx");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(new Date());
		StringBuilder sql = new StringBuilder();
		boolean isFault = false; // �Ƿ����

		/*
		 * ���ڴ���0, 5, 20, 21, 22, 30, 32, 34, 35, 53, 55, 59 ��; 
		 * ����4, 29  ��; 
		 * ��Ӣ���ַ�����12, 60 ��;
		 * ��ѧ��������61
		 * ���ͣ� 24
		 */
		for (int row = 0; row < dataList.size(); row++) {
			// ��ʾ����
			if (row == dataList.size() / 5) {
				System.out.println("Completed 1/5");
			} else if (row == dataList.size() / 5 * 2) {
				System.out.println("Completed 2/5");
			} else if (row == dataList.size() / 5 * 3) {
				System.out.println("Completed 3/5");
			} else if (row == dataList.size() / 5 * 4) {
				System.out.println("Completed 4/5");
			} else if (row == dataList.size()-1) {
				System.out.println("Completed 5/5");
			}
			sql.append("insert into xxx values(");
			for (int i = 0; i < 62; i++) {
				// ���ڴ��� pass

				// ���/���ʹ���
				if (i == 24) {
					if (dataList.get(row)[i].equals("")) {
						sql.append("null");
					} else {
						sql.append(dataList.get(row)[i]);
					}
				} else if (i == 4 || i == 29) {
					if (dataList.get(row)[i].equals("")) {
						sql.append("0");
					} else {
						sql.append(dataList.get(row)[i].replace(",", ""));
					}
				} else if (i == 12 || i == 60) { // �������Ļ������ַ�
					sql.append("N'" + dataList.get(row)[i].replace("'", "''") + "'");
				} else if (i == 61) {
					if (dataList.get(row)[i].contains("E")) { // ��ѧ������
						BigDecimal bd = new BigDecimal(dataList.get(row)[i]);
						sql.append("'" + bd.toPlainString() + "'");
					} else {
						sql.append("'" + dataList.get(row)[i] + "'");
					}
				} else {
					sql.append("'" + dataList.get(row)[i].replace("'", "''") + "'");
				}
				sql.append(',');
			}
			sql.append("'" + nowTime + "'");
			sql.append(')');
			try {
				sqlConnector.insertOneRecord(sql.toString());
				sql.delete(0, sql.length());
			} catch (SQLException e) {
				System.out.println(sql.toString());
				System.out.println(String.format("Line %s maybe ircorrect, please check your csv file and restart this program!", row + 2));
				isFault = true;
				break;
			}
		}
		if (isFault) {
			sqlConnector.rollBack();
		}
		sqlConnector.close();
	}
}
