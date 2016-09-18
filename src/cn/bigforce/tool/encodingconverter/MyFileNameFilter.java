package cn.bigforce.tool.encodingconverter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class MyFileNameFilter implements FileFilter {

	String[] suffixs;
	/**
	 * use ';' to separate different suffix
	 * @param suffixs
	 */
	public MyFileNameFilter(String suffixs) {
		this.suffixs=suffixs.split(";");
	}

	@Override
	public boolean accept(File pathname) {
		if(pathname.isDirectory())
			return true;
		for(String suffix:suffixs){
			if(pathname.getName().endsWith(suffix))
				return true;
		}
		return false;
	}

}
