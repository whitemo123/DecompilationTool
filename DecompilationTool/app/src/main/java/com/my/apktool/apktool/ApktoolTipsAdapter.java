package com.my.apktool.apktool;
import android.widget.*;
import android.view.*;
import android.content.*;
import com.my.apktool.R;
import java.util.*;

public class ApktoolTipsAdapter extends BaseAdapter
{

	public ApktoolTipsAdapter(Context mContext)
	{
		this.mContext = mContext;
	}

	public void add(String msg)
	{
		data.add(msg);
		num ++;
		this.notifyDataSetInvalidated();
		//this.notifyDataSetChanged();
	}
	
	public void ok()
	{
		num ++;
		this.notifyDataSetInvalidated();
		//this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return data.size();
	}

	@Override
	public String getItem(int p1)
	{
		// TODO: Implement this method
		return data.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		ViewHolder holder;
		
		
		if(p2 == null)
		{
			p2 = View.inflate(mContext,R.layout.apktool_tips_view,null);
			holder = new ViewHolder(p2);
			p2.setTag(holder);
		}
		else
		{
			holder = (ApktoolTipsAdapter.ViewHolder) p2.getTag();
		}
		
		if(num == p1)
		{
			holder.runIcon.setVisibility(View.VISIBLE);
			holder.progress.setVisibility(View.VISIBLE);
		}else
		{
			holder.runIcon.setVisibility(View.INVISIBLE);
			holder.progress.setVisibility(View.INVISIBLE);
			
		}
		 
		holder.msg.setText(getItem(p1));
		
		return p2;
	}
	
	private static class ViewHolder
	{
		
		ImageView runIcon;
		TextView msg;
		ProgressBar progress;
		
		public ViewHolder(View view)
		{
			runIcon = (ImageView) view.findViewById(R.id.apktool_tips_view_run);
			msg = (TextView) view.findViewById(R.id.apktool_tips_view_msg);
			progress = (ProgressBar) view.findViewById(R.id.apktool_tips_view_progress);
		}
	}
	
	private Context mContext;
	private List<String> data = new ArrayList<>();
	private int num = -1;
	
	
	
}
