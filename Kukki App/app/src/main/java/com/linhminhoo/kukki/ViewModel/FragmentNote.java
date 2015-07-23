package com.linhminhoo.kukki.ViewModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.linhminhoo.kukki.AdapterListView.NoteAdapter;
import com.linhminhoo.kukki.Items.NoteItems;
import com.linhminhoo.kukki.Model.NoteModel;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class FragmentNote extends Fragment{
    BaseActivity parent;
    public static FragmentNote newInstance(BaseActivity activity) {
        FragmentNote fragmentNote = new FragmentNote();
        fragmentNote.parent = activity;
        return fragmentNote;
    }

    Button btn_slidemenu;
    ListView lv_note;
    TextView tv_empty;
    ArrayList<NoteItems>arr_note;
    ArrayAdapter<NoteItems>adapter;
    NoteModel noteModel;
    UserModel userModel;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_note, container, false);

        btn_slidemenu= (Button) rootView.findViewById(R.id.btn_note_slidemenu);
        lv_note= (ListView) rootView.findViewById(R.id.lv_note);
        tv_empty= (TextView) rootView.findViewById(R.id.tv_note_empty);
        noteModel=new NoteModel(getActivity());
        userModel=new UserModel(getActivity());
        int user_id=userModel.getIdUser();
        userModel.closeDB();
        arr_note=new ArrayList<NoteItems>();

        adapter=new NoteAdapter(getActivity(), R.layout.item_note, arr_note);
        lv_note.setAdapter(adapter);
        ArrayList<NoteItems>arr_temp=new ArrayList<NoteItems>();
        arr_temp=noteModel.returnArrNote(user_id);
        for(int i=0;i<arr_temp.size();i++){
            int id=arr_temp.get(i).getId();
            int userID=arr_temp.get(i).getUser_id();
            String title=arr_temp.get(i).getTitle();
            arr_note.add(new NoteItems(id, userID, title));
        }
        adapter.notifyDataSetChanged();

        if(arr_note.size()==0){
            tv_empty.setVisibility(View.VISIBLE);
            lv_note.setVisibility(View.GONE);
        }

        return rootView;
    }
}
