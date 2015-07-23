package com.linhminhoo.kukki.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.linhminhoo.kukki.DbAdapter.DbAdapter;
import com.linhminhoo.kukki.Items.TempContentItem;
import com.linhminhoo.kukki.Items.TempMaterialItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/12/2015.
 */
public class TempContentModel {
    private SQLiteDatabase mdatabase;
    private Context context;
    private DbAdapter db;

    public TempContentModel(Context mcontext){
        this.context=mcontext;
        db=new DbAdapter(context);
        try{
            openDB();
        }catch(Exception e){
            Log.e("Detail", "Error in open TempContinentModel: " + e.toString());
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
        String sql="select * from TempContent";
        Cursor c=mdatabase.rawQuery(sql, null);
        if(c.getCount()>0){
            check=false;
        }
        c.close();
        return check;
    }
    public boolean isCLearTempContent(){
        boolean check=true;
        //String sql="delete from User_Lesson";
        int num=mdatabase.delete("TempContent",null,null);
        if(num==0){
            check=false;
        }
        return check;
    }
    public boolean insertTempContent(String img_api_url, String description){
        boolean check=true;
        ContentValues content=new ContentValues();
        content.put("img_api_url", img_api_url);
        content.put("description", description);
        long num=mdatabase.insert("TempContent", null, content);
        if(num==-1){
            check=false;
        }
        return check;
    }
    public int getSize(){
        String sql="select * from TempContent";
        Cursor c=mdatabase.rawQuery(sql,null);
        int n=c.getCount();
        c.close();
        return n;
    }
    public ArrayList<TempContentItem> returnArrContent(){
        ArrayList<TempContentItem>arr_content=new ArrayList<TempContentItem>();
        String sql="select * from TempContent";
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                int id  =c.getInt(0);
                String img_api_url=c.getString(1);
                String description=c.getString(2);
                arr_content.add(new TempContentItem(id, img_api_url, description));
            }while (c.moveToNext());
        }
        c.close();
        return  arr_content;
    }
}
