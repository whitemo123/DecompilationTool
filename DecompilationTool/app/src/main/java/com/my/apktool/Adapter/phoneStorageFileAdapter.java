package com.my.apktool.Adapter;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.my.apktool.*;
import com.my.apktool.Utils.*;
import java.io.*;
import java.util.*;
import java.text.*;
import android.view.View.*;

public class phoneStorageFileAdapter extends BaseAdapter
{
	
	List<File> data;
	Context mcontext;
	OnClickListener onClickListener;
	
	public phoneStorageFileAdapter(Context mcontext,List<File> data,OnClickListener onClickListener)
	{
		this.mcontext = mcontext;
		this.data = data;
		this.onClickListener = onClickListener;
	}
	
	public File[]  setfiledata(List<File> data) 
	{
        this.data = data;
        sort();
        this.notifyDataSetChanged();
        File[] files = new File[data.size()];
        for (int i = 0;i<files.length;i++) 
		{
            files[i] = data.get(i);
        }
        return files;
    }

    public File[]  setfiledata() 
	{
        File[] files = new File[data.size()];
        for (int i = 0;i<files.length;i++) 
		{
            files[i] = data.get(i);
        }
        return files;
    }
	
	int sortWay = FileSortFactory.SORT_BY_FOLDER_AND_NAME;


    public void setSortWay(int sortWay) 
	{
        this.sortWay = sortWay;
    }
	
	private void sort()
	{
        Collections.sort(this.data, FileSortFactory.getWebFileQueryMethod(sortWay));
    }
	
	
	@Override
	public void notifyDataSetChanged()
	{
		// TODO: Implement this method
		sort();
		super.notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return data.size();
	}

	@Override
	public Object getItem(int p1)
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
		// TODO: Implement this method
		File file = data.get(p1);
		MyViewHolder myViewHolder;
		if(p2 == null)
		{
			p2 = LayoutInflater.from(mcontext).inflate(R.layout.activity_phonestorageitem,null);
			myViewHolder = new MyViewHolder(p2);
			p2.setTag(myViewHolder);
		}
		else
		{
			myViewHolder = (phoneStorageFileAdapter.MyViewHolder) p2.getTag();
		}
		
		if(file.isDirectory())
		{
			myViewHolder.iv1.setImageResource(R.drawable.folder);
			myViewHolder.tv3.setText("");
		}
		else
		{
			String resd = file.toString();
			if(resd.endsWith(".apk"))
			{
				myViewHolder.iv1.setImageResource(R.drawable.apk);
			}
			else if(resd.endsWith(".xml"))
			{
				myViewHolder.iv1.setImageResource(R.drawable.xml);
			}
			else if(resd.endsWith(".dex"))
			{
				myViewHolder.iv1.setImageResource(R.drawable.dex);
			}
			else
			{
				myViewHolder.iv1.setImageResource(R.drawable.android);
			}
			myViewHolder.tv3.setText(generateSize(file));
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		myViewHolder.tv1.setText(file.getName());
		myViewHolder.tv2.setText(dateFormat.format(new Date(file.lastModified())));
		
		myViewHolder.iv2.setTag(p1);
		myViewHolder.iv2.setOnClickListener(this.onClickListener);
		return p2;
	}

	class MyViewHolder extends RecyclerView.ViewHolder
	{
		private ImageView iv1,iv2;
		private TextView tv1,tv2,tv3;
		
		public MyViewHolder(View view)
		{
			super(view);
			iv1 = (ImageView) view.findViewById(R.id.files_image);
			iv2 = (ImageView) view.findViewById(R.id.files_items);
			tv1 = (TextView) view.findViewById(R.id.files_name);
			tv2 = (TextView) view.findViewById(R.id.files_time);
			tv3 = (TextView) view.findViewById(R.id.files_size);
		}
	}
	
	private String generateSize(File file)
	{
        long result = file.length();
		long gb = 2 << 29;
		long mb = 2 << 19;
		long kb = 2 << 9;
		if (result < kb)
			return result + "B";
		else if (result >= kb && result < mb)
			return String.format("%.2fKB", result / (double) kb);
		else if (result >= mb && result < gb)
			return String.format("%.2fMB", result / (double) mb);
		else if (result >= gb)
			return String.format("%.2fGB", result / (double) gb);
		return null;
	}
}
