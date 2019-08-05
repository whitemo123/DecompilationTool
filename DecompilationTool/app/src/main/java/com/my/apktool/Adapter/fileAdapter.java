package com.my.apktool.Adapter;
import android.support.v7.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import com.my.apktool.R;
import android.widget.*;
import android.widget.AdapterView.*;

public class fileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private Context mcontext;
	private List<fileData> data;
	
	
	public fileAdapter(Context mcontext,List<fileData> data)
	{
		this.mcontext = mcontext;
		this.data = data;
	}
	
	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
	}
	
	private OnItemClickListener monItemClickListener;
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		monItemClickListener = onItemClickListener;
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		View view = LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_item,p1,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder p1, int p2)
	{
		// TODO: Implement this method
		fileData mobai = data.get(p2);
		((MyViewHolder) p1).tv1.setText(mobai.getFileName());
		((MyViewHolder)p1).tv2.setText(""+mobai.getFileTime());
		((MyViewHolder)p1).iv1.setImageResource(mobai.getApkIcon());
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private ImageView iv1,iv2;
		private TextView tv1,tv2;
		public MyViewHolder(View view)
		{
			super(view);
			tv1 = (TextView) view.findViewById(R.id.file_name);
			tv2 = (TextView) view.findViewById(R.id.file_time);
			iv1 = (ImageView) view.findViewById(R.id.file_image);
			iv2 = (ImageView) view.findViewById(R.id.file_items);
			
			view.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						// TODO: Implement this method
						Toast.makeText(mcontext,""+data.get(getLayoutPosition()).getFileName(),3000).show();
					}
					});
		}
		
		@Override
		public void onClick(View v)
		{
			if(monItemClickListener != null)
			{
				monItemClickListener.onItemClick(v,getItemCount());
			}
		}
	}
}
