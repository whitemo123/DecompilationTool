package cn.denghui.smali2java;

import java.util.HashMap;

public class Command {
	
	//:cond_  被跳转的指令
	//:goto_
	
	//0.其它特殊的指令
	public static String[] OTHER_PRE_CMD = new String[]{
		":goto",":cond"
	};
	
	public static boolean IS_NO_VALUE_CMD(String line){
		for (String cmd : NO_VALUE_CMD) {
			if(line.startsWith(cmd)){
				return true;
			}
		}
		return false;
	}
	
//	//十六进制数字
//	public static String[] SIX_NUM = new String[]{
//		"const/4","const/16","const-wide/16",
//		"mul-int/lit8","add-int/lit16",
//		".packed-switch",
//		".array-data"
//	};
	
	//1.忽略的指令
	public static String[] NO_VALUE_CMD = new String[]{
		".line",".prologue",".parameter","nop",
		".annotation",".end annotation",
		":try_start",":try_end",".catchall",".catch",":catch",
		"#"
		//value array.data
	};
	
	//2.类型
	public static String[] TYPE_CONVERT_CMD = new String[]{
		"V","Z","B","S","C","I","J","F","D","L","["
	};
	
	public static HashMap<String,String[]> initCmdData(){
		HashMap<String,String[]> cmd_data = new HashMap<String,String[]>();
		//3 - 15
		cmd_data.put("DATA_OPT_TWO_CMD", DATA_OPT_TWO_CMD);
		cmd_data.put("DATA_OPT_ONE_CMD", DATA_OPT_ONE_CMD);
		cmd_data.put("RETURN_CMD", RETURN_CMD);
		cmd_data.put("DATA_DEFINE_CMD", DATA_DEFINE_CMD);
		cmd_data.put("LOCK_CMD", LOCK_CMD);
		cmd_data.put("OBJ_OPT_CMD", OBJ_OPT_CMD);
		cmd_data.put("OBJ_OPT_INSTANCEOF_CMD", OBJ_OPT_INSTANCEOF_CMD);
		cmd_data.put("ARRAY_OPT_ARRAYLEN_CMD", ARRAY_OPT_ARRAYLEN_CMD);
		cmd_data.put("ARRAY_OPT_NEWARRAY_CMD", ARRAY_OPT_NEWARRAY_CMD);
		cmd_data.put("ARRAY_OPT_CMD", ARRAY_OPT_CMD);
		cmd_data.put("ARRAY_OPT_GET_CMD", ARRAY_OPT_GET_CMD);
		cmd_data.put("ARRAY_OPT_PUT_CMD", ARRAY_OPT_PUT_CMD);
		cmd_data.put("THROW_CMD", THROW_CMD);
		cmd_data.put("JUMP_GOTO_CMD", JUMP_GOTO_CMD);
		cmd_data.put("JUMP_SWITCH_CMD", JUMP_SWITCH_CMD);
		cmd_data.put("JUMP_IF_CMD", JUMP_IF_CMD);
		cmd_data.put("JUMP_IFZ_CMD", JUMP_IFZ_CMD);
		cmd_data.put("COMPARE_CMD", COMPARE_CMD);
		cmd_data.put("FIELD_OPT_IGET_CMD", FIELD_OPT_IGET_CMD);
		cmd_data.put("FIELD_OPT_SGET_CMD", FIELD_OPT_SGET_CMD);
		cmd_data.put("FIELD_OPT_IPUT_CMD", FIELD_OPT_IPUT_CMD);
		cmd_data.put("FIELD_OPT_SPUT_CMD", FIELD_OPT_SPUT_CMD);
		cmd_data.put("METHOD_OPT_CMD", METHOD_OPT_CMD);
		cmd_data.put("DATA_CONVERT_CMD", DATA_CONVERT_CMD);
		cmd_data.put("DATA_ARITH_CMD", DATA_ARITH_CMD);
		cmd_data.put("DATA_ARITH_2ADDR_CMD", DATA_ARITH_2ADDR_CMD);
		cmd_data.put("DATA_ARITH_LIT16_CMD", DATA_ARITH_LIT16_CMD);
		cmd_data.put("DATA_ARITH_LIT8_CMD", DATA_ARITH_LIT8_CMD);
		return cmd_data;
	}
	
