package com.example.icbc.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.icbc.newsapp.adapter.NewsAdapter;
import com.example.icbc.newsapp.entity.VolleyItem;
import com.example.icbc.newsapp.http.AsynImageLoader;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = getClass().getSimpleName();
    public static final String KEY = "9f2875cfe5b06809d5dfaed4604aae80";
    private String url1 = "http://op.juhe.cn/onebox/news/words?key="+KEY;
    private static final int SHOW_RESPONSE = 0;

    public static String title = "WordItem";

    private Button sendRequest;

    private TextView responseText;

    private ImageView imageView;

    private Context context;

    private List<String> datas = new ArrayList<String>();

    private StringRequest stringRequest;

    private ArrayAdapter<String> adapter;

//    异步加载UI界面
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Log.i("HandleMessage",""+response);
                    responseText.setText(response);
            }
        }
    };
    private RequestQueue requestQueue;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.context = MainActivity.this;

        requestQueue = Volley.newRequestQueue(context);

        this.stringRequest = new StringRequest(url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("result");
                    Log.i(TAG,array.get(0).toString());
                    for(int i = 1;i<array.length();i++){
                        datas.add(array.get(i).toString());

                        if(adapter != null){
                            adapter.notifyDataSetChanged();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG",volleyError.getMessage());
            }
        });

        requestQueue.add(stringRequest);

        requestQueue.start();

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,datas);

        this.listView = (ListView)findViewById(R.id.list_view);

        this.listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                Log.i(TAG, "Item文字:" + tv.getText().toString());
                intent.putExtra(title, tv.getText().toString());
                startActivity(intent);
            }
        });

    }

/*
    private List<VolleyItem> initData(){
        final List<NewsBean.NewsResult> data = new ArrayList<NewsBean.NewsResult>();

        String key = "9f2875cfe5b06809d5dfaed4604aae80";
        String url1 = "http://op.juhe.cn/onebox/news/words?key="+key;
        String queryOption = "油价六连跌";
        final String imageUrl = "http://avatar.csdn.net/9/C/5/1_dyyaries.jpg";
        String url2 = "http://op.juhe.cn/onebox/news/query?key="+key+"&q=" + queryOption;
        StringRequest stringRequest = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("result");
                    for(int i = 1;i<array.length();i++){
                        NewsBean.NewsResult result =
                                new Gson().fromJson(array.getJSONObject(i).toString(),
                                        NewsBean.NewsResult.class);
                        Log.i("result", "" + result.toString());
//                        静态设置imageUrl用于测试
                        result.setImg(imageUrl);
                        data.add(result);
//                        动态加载列表项
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG",volleyError.getMessage());
            }
        });
        requestQueue.add(stringRequest);
        return null;
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.send_request){
//            sendRequestWithHttpUrlConnect();
//            NewsBean newsBean = JSONString.proxyJson(jsonData);
//            Log.i("onClick",""+newsBean.toString());
//            异步加载网络图片

            /*//测试网络图片加载
            AsynImageLoader asynImageLoader = new AsynImageLoader();
            String imageUrl = "http://avatar.csdn.net/9/C/5/1_dyyaries.jpg";
            asynImageLoader.showImageAsyn(imageView, imageUrl, R.mipmap.ic_launcher);*/
//          测试网络数据访问
//            try{
//                JSONObject obj = new JSONObject(jsonData);
//                Log.i("返回结果：","reason = "+obj.getString("reason"));
//                int error_code = obj.getInt("error_code");
//                Log.i("返回状态码：", "error_code = " + obj.getInt("error_code"));
//                if(error_code != 0){
//                    return;
//                }
//                JSONArray array = obj.getJSONArray("result");
//                for (int i = 0;i<array.length();i++){
//                    Log.i("result对象",new Gson().fromJson(array.getJSONObject(i).toString(),NewsBean.NewsResult.class).toString());
//                    Log.i("result["+i+"]",""+array.getJSONObject(i).toString());
//                    Log.i("title",""+array.getJSONObject(i).getString("title"));
//                    Log.i("content",""+array.getJSONObject(i).getString("content"));
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String key = "9f2875cfe5b06809d5dfaed4604aae80";
            String url1 = "http://op.juhe.cn/onebox/news/words?key="+key;
            String queryOption = "油价六连跌";
            String url2 = "http://op.juhe.cn/onebox/news/query?key="+key+"&q=" + queryOption;
            StringRequest stringRequest = new StringRequest(url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i("TAG",s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("TAG",volleyError.getMessage());
                }
            });
            requestQueue.add(stringRequest);

        }
    }

    public void sendRequestWithHttpUrlConnect(){
//        开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL("Http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(connection.getInputStream())
                            );
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    Log.i("MainActivity",""+response.toString());
                    Message msg = new Message();
                    msg.obj = response.toString();
                    msg.what = SHOW_RESPONSE;

                    handler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
