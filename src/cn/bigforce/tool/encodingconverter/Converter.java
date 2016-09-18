package cn.bigforce.tool.encodingconverter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Converter {
	public static final int UTF8_GBK=0; 
	public static final int GBK_UTF8=1; 
	
	int mode;
	public Converter(int mode){
		this.mode=mode;
	}

	public void process(File file) throws IOException{
		switch (mode) {
		case UTF8_GBK:
			process(file,"utf-8","gbk");
			break;
		case GBK_UTF8:
			process(file,"gbk","utf-8");
			break;
		}
	}
	private void process(File file,String source,String target) throws IOException{
		System.out.println("process....");
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, source);
		BufferedReader br = new BufferedReader(isr);

		List<String> list = new ArrayList<>();
		String line=null;
		while ((line=br.readLine())!=null) {
			list.add(line);
		}
		
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, target);
		BufferedWriter bw = new BufferedWriter(osw);
		
		for(String str : list){
			bw.write(str);
			bw.newLine();
		}
		br.close();
		bw.close();
		osw.close();
		isr.close();
	}

}
