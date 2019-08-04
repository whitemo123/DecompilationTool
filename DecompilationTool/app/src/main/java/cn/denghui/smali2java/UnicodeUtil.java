package cn.denghui.smali2java;

public class UnicodeUtil {
	
	//"\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u8d26\u53f7"
	//0x7f060017
	//const-string v6, " \u5185\u5bb9\uff1a"
	public static String translateValue(String value){
		String result = null;
		if(value.contains("0x")){
			try{
				result = Integer.parseInt(value.replaceFirst("0x", ""),16)+"";
			}catch(NumberFormatException e){
				result = value;
			}
		}
		if(value.startsWith("\"")&&value.endsWith("\"")){
			String tmp = value.substring(1,value.length()-1);
			result = "\""+UnicodeUtil.ascii2Native(tmp)+"\"";
		}
		return result;
	}
	
	private static String ascii2Native(String str) {   
        StringBuilder sb = new StringBuilder();   
        int begin = 0;   
        int index = str.indexOf("\\u");   
        while (index != -1) {   
            sb.append(str.substring(begin, index));   
            sb.append(ascii2Char(str.substring(index, index + 6)));   
            begin = index + 6;   
            index = str.indexOf("\\u", begin);   
        }   
        sb.append(str.substring(begin));   
        return sb.toString();   
    }  
 
 private static char ascii2Char(String str) {   
        if (str.length() != 6 || !str.startsWith("\\u")) {   
        	return ' ';
        }   
        String tmp = str.substring(2, 4);   
        int code = Integer.parseInt(tmp, 16) << 8;   
        tmp = str.substring(4, 6);   
        code += Integer.parseInt(tmp, 16);   
        return (char) code;   
    }  
}
