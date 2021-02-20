package com.qianyou.listener5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;


public class FileLog {
		private String name;
		private JSONObject obj;
		private int idx=0;
		public FileLog(String flieName){
			this.name = flieName;
			this.obj = new JSONObject();
			File file=new File(flieName);
			
			if(file.exists())
			{
				try {
					InputStream in = new FileInputStream(flieName);
					long len = file.length();
					byte []buffer=new byte[(int) len];
					in.read(buffer);
					String data=new String(buffer);
					obj=new JSONObject(data);
					in.close();
					idx=obj.getInt("idx");
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		public void WriteStr(String str){
			try {
				obj.put(""+idx, str);
				idx++;
				obj.put("idx",idx);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Save();
		}
		
		public String ReadStr(int id){
			try {
				return (String) obj.get(String.format("%d", id));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}
		
		public int GetNum(){
			try {
				return  obj.getInt("idx");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		
		@SuppressLint("NewApi") 
		public void Delete(String id){
			obj.remove(id);
		}
		
		@SuppressLint("NewApi") 
		public void Save() {
	        FileWriter fw;
	        try {     
	        	File saveFile = new File(name);
	        	saveFile.createNewFile();        	
	            fw = new FileWriter(name);
	            PrintWriter out = new PrintWriter(fw);
	            out.write(obj.toString());
	            out.println();
	            fw.close();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	

	
}
