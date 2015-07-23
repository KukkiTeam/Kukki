package com.linhminhoo.kukki.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.linhminhoo.kukki.DbAdapter.DbAdapter;
import com.linhminhoo.kukki.Items.AreaContinentItem;
import com.linhminhoo.kukki.Items.TempMaterialItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/12/2015.
 */
public class AreaContinentModel {
    private SQLiteDatabase mdatabase;
    private Context context;
    private DbAdapter db;

    public AreaContinentModel(Context mcontext){
        this.context=mcontext;
        db=new DbAdapter(context);
        try{
            openDB();
        }catch(Exception e){
            Log.e("Detail", "Error in open AreaContinentModel: " + e.toString());
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
        String sql="select * from AreaContinent";
        Cursor c=mdatabase.rawQuery(sql, null);
        if(c.getCount()>0){
            check=false;
        }
        c.close();
        return check;
    }
    public boolean isCLearAreaContinent(){
        boolean check=true;
        //String sql="delete from User_Lesson";
        int num=mdatabase.delete("AreaContinent",null,null);
        if(num==0){
            check=false;
        }
        return check;
    }
    public boolean insertAreaContinent(String area, String continent){
        boolean check=true;
        ContentValues content=new ContentValues();
        content.put("area", area);
        content.put("continent", continent);

        long num=mdatabase.insert("AreaContinent", null, content);
        if(num==-1){
            check=false;
        }
        return check;
    }
    public String getAreaName(){
        String sql="select area from AreaContinent";
        String area="";
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                area=c.getString(0);
            }while (c.moveToNext());
        }
        c.close();
        return area;
    }
    public String getContinentName(){
        String sql="select continent from AreaContinent";
        String continent="";
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                continent=c.getString(0);
            }while (c.moveToNext());
        }
        c.close();
        return continent;
    }
    public ArrayList<AreaContinentItem> returnArrAreaContinent(){
        ArrayList<AreaContinentItem>arr_area_continent=new ArrayList<AreaContinentItem>();
        String sql="select * from AreaContinent";
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                String area=c.getString(0);
                String continent=c.getString(1);
                arr_area_continent.add(new AreaContinentItem(area, continent));
            }while (c.moveToNext());
        }
        c.close();
        return  arr_area_continent;
    }
}
