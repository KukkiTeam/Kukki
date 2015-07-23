package com.linhminhoo.kukki.ViewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linhminhoo on 7/19/2015.
 */
public class ProfileActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Init();
    }

    private ProgressDialog progress;
    String error=null;
    String message=null;
    int time=0;
    UserModel userModel;
    JSONObject object_profile=null;
    JSONObject object_profile_2=null;

    String artist="";
    String total_following="";
    String total_followed="";
    String total_receipt="";
    String url_api_avatar="";
    String description="";
    String artist_id="";
    String is_follow="";

    TextView tv_artist, tv_total_following, tv_total_followed, tv_total_receipt, tv_description;
    NetworkImageView mImageView;
    ListView lv_profile;
    ImageLoader imageLoader = AppController.getInstance(this).getImageLoader();


    //  ArrayList<NewFeedItems> arr_items;
    //  NewFeedAdapter feedAdapter;
    //   String request_page="/0|5";
    //   String get_request="";
    String user_id="";
    Button btn_follow, btn_receipt, btn_album, btn_back;
    boolean isAllow=true;
    private void Init() {

        tv_artist= (TextView) findViewById(R.id.tv_pro_artist);
        tv_description= (TextView) findViewById(R.id.tv_pro_description);
        tv_total_followed= (TextView) findViewById(R.id.tv_pro_total_followed);
        tv_total_following= (TextView) findViewById(R.id.tv_pro_total_following);
        tv_total_receipt= (TextView) findViewById(R.id.tv_pro_total_receipt);
        mImageView= (NetworkImageView) findViewById(R.id.img_pro_avatar);
        btn_receipt= (Button) findViewById(R.id.btn_pro_receipt);
        btn_album= (Button) findViewById(R.id.btn_pro_album);
        btn_follow= (Button) findViewById(R.id.btn_pro_edit);
        btn_back= (Button) findViewById(R.id.btn_pro_back);

        Intent myIntent=getIntent();
        Bundle bundle=myIntent.getBundleExtra("data");
        artist_id=bundle.getString("artist_id");

        btn_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_receipt.setBackgroundResource(R.drawable.btn_profile_receipt_press);
                btn_album.setBackgroundResource(R.drawable.btn_profile_album_normal);

            }
        });
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_album.setBackgroundResource(R.drawable.btn_profile_album_press);
                btn_receipt.setBackgroundResource(R.drawable.btn_profile_receipt_normal);
            }
        });

        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        Fragment_profile_receipt fragment_receipt=new Fragment_profile_receipt();
        Bundle bundle_1=new Bundle();
        bundle_1.putString("artist_id", artist_id);
        fragment_receipt.setArguments(bundle_1);
        FT.add(R.id.fragment_pro, fragment_receipt);
        FT.commit();


        if (imageLoader == null)
            imageLoader = AppController.getInstance(this).getImageLoader();
        new RetrieveProfile().execute();

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllow){
                    if(is_follow.equalsIgnoreCase("1")){
                        btn_follow.setBackgroundResource(R.drawable.btn_pro_follow_normal);
                        new UnFollowOne().execute();
                    }else{
                        btn_follow.setBackgroundResource(R.drawable.btn_pro_follow_press);
                        new FollowOne().execute();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private class RetrieveProfile extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userModel=new UserModel(ProfileActivity.this);
            progress = new ProgressDialog(ProfileActivity.this);
            progress.setMessage("Xin chờ....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_retrieve_profile_other+userModel.getIdUser()+"|"+artist_id, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //      Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                                message=response.getString("message");
                                object_profile=response;
                            } catch (JSONException e) {

                                message=e.toString();
                                Log.e("Detail", "Error in catch response: " + e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                        Log.e("Detail","1");
                    } else if (volleyError instanceof AuthFailureError) {
                        Log.e("Detail","2");
                    } else if (volleyError instanceof ServerError) {
                        Log.e("Detail","3");
                    } else if (volleyError instanceof NetworkError) {
                        Log.e("Detail","4");
                    } else if (volleyError instanceof ParseError) {
                        Log.e("Detail","5");
                    }}
                /*
                @Override
                public void onErrorResponse(VolleyError error) {
                    //       Log.d("Response.Error",  error.getMessage());
                    Log.e("Detail", "Error in Volley: "+error.getMessage());
                }
                */
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

            AppController.getInstance(ProfileActivity.this).addToRequestQueue(jsonObjReq);

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
                    message=null;
                    try {

                        object_profile_2=object_profile.getJSONObject("message");
                        artist=object_profile_2.getString("artist");
                        total_following=object_profile_2.getString("total_following");
                        total_followed=object_profile_2.getString("total_followed");
                        total_receipt=object_profile_2.getString("total_receipt");
                        description=object_profile_2.getString("description");
                        url_api_avatar=object_profile_2.getString("img_api");
                        is_follow=object_profile_2.getString("is_follow");
                        //    Log.e("Detail","url: "+url_api_avatar);

                    }catch (Exception ex){
                        message=ex.toString();
                        Log.e("Detail", "Error in get User JsonArray: "+ex.toString());
                    }
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
            tv_artist.setText(artist);
            tv_total_receipt.setText(total_receipt);
            tv_total_following.setText(total_following);
            tv_total_followed.setText(total_followed);
            tv_description.setText(description);
            mImageView.setImageUrl(url_api_avatar, imageLoader);
            if(is_follow.equalsIgnoreCase("1")){
                btn_follow.setBackgroundResource(R.drawable.btn_pro_follow_press);
            }else{
                btn_follow.setBackgroundResource(R.drawable.btn_pro_follow_normal);
            }
            progress.dismiss();
            if(message!=null){
                showDialog(message);
            }
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
    private class FollowOne extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAllow=false;
            userModel=new UserModel(ProfileActivity.this);
            user_id=userModel.getIdUser()+"";
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> postParam= new HashMap<String, String>();

            postParam.put("user_id", user_id );
            postParam.put("following_id", artist_id);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    PacketItem.url_follow_one, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Detail", response.toString());
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
            AppController.getInstance(ProfileActivity.this).addToRequestQueue(jsonObjReq);


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
            is_follow="1";
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
    private class UnFollowOne extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAllow=false;
            userModel=new UserModel(ProfileActivity.this);
            user_id=userModel.getIdUser()+"";
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> postParam= new HashMap<String, String>();

            postParam.put("user_id", user_id );
            postParam.put("following_id", artist_id);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    PacketItem.url_unfollow_one, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Detail", response.toString());
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
            AppController.getInstance(ProfileActivity.this).addToRequestQueue(jsonObjReq);


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
            is_follow="0";
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
    private void showDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setTitle("Thông báo !");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
