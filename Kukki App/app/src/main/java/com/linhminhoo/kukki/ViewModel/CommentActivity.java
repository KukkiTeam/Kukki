package com.linhminhoo.kukki.ViewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.linhminhoo.kukki.AdapterListView.CommentAdapter;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Items.CommentItem;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linhminhoo on 7/15/2015.
 */
public class CommentActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Init();
    }

    TextView tv_total_like;
    Button btn_back, btn_send, btn_reload;
    EditText edt_comment;
    ListView lv_comment;
    ArrayList<CommentItem>arr_comment;

    private ProgressDialog progress;
    String error=null;
    String message=null;
    int time=0;
    UserModel userModel;
    String post_id="";
    String total_like="";
    ArrayAdapter adapter;
    String comment_description="";

    JSONArray array_comment=null;
    private void Init() {

        tv_total_like= (TextView) findViewById(R.id.tv_comment_total_like);
        btn_back= (Button) findViewById(R.id.btn_comment_back);
        btn_reload= (Button) findViewById(R.id.btn_comment_reload);
        btn_send= (Button) findViewById(R.id.btn_comment_send);
        lv_comment= (ListView) findViewById(R.id.lv_comment);
        edt_comment= (EditText) findViewById(R.id.edt_comment);

        Intent myItent=getIntent();
        Bundle bundle=myItent.getBundleExtra("data");
        post_id=bundle.getString("post_id");
        total_like=bundle.getString("total_like");
        tv_total_like.setText("Có tất cả "+total_like+" người thích món ăn này");
        Log.e("Detail","post_id: "+post_id);

        arr_comment=new ArrayList<CommentItem>();
        adapter=new CommentAdapter(getApplicationContext(), R.layout.item_comment, arr_comment);
        lv_comment.setAdapter(adapter);

        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr_comment.clear();
                new RetrieveComment().execute();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              comment_description=edt_comment.getText().toString();
                new SendComment().execute();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new RetrieveComment().execute();



    }

    private class RetrieveComment extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userModel=new UserModel(CommentActivity.this);
            progress = new ProgressDialog(CommentActivity.this);
            progress.setMessage("Xin chờ....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_retrieve_comment+post_id+"?user_id="+userModel.getIdUser(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                                 Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                         //       receipt_jsonObject=response.getJSONObject("message");

                                array_comment=response.getJSONArray("message");
                            } catch (JSONException e) {
                                // e.printStackTrace();
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
        //    mRequestQueue.add(jsonObjReq);
                       AppController.getInstance(CommentActivity.this).addToRequestQueue(jsonObjReq);

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

                    //    JSONObject object_child=array_comment.getJSONObject(0);
//                        Log.e("Detail", "child: "+object_child.toString());
//                        Log.e("Detail", "child 22: "+object_child.getString("description"));
                    for(int i=0;i<array_comment.length();i++){
                        JSONObject object_child=array_comment.getJSONObject(i);
                        int id=Integer.parseInt(object_child.getString("user_id"));
                        String user_name=object_child.getString("user_name");
                        String description=object_child.getString("description");
                        String time=object_child.getString("created_at");
                        String img_api_url=object_child.getString("url_api_avatar");
                        arr_comment.add(new CommentItem(id, user_name, description, time, img_api_url));
                    }

                    }catch (Exception ex){
                            message=ex.toString();
                        Log.e("Detail", "Error in get User JsonArray: "+ex.toString());
                    }
                    //   message="Tạo tài khoản thành công, môt link kích hoạt được gửi tới email bạn đã đăng ký. \n Hãy kích hoạt tài khoản để bắt đầu tham gia Kukki ";
                }else{
                    error=null;
                   // message="Xảy ra lỗi, thử lại sau !";
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
            adapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
            progress.dismiss();

            if(message!=null){
                showDialogClose(message);
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
    private class SendComment extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userModel=new UserModel(CommentActivity.this);
            progress = new ProgressDialog(CommentActivity.this);
            progress.setMessage("Xin chờ....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("user_id", userModel.getIdUser()+"");
            postParam.put("post_id", post_id);
            postParam.put("description", comment_description);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    PacketItem.url_send_comment, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                                //       receipt_jsonObject=response.getJSONObject("message");
                                message=response.getString("message");
                            //    array_comment=response.getJSONArray("message");
                            } catch (JSONException e) {
                                // e.printStackTrace();
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
            //    mRequestQueue.add(jsonObjReq);
            AppController.getInstance(CommentActivity.this).addToRequestQueue(jsonObjReq);

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

                    //   message="Tạo tài khoản thành công, môt link kích hoạt được gửi tới email bạn đã đăng ký. \n Hãy kích hoạt tài khoản để bắt đầu tham gia Kukki ";
                }else{
                    error=null;
                    // message="Xảy ra lỗi, thử lại sau !";
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
            edt_comment.setText("");
         //   adapter.notifyDataSetChanged();
            progress.dismiss();

            if(message!=null){
                showDialogClose(message);
            }else{
                arr_comment.clear();
                new RetrieveComment().execute();
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
    private void showDialogClose(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(CommentActivity.this).create();
        alertDialog.setTitle("Thông báo !");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }
    private void scrollMyListViewToBottom() {
        lv_comment.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv_comment.setSelection(adapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
