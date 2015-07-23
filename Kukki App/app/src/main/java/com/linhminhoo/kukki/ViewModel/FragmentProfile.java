package com.linhminhoo.kukki.ViewModel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
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
import com.linhminhoo.kukki.AdapterListView.NewFeedAdapter;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Items.CommentItem;
import com.linhminhoo.kukki.Items.NewFeedItems;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.ImageNetwork.FeedImageView;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentProfile extends Fragment {


    BaseActivity parent;
    public static FragmentProfile newInstance(BaseActivity activity) {
        FragmentProfile fragmentProfile = new FragmentProfile();
        fragmentProfile.parent = activity;
        return fragmentProfile;
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

    TextView tv_artist, tv_total_following, tv_total_followed, tv_total_receipt, tv_description, tv_profile_empty;
    NetworkImageView mImageView;
    ListView lv_profile;
    ImageLoader imageLoader = AppController.getInstance(getActivity()).getImageLoader();


  //  ArrayList<NewFeedItems> arr_items;
  //  NewFeedAdapter feedAdapter;
 //   String request_page="/0|5";
 //   String get_request="";
    String user_id="";
    Button btn_create, btn_receipt, btn_album;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        tv_artist= (TextView) rootView.findViewById(R.id.tv_profile_artist);
        tv_total_followed= (TextView) rootView.findViewById(R.id.tv_profile_total_followed);
        tv_total_following= (TextView) rootView.findViewById(R.id.tv_profile_total_following);
        tv_total_receipt= (TextView) rootView.findViewById(R.id.tv_profile_total_receipt);
        tv_description= (TextView) rootView.findViewById(R.id.tv_profile_description);
        mImageView= (NetworkImageView) rootView.findViewById(R.id.img_profile_avatar);
        btn_create= (Button) rootView.findViewById(R.id.btn_profile_create);
        btn_album= (Button) rootView.findViewById(R.id.btn_profile_album);
        btn_receipt= (Button) rootView.findViewById(R.id.btn_profile_receipt);
        userModel=new UserModel(getActivity());
        user_id=userModel.getIdUser()+"";
        userModel.closeDB();

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

        if (imageLoader == null)
            imageLoader = AppController.getInstance(getActivity()).getImageLoader();

        new RetrieveProfile().execute();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myItent=new Intent(getActivity(), PostActivity.class);
                startActivity(myItent);
            }
        });

        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        Fragment_profile_receipt fragment_receipt=new Fragment_profile_receipt();
        Bundle bundle_1=new Bundle();
        bundle_1.putString("artist_id","noop");
        fragment_receipt.setArguments(bundle_1);
        FT.add(R.id.fragment_profile, fragment_receipt);
        FT.commit();

		return rootView;
	}
    private class RetrieveProfile extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userModel=new UserModel(getActivity());
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Xin chờ....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_retrieve_profile+userModel.getIdUser(), null,
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

            AppController.getInstance(getActivity()).addToRequestQueue(jsonObjReq);

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
    /*
    private class LoadNewFeed extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_newfeed_profile+get_request+"|"+user_id, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //   VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        Log.e("Detail", "Response: " + response.toString());
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.e("Detail", "error in get response: "+error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance(getActivity()).addToRequestQueue(jsonReq);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
    */
    private void showDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
    /*
    private void parseJsonFeed(JSONObject response) {
        Log.e("Detail", "ParseJson");
        try {
            JSONArray feedArray = response.getJSONArray("message");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                NewFeedItems item = new NewFeedItems();
                item.setId(feedObj.getInt("id"));
                item.setTitle(feedObj.getString("title"));
                item.setArtist(feedObj.getString("name"));
                item.setTime_finish(feedObj.getString("total_time_finish"));
                item.setTotal_comment(feedObj.getString("total_comment"));
                item.setTotal_like(feedObj.getString("total_like"));
                item.setTotal_kcal(feedObj.getString("total_kcal"));
                item.setTotal_view(feedObj.getString("total_view"));
                item.setIsLike(feedObj.getString("isLike"));
                item.setFeedImage(feedObj.getString("img_api_url"));
                arr_items.add(item);
            }
            if(arr_items.size()<3 && arr_items.size()>0){
                btn_loadmore.setVisibility(View.GONE);
            }else if(arr_items.size()==0){
                lv_profile.setVisibility(View.GONE);
                tv_profile_empty.setVisibility(View.VISIBLE);
            }
            feedAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            // e.printStackTrace();
            Log.e("Detail", "Loi get du lieu: "+e.getMessage());
        }
    }
    */
    public String returnRequestPage(String request){

        String temp="";
        for(int i=1;i<request.length();i++){
            temp+=request.charAt(i)+"";
        }
        temp=temp.replace("|","-");
        String[] split=temp.split("-");
        int start_old=Integer.parseInt(split[0]);
        int end_old=Integer.parseInt(split[1]);
        int sum=start_old+end_old;
        String result="/"+sum+"|5";
        return result;

    }
}
