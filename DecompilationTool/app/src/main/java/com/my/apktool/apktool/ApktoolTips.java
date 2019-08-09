package com.my.apktool.apktool;
import android.content.*;
import android.app.*;
import android.view.*;
import com.my.apktool.R;
import android.widget.*;
import android.view.View.*;

public class ApktoolTips
{
	private Context mContext;
	private int mode;
	private AlertDialog dialog;
	
	private String inFile;
	private String outFile;
	
	private TextView tv_in;
	private TextView tv_out;
	private ListView list;
	private Button btn_postive;
	
	public static final int INSTALL_FRAMEWORK = 0;
	public static final int DECODER = 1;
	public static final int BUILD = 2;
	
	private String title;
	private ApktoolTipsAdapter adapter = null;
	
	
	public ApktoolTips(Context mContext,int mode)
	{
		this.mContext = mContext;
		this.mode = mode;
		
		switch(mode)
		{
			case INSTALL_FRAMEWORK:
				title = "安装框架";
				break;
			case DECODER:
				title = "反编译";
				break;
			case BUILD:
				title = "回编译";
				break;
		}
		
		adapter = new ApktoolTipsAdapter(mContext);
		
	}

	public void setInFile(String inFile)
	{
		this.inFile = inFile;
	}

	public String getInFile()
	{
		return inFile;
	}

	public void setOutFile(String outFile)
	{
		this.outFile = outFile;
	}

	public String getOutFile()
	{
		return outFile;
	}
	
	public void show()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(title);
		builder.setCancelable(false);
		builder.setView(tipsView());
		dialog = builder.create();
		dialog.show();
	}
	
	public void postive()
	{
		btn_postive.setVisibility(View.VISIBLE);
		adapter.ok();
	}
	
	public void add(String msg)
	{
		
		adapter.add(msg);
		list.setSelection(list.getBottom());
	}
	
	public View tipsView()
	{
		View tipsView = View.inflate(mContext,R.layout.apktool_tips,null);
		tv_in = (TextView) tipsView.findViewById(R.id.apktool_tips_tv_in);
		tv_out = (TextView) tipsView.findViewById(R.id.apktool_tips_tv_out);
		list = (ListView) tipsView.findViewById(R.id.apktool_tips_list);
		btn_postive = (Button) tipsView.findViewById(R.id.apktool_tips_btn_postive);
		
		//
		
		tv_in.setText("输入： "+inFile);
		tv_out.setText("输出: "+outFile);
		
		btn_postive.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dialog.dismiss();
				}
			});
			
		
			
		
		
		list.setAdapter(adapter);
		
		return tipsView;
			
	}
	
}