	//3.数据操作指令 two
	public static String[] DATA_OPT_TWO_CMD = new String[]{
		"move","move/from16","move/16",
		"move-wide","move-wide/from16",
		"move-object","move-object/16","move-object/from16"
	};
	
	//3.数据操作指令 one
	public static String[] DATA_OPT_ONE_CMD = new String[]{
		"move-result","move-result-wide","move-result-object",
		"move-exception"
	};
	//4.返回指令
	public static String[] RETURN_CMD = new String[]{
		"return-void","return","return-wide","return-object"
	};
	//5.数据定义指令
	public static String[] DATA_DEFINE_CMD = new String[]{
		"const/4","const/16","const","const/high16",
		"const-wide/16","const-wide/32","const-wide/high16","const-wide",
		"const-string","const-string/jumbo",
		"const-class","const-class/jumbo"
	};
	//6.锁指令
	public static String[] LOCK_CMD = new String[]{
		"monitor-enter","monitor-exit"
	};
	//7.对象操作指令
	public static String[] OBJ_OPT_CMD = new String[]{
		"check-cast","check-cast/jumbo",
		"new-instance","new-instance/jumbo"
	};
	//7.对象操作指令
	public static String[] OBJ_OPT_INSTANCEOF_CMD = new String[]{
		"instance-of","instance-of/jumbo"
	};
	//8.数组操作指令
	public static String[] ARRAY_OPT_ARRAYLEN_CMD = new String[]{
		"array-length"
	};
	
	public static String[] ARRAY_OPT_NEWARRAY_CMD= new String[]{
		"new-array","new-array/jumbo"
	};
	
	public static String[] ARRAY_OPT_CMD = new String[]{
		"filled-new-array","filled-new-array/range","filled-new-array/jumbo",
		"fill-array-data"
	};
	//8.数组操作指令 get
	public static String[] ARRAY_OPT_GET_CMD = new String[]{
		"aget","aget-wide","aget-object","aget-boolean","aget-byte","aget-char","aget-short"
	};
	//8.数组操作指令 put
	public static String[] ARRAY_OPT_PUT_CMD = new String[]{
		"aput","aput-wide","aput-object","aput-boolean","aput-byte","aput-char","aput-short"
	};
	//9.异常指令
	public static String[] THROW_CMD = new String[]{
		"throw"
	};
	
	//10.跳转指令 goto
	public static String[] JUMP_GOTO_CMD = new String[]{
		"goto","goto/16","goto/32"
	};
	//10.跳转指令 switch
	public static String[] JUMP_SWITCH_CMD = new String[]{
		"packed-switch","sparse-switch"
	};
	
	//10.跳转指令 if
	public static String[] JUMP_IF_CMD = new String[]{
		"if-eq","if-ne","if-lt","if-le","if-gt","if-ge"
	};
	//10.跳转指令 ifz
	public static String[] JUMP_IFZ_CMD = new String[]{
		"if-eqz","if-nez","if-ltz","if-lez","if-gtz","if-gez"
	};
	//11.比较指令
	public static String[] COMPARE_CMD = new String[]{
		"cmpl-float","cmpl-double",
		"cmpg-float","cmpg-double",
		"cmp-long"
	};
	
