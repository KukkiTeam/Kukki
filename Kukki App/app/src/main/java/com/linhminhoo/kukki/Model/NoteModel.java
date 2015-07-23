package com.linhminhoo.kukki.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.linhminhoo.kukki.DbAdapter.DbAdapter;
import com.linhminhoo.kukki.Items.NoteItems;
import com.linhminhoo.kukki.Items.TempMaterialItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class NoteModel {
    private SQLiteDatabase mdatabase;
    private Context context;
    private DbAdapter db;

    public NoteModel(Context mcontext){
        this.context=mcontext;
        db=new DbAdapter(context);
        try{
            openDB();
        }catch(Exception e){
            Log.e("Detail", "Error in open NoteModel: " + e.toString());
        }

    }
    public void openDB() throws SQLException {
        mdatabase=db.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
    }

    public boolean insertNote(String title, int user_id){
        boolean check=true;
        ContentValues content=new ContentValues();
        content.put("title", title);
        content.put("user_id", user_id);

        long num=mdatabase.insert("Note", null, content);
        if(num==-1){
            check=false;
        }
        return check;
    }
    public boolean deleteNote(int id){
        String where="id="+id;
        return mdatabase.delete("Note", where, null)!=0;
    }
    public ArrayList<NoteItems> returnArrNote(int userID){
        ArrayList<NoteItems>arr_note=new ArrayList<NoteItems>();
        String sql="select * from Note where user_id="+userID;
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                int id  =c.getInt(0);
                String title=c.getString(1);
                int user_id=c.getInt(2);
                arr_note.add(new NoteItems(id, user_id, title));
            }while (c.moveToNext());
        }
        c.close();
        return  arr_note;
    }
    public int getIdNoteMax(){
        String sql="select id from Note order by id desc limit 1";
        int id=0;
        Cursor c=mdatabase.rawQuery(sql,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                id=c.getInt(0);
            }while (c.moveToNext());
        }
        c.close();
        return id;
    }
    public boolean isCLearNote(){
        boolean check=true;
        //String sql="delete from User_Lesson";
        int num=mdatabase.delete("Note",null,null);
        if(num==0){
            check=false;
        }
        return check;
    }


}
