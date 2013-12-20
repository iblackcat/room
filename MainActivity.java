package com.example.room;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button shitang;
	private Button caipin;
	private Button pingjia;
	private Button denglu;
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);  
        setupViewComponent();
    } 
	private void setupViewComponent(){
        Button shitang = (Button) this.findViewById(R.id.shitang); 
        Button caipin = (Button) this.findViewById(R.id.caipin); 
        Button pingjia = (Button) this.findViewById(R.id.pingjia); 
        Button denglu = (Button) this.findViewById(R.id.denglu);
        
		shitang.setOnClickListener(shitangClick);
		caipin.setOnClickListener(caipinClick);
		pingjia.setOnClickListener(pingjiaClick);
		denglu.setOnClickListener(dengluClick);
	}
	
	private Button.OnClickListener shitangClick = new Button.OnClickListener(){
		public void onClick(View v){//Ê³ÌÃ
				Intent it = new Intent();
				//it.setClass(MainActivity.this, shitangActivity.class);
				startActivity(it);
		}
	};
	private Button.OnClickListener caipinClick = new Button.OnClickListener(){
		public void onClick(View v){//²ËÆ·
			Intent it = new Intent();
			it.setClass(MainActivity.this, listfood.class);
			startActivity(it);
		}
	};
	private Button.OnClickListener pingjiaClick = new Button.OnClickListener(){
		public void onClick(View v){//ÆÀ¼Û
				Intent it = new Intent();
				it.setClass(MainActivity.this, comment.class);
				startActivity(it);
		}
	};
	private Button.OnClickListener dengluClick = new Button.OnClickListener(){
		public void onClick(View v){//µÇÂ¼
				Intent it = new Intent();
				//it.setClass(MainActivity.this, login.class);
				startActivity(it);
		}
	};
	
}
