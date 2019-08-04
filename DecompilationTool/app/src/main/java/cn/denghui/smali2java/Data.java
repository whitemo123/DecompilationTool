package cn.denghui.smali2java;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {
	
	/* 
	 * 
	 * 已解决问题
	 * 1、指令、类型转换问题	
	 * 2、寄存器取值赋值问题
	 * 3、unicode中文转换及16进制转换问题
	 * 4、寄存器赋值过长或过短问题
	 * 5、行首对齐问题
	 * 6、switch结构问题
	 * 7、当没有class时那些数据如何转换
	 * 8、if结构问题
	 * 9、循环结构问题
	 * 10、if中变量递增问题,不能直接增加并赋值给寄存器
	 * 11、long和double需要两个寄存器
	 * 
	 * 未解决
	 * 1、if中判断可能是boolean值,可能是数值,可能是class类型值
	 * 2、变量名重复问题
	 * 4、try-catch块结构问题
	 * 
	 * 
	 * 
	 */
	
	//存储指令字符串
	public static HashMap<String, String[]> cmdData;
	//类名，用于转换构造方法名
	public static String className = null;
	//是否有类，判断是否要在文件尾加后缀
	public static boolean inClass = false;
	//根据静态方法与否，寄存器的大小及指定位置(this)位是否赋值
	public static boolean isStaticMethod = false;
	public static int FourZ_SIZE = 0;
	
	//方法中参数个数
	public static int jd_params_num = 0;
	//方法中存放参数的数组
	public static List<String> params = null;
	//存储method中寄存器的值
	public static String[] v_value = null;
	//存储翻译后的结果
	private static List<String> pool = null;
	
	public static void initPool(){
		cmdData = Command.initCmdData();
		pool = new ArrayList<String>();
		v_value = new String[30]; //若没有Locals，默认是30个存储空间
		params = null;
		jd_params_num=0;
		
		isStaticMethod = false;
		inClass = false;
		className = null;
		
		FourZ_SIZE = 0;
	}
	
	public static void putPrefix(){
		putLine("{");
		FourZ_SIZE ++ ;
	}
	
	public static void putSuffix() {
		FourZ_SIZE -- ;
		putLine("}");
	}
	
	public static void putValue(String v,String value) {
		int index = Integer.valueOf(v.substring(1));
		Data.v_value[index] = value;
	}
	
	public static String getValue(String v) {
		int index = Integer.valueOf(v.substring(1));
		if(Data.v_value != null && index<Data.v_value.length && Data.v_value[index]!=null && !Data.v_value[index].equals("")){
			return Data.v_value[index];
		}
		return v;
	}
	
	public static void putLine(String line){
		String kongge = FourZ_KONG();
		push(kongge+line);			
	}
	
	public static boolean isPoolNull(){
		if(pool==null)return true;
		return false;
	}
	public static int getPoolSize(){
		return pool.size();
	}
	
	public static void push(String line){
		pool.add(line);
	}
	
	public static String pop(){
		return pool.remove(pool.size()-1);
	}
	
	private static String FourZ_KONG() {
		if(FourZ_SIZE < 0 ){
			FourZ_SIZE = 0 ;
		}
		String result = "";
		for (int i = 0; i < FourZ_SIZE; i++) {
			result+="    ";
		}
		return result;
	}

	
	
	
	
	
	public static void printPool(String filePath) throws IOException{
		
		File file = new File(filePath);
		if(!file.exists()){
			file.getParentFile().mkdirs();
            file.createNewFile();   
        }
		PrintWriter ps = new PrintWriter(file);
		for (String line : pool) {
			ps.println(line);
		}
		ps.flush();
		ps.close();
	}
	
//	public static void printV(){
//		for (int i = 0; i < v_value.length; i++) {
//			System.out.println("v["+i+"] = "+v_value[i]);
//		}
//	}
	
//	public static void printLine(String line){
//		System.out.println(line);
//	}
	
}
