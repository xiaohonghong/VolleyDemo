package com.example.icbc.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.icbc.newsapp.R;
import com.example.icbc.newsapp.entity.VolleyItem;
import com.example.icbc.newsapp.util.BitMapCache;

import java.util.List;

/**
 * 新闻列表装载器
 * Created by admin on 2015/9/5.
 */
public class NewsAdapter extends BaseAdapter {

    public final String TAG = getClass().getSimpleName();

    private RequestQueue queue;

    private Context mContext;

    private List<VolleyItem> items;

    private LayoutInflater inflater;

    private ImageLoader imageLoader;

    public NewsAdapter() {
    }

    public NewsAdapter(Context context,RequestQueue queue,List<VolleyItem> itemList) {
        super();
        this.mContext = context;
        this.queue = queue;
        this.items = itemList;
        this.inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        初始化imageLoader
        this.imageLoader = new ImageLoader(queue,new BitMapCache());
    }

    @Override
    public int getCount() {
        if(items == null){
            return 0;
        }
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.imageView = (ImageView)convertView.
                    findViewById(R.id.new_image);
            viewHolder.title = (TextView)convertView.
                    findViewById(R.id.news_title);
            viewHolder.content = (TextView)convertView.
                    findViewById(R.id.news_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        VolleyItem item = items.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.content.setText(item.getContent());
//        利用Volley加载网络图片
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(viewHolder.imageView,0,
                        R.mipmap.ic_launcher);

        if(!item.getImg().equals("")){
            imageLoader.get(item.getImg(),listener);
        }
        return convertView;
    }
    private static class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView content;
    }
    public void ntifyDataSetChanged(List<VolleyItem> items){
        this.items = items;
        notifyDataSetChanged();
    }
}
