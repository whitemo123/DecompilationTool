package cn.denghui.smali2java;

import java.util.ArrayList;
import java.util.List;

public class IFParser {
	private static String[] command = new String[]{
		"if-eq","if-ne","if-lt","if-le","if-gt","if-ge",
		"if-eqz","if-nez","if-ltz","if-lez","if-gtz","if-gez"
	};
	
	private static String[] str = new String[]{
		" == "," != "," < "," <= "," > "," >= ",
		" == 0"," != 0"," < 0"," <= 0"," > 0"," >= 0",
	};
	
	private static String line2condition(String line){
		String[] split = line.split(" ");
		int index = isIFcmd(split[0]);
		String v1 = split[1].substring(0, split[1].length()-1);
		String v2 = null;
		if(split[0].endsWith("z")){
			return "("+Data.getValue(v1)+str[index]+")";
		}else{
			v2 = split[2].substring(0, split[2].length()-1);
			return "("+Data.getValue(v1)+str[index]+Data.getValue(v2)+")";
		}
		
	}
	
	private static int isIFcmd(String cmd){
		for (int i = 0; i < command.length; i++) {
			if(cmd.equals(command[i])){
				return i;
			}
		}
		return -1;
	}
	
	
	static List<String> jumpList = null;
	static List<String> tmp_data = null;
	static boolean is_IN_IF = false;
	
	private static void initSwitchData(){
		jumpList = new ArrayList<String>();
		tmp_data = new ArrayList<String>();
		if_list = new ArrayList<IFclass>();
		is_IN_IF = true;
	}
	
	private static void change_Jump_Data(String line) {
		String jump = line.substring(line.lastIndexOf(" ")+1);
		//String condition = line2condition(line);
		if(!jumpList.contains(jump)){
			jumpList.add(jump);
		}
		//conditionList.add(jump+"----"+condition);
	}
	
	private static boolean isIF_EndLine(String line) {
		
		for (int i = 0; i < jumpList.size(); i++) {
			if(line.startsWith(jumpList.get(i))){
				jumpList.remove(i);
				if(jumpList.size()==0){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean parseLine(String line){
		String cmd = line.split(" ")[0];
		if(isIFcmd(cmd)!=-1){
			//第一次进入if里面
			if(!is_IN_IF){
				initSwitchData();
			}
			
			tmp_data.add(line);
			change_Jump_Data(line);
		}else if(is_IN_IF){
			//在IF中间
			tmp_data.add(line);
			if(isIF_EndLine(line)){
				//该IF终结了
				is_IN_IF = false;
				initIFparam(tmp_data);
				handleData(tmp_data);
			}
			
		}else {
			return false;
		}
		return true;
	}
	
	private static void handleData(List<String> tmp_data){
		if(isFirstIFBig()){
			for (int i = 0; i < tmp_data.size(); i++) {
				String line = tmp_data.get(i);
				if(line.startsWith(":cond")){
					int num = getJumpNum(line);
					for (int j = 0; j < num; j++) {
						Data.putSuffix();
					}
					continue;
				}
				LineParser.parseLine(line);
			}
		}else{
			for (int i = 0; i < tmp_data.size(); i++) {
				String line = tmp_data.get(i);
				String jump = line.substring(line.lastIndexOf(" ")+1);
				if(isIFcmd(line.split(" ")[0])!=-1){
					Data.putLine("if"+line2condition(line)+" break "+jump.substring(1)+";");
				}else if(line.startsWith(":cond")){
					Data.putLine(line.substring(1)+":");
				}else{
					LineParser.parseLine(line);
				}
			}
		}
		
	}
	

	private static void initIFparam(List<String> tmp_data2) {
		int i = 0;
		for (String line : tmp_data2) {
			if(isIFcmd(line.split(" ")[0])!=-1){
				IFclass clazz = new IFclass();
				String jump = line.substring(line.lastIndexOf(" ")+1);
				clazz.condition = line2condition(line);
				clazz.jump_str = jump;
				clazz.jump_begin_index = i;
				if_list.add(clazz);
			}
			List<IFclass> ifclass = null;
			if(line.startsWith(":cond")){
				ifclass = getObjByJumpStr(line);
				for (IFclass iFclass2 : ifclass) {
					iFclass2.jump_end_index = i;
				}
			}
			i++;
		}
	}
	
	private static List<IFclass> if_list = null;

	private static List<IFclass> getObjByJumpStr(String jumpStr){
		List<IFclass> list = new ArrayList<IFclass>();
		for (IFclass ifclass : if_list) {
			if(ifclass.jump_str.equals(jumpStr)){
				list.add(ifclass);
			}
		}
		return list;
	}
	
	private static boolean isFirstIFBig(){
		IFclass first_class = if_list.get(0);
		for (IFclass ifclass : if_list) {
			if(first_class.jump_end_index < ifclass.jump_end_index){
				return false;
			}
		}
		return true;
	}
	
	private static int getJumpNum(String jumpStr){
		int i = 0;
		for (IFclass ifclass : if_list) {
			if(ifclass.jump_str.equals(jumpStr)){
				i++;
			}
		}
		return i;
	}
	
	public static class IFclass{
		public String condition;
		public String jump_str;
		public int jump_begin_index;
		public int jump_end_index;
		
		@Override
		public String toString() {
			return "condition="+condition+",jump_str="+jump_str+",jump_begin_index="+jump_begin_index+",jump_end_index="+jump_end_index;
		}
	}
}
