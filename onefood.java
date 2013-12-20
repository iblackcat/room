package com.example.room;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class onefood extends Activity {
	/*
	 * 显示问题的具体内容
	 * 以及 对应问题的所有答案
	 * 
	 * 点击回复问题按钮，跳转到回复界面final_ans
	 */
	
	/*static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_CONTEXT = "context";*/
	
	static final String KEY_FOODID = "foodid";
	static final String KEY_FOODNAME = "foodname";
	static final String KEY_FOODINFO = "foodinfo";
	
	String foodid;
	String foodname;
	String foodinfo;
	private Button comment;
	private TextView food_name;

	ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onefood);
		Bundle bundle = this.getIntent().getExtras();
		
		foodid = bundle.getString("FOODID");
		foodname = bundle.getString("FOODNAME");
		foodinfo = bundle.getString("FOODINFO");
		comment = (Button) findViewById(R.id.comment);
		food_name = (TextView) findViewById(R.id.food_name);
		food_name.setText(foodname);
		comment.setOnClickListener(replyOnClick);

		/*
		 * 从服务器端获取对应问题的回复
		 */
		String url = "http://192.168.17.180/web/getcomment.php";

		HttpClient client;
		HttpEntity entity;
		HttpPost post;
		HttpResponse response = null;
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();

		NameValuePair pair = new BasicNameValuePair("foodid", foodid);
		pairList.add(pair);

		try {
			client = new DefaultHttpClient();
			entity = new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
			post = new HttpPost(url);
			post.setEntity(entity);

			response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}


		entity = response.getEntity();
		InputStream is;
		BufferedReader reader;
		StringBuilder sb;
		String line, result = null;
		JSONArray jArray;

		// 获得JSON格式对象字符串
		try {
			is = entity.getContent();
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
			sb = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<HashMap<String, String>> answerList = new ArrayList<HashMap<String, String>>();

		try {
			jArray = new JSONArray(result);
			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("comment", json_data.getString("comment"));
				answerList.add(map);
			}
		} catch (JSONException e) {
		} catch (ParseException e) {
		}

		

		SimpleAdapter adapter = new SimpleAdapter(this, answerList,
				R.layout.single_item2, new String[] { "comment" },
				new int[] { R.id.Item_title });
		((ListView) findViewById(R.id.comment_list)).setAdapter(adapter);

	}

	private Button.OnClickListener replyOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Bundle bundle = new Bundle();
			bundle.putString("FOODID", foodid);
			Intent it_a = new Intent();
			it_a.putExtras(bundle);
			it_a.setClass(onefood.this, comment.class);
			startActivity(it_a);
			onefood.this.finish();
		}
	};
}