	//12.字段操作指令 iget
	public static String[] FIELD_OPT_IGET_CMD = new String[]{
		"iget","iget-wide","iget-object","iget-boolean","iget-byte","iget-char","iget-short"
	};
	//12.字段操作指令 sget
	public static String[] FIELD_OPT_SGET_CMD = new String[]{
		"sget","sget-wide","sget-object","sget-boolean","sget-byte","sget-char","sget-short"
	};
	//12.字段操作指令 iput
	public static String[] FIELD_OPT_IPUT_CMD = new String[]{
		"iput","iput-wide","iput-object","iput-boolean","iput-byte","iput-char","iput-short"
	};
	//12.字段操作指令 sput
	public static String[] FIELD_OPT_SPUT_CMD = new String[]{
		"sput","sput-wide","sput-object","sput-boolean","sput-byte","sput-char","sput-short"
	};
	//13.方法调用指令
	public static String[] METHOD_OPT_CMD = new String[]{
		"invoke-virtual","invoke-super","invoke-direct","invoke-static","invoke-interface",
		"invoke-virtual/range","invoke-super/range","invoke-direct/range","invoke-static/range","invoke-interface/range"
	};
	//14.数据转换指令
	public static String[] DATA_CONVERT_CMD = new String[]{
		"neg-int","neg-long","neg-float","neg-double",
		"not-int","not-long",
		"int-to-long","int-to-float","int-to-double",
		"long-to-int","long-to-float","long-to-double",
		"float-to-int","float-to-long","float-to-double",
		"double-to-int","double-to-long","double-to-float",
		"int-to-byte","int-to-char","int-to-short"
	};
	//15.数据运算指令
	public static String[] DATA_ARITH_CMD = new String[]{
		"add-int","sub-int","mul-int","div-int","rem-int","and-int","or-int","xor-int","shl-int","shr-int","ushr-int",
		"add-float","sub-float","mul-float","div-float","rem-float","and-float","or-float","xor-float","shl-float","shr-float","ushr-float",
		"add-long","sub-long","mul-long","div-long","rem-long","and-long","or-long","xor-long","shl-long","shr-long","ushr-long",
		"add-double","sub-double","mul-double","div-double","rem-double","and-double","or-double","xor-double","shl-double","shr-double","ushr-double",
	};
	//15.数据运算指令  2addr
	public static String[] DATA_ARITH_2ADDR_CMD = new String[]{
		"add-int/2addr","sub-int/2addr","mul-int/2addr","div-int/2addr","rem-int/2addr","and-int/2addr","or-int/2addr","xor-int/2addr","shl-int/2addr","shr-int/2addr","ushr-int/2addr",
		"add-float/2addr","sub-float/2addr","mul-float/2addr","div-float/2addr","rem-float/2addr","and-float/2addr","or-float/2addr","xor-float/2addr","shl-float/2addr","shr-float/2addr","ushr-float/2addr",
		"add-long/2addr","sub-long/2addr","mul-long/2addr","div-long/2addr","rem-long/2addr","and-long/2addr","or-long/2addr","xor-long/2addr","shl-long/2addr","shr-long/2addr","ushr-long/2addr",
		"add-double/2addr","sub-double/2addr","mul-double/2addr","div-double/2addr","rem-double/2addr","and-double/2addr","or-double/2addr","xor-double/2addr","shl-double/2addr","shr-double/2addr","ushr-double/2addr",
	};
	//15.数据运算指令  lit16
	public static String[] DATA_ARITH_LIT16_CMD = new String[]{
		"add-int/lit16","sub-int/lit16","mul-int/lit16","div-int/lit16","rem-int/lit16","and-int/lit16","or-int/lit16","xor-int/lit16","shl-int/lit16","shr-int/lit16","ushr-int/lit16",
		"add-float/lit16","sub-float/lit16","mul-float/lit16","div-float/lit16","rem-float/lit16","and-float/lit16","or-float/lit16","xor-float/lit16","shl-float/lit16","shr-float/lit16","ushr-float/lit16",
		"add-long/lit16","sub-long/lit16","mul-long/lit16","div-long/lit16","rem-long/lit16","and-long/lit16","or-long/lit16","xor-long/lit16","shl-long/lit16","shr-long/lit16","ushr-long/lit16",
		"add-double/lit16","sub-double/lit16","mul-double/lit16","div-double/lit16","rem-double/lit16","and-double/lit16","or-double/lit16","xor-double/lit16","shl-double/lit16","shr-double/lit16","ushr-double/lit16",
	};
	//15.数据运算指令 lit8
	public static String[] DATA_ARITH_LIT8_CMD = new String[]{
		"add-int/lit8","sub-int/lit8","mul-int/lit8","div-int/lit8","rem-int/lit8","and-int/lit8","or-int/lit8","xor-int/lit8","shl-int/lit8","shr-int/lit8","ushr-int/lit8",
		"add-float/lit8","sub-float/lit8","mul-float/lit8","div-float/lit8","rem-float/lit8","and-float/lit8","or-float/lit8","xor-float/lit8","shl-float/lit8","shr-float/lit8","ushr-float/lit8",
		"add-long/lit8","sub-long/lit8","mul-long/lit8","div-long/lit8","rem-long/lit8","and-long/lit8","or-long/lit8","xor-long/lit8","shl-long/lit8","shr-long/lit8","ushr-long/lit8",
		"add-double/lit8","sub-double/lit8","mul-double/lit8","div-double/lit8","rem-double/lit8","and-double/lit8","or-double/lit8","xor-double/lit8","shl-double/lit8","shr-double/lit8","ushr-double/lit8",
	};
	
}
