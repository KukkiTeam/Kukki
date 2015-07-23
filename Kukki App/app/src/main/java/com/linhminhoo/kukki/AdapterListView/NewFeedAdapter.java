package com.linhminhoo.kukki.AdapterListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Items.NewFeedItems;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.ImageNetwork.FeedImageView;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;
import com.linhminhoo.kukki.ViewModel.CommentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linhminhoo on 7/6/2015.
 */
public class NewFeedAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<NewFeedItems> feedItems;

    String error=null;
    String message=null;
    int time=0;
    String post_id="";
    String user_id="";

    boolean isAllow=true;

//    ArrayList<Integer>arr_selected=new ArrayList<Integer>();

    RequestQueue mRequestQueue=null;
    UserModel userModel;
    ImageLoader imageLoader = AppController.getInstance(activity).getImageLoader();
    public NewFeedAdapter(Activity context, ArrayList<NewFeedItems> objects) {
        this.activity=context;
        feedItems=objects;

    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.new_feed, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance(activity).getImageLoader();

        TextView title= (TextView) convertView.findViewById(R.id.tv_newfeed_title);
        TextView artist= (TextView) convertView.findViewById(R.id.tv_newfeed_artist);
        TextView time_finish= (TextView) convertView.findViewById(R.id.tv_newfeed_time);
        final TextView total_like= (TextView) convertView.findViewById(R.id.tv_newfeed_total_like);
        TextView total_comment= (TextView) convertView.findViewById(R.id.tv_newfeed_total_comment);
        TextView total_view= (TextView) convertView.findViewById(R.id.tv_newfeed_total_view);
        TextView total_kcal= (TextView) convertView.findViewById(R.id.tv_newfeed_kcal);
        ImageView img_like= (ImageView) convertView.findViewById(R.id.newfeed_img_like);

        final ImageView img_isLike= (ImageView) convertView.findViewById(R.id.newfeed_img_like);


        FeedImageView feedImageView= (FeedImageView) convertView.findViewById(R.id.feedImage1);
       // mRequestQueue= Volley.newRequestQueue(activity);

        final NewFeedItems items=feedItems.get(position);
        /*
        if(arr_selected.contains(items.getId())){
            img_isLike.setBackgroundResource(R.drawable.home_like_tap);
        }
        */
        title.setText(items.getTitle());
        artist.setText(items.getArtist());
        if(items.getTime_finish()==null){
            time_finish.setText("Chưa cập nhật");
        }else{
            time_finish.setText(items.getTime_finish()+" phút");
        }
        total_like.setText(items.getTotal_like());
        total_comment.setText(items.getTotal_comment());
        total_view.setText(items.getTotal_view());

        if(items.getIsLike().equalsIgnoreCase("1")){
            img_isLike.setBackgroundResource(R.drawable.home_like_tap);
            img_isLike.setTag("Like");
        }else{
            img_like.setBackgroundResource(R.drawable.home_like);
            img_isLike.setTag("Unlike");
        }

        if(items.getTotal_kcal()==null){
            total_kcal.setText("Chưa cập nhật");
        }else{
            total_kcal.setText(items.getTotal_kcal()+" kcal");
        }
        feedImageView.setImageUrl(items.getFeedImage(), imageLoader);
        feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                    @Override
                    public void onError() {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_isLike.getTag().equals("Like")){

                    img_isLike.setBackgroundResource(R.drawable.home_like);
                    img_isLike.setTag("Unlike");
                    post_id=items.getId()+"";
                    items.setIsLike("0");
                    int sum=Integer.parseInt(items.getTotal_like())-1;
                    total_like.setText(sum+"");
                    items.setTotal_like(sum+"");
                    if(isAllow){
                        new UnLikePost().execute();
                    }

                  //  arr_selected.remove(items.getId());
                //    Toast.makeText(activity, "Unlike "+items.getId(), Toast.LENGTH_SHORT).show();
                }else{

                    img_isLike.setBackgroundResource(R.drawable.home_like_tap);
                    img_isLike.setTag("Like");
                    post_id=items.getId()+"";
                    items.setIsLike("1");
                    int sum=Integer.parseInt(items.getTotal_like())+1;
                    total_like.setText(sum+"");
                    items.setTotal_like(sum+"");
                    notifyDataSetChanged();
                    if(isAllow){
                        new LikePost().execute();
                    }

                //    new LikePost().execute();
                }
            }
        });

        return convertView;
    }
    private class LikePost extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAllow=false;
            userModel=new UserModel(activity);
            user_id=userModel.getIdUser()+"";
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> postParam= new HashMap<String, String>();

            postParam.put("post_id", post_id );
            postParam.put("user_id", user_id);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    PacketItem.url_like_post, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                      //      Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                                message=response.getString("message");
                            } catch (JSONException e) {
                                Log.e("Detail","Error in get response: ");
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Detail", "Error: "+error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put( "Authorization", userModel.getApiKeyUser());
                    return headers;
                }
            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //    mRequestQueue.add(jsonObjReq);
            AppController.getInstance(activity).addToRequestQueue(jsonObjReq);


            while(isNull(error)){
                SystemClock.sleep(500);
                time++;
                if(time>26){
                    time=0;
                    break;
                }
            }
            if(error!=null){
                if(error.equals("false")){
                    error=null;
                    message="";
                }else{
                    error=null;

                }
            }else{
                message="Không có phản hồi từ máy chủ, vui lòng thử lại sau !";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userModel.closeDB();
            if(!message.equalsIgnoreCase("")){
                Log.e("Detail", "message: "+message);
            }
            isAllow=true;

        }

        private boolean isNull(String temp){
            boolean check=false;
            if(temp==null){
                check=true;
            }else{
                check=false;
            }
            return check;
        }

    }
    private class UnLikePost extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAllow=false;
            userModel=new UserModel(activity);
            user_id=userModel.getIdUser()+"";
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> postParam= new HashMap<String, String>();

            postParam.put("post_id", post_id );
            postParam.put("user_id", user_id);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    PacketItem.url_unlike_post, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
              //              Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                                message=response.getString("message");
                            } catch (JSONException e) {
                                Log.e("Detail","Error in get response: ");
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Detail", "Error: "+error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put( "Authorization", userModel.getApiKeyUser());
                    return headers;
                }
            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          //  mRequestQueue.add(jsonObjReq);
            AppController.getInstance(activity).addToRequestQueue(jsonObjReq);

            while(isNull(error)){
                SystemClock.sleep(500);
                time++;
                if(time>26){
                    time=0;
                    break;
                }
            }
            if(error!=null){
                if(error.equals("false")){
                    error=null;
                    message="";
                }else{
                    error=null;

                }
            }else{
                message="Không có phản hồi từ máy chủ, vui lòng thử lại sau !";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userModel.closeDB();
            if(!message.equalsIgnoreCase("")){
                Log.e("Detail", "message: "+message);
            }
            isAllow=true;

        }

        private boolean isNull(String temp){
            boolean check=false;
            if(temp==null){
                check=true;
            }else{
                check=false;
            }
            return check;
        }

    }
}
