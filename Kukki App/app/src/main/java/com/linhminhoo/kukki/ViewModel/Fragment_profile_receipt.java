package com.linhminhoo.kukki.ViewModel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.linhminhoo.kukki.AdapterListView.NewFeedAdapter;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Items.NewFeedItems;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by linhminhoo on 7/17/2015.
 */
public class Fragment_profile_receipt extends Fragment {

    ListView lv_receipt;
    TextView tv_empty;
    Button btn_loadmore;
    ArrayList<NewFeedItems> arr_items;
    NewFeedAdapter feedAdapter;
    String request_page="/0|5";
    String get_request="";
    String user_id="";
    String artist_id="";

    UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_receipt, container,false);

        lv_receipt= (ListView) view.findViewById(R.id.lv_profile_receipt);
        tv_empty= (TextView) view.findViewById(R.id.tv_profile_receipt_empty);

        arr_items=new ArrayList<NewFeedItems>();
        feedAdapter=new NewFeedAdapter(getActivity(), arr_items);
        btn_loadmore=new Button(getActivity());
        lv_receipt.addFooterView(btn_loadmore);
        btn_loadmore.setText("Tải nhiều hơn");

        lv_receipt.setAdapter(feedAdapter);

        userModel=new UserModel(getActivity());
        user_id=userModel.getIdUser()+"";

        artist_id=getArguments().getString("artist_id");
        if(artist_id.equalsIgnoreCase("noop")){
            Cache cache = AppController.getInstance(getActivity()).getRequestQueue().getCache();
            Cache.Entry entry = cache.get(PacketItem.url_newfeed_profile+request_page+"|"+user_id);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    //e.printStackTrace();
                    Log.e("Detail", "error cache: "+e.toString());
                }

            } else {
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        PacketItem.url_newfeed_profile+request_page+"|"+user_id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //   VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                    //        Log.e("Detail", "Response: " + response.toString());
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
            }

            btn_loadmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(get_request.equalsIgnoreCase("")){
                        get_request=returnRequestPage(request_page);
                    }else{
                        get_request=returnRequestPage(get_request);
                    }
                    new LoadNewFeed().execute();
                }
            });
            lv_receipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //    Toast.makeText(getActivity(), arr_items.get(position).getId()+"", Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("receipt_id", arr_items.get(position).getId()+"");
                    Intent myItent=new Intent(getActivity(), ReceiptActivity.class);
                    myItent.putExtra("data", bundle);
                    startActivity(myItent);
                }
            });
        }else{
            Cache cache = AppController.getInstance(getActivity()).getRequestQueue().getCache();
            Cache.Entry entry = cache.get(PacketItem.url_newfeed_profile+request_page+"|"+artist_id);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    //e.printStackTrace();
                    Log.e("Detail", "error cache: "+e.toString());
                }

            } else {
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        PacketItem.url_newfeed_profile+request_page+"|"+artist_id, null, new Response.Listener<JSONObject>() {

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
            }

            btn_loadmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(get_request.equalsIgnoreCase("")){
                        get_request=returnRequestPage(request_page);
                    }else{
                        get_request=returnRequestPage(get_request);
                    }
                    new LoadNewFeedOther().execute();
                }
            });
            lv_receipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //    Toast.makeText(getActivity(), arr_items.get(position).getId()+"", Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("receipt_id", arr_items.get(position).getId()+"");
                    Intent myItent=new Intent(getActivity(), ReceiptActivity.class);
                    myItent.putExtra("data", bundle);
                    startActivity(myItent);
                }
            });
        }





        return view;
    }
    private void parseJsonFeed(JSONObject response) {

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
                lv_receipt.setVisibility(View.GONE);
                tv_empty.setVisibility(View.VISIBLE);
            }
            feedAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            // e.printStackTrace();
            Log.e("Detail", "Loi get du lieu: "+e.getMessage());
        }
    }
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
              //         VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
               //         Log.e("Detail", "Response: " + response.toString());
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
    private class LoadNewFeedOther extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_newfeed_profile+get_request+"|"+artist_id, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //   VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                   //     Log.e("Detail", "Response: " + response.toString());
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
}
