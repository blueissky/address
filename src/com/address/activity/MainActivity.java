package com.address.activity;

import java.util.ArrayList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.address.contact.ContactUtil;
import com.example.tongxunlu.R;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	private ListView list_Phone;
	private ArrayList<Map<String, Object>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list_Phone = (ListView) findViewById(R.id.list_Phone);
	}

	/**
	 * 列表显示联系人 TODO
	 * 
	 * @Author 段彬彬 void
	 * @Date 2015-8-9 更新日志 2015-8-9 段彬彬 首次创建
	 * 
	 */
	public void showList(final Context context) {
		String[] from = new String[] { "_name", "_id", "_tel" };
		int[] to = new int[] { R.id._name, R.id._id, R.id._tel };
		data = (ArrayList) ContactUtil.getUtil().getAddress2(this);
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.phone_list, from, to);
		list_Phone.setAdapter(adapter);
		list_Phone.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				Map<String, Object> map = data.get(position);

				Intent intent = new Intent(context, TelphoneActivity.class);
				intent.putExtra("name", map.get("_name").toString());
				intent.putExtra("id", map.get("_id").toString());
				intent.putExtra("tel", map.get("_tel").toString());
				context.startActivity(intent);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.add_people) {
			Intent intent=new Intent(this,AddPelpleActivity.class);
			this.startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		this.showList(this);
		Toast.makeText(this,"resume",Toast.LENGTH_SHORT).show();
		super.onResume();
	}
	@Override
	protected void onPause() {
		Toast.makeText(this,"onpause",Toast.LENGTH_SHORT).show();
		super.onPause();
	}
}
