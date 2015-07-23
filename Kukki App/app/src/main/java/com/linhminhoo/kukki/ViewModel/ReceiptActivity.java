package com.linhminhoo.kukki.ViewModel;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Model.AreaContinentModel;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.ImageNetwork.FeedImageView;
import com.linhminhoo.kukki.Model.TempContentModel;
import com.linhminhoo.kukki.Model.TempMaterialModel;
import com.linhminhoo.kukki.Model.UserModel;
import com.linhminhoo.kukki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linhminhoo on 7/10/2015.
 */
public class ReceiptActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);
        Init();
    }

    Button btn_nguyenlieu, btn_chebien;
    ImageView img_back, img_schedule, img_download, img_edit, img_comment;
    TextView tv_title, tv_artist, tv_time, tv_total_like, tv_total_comment, tv_kcal, tv_description, tv_total_view;
    FeedImageView feedImgReceiptCover;

    private ProgressDialog progress;
    RequestQueue mRequestQueue=null;
    ImageLoader imageLoader = AppController.getInstance(ReceiptActivity.this).getImageLoader();

    UserModel userModel;
    TempMaterialModel tempMaterialModel;
    TempContentModel tempContentModel;
    AreaContinentModel areaContinentModel;

    String receipt_id="";
    String error=null;
    String message=null;
    int time=0;
    JSONObject receipt_jsonObject=null;
    JSONArray receipt_material_jsonArray=null;
    JSONArray receipt_content_jsonArray=null;

    String title="";
    String artist="";
    String description="";
    String total_time="";
    String total_kcal="";
    String total_view="";
    String total_like="";
    String total_comment="";
    String img_cover_url="";
    String receipt_artist_id="";
    String area="";
    String user_id="";
    String continent="";




    private void Init() {

        btn_nguyenlieu= (Button) findViewById(R.id.btn_receipt_nguyenlieu);
        btn_chebien= (Button) findViewById(R.id.btn_receipt_chebien);
        img_back= (ImageView) findViewById(R.id.img_receipt_back);
        img_download= (ImageView) findViewById(R.id.img_receipt_download);
        img_edit= (ImageView) findViewById(R.id.img_receipt_edit);
        img_schedule= (ImageView) findViewById(R.id.img_receipt_schedule);
        tv_artist= (TextView) findViewById(R.id.tv_receipt_artist);
        tv_description= (TextView) findViewById(R.id.tv_receipt_des);
        tv_kcal= (TextView) findViewById(R.id.tv_receipt_kcal);
        tv_time= (TextView) findViewById(R.id.tv_receipt_time);
        tv_title= (TextView) findViewById(R.id.tv_receipt_title);
        tv_total_like= (TextView) findViewById(R.id.tv_receipt_total_like);
        tv_total_comment= (TextView) findViewById(R.id.tv_receipt_total_comment);
        tv_total_view= (TextView) findViewById(R.id.tv_receipt_total_view);
        img_comment= (ImageView) findViewById(R.id.img_receipt_comment);
        feedImgReceiptCover= (FeedImageView) findViewById(R.id.feedImage_receipt);

        imageLoader = AppController.getInstance(ReceiptActivity.this).getImageLoader();

        Intent myItent=getIntent();
        final Bundle bundle=myItent.getBundleExtra("data");
        receipt_id=bundle.getString("receipt_id");
        mRequestQueue= Volley.newRequestQueue(ReceiptActivity.this);

        userModel=new UserModel(ReceiptActivity.this);
        user_id=userModel.getIdUser()+"";
        userModel.closeDB();
        // Nguyen lieu first


        // Init Description
        new GetReceiptDetail().execute();

        btn_chebien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_nguyenlieu.setBackgroundResource(R.drawable.receipt_btn_nguyenlieu_normal);
                btn_chebien.setBackgroundResource(R.drawable.receipt_btn_chebien_press);
                FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                Receipt_fragment_cooking fa=new Receipt_fragment_cooking();
                FT.replace(R.id.frag_content_receipt, fa);
               // FT.add(R.id.frag_content_receipt, fa);
                FT.commit();
            }
        });
        btn_nguyenlieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_chebien.setBackgroundResource(R.drawable.receipt_btn_chebien_normal);
                btn_nguyenlieu.setBackgroundResource(R.drawable.receipt_btn_nguyenlieu_press);
                FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                Receipt_fragment_material fa=new Receipt_fragment_material();
                FT.replace(R.id.frag_content_receipt, fa);
                //FT.add(R.id.frag_content_receipt, fa);
                FT.commit();
            }
        });
        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myItent1=new Intent(ReceiptActivity.this, CommentActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("post_id", receipt_id);
                bundle1.putString("total_like", total_like);
                myItent1.putExtra("data", bundle1);
                startActivity(myItent1, bundle1);
            }
        });

            tv_artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(user_id.equalsIgnoreCase(receipt_artist_id)==false){
                     //   Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_SHORT).show();
                        Intent myItent=new Intent(ReceiptActivity.this, ProfileActivity.class);
                        Bundle bundel_1=new Bundle();
                        bundel_1.putString("artist_id", receipt_artist_id);
                        myItent.putExtra("data", bundel_1);
                        startActivity(myItent, bundel_1);
                    }

                }
            });


    }

    private class GetReceiptDetail extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userModel=new UserModel(ReceiptActivity.this);
            tempMaterialModel=new TempMaterialModel(ReceiptActivity.this);
            tempContentModel=new TempContentModel(ReceiptActivity.this);
            areaContinentModel=new AreaContinentModel(ReceiptActivity.this);
            progress = new ProgressDialog(ReceiptActivity.this);
            progress.setMessage("Xin chờ....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            /*
            Log.e("Detail", "start");
            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("user_id", usermodel.getIdUser()+"");
            */
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    PacketItem.url_getReceipt_detail+receipt_id+"?user_id="+user_id, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                       //     Log.e("Detail", response.toString());
                            try {
                                error=response.getString("error");
                                receipt_jsonObject=response.getJSONObject("message");
                            } catch (JSONException e) {
                                // e.printStackTrace();
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
            mRequestQueue.add(jsonObjReq);
 //           AppController.getInstance(ReceiptActivity.this).addToRequestQueue(jsonObjReq);

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
                        title=receipt_jsonObject.getString("title");
                        artist=receipt_jsonObject.getString("artist");
                        description=receipt_jsonObject.getString("description");
                        total_time=receipt_jsonObject.getString("total_time");
                        total_kcal=receipt_jsonObject.getString("total_kcal");
                        total_view=receipt_jsonObject.getString("total_view");
                        total_like=receipt_jsonObject.getString("total_like");
                        total_comment=receipt_jsonObject.getString("total_comment");
                        img_cover_url=receipt_jsonObject.getString("img_cover_url");
                        receipt_artist_id=receipt_jsonObject.getString("receipt_artist_id");
                        area=receipt_jsonObject.getString("area");
                        continent=receipt_jsonObject.getString("continent");
                        receipt_material_jsonArray=receipt_jsonObject.getJSONArray("material");
                        receipt_content_jsonArray=receipt_jsonObject.getJSONArray("content");
                        if(areaContinentModel.isEmpty()){
                            areaContinentModel.insertAreaContinent(area, continent);
                        }else{
                            areaContinentModel.isCLearAreaContinent();
                            areaContinentModel.insertAreaContinent(area, continent);
                        }
                            if(tempContentModel.isEmpty()){
                                for(int i=0;i<receipt_content_jsonArray.length();i++){
                                    JSONObject receipt_content_jsonObj=receipt_content_jsonArray.getJSONObject(i);
                                    String img_api_url_insert=receipt_content_jsonObj.getString("img_api_url");
                                    String description_insert=receipt_content_jsonObj.getString("description");
                                    tempContentModel.insertTempContent(img_api_url_insert, description_insert);
                                }
                            }else{
                                tempContentModel.isCLearTempContent();
                                for(int i=0;i<receipt_content_jsonArray.length();i++){
                                    JSONObject receipt_content_jsonObj=receipt_content_jsonArray.getJSONObject(i);
                                    String img_api_url_insert=receipt_content_jsonObj.getString("img_api_url");
                                    String description_insert=receipt_content_jsonObj.getString("description");
                                    tempContentModel.insertTempContent(img_api_url_insert, description_insert);
                                }
                            }


                    //    JSONObject receipt_content_jsonObj=receipt_content_jsonArray.getJSONObject(0);
                   //     Log.e("Detail","arr receipt: "+tempContentModel.getSize());
                        if(tempMaterialModel.isEmpty()){
                          //  Log.e("Detail","Clear");
                            for(int i=0;i<receipt_material_jsonArray.length();i++){
                                tempMaterialModel.insertTempMaterial(receipt_material_jsonArray.get(i).toString());
                            }
                        //    Log.e("Detail","arr size: "+tempMaterialModel.getSize());
                        }else{
                      //      Log.e("Detail","Not Clear");
                            tempMaterialModel.isCLearTempMaterial();
                      //      Log.e("Detail","new size: "+tempMaterialModel.getSize()+"");
                            for(int i=0;i<receipt_material_jsonArray.length();i++){
                                tempMaterialModel.insertTempMaterial(receipt_material_jsonArray.get(i).toString());
                            }
                        //    Log.e("Detail","arr size: "+tempMaterialModel.getSize());
                        }


                 //       Log.e("Detail","temp size: "+tempMaterialModel.getSize()+"");
                  //      Log.e("Detail", "material all: " + receipt_material_jsonArray.toString());
                  //      Log.e("Detail", "material 0: "+receipt_material_jsonArray.get(0).toString());

                    }catch (Exception ex){
                    //    message="no success";
                        Log.e("Detail", "Error in get User JsonArray: "+ex.toString());
                    }
                    //   message="Tạo tài khoản thành công, môt link kích hoạt được gửi tới email bạn đã đăng ký. \n Hãy kích hoạt tài khoản để bắt đầu tham gia Kukki ";
                }else{
                    error=null;
                    message="Xảy ra lỗi, thử lại sau !";
                }
            }else{
                message="Không có phản hồi từ máy chủ, vui lòng thử lại sau !";
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            feedImgReceiptCover.setImageUrl(img_cover_url, imageLoader);
            feedImgReceiptCover.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {
                }

                @Override
                public void onSuccess() {
                }
            });
            tv_title.setText(title);
            if(user_id.equalsIgnoreCase(receipt_artist_id)==false){
                SpannableString content = new SpannableString(artist);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                tv_artist.setText(content);
            }else{
                tv_artist.setText(artist);
            }

            tv_time.setText(total_time+" phút");
            tv_total_like.setText(total_like);
            tv_total_comment.setText(total_comment);
            tv_description.setText(description);
            tv_kcal.setText(total_kcal+" kcal");
            tv_total_view.setText(total_view);
            setMaterialFirst();
            userModel.closeDB();
            tempMaterialModel.closeDB();
            tempContentModel.closeDB();
            areaContinentModel.closeDB();
            progress.dismiss();
            if(message!=null){
                showDialogClose(message);
            }
           // showDialog(message);
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

    public void setMaterialFirst(){
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        Receipt_fragment_material fa=new Receipt_fragment_material();
        Bundle bundle_receipt=new Bundle();
        bundle_receipt.putString("receipt_id", receipt_id);
        fa.setArguments(bundle_receipt);
        FT.add(R.id.frag_content_receipt, fa);
        FT.commit();
    }
    private void showDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ReceiptActivity.this).create();
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
    private void showDialogClose(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ReceiptActivity.this).create();
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
    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    //Somewhere that has access to a context

}
