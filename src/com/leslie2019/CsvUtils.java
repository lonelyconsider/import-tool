package com.leslie2019;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import com.csvreader.CsvReader;

public class CsvUtils {
	
	/***
	 * 读CSV文件到数组列表
	 * @param filePath CSV文件绝对路径
	 * @param hasTitle 是否带表头
	 * @return
	 */
	public static ArrayList<String[]> ReadCsv(String filePath, boolean needTitle) {
		ArrayList<String[]> csvDataList = new ArrayList<String[]>();
		try {
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
			
			// 如果需要表头，则将第一行作为表头
			reader.readHeaders();
			if (needTitle) {csvDataList.add(reader.getValues());}
			
			// 逐行读入除表头的数据
			while (reader.readRecord()) {
				csvDataList.add(reader.getValues());
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csvDataList;

	}
}
