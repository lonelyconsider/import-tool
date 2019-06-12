package com.leslie2019;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import com.csvreader.CsvReader;

public class CsvUtils {
	
	/***
	 * ��CSV�ļ��������б�
	 * @param filePath CSV�ļ�����·��
	 * @param hasTitle �Ƿ����ͷ
	 * @return
	 */
	public static ArrayList<String[]> ReadCsv(String filePath, boolean needTitle) {
		ArrayList<String[]> csvDataList = new ArrayList<String[]>();
		try {
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
			
			// �����Ҫ��ͷ���򽫵�һ����Ϊ��ͷ
			reader.readHeaders();
			if (needTitle) {csvDataList.add(reader.getValues());}
			
			// ���ж������ͷ������
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
