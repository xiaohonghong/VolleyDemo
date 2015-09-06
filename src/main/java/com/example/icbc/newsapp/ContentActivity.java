package com.example.icbc.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.icbc.newsapp.adapter.NewsAdapter;
import com.example.icbc.newsapp.entity.VolleyItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/9/6.
 */
public class ContentActivity extends AppCompatActivity {
    private Context mContext;
    private Bundle mBundle;
    private String queryOption;
    private String url2 = "http://op.juhe.cn/onebox/news/query?key="+MainActivity.KEY+"&q=";
    public final String TAG = getClass().getSimpleName();
    private ListView listView;
    private List<VolleyItem> listData;
    private RequestQueue mqueue;
    private NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_activity);
        mContext = ContentActivity.this;
        queryOption = getIntent().getStringExtra(MainActivity.title);
//        Log.i(TAG,url2 + queryOption);
        url2 += queryOption;
        mqueue = Volley.newRequestQueue(mContext);
        initListData();
        listView = (ListView)findViewById(R.id.content_list_id);
        adapter = new NewsAdapter(mContext,mqueue,listData);
        listView.setAdapter(adapter);
    }
    private void initListData(){
        StringRequest request = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                listData = new ArrayList<VolleyItem>();
                try {
                    jsonObject = new JSONObject(response);
                    Log.i(TAG,"返回的新闻搜索结果"+jsonObject.toString());
                    if(jsonObject.get("error_code") == 0){
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for(int i=0;i<jsonArray.length();i++){
                            listData.add(new Gson().
                                    fromJson(jsonArray.getString(i), VolleyItem.class));
                            adapter.ntifyDataSetChanged(listData);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "请求失败！");
            }
        });
        mqueue.add(request);
        mqueue.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
