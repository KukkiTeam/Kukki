package com.linhminhoo.kukki.AdapterListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linhminhoo.kukki.Items.CommentItem;
import com.linhminhoo.kukki.Items.NoteItems;
import com.linhminhoo.kukki.Items.NoteMaterialItems;
import com.linhminhoo.kukki.Model.NoteMaterialModel;
import com.linhminhoo.kukki.Model.NoteModel;
import com.linhminhoo.kukki.R;
import com.linhminhoo.kukki.ViewModel.FragmentNote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class NoteAdapter extends ArrayAdapter<NoteItems> {

    private Activity activity;
    private int layout;
    private ArrayList<NoteItems> arr_note;

    public NoteAdapter(Activity context, int resource, ArrayList<NoteItems> objects) {
        super(context, resource, objects);
        this.activity=context;
        arr_note=objects;
    }

    TextView tv_title;
    Button btn_delete;

    NoteModel noteModel;
    NoteMaterialModel noteMaterialModel;
    ArrayList<NoteMaterialItems>arr_note_material;
    HashMap<CheckBox, Integer>map_cbox=new HashMap<CheckBox, Integer>();
 //   ArrayList<CheckBox>arr_cb;
    int index=0;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_note,parent,false);

        tv_title= (TextView) rowView.findViewById(R.id.tv_note_title);
        btn_delete= (Button) rowView.findViewById(R.id.btn_note_delete);

        tv_title.setText(arr_note.get(position).getTitle());
        LayoutInflater vi= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout=(LinearLayout)rowView.findViewById(R.id.content_note);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   noteModel=new NoteModel(activity);
                new AlertDialog.Builder(activity)
                        .setTitle("Thông báo !")
                        .setMessage("Bạn muốn xóa ghi chú nguyên liệu cho công thức này ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                noteModel=new NoteModel(activity);
                                noteMaterialModel=new NoteMaterialModel(activity);
                                noteModel.deleteNote(arr_note.get(position).getId());
                                noteMaterialModel.deleteNoteMaterial(arr_note.get(position).getId());
                                notifyDataSetChanged();
                                noteModel.closeDB();
                                noteMaterialModel.closeDB();
                            }})
                        .setNegativeButton("No", null).show();

              //  noteModel.closeDB();
            }
        });

        int id_note=arr_note.get(position).getId();
        arr_note_material=new ArrayList<NoteMaterialItems>();
        noteMaterialModel=new NoteMaterialModel(activity);
        arr_note_material=noteMaterialModel.returnArrNoteMaterial(id_note);
        noteMaterialModel.closeDB();
        for(int i=0;i<arr_note_material.size();i++){
            final View view=vi.inflate(R.layout.item_child_note, null);
            final CheckBox cb_note= (CheckBox) view.findViewById(R.id.cb_note);
            index=i;
            map_cbox.put(cb_note, arr_note_material.get(i).getId());
            cb_note.setText("   "+arr_note_material.get(i).getName());

            if(arr_note_material.get(index).getIsChecked()==1){
                cb_note.setChecked(true);
            }else{
                cb_note.setChecked(false);
            }

            cb_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteMaterialModel = new NoteMaterialModel(activity);
                    if (cb_note.isChecked()) {
                        noteMaterialModel.updateNoteMaterial(map_cbox.get(cb_note), 1);
                    } else {
                        noteMaterialModel.updateNoteMaterial(map_cbox.get(cb_note), 0);
                    }
                       noteMaterialModel.closeDB();
                }
            });
            linearLayout.addView(view);
        }
//        noteModel.isCLearNote();
//        noteMaterialModel.isCLearNoteMaterial();


      //    noteModel.closeDB();

        return rowView;
    }
}
