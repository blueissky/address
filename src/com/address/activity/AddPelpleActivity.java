package com.address.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.address.contact.ContactUtil;
import com.example.tongxunlu.R;

public class AddPelpleActivity extends Activity {
	private EditText name;
	private EditText phone;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_people);
		name=(EditText)findViewById(R.id.add_name);
		phone=(EditText)findViewById(R.id.add_phone);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.add_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * 新增联系人
	 * TODO
	 * @Author	段彬彬
	 * @param v  void
	 * @Date	2015-8-10
	 * 更新日志
	 * 2015-8-10 段彬彬  首次创建
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		ContactUtil.getUtil().addContacts(this, name.getText().toString(), 
											phone.getText().toString());
		this.finish();
		return super.onOptionsItemSelected(item);
	}
}
