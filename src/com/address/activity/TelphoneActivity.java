package com.address.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.address.contact.ContactUtil;
import com.example.tongxunlu.R;

public class TelphoneActivity extends Activity {
	private TextView textName;
	private TextView textPhone;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telphone);
		textName = (TextView)findViewById(R.id.name);
		textPhone= (TextView)findViewById(R.id.phone);
		String name = this.getIntent().getExtras().getString("name");//获取传递过来的值
		id = this.getIntent().getExtras().getString("id");
		String tel = this.getIntent().getExtras().getString("tel");
		this.setText(name, tel);
	}
	public void setText(String name,String tel){
		textName.setText(name);
		textPhone.setText(tel);
	}
	public void call(View v){
		ContactUtil.getUtil().telphone(this,textPhone.getText().toString());
	}
	public void delPeople(View v){
		ContactUtil.getUtil().deleteContact(this,Integer.parseInt(id));
		this.finish();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
