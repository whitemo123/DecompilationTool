package cn.denghui.smali2java;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateUtil {
	
	
	//QuickStopTask(Ljava/lang/String;)Lcom/cooee/mcesys/download/DownloadErrorCode;
	//.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
	//.method private a(I)V
	public static String method_convert(String line){
		String[] split = line.split(" ");
		String tmp = "";
		for (String code : split) {
			if(code.equals(".method")){
				continue;
			}else if(code.contains("(")){
				String method_back = code.substring(code.lastIndexOf(")")+1);
				String method_name = code.substring(0, code.indexOf("("));
				String method_args = code.substring(code.indexOf("(")+1,code.indexOf(")"));
				if(method_name.equals("<init>")){
					tmp += type_convert(Data.className)+"(";
				}else{
					tmp += type_convert(method_back)+" ";
					tmp += method_name+"(";
				}
				tmp += args_convert(method_args)+")";
			}else{
				if(code.equals("constructor")){
					continue;
				}
				tmp += code+" ";
			}
		}
		return tmp.trim();
	}
	
	public static String invoke_args_convert(String[] arg, String args,int index) {
		if (arg[0].equals("")) {
			return "";
		}
		String result = "";
		String regex = "(\\[*L(.*?);)|(\\[*[VZBSCIJFD])|(L(.*?);)|[VZBSCIJFD]";
		Pattern pattern = Pattern.compile(regex);
		Matcher ma = pattern.matcher(args);
		int i =index;
		while(ma.find()){
			String single = ma.group();
			String type = type_convert(single);
			if(type.equals("double")||type.equals("long")){
				result += Data.getValue(arg[i])+",";
				i++;
			}else{
				result += Data.getValue(arg[i])+",";
			}
			i++;
		}
		if (result.endsWith(",")) { // 只有当结果有逗号(,)的时候才会执行
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	//DDLjava/lang/Object;[ILjava/lang/String;
	public static String args_convert(String args){
		Data.params = new ArrayList<String>();
		if(args==null || args.trim().equals("")){
			return "";
		}
		String args_result = "";
		String regex = "(\\[*L(.*?);)|(\\[*[VZBSCIJFD])|(L(.*?);)|[VZBSCIJFD]";
		Pattern pattern = Pattern.compile(regex);
		Matcher ma = pattern.matcher(args);
		int i = 0;
		while(ma.find())
		{
			String single = ma.group();
			String type = type_convert(single);
			String bigStr = type.replaceFirst(type.substring(0, 1), type.substring(0,1).toUpperCase());
			String param = "param"+bigStr.replaceAll("\\[\\]", "")+i++;
			
			if(type.equals("long")||type.equals("double")){
				param = "jd_"+param;
				Data.jd_params_num+=1;
				System.out.println("-----------"+param);
			}
			args_result += type+" "+param+", ";
			Data.params.add(param);
		}
		args_result = args_result.trim();
		args_result = args_result.substring(0, args_result.length()-1);
		return args_result;
	}
	
	public static String field_convert(String line){
		String split[] = line.split(" ");
		String tmp = "";
		for (String code : split) {
			if(code.equals(".field")){
				continue;
			}else if(code.contains(":")){
				String[] tmp_arr = code.split(":");
				tmp += type_convert(tmp_arr[1])+" ";
				tmp += tmp_arr[0]+" ";
			}else{
				tmp += code+" ";
			}
		}
		return tmp.trim()+";";
	}

	//"V","Z","B","S","C","I","J","F","D"
	public static String type_convert(String type){
		if(type == null || type.equals(""))return "";
		switch (type) {
		case "V":
			return "void";
		case "Z":
			return "boolean";
		case "B":
			return "byte";
		case "S":
			return "short";
		case "C":
			return "char";
		case "I":
			return "int";
		case "J":
			return "long";
		case "F":
			return "float";
		case "D":
			return "double";
		default:
			if(type.startsWith("[")){
				String tmp = type.substring(1);
				return type_convert(tmp)+"[]";
			}else if(type.startsWith("L")&&type.endsWith(";")){
				int begin = 0;
				int end = type.length()-1;
				if(type.contains("/")){
					begin = type.lastIndexOf("/")+1;
				}else{
					begin = 1;
				}
				return type.substring(begin, end);
			}
		}
		return "";
	}
	
}
