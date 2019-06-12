package com.leslie2019;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileChooser {
	/**
	 * 创建一个新的JFileChooser
	 */
	private String filename;
	
	public MyFileChooser() {
		JFileChooser chooser = new JFileChooser(new File("C:\\DATA"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"csv file", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			filename = chooser.getSelectedFile().getAbsolutePath();
		}
	}
	
	public String getFileName() {	
		return filename;
	}
}
