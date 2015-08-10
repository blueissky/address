package com.address.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class ContactUtil {
	private static ContactUtil util;
	public static ContactUtil getUtil(){
		if(util==null){
			util=new ContactUtil();
		}
		return util;
	}
	/**
	 *获取所有联系人
	 * TODO
	 * @Author	段彬彬
	 * @param context  void
	 * @Date	2015-8-9
	 * 更新日志
	 * 2015-8-9 段彬彬  首次创建
	 *
	 */
	public ArrayList<String> getAddress(Context context){
		Uri uri_sim = Uri.parse("content://icc/adn"); //simKa 
		Uri uri_storage = Phone.CONTENT_URI;//手机存储
		ArrayList<String> list=new ArrayList<String>();//查询集合
		Cursor cursor_sotrage = context.getContentResolver().query(uri_storage,null,null,null,null);//查询手机存储
		while(cursor_sotrage.moveToNext()){
			list.add(cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.DISPLAY_NAME)));
		}
		cursor_sotrage.close();
		Cursor cursor_sim = context.getContentResolver().query(uri_sim,null,null,null,null);//查询SIM
		while(cursor_sim.moveToNext()){
			list.add(cursor_sim.getString(cursor_sim.getColumnIndex(Phone.DISPLAY_NAME)));
		}
		cursor_sim.close();
		return list;
	}
	public List<Map<String, Object>> getAddress2(Context context){
		Uri uri_sim = Uri.parse("content://icc/adn"); //simKa 
		Uri uri_storage = Phone.CONTENT_URI;//手机存储
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();//查询集合
		Cursor cursor_sotrage = context.getContentResolver().query(uri_storage,null,null,null,null);//查询手机存储
		HashMap<String, Object> map=null;
		while(cursor_sotrage.moveToNext()){
			map = new HashMap<String,Object>();
			map.put("_name",cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.DISPLAY_NAME)));
			map.put("_id", cursor_sotrage.getLong(cursor_sotrage.getColumnIndex(Phone.CONTACT_ID)));
			map.put("_tel", cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.NUMBER)));
			data.add(map);
		}
		cursor_sotrage.close();
		Cursor cursor_sim = context.getContentResolver().query(uri_sim,null,null,null,null);//查询SIM
		while(cursor_sim.moveToNext()){
			map = new HashMap<String,Object>();
			map.put("_name",cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.DISPLAY_NAME)));
			map.put("_id", cursor_sotrage.getLong(cursor_sotrage.getColumnIndex(Phone.CONTACT_ID)));
			map.put("_tel", cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.NUMBER)));
			data.add(map);
		}
		cursor_sim.close();
		return data;
//		(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
//		contactId=cursor.getLong(cursor.getColumnIndex(Phone.CONTACT_ID));
	}
	/**
	 * 新增联系人信息
	 * TODO
	 * @Author	段彬彬
	 * @param context
	 * @param name
	 * @param phoneNumber  void
	 * @Date	2015-8-9
	 * 更新日志
	 * 2015-8-9 段彬彬  首次创建
	 *
	 */
	 public void addContacts(Context context,String name,String phoneNumber){  
	        /* 往 raw_contacts 中添加数据，并获取添加的id号*/  
	        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");  
	        ContentResolver resolver = context.getContentResolver();  
	        ContentValues values = new ContentValues();  
	        /* 往 data 中添加数据（要根据前面获取的id号） */  
	        long contactId = ContentUris.parseId(resolver.insert(uri, values));  
	        uri = Uri.parse("content://com.android.contacts/data");  
	        // 添加姓名  
	        values.put("raw_contact_id", contactId);  
	        values.put("mimetype", "vnd.android.cursor.item/name");  
	        values.put("data2", name);  
	        resolver.insert(uri, values);  
	        values.clear();  
	          
	        // 添加电话  
	        values.put("raw_contact_id", contactId);  
	        values.put("mimetype", "vnd.android.cursor.item/phone_v2");  
	        values.put("data1", phoneNumber);  
	        resolver.insert(uri, values);  
	        values.clear(); 
	    }  
	 /**
	  * 删除联系人
	  * TODO
	  * @Author	段彬彬
	  * @param rawContactId  void
	  * @Date	2015-8-9
	  * 更新日志
	  * 2015-8-9 段彬彬  首次创建
	  *
	  */
	 public void deleteContact(Context context,long rawContactId){
		 context.getContentResolver().delete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, rawContactId), null,null);
	 
	 }
	 /**
	  * 删除联系人
	  * TODO
	  * @Author	段彬彬
	  * @throws Exception  void
	  * @Date	2015-8-9
	  * 更新日志
	  * 2015-8-9 段彬彬  首次创建
	  *
	  */
	 public void delete(Context context){  
	        String name = "山鸡";  
	        //根据姓名求id  
	        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");  
	        ContentResolver resolver = context.getContentResolver();  
	        Cursor cursor = resolver.query(uri, new String[]{Data._ID},"display_name=?", new String[]{name}, null);  
	        if(cursor.moveToFirst()){  
	            int id = cursor.getInt(0);  
	            //根据id删除data中的相应数据  
	            resolver.delete(uri, "display_name=?", new String[]{name});  
	            uri = Uri.parse("content://com.android.contacts/data");  
	            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});  
	        }  
	    }
	 /**
	  * 更新联系人信息
	  * 
	  * TODO
	  * @Author	段彬彬
	  * @param rawContactId
	  * @param phoneNumber  void
	  * @Date	2015-8-9
	  * 更新日志
	  * 2015-8-9 段彬彬  首次创建
	  *
	  */
	 public void updataContact(Context context,long rawContactId,String phoneNumber){
		 ContentValues values = new ContentValues();
		 values.put(Phone.NUMBER,phoneNumber);
		 values.put(Phone.TYPE,Phone.TYPE_MOBILE);
		 String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
	                + ContactsContract.Data.MIMETYPE + "=?";
	     String[] selectionArgs = new String[] { String.valueOf(rawContactId),Phone.CONTENT_ITEM_TYPE };
	     context.getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,where, selectionArgs);
		 
	 }
	 /**
	  * 打电话
	  * TODO
	  * @Author	段彬彬
	  * @param context
	  * @param phoneNumber  void
	  * @Date	2015-8-9
	  * 更新日志
	  * 2015-8-9 段彬彬  首次创建
	  *
	  */
	 public void telphone(Context context,String phoneNumber){
		 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));  
		 context.startActivity(intent);
	 }
	 /**
	  * 获得手机号码
	  * TODO
	  * @Author	段彬彬
	  * @param context
	  * @param name
	  * @return  String
	  * @Date	2015-8-9
	  * 更新日志
	  * 2015-8-9 段彬彬  首次创建
	  *
	  */
	 public String findByName(Context context,String name){
	 	Uri uri_sim = Uri.parse("content://icc/adn"); //simKa 
		Uri uri_storage = Phone.CONTENT_URI;//手机存储
		ArrayList<String> list=new ArrayList<String>();//查询集合
		Cursor cursor_sotrage = context.getContentResolver().query(uri_storage,null,
				Data.DISPLAY_NAME+"="+name,null,null);//查询手机存储
		return cursor_sotrage.getString(cursor_sotrage.getColumnIndex(Phone.NUMBER));
	 }	 
	 
	 
	 public void testContactNameByNumber(Context context){
	        String number = "18052369652";
	        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
	        ContentResolver resolver = context.getContentResolver();
	        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
	        if (cursor.moveToFirst()) {
	            String name = cursor.getString(0);
	        }
	        cursor.close();
	    }
}
