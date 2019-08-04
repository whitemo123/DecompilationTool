package cn.denghui.smali2java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void listFiles(File dir, List<File> list) {
	    if (dir.isDirectory()) {
	      for (File file : dir.listFiles())
	        listFiles(file, list);
	    }else{
	      list.add(dir);
	    }
	}
	
	public static List<String> readFile(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while((line=br.readLine())!=null){
			result.add(line);
		}
		br.close();
		return result;
	}
	public static void printFile(File file,List<String> data) throws FileNotFoundException{
		PrintStream ps = new PrintStream(file);
		for (String line : data) {
			ps.println(line);
		}
		ps.flush();
		ps.close();
	}
}
