package com.linhminhoo.kukki.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.linhminhoo.kukki.DbAdapter.DbAdapter;
import com.linhminhoo.kukki.Items.TempMaterialItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/11/2015.
 */
public class TempMaterialModel {
    private SQLiteDatabase mdatabase;
    private Context context;
    private DbAdapter db;

    public TempMaterialModel(Context mcontext){
        this.context=mcontext;
        db=new DbAdapter(context);
        try{
            openDB();
        }catch(Exception e){
            Log.e("Detail", "Error in open TempMaterialModel: " + e.toString());
        }

    }
    public void openDB() throws SQLException {
        mdatabase=db.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
    }
    public boolean isEmpty(){
        boolean check=true;
        String sql="select * from TempMaterial";
        Cursor c=mdatabase.rawQuery(sql, null);
        if(c.getCount()>0){
            check=false;
        }
        c.close();
        return check;
    }
    public boolean isCLearTempMaterial(){
        boolean check=true;
        //String sql="delete from User_Lesson";
        int num=mdatabase.delete("TempMaterial",null,null);
        if(num==0){
            check=false;
        }
        return check;
    }
    public boolean insertTempMaterial(String name){
        boolean check=true;
        ContentValues content=new ContentValues();
        content.put("name", name);
        long num=mdatabase.insert("TempMaterial", null, content);
        if(num==-1){
            check=false;
        }
        return check;
    }
    public int getSize(){
        String sql="select * from TempMaterial";
        Cursor c=mdatabase.rawQuery(sql,null);
        int n=c.getCount();
        c.close();
        return n;
    }
    public ArrayList<TempMaterialItem> returnArrMaterial(){
        ArrayList<TempMaterialItem>arr_material=new ArrayList<TempMaterialItem>();
        String sql="select * from TempMaterial";
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
              int id  =c.getInt(0);
                String name=c.getString(1);
                arr_material.add(new TempMaterialItem(id, name));
            }while (c.moveToNext());
        }
        c.close();
        return  arr_material;
    }
}
