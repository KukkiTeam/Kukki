package com.linhminhoo.kukki.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.linhminhoo.kukki.DbAdapter.DbAdapter;
import com.linhminhoo.kukki.Items.NoteItems;
import com.linhminhoo.kukki.Items.NoteMaterialItems;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class NoteMaterialModel {
    private SQLiteDatabase mdatabase;
    private Context context;
    private DbAdapter db;

    public NoteMaterialModel(Context mcontext){
        this.context=mcontext;
        db=new DbAdapter(context);
        try{
            openDB();
        }catch(Exception e){
            Log.e("Detail", "Error in open NoteMaterialModel: " + e.toString());
        }

    }
    public void openDB() throws SQLException {
        mdatabase=db.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
    }
    public boolean insertNote(String name, int isChecked, int note_id){
        boolean check=true;
        ContentValues content=new ContentValues();
        content.put("name", name);
        content.put("isChecked", isChecked);
        content.put("note_id", note_id);

        long num=mdatabase.insert("NoteMaterial", null, content);
        if(num==-1){
            check=false;
        }
        return check;
    }
    public ArrayList<NoteMaterialItems> returnArrNoteMaterial(int noteID){
        ArrayList<NoteMaterialItems>arr_noteMaterial=new ArrayList<NoteMaterialItems>();
        String sql="select * from NoteMaterial where note_id="+noteID;
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                int id  =c.getInt(0);
                String name=c.getString(1);
                int isChecked=c.getInt(2);
                int note_id=c.getInt(3);
                arr_noteMaterial.add(new NoteMaterialItems(id, isChecked, note_id, name));
            }while (c.moveToNext());
        }
        c.close();
        return  arr_noteMaterial;
    }
    public boolean updateNoteMaterial(int id, int isChecked){
        String where="id="+id;
        ContentValues content=new ContentValues();
        content.put("isChecked", isChecked);
        return mdatabase.update("NoteMaterial", content, where, null)!=0;
    }
    public int getNoteMaterialCount(){
        String sql="select * from NoteMaterial";
        Cursor c = mdatabase.rawQuery(sql, null);
        int count =c.getCount();
        c.close();
        return count;
    }
    public boolean isCLearNoteMaterial(){
        boolean check=true;
        //String sql="delete from User_Lesson";
        int num=mdatabase.delete("NoteMaterial",null,null);
        if(num==0){
            check=false;
        }
        return check;
    }
    public boolean deleteNoteMaterial(int noteID){
        String where="note_id="+noteID;
        return mdatabase.delete("NoteMaterial", where, null)!=0;
    }
}
