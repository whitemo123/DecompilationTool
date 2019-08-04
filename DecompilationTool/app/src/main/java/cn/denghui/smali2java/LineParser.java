package cn.denghui.smali2java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map.Entry;

public class LineParser {

	public static boolean parseLine(String line) {
		String[] line_split = line.split(" ");
		if (Data.cmdData == null) {
			Data.cmdData = Command.initCmdData();
		}
		for (Entry<String, String[]> entry : Data.cmdData.entrySet()) {
			String methodName = entry.getKey();
			String[] cmd_data = entry.getValue();
			for (String single_cmd : cmd_data) {
				if (line_split[0].equals(single_cmd)) {
					switch (methodName) {
					case "DATA_OPT_TWO_CMD":
						DATA_OPT_TWO_CMD(line);
						break;
					case "DATA_OPT_ONE_CMD":
						DATA_OPT_ONE_CMD(line);
						break;
					case "RETURN_CMD":
						RETURN_CMD(line);
						break;
					case "DATA_DEFINE_CMD":
						DATA_DEFINE_CMD(line);
						break;
					case "LOCK_CMD":
						LOCK_CMD(line);
						break;
					case "OBJ_OPT_CMD":
						OBJ_OPT_CMD(line);
						break;
					case "OBJ_OPT_INSTANCEOF_CMD":
						OBJ_OPT_INSTANCEOF_CMD(line);
						break;
					case "ARRAY_OPT_ARRAYLEN_CMD":
						ARRAY_OPT_ARRAYLEN_CMD(line);
						break;
					case "ARRAY_OPT_NEWARRAY_CMD":
						ARRAY_OPT_NEWARRAY_CMD(line);
						break;
					case "ARRAY_OPT_CMD":
						ARRAY_OPT_CMD(line);
						break;
					case "ARRAY_OPT_GET_CMD":
						ARRAY_OPT_GET_CMD(line);
						break;
					case "ARRAY_OPT_PUT_CMD":
						ARRAY_OPT_PUT_CMD(line);
						break;
					case "THROW_CMD":
						THROW_CMD(line);
						break;
					case "JUMP_GOTO_CMD":
						JUMP_GOTO_CMD(line);
						break;
					case "JUMP_SWITCH_CMD":
						JUMP_SWITCH_CMD(line);
						break;
					case "JUMP_IF_CMD":
						JUMP_IF_CMD(line);
						break;
					case "JUMP_IFZ_CMD":
						JUMP_IFZ_CMD(line);
						break;
					case "COMPARE_CMD":
						COMPARE_CMD(line);
						break;
					case "FIELD_OPT_IGET_CMD":
						FIELD_OPT_IGET_CMD(line);
						break;
					case "FIELD_OPT_SGET_CMD":
						FIELD_OPT_SGET_CMD(line);
						break;
					case "FIELD_OPT_IPUT_CMD":
						FIELD_OPT_IPUT_CMD(line);
						break;
					case "FIELD_OPT_SPUT_CMD":
						FIELD_OPT_SPUT_CMD(line);
						break;
					case "METHOD_OPT_CMD":
						METHOD_OPT_CMD(line);
						break;
					case "DATA_CONVERT_CMD":
						DATA_CONVERT_CMD(line);
						break;
					case "DATA_ARITH_CMD":
						DATA_ARITH_CMD(line);
						break;
					case "DATA_ARITH_2ADDR_CMD":
						DATA_ARITH_2ADDR_CMD(line);
						break;
					case "DATA_ARITH_LIT16_CMD":
						DATA_ARITH_LIT16_CMD(line);
						break;
					case "DATA_ARITH_LIT8_CMD":
						DATA_ARITH_LIT8_CMD(line);
						break;
					}
					return true;
				}
			}
		}
		return false;
	}

