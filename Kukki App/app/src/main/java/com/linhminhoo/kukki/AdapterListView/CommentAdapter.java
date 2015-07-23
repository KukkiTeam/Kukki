package com.linhminhoo.kukki.AdapterListView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.linhminhoo.kukki.Items.CommentItem;
import com.linhminhoo.kukki.Items.NewFeedItems;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhminhoo on 7/15/2015.
 */
public class CommentAdapter extends ArrayAdapter {
    private Context context;
    private int layout;
    private ArrayList<CommentItem> arr_comment;

    private ImageLoader mImageLoader;
    public CommentAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        arr_comment=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.item_comment,parent,false);

        if(mImageLoader==null){
            mImageLoader= AppController.getInstance(context).getImageLoader();
        }


        TextView tv_artist= (TextView) rowView.findViewById(R.id.tv_comment_artist);
        TextView tv_description= (TextView) rowView.findViewById(R.id.tv_comment_description);
        TextView tv_time= (TextView) rowView.findViewById(R.id.tv_comment_time);
        NetworkImageView img_avatar= (NetworkImageView) rowView.findViewById(R.id.img_comment_avatar);

        tv_artist.setText(arr_comment.get(position).getArtist());
        tv_description.setText(arr_comment.get(position).getDescription());
        tv_time.setText(arr_comment.get(position).getCreated_at());
        img_avatar.setImageUrl(arr_comment.get(position).getUrl_api_avatar(), mImageLoader);

        return rowView;
    }
}
