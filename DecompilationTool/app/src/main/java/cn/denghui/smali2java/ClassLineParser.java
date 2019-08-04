package cn.denghui.smali2java;

import java.util.Arrays;


public class ClassLineParser
{
	static String cmd[] = new String[]{
		".class",".super",".implements",".field",".method",".end method"
	};

	public static boolean parseLine(String line)
	{
		if (line.startsWith(".class"))
		{
			//.class public final Lcom/baidu/input/ciku/CikuOptmizerView;
			//.class Labh;
			String tmp = "";
			if (line.indexOf(" ") < line.lastIndexOf(" "))
			{
				tmp = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
			}
			String className = line.substring(line.lastIndexOf(" ") + 1);
			Data.putLine(tmp + " class " + TranslateUtil.type_convert(className));
			Data.className = className;
		}
		else if (line.startsWith(".super"))
		{
			String superName = line.substring(line.indexOf(" ") + 1);
			if (!superName.equals("Ljava/lang/Object;"))
			{
				String className = Data.pop().trim();
				Data.FourZ_SIZE = 0;
				superName = TranslateUtil.type_convert(superName);
				Data.putLine(className + " extends " + superName);
			}
			Data.inClass = true;
			Data.putPrefix();
		}
		else if (line.startsWith(".implements"))
		{
			Data.pop();//去掉{
			String implementName = line.substring(line.indexOf(" ") + 1);
			implementName = TranslateUtil.type_convert(implementName);
			String className = Data.pop().trim();
			Data.FourZ_SIZE = 0;
			if (className.contains("implements"))
			{
				Data.putLine(className + ", " + implementName);	
			}
			else
			{
				Data.putLine(className + " implements " + implementName);				
			}
			Data.putPrefix();
		}
		else if (line.startsWith(".field"))
		{
			Data.FourZ_SIZE = 0;
			Data.putLine(TranslateUtil.field_convert(line));
		}
		else if (line.startsWith(".method"))
		{
			if (line.contains("static"))
			{
				Data.isStaticMethod = true;
			}
			else
			{
				Data.isStaticMethod = false;
			}
			Data.FourZ_SIZE = 0;
			Data.putLine(TranslateUtil.method_convert(line));
			Data.putPrefix();
		}
		else if (line.startsWith(".end method"))
		{
			Data.putSuffix();
		}
		else if (line.startsWith(".locals"))
		{
			int locals = Integer.valueOf(line.split(" ")[1]);
			if (Data.isStaticMethod)
			{
				Data.v_value = new String[locals + Data.params.size() + Data.jd_params_num + 10];	//多添加10个位置存储一些超范围的寄存器
				int j = 0;
				for (int i = 0; i < Data.params.size(); i++,j++)
				{
					Data.v_value[locals + j] = Data.params.get(i);
					//下一个寄存器也放置该值
					if (Data.params.get(i).startsWith("jd_"))
					{
						Data.v_value[locals + j + 1] = Data.params.get(i);
						j++;
					}
				}
			}
			else
			{
				Data.v_value = new String[locals + Data.params.size() + Data.jd_params_num + 1 + 10];//多添加10个位置存储一些超范围的寄存器
				Data.v_value[locals] = "this";
				int j = 1;
				for (int i = 0; i < Data.params.size(); i++,j++)
				{
					Data.v_value[locals + j] = Data.params.get(i);
					//下一个寄存器也放置该值
					if (Data.params.get(i).startsWith("jd_"))
					{
						Data.v_value[locals + j + 1] = Data.params.get(i);
						j++;
					}
				}
			}
			System.out.println(Arrays.toString(Data.v_value));
		}
		else
		{
			return false;
		}
		return true;
	}
}