	public static boolean parseLine2(String line) {
		String[] line_split = line.split(" ");
		if (Data.cmdData == null) {
			Data.cmdData = Command.initCmdData();
		}
		for (Entry<String, String[]> entry : Data.cmdData.entrySet()) {
			String methodName = entry.getKey();
			String[] cmd_data = entry.getValue();
			for (String single_cmd : cmd_data) {
				if (line_split[0].equals(single_cmd)) {
					try {
						Class clazz = LineParser.class;
						Method method = clazz.getMethod(methodName,
								line.getClass());
						method.invoke(clazz, line);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

					return true;
				}
			}
		}
		return false;
	}

	private static String cutBehind(String line, int num) {
		return line.substring(0, line.length() - num);
	}

	private static String cutMiddle(String line, String begin, String end) {
		return line.substring(line.indexOf(begin) + begin.length(),
				line.indexOf(end));
	}

	// move-result
	public static void DATA_OPT_ONE_CMD(String line) {

		String[] split = line.split(" ");
		String pop = Data.pop();
		// Iterator v4{v} = v3.iterator();
		String result = pop.replace("{v}", split[1]);
		// 修改为Data.putValue看看会不会好点
		Data.putLine(result.trim());
		Data.putValue(split[1], split[1]);
		// if(result.contains(" = "))
		// Data.putValue(split[1],cutMiddle(result, " = ", ";"));
		// else
		// Data.putLine(result);
	}

	public static void RETURN_CMD(String line) {
		// return-void
		String[] split = line.split(" ");
		String cmd = split[0];
		switch (cmd) {
		case "return-void":
			Data.putLine("return;");
			break;
		case "return":
		case "return-wide":
		case "return-object":
			Data.putLine("return " + Data.getValue(split[1]) + ";");
			break;
		}
	}

	// move
	public static void DATA_OPT_TWO_CMD(String line) {
		// cutBehind(split_line[1],1)+" = "+split_line[2]+";";
		String[] split = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = split[2];
		Data.putValue(v1, Data.getValue(v2));
	}

	public static void LOCK_CMD(String line) {
		String line_split[] = line.split(" ");
		switch (line_split[0]) {
		case "monitor-enter":
			Data.putLine("synchronized(" + Data.getValue(line_split[1]) + ")");
			Data.putPrefix();
			break;
		case "monitor-exit":
			Data.putSuffix();
			break;
		}
	}

	public static void OBJ_OPT_CMD(String line) {
		// check-cast v2, Landroid/app/ActivityManager$RunningAppProcessInfo;
		// new-instance v1, Ljava/util/ArrayList;
		String line_split[] = line.split(" ");
		String type = TranslateUtil.type_convert(line_split[2]);
		String v = cutBehind(line_split[1], 1);
		switch (line_split[0]) {
		case "check-cast":
		case "check-cast/jumbo":
			// Data.putLine(v+" = ("+type+")"+Data.getValue(v)+";");
			Data.putValue(v, "((" + type + ")" + Data.getValue(v) + ")");
			break;
		case "new-instance":
		case "new-instance/jumbo":
			Data.putValue(v, v);
			// Data.v_value[index] = "new "+type;
			break;
		}
	}

	public static void ARRAY_OPT_NEWARRAY_CMD(String line) {
		// new-array v0, v0, [I new-array v1, v4, [B
		String[] split_line = line.split(" ");
		String type = TranslateUtil.type_convert(split_line[3].substring(1));
		String v1 = cutBehind(split_line[1], 1);
		String size = cutBehind(split_line[2], 1);
		switch (split_line[0]) {
		case "new-array":
		case "new-array/jumbo":
			// 将v1转换成指定变量
			String paramName = "local" + type;
			Data.putLine(type + "[] " + paramName + " = new " + type + "["
					+ Data.getValue(size) + "];");
			Data.putValue(v1, paramName);
			break;
		}
	}

	public static void OBJ_OPT_INSTANCEOF_CMD(String line) {
		// instance-of
		String[] split_line = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String v2 = cutBehind(split_line[2], 1);
		String type = TranslateUtil.type_convert(split_line[3]);
		Data.putValue(v1, Data.getValue(v2) + " instanceof " + type);
	}

	public static void ARRAY_OPT_ARRAYLEN_CMD(String line) {
		// array-length v1, v4
		String[] split = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = split[2];
		switch (split[0]) {
		case "array-length":
			Data.putValue(v1, Data.getValue(v2) + ".length");
			break;
		}
	}

	// 定义语句，在if和for循环之前定义的好
	public static void DATA_DEFINE_CMD(String line) {
		// const/4 v2, 0x1
		/*
		 * "const/4","const/16","const","const/high16",
		 * "const-wide/16","const-wide/32","const-wide/high16","const-wide",
		 * "const-string","const-string/jumbo",
		 * "const-class","const-class/jumbo"
		 */
		String[] split_line = line.split(" ");
		String cmd = split_line[0];
		String v = cutBehind(split_line[1], 1);
		// 有可能值中有空格
		int index = line.indexOf(" ", line.indexOf(" ") + 1);
		String value = line.substring(index + 1);
		switch (cmd) {
		case "const":
		case "const/4":
		case "const/16":
		case "const/high16":
		case "const-wide":
		case "const-wide/16":
		case "const-wide/32":
		case "const-wide/high16":
		case "const-string":
		case "const-string/jumbo":
			Data.putValue(v, UnicodeUtil.translateValue(value));
			break;
		case "const-class":
		case "const-class/jumbo":
			Data.putValue(v, TranslateUtil.type_convert(value) + ".class");
			break;
		}
	}

	public static void ARRAY_OPT_CMD(String line) {
		Data.putLine(line);
	}

	public static void ARRAY_OPT_GET_CMD(String line) {
		String split[] = line.split(" ");
		String v = cutBehind(split[1], 1);
		String arr = cutBehind(split[2], 1);
		String size = split[3];
		Data.putValue(v, Data.getValue(arr) + "[" + Data.getValue(size) + "]");
	}

	public static void ARRAY_OPT_PUT_CMD(String line) {
		String split_line[] = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String v2 = cutBehind(split_line[2], 1);
		String v3 = split_line[3];
		Data.putLine(Data.getValue(v2) + "[" + Data.getValue(v3) + "] = "
				+ Data.getValue(v1) + ";");
	}

	public static void THROW_CMD(String line) {
		String split_line[] = line.split(" ");
		Data.putLine("throw " + split_line[1] + ";");
	}

	public static void FIELD_OPT_IGET_CMD(String line) {
		// "iget-object v1, v2, Lcom/cooee/mcesys/download/DownloadManager;->listObject:Ljava/lang/Object;"
		String split_line[] = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String v2 = cutBehind(split_line[2], 1);
		String fieldName = cutMiddle(split_line[3], "->", ":");
		Data.putValue(v1, Data.getValue(v2) + "." + fieldName);
	}

	public static void FIELD_OPT_IPUT_CMD(String line) {
		// "iput-byte v0, v2, LTest;->b:B"
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = cutBehind(split[2], 1);
		String fieldName = cutMiddle(split[3], "->", ":");
		Data.putLine(Data.getValue(v2) + "." + fieldName + " = "
				+ Data.getValue(v1) + ";");

	}

	// 给寄存器赋值的 put
	public static void FIELD_OPT_SGET_CMD(String line) {
		// sget-object v0,
		// Lcom/cooee/mcesys/common/RotaEnvironment;->context:Landroid/content/Context;
		// sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String fieldName = cutMiddle(split[2], "->", ":");
		String className = split[2].substring(0, split[2].indexOf("->"));
		className = className.substring(1, className.length() - 1);
		className = className.substring(className.lastIndexOf("/") + 1);
		String value = className + "." + fieldName;
		Data.putValue(v1, value);
	}

	// 获取寄存器的值的 get
	public static void FIELD_OPT_SPUT_CMD(String line) {
		// sput-object v1,
		// Lcom/cooee/mcesys/common/RotaEnvironment;->context:Landroid/content/Context;
		String split_line[] = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String fieldName = cutMiddle(split_line[2], "->", ":");
		String className = split_line[2].substring(0,
				split_line[2].indexOf("->"));
		className = className.substring(1, className.length() - 1);
		className = className.substring(className.lastIndexOf("/") + 1);
		Data.putLine(className + "." + fieldName + " = " + Data.getValue(v1)
				+ ";");
	}

	public static void COMPARE_CMD(String line) {
		// cmpl-float v1, v1, v3
		// "cmpl-float","cmpl-double","cmpg-float","cmpg-double","cmp-long"
		String split_line[] = line.split(" ");
		String va = cutBehind(split_line[1], 1);
		String vb = cutBehind(split_line[2], 1);
		String vc = split_line[3];
		switch (split_line[0]) {
		case "cmpl-float":
		case "cmpl-double":
			Data.putLine(va + " = " + vb + "==" + vc + "?0:" + vb + ">" + vc
					+ "?-1:1;");
			Data.putValue(va, va);
			break;
		case "cmpg-float":
		case "cmpg-double":
		case "cmp-long":
			Data.putLine(va + " = " + vb + "==" + vc + "?0:" + vb + ">" + vc
					+ "?1:-1;");
			Data.putValue(va, va);
			break;
		}
	}

	public static void JUMP_IFZ_CMD(String line) {
		String split_line[] = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String flag = "";
		switch (split_line[0]) {
		case "if-eqz":
			flag = "==";
			break;
		case "if-nez":
			flag = "!=";
			break;
		case "if-ltz":
			flag = "<";
			break;
		case "if-lez":
			flag = "<=";
			break;
		case "if-gtz":
			flag = ">";
			break;
		case "if-gez":
			flag = ">=";
			break;
		}
		Data.putLine("if(" + Data.getValue(v1) + " " + flag + " 0)"+" break "+split_line[2]+";");
	}

	// "if-eq","if-ne","if-lt","if-le","if-gt","if-ge"
	public static void JUMP_IF_CMD(String line) {
		String split_line[] = line.split(" ");
		String v1 = cutBehind(split_line[1], 1);
		String v2 = cutBehind(split_line[2], 1);
		String flag = "";
		switch (split_line[0]) {
		case "if-eq":
			flag = "==";
			break;
		case "if-ne":
			flag = "!=";
			break;
		case "if-lt":
			flag = "<";
			break;
		case "if-le":
			flag = "<=";
			break;
		case "if-gt":
			flag = ">";
			break;
		case "if-ge":
			flag = ">=";
			break;
		}
		Data.putLine("if(" + Data.getValue(v1) + " " + flag + " "+ Data.getValue(v2) + ")"+" break "+split_line[3]+";");
	}

	public static void JUMP_SWITCH_CMD(String line) {
		// packed-switch v0, :pswitch_data_14
		String split[] = line.split(" ");
		String v = cutBehind(split[1], 1);
		Data.putLine("switch(" + Data.getValue(v) + ")");
		Data.putPrefix();
	}

	public static void JUMP_GOTO_CMD(String line) {
		// goto :goto_c
//		Data.inWhile = false;
//		Data.putSuffix();
		String tmp = line.replaceFirst("/16", "").replaceFirst("/32", "");
		Data.putLine(tmp);
	}

	public static void DATA_ARITH_LIT8_CMD(String line) {
		// add-int/lit8 v4, v10, 0x20
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = cutBehind(split[2], 1);
		String value = line.substring(line.lastIndexOf(" ") + 1);
		String flag = null;
		switch (split[0]) {
		case "add-int/lit8":
		case "add-float/lit8":
		case "add-long/lit8":
		case "add-double/lit8":
			flag = "+";
			break;
		case "sub-int/lit8":
		case "sub-float/lit8":
		case "sub-long/lit8":
		case "sub-double/lit8":
			flag = "-";
			break;
		case "mul-int/lit8":
		case "mul-float/lit8":
		case "mul-long/lit8":
		case "mul-double/lit8":
			flag = "*";
			break;
		case "div-int/lit8":
		case "div-float/lit8":
		case "div-long/lit8":
		case "div-double/lit8":
			flag = "/";
			break;
		case "rem-int/lit8":
		case "rem-float/lit8":
		case "rem-long/lit8":
		case "rem-double/lit8":
			flag = "%";
			break;
		case "and-int/lit8":
		case "and-float/lit8":
		case "and-long/lit8":
		case "and-double/lit8":
			flag = "&";
			break;
		case "or-int/lit8":
		case "or-float/lit8":
		case "or-long/lit8":
		case "or-double/lit8":
			flag = "|";
			break;
		case "xor-int/lit8":
		case "xor-float/lit8":
		case "xor-long/lit8":
		case "xor-double/lit8":
			flag = "^";
			break;
		case "shl-int/lit8":
		case "shl-float/lit8":
		case "shl-long/lit8":
		case "shl-double/lit8":
			flag = "<<";
			break;
		case "shr-int/lit8":
		case "shr-float/lit8":
		case "shr-long/lit8":
		case "shr-double/lit8":
			flag = ">>";
			break;
		case "ushr-int/lit8":
		case "ushr-float/lit8":
		case "ushr-long/lit8":
		case "ushr-double/lit8":
			flag = ">>>";
			break;
		}
		Data.putLine(v1 + " = (" + Data.getValue(v2) + " " + flag + " "
				+ UnicodeUtil.translateValue(value) + ");");
		Data.putValue(v1, v1);
	}

	public static void DATA_ARITH_LIT16_CMD(String line) {
		// add-int/lit16 v3, v3, 0x1f4
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = cutBehind(split[2], 1);
		String value = line.substring(line.lastIndexOf(" ") + 1);
		String flag = null;
		switch (split[0]) {
		case "add-int/lit16":
		case "add-float/lit16":
		case "add-long/lit16":
		case "add-double/lit16":
			flag = "+";
			break;
		case "sub-int/lit16":
		case "sub-float/lit16":
		case "sub-long/lit16":
		case "sub-double/lit16":
			flag = "-";
			break;
		case "mul-int/lit16":
		case "mul-float/lit16":
		case "mul-long/lit16":
		case "mul-double/lit16":
			flag = "*";
			break;
		case "div-int/lit16":
		case "div-float/lit16":
		case "div-long/lit16":
		case "div-double/lit16":
			flag = "/";
			break;
		case "rem-int/lit16":
		case "rem-float/lit16":
		case "rem-long/lit16":
		case "rem-double/lit16":
			flag = "%";
			break;
		case "and-int/lit16":
		case "and-float/lit16":
		case "and-long/lit16":
		case "and-double/lit16":
			flag = "&";
			break;
		case "or-int/lit16":
		case "or-float/lit16":
		case "or-long/lit16":
		case "or-double/lit16":
			flag = "|";
			break;
		case "xor-int/lit16":
		case "xor-float/lit16":
		case "xor-long/lit16":
		case "xor-double/lit16":
			flag = "^";
			break;
		case "shl-int/lit16":
		case "shl-float/lit16":
		case "shl-long/lit16":
		case "shl-double/lit16":
			flag = "<<";
			break;
		case "shr-int/lit16":
		case "shr-float/lit16":
		case "shr-long/lit16":
		case "shr-double/lit16":
			flag = ">>";
			break;
		case "ushr-int/lit16":
		case "ushr-float/lit16":
		case "ushr-long/lit16":
		case "ushr-double/lit16":
			flag = ">>>";
			break;
		}
		Data.putLine(v1 + " = (" + Data.getValue(v2) + " " + flag + " "
				+ UnicodeUtil.translateValue(value) + ");");
		Data.putValue(v1, v1);
	}

	public static void DATA_ARITH_2ADDR_CMD(String line) {
		// add-int/2addr v10, v8
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = split[2];
		String flag = null;
		switch (split[0]) {
		case "add-int/2addr":
		case "add-float/2addr":
		case "add-long/2addr":
		case "add-double/2addr":
			flag = "+";
			break;
		case "sub-int/2addr":
		case "sub-float/2addr":
		case "sub-long/2addr":
		case "sub-double/2addr":
			flag = "-";
			break;
		case "mul-int/2addr":
		case "mul-float/2addr":
		case "mul-long/2addr":
		case "mul-double/2addr":
			flag = "*";
			break;
		case "div-int/2addr":
		case "div-float/2addr":
		case "div-long/2addr":
		case "div-double/2addr":
			flag = "/";
			break;
		case "rem-int/2addr":
		case "rem-float/2addr":
		case "rem-long/2addr":
		case "rem-double/2addr":
			flag = "%";
			break;
		case "and-int/2addr":
		case "and-float/2addr":
		case "and-long/2addr":
		case "and-double/2addr":
			flag = "&";
			break;
		case "or-int/2addr":
		case "or-float/2addr":
		case "or-long/2addr":
		case "or-double/2addr":
			flag = "|";
			break;
		case "xor-int/2addr":
		case "xor-float/2addr":
		case "xor-long/2addr":
		case "xor-double/2addr":
			flag = "^";
			break;
		case "shl-int/2addr":
		case "shl-float/2addr":
		case "shl-long/2addr":
		case "shl-double/2addr":
			flag = "<<";
			break;
		case "shr-int/2addr":
		case "shr-float/2addr":
		case "shr-long/2addr":
		case "shr-double/2addr":
			flag = ">>";
			break;
		case "ushr-int/2addr":
		case "ushr-float/2addr":
		case "ushr-long/2addr":
		case "ushr-double/2addr":
			flag = ">>>";
			break;
		}
		Data.putLine(v1 + " = (" + Data.getValue(v1) + " " + flag + " "
				+ Data.getValue(v2) + ");");
		Data.putValue(v1, v1);
	}

	public static void DATA_ARITH_CMD(String line) {
		// add-int v10, v3, v0
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = cutBehind(split[2], 1);
		String v3 = split[3];
		String flag = null;
		switch (split[0]) {
		case "add-int":
		case "add-float":
		case "add-long":
		case "add-double":
			flag = "+";
			break;
		case "sub-int":
		case "sub-float":
		case "sub-long":
		case "sub-double":
			flag = "-";
			break;
		case "mul-int":
		case "mul-float":
		case "mul-long":
		case "mul-double":
			flag = "*";
			break;
		case "div-int":
		case "div-float":
		case "div-long":
		case "div-double":
			flag = "/";
			break;
		case "rem-int":
		case "rem-float":
		case "rem-long":
		case "rem-double":
			flag = "%";
			break;
		case "and-int":
		case "and-float":
		case "and-long":
		case "and-double":
			flag = "&";
			break;
		case "or-int":
		case "or-float":
		case "or-long":
		case "or-double":
			flag = "|";
			break;
		case "xor-int":
		case "xor-float":
		case "xor-long":
		case "xor-double":
			flag = "^";
			break;
		case "shl-int":
		case "shl-float":
		case "shl-long":
		case "shl-double":
			flag = "<<";
			break;
		case "shr-int":
		case "shr-float":
		case "shr-long":
		case "shr-double":
			flag = ">>";
			break;
		case "ushr-int":
		case "ushr-float":
		case "ushr-long":
		case "ushr-double":
			flag = ">>>";
			break;
		}
		Data.putLine(v1 + " = (" + Data.getValue(v2) + " " + flag + " "
				+ Data.getValue(v3) + ");");
		Data.putValue(v1, v1);
	}

	// neg-int v1, v2
	/*
	 * "neg-int","neg-long","neg-float","neg-double", "not-int","not-long",
	 * "int-to-long","int-to-float","int-to-double",
	 * "long-to-int","long-to-float","long-to-double",
	 * "float-to-int","float-to-long","float-to-double",
	 * "double-to-int","double-to-long","double-to-float",
	 * "int-to-byte","int-to-char","int-to-short"
	 */
	public static void DATA_CONVERT_CMD(String line) {
		String split[] = line.split(" ");
		String v1 = cutBehind(split[1], 1);
		String v2 = split[2];
		String flag = null;
		switch (split[0]) {
		case "neg-int":
		case "neg-long":
		case "neg-float":
		case "neg-double":
			flag = "-";
			break;
		case "not-int":
		case "not-long":
			flag = "~";
			break;
		case "int-to-long":
		case "float-to-long":
		case "double-to-long":
			flag = "(long)";
			break;
		case "int-to-float":
		case "long-to-float":
		case "double-to-float":
			flag = "(float)";
			break;
		case "int-to-double":
		case "long-to-double":
		case "float-to-double":
			flag = "(double)";
			break;
		case "long-to-int":
		case "float-to-int":
		case "double-to-int":
			flag = "(int)";
			break;
		case "int-to-byte":
			flag = "(byte)";
			break;
		case "int-to-char":
			flag = "(char)";
			break;
		case "int-to-short":
			flag = "(short)";
			break;
		}
		Data.putValue(v1, "(" + flag + Data.getValue(v2) + ")");
	}

	public static void METHOD_OPT_CMD(String line) {
		System.out.println(line);
		String result = "";
		String split[] = line.split(" ");
		String arg_string = cutMiddle(line, "{", "}");
		String arg[] = ArgToArray(arg_string);
		String methodName = cutMiddle(line, "->", "(");
		String invoke_args = cutMiddle(line, "(", ")");
		System.out.println(Arrays.toString(arg));
		String backType = TranslateUtil.type_convert(line.substring(line
				.lastIndexOf(")") + 1));
		String className = TranslateUtil.type_convert(line.substring(
				line.lastIndexOf(" ") + 1, line.indexOf("->")));
		// 构造方法
		if (methodName.equals("<init>")) {
			String paramName = null;
			if (Data.getValue(arg[0]).equals("this")) {
				paramName = "this";
			} else {
				paramName = "local" + className;
			}
			String direct_str = TranslateUtil.invoke_args_convert(arg,invoke_args,1);
			result = className + " " + paramName + " = new " + className + "("
					+ direct_str + ");";
			Data.putLine(result);
			Data.putValue(arg[0], paramName);
			return;
		}
		switch (split[0]) {
		case "invoke-virtual":
		case "invoke-super":
		case "invoke-direct":
		case "invoke-interface":
		case "invoke-virtual/range":
		case "invoke-super/range":
		case "invoke-direct/range":
		case "invoke-interface/range":
			String direct_str = TranslateUtil.invoke_args_convert(arg,invoke_args,1);
			result = Data.getValue(arg[0]) + "." + methodName + "("
					+ direct_str + ");";
			break;
		case "invoke-static":
		case "invoke-static/range":
			String static_str = TranslateUtil.invoke_args_convert(arg,invoke_args,0);
			result = className + "." + methodName + "(" + static_str
					+ ");";
			break;
		}
		if (!backType.equals("void")) {
			result = backType + " {v} = " + result;
		}
		Data.putLine(result);
	}

	private static String[] ArgToArray(String args) {
		if (args.contains(", ")) {
			return args.split(", ");
		} else if (args.contains(" .. ")) {
			String[] vs = args.split(" .. ");
			int start = Integer.valueOf(vs[0].substring(1));
			int end = Integer.valueOf(vs[1].substring(1));
			String[] result = new String[end - start + 1];
			for (int i = start; i <= end; i++) {
				result[i - start] = "v" + i;
			}
			return result;
		} else {
			return new String[] { args };
		}
	}

	private static String ArgToString(String[] args, int index) {
		String result = "";
		if (args[0].equals("")) {
			return "";
		}
		for (int i = index; i < args.length; i++) {
			result += Data.getValue(args[i]) + ",";
		}
		if (result.endsWith(",")) { // 只有当结果有逗号(,)的时候才会执行
			result = cutBehind(result, 1);
		}
		return result;
	}

}
