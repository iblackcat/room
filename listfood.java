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
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// ����HttpURLConnection����,���ǿ��Դ������л�ȡ��ҳ����. 
		conn.setConnectTimeout(5 * 1000); // ��λ�Ǻ��룬���ó�ʱʱ��Ϊ5�� 
		conn.setRequestMethod("GET"); // HttpURLConnection��ͨ��HTTPЭ������path·���ģ�������Ҫ��������ʽ,���Բ����ã���ΪĬ��ΪGET 

		InputStream is = conn.getInputStream(); // ��ȡ������ 
		byte[] data = readStream(is); // ��������ת�����ַ����� 
		json = new String(data); // ���ַ�����ת�����ַ��� 
	
		//������ʽ��[{"id":1,"name":"С��","age":22},{"id":2,"name":"Сè","age":23}] 
		JSONArray jsonArray = new JSONArray(json); //����ֱ��Ϊһ��������ʽ�����Կ���ֱ�� ��android�ṩ�Ŀ��JSONArray��ȡJSON���ݣ�ת����Array 
		
		for (int i = 0; i < jsonArray.length(); i++) { 
			JSONObject item = jsonArray.getJSONObject(i); //ÿ����¼���ɼ���Object������� 
			int id = item.getInt("foodid"); // ��ȡ�����Ӧ��ֵ 
			String name = item.getString("foodname");
			String type = item.getString("type");
			String info = item.getString("foodinfo");
		
			map = new HashMap<String, String>(); // ��ŵ�MAP���� 
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
