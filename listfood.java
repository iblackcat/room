package com.example.room;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayOutputStream; 
import android.util.Log; 

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class listfood extends Activity {
	
	static final String KEY_FOODID = "foodid";
	static final String KEY_USERID = "userid";
	static final String KEY_FOODNAME = "foodname";
	static final String KEY_TYPE = "type";
	static final String KEY_FOODINFO = "foodinfo";
	static final String KEY_SDATE = "startdate";
	static final String KEY_EDATE = "enddate";
	

	public static List<Map<String, String>> getJSONArray(String path) throws Exception { 
		String json = null; 
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); 
		Map<String, String> map = null; 
		URL url = new URL(path); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据. 
		conn.setConnectTimeout(5 * 1000); // 单位是毫秒，设置超时时间为5秒 
		conn.setRequestMethod("GET"); // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET 

		InputStream is = conn.getInputStream(); // 获取输入流 
		byte[] data = readStream(is); // 把输入流转换成字符数组 
		json = new String(data); // 把字符数组转换成字符串 
	
		//数据形式：[{"id":1,"name":"小猪","age":22},{"id":2,"name":"小猫","age":23}] 
		JSONArray jsonArray = new JSONArray(json); //数据直接为一个数组形式，所以可以直接 用android提供的框架JSONArray读取JSON数据，转换成Array 
		
		for (int i = 0; i < jsonArray.length(); i++) { 
			JSONObject item = jsonArray.getJSONObject(i); //每条记录又由几个Object对象组成 
			int id = item.getInt("foodid"); // 获取对象对应的值 
			String name = item.getString("foodname");
			String type = item.getString("type");
			String info = item.getString("foodinfo");
		
			map = new HashMap<String, String>(); // 存放到MAP里面 
			map.put("foodid", id + ""); 
			map.put("foodname", name); 
			map.put("type", type);
			map.put("foodinfo", info);
			list.add(map); 
		}
		/*
		for (Map<String, String> list2 : list) { 
			String id = list2.get("id"); 
			String name = list2.get("name"); 
			Log.i("abc", "id:" + id + " | name:" + name); 
		} 
		*/
		return list; 
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allfood);
		
		String path = "http://192.168.17.180/web/allmenu.php";
		List<Map<String, String>> foodlist;
		try {
			foodlist = getJSONArray(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public static byte[] readStream(InputStream inputStream) throws Exception { 
		ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
		byte[] buffer = new byte[1024]; 
		int len = 0; 
		while ((len = inputStream.read(buffer)) != -1) { 
			bout.write(buffer, 0, len); 
		} 
		bout.close(); 
		inputStream.close();
		return bout.toByteArray(); 
	} 
}
