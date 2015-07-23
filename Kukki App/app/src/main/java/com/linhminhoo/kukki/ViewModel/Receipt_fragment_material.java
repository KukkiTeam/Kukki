package com.linhminhoo.kukki.ViewModel;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.linhminhoo.kukki.FunctionCommon.PacketItem;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.NoteMaterialModel;
import com.linhminhoo.kukki.Model.NoteModel;
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
public class Receipt_fragment_material extends Fragment {


    private ProgressDialog progress;
    String error=null;
    String message=null;
    String receipt_id="";
    String user_id="";
    String api_key="";
    int time=0;

    JSONObject object_message;
    JSONArray array_material;

    UserModel userModel;
    NoteModel noteModel;
    NoteMaterialModel noteMaterialModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nguyenlieu, container,false);

        receipt_id=getArguments().getString("receipt_id");
        userModel=new UserModel(getActivity());
        user_id=userModel.getIdUser()+"";
        api_key=userModel.getApiKeyUser();
        userModel.closeDB();

        LayoutInflater vi= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.content_material);
        linearLayout.removeAllViews();
        TempMaterialModel tempMaterialModel=new TempMaterialModel(getActivity());
        for(int i=0;i<tempMaterialModel.getSize();i++){
            final View view=vi.inflate(R.layout.item_nguyenlieu, null);
       //     view.setLayoutParams(new ViewGroup.LayoutParams(0, 20));
         //   view.getLayoutParams().height=46;
           // LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
           // params.bottomMargin=15;
          //  view.setLayoutParams(params);
            TextView tv_name= (TextView) view.findViewById(R.id.item_lv_nguyenlieu_name);
            tv_name.setText(tempMaterialModel.returnArrMaterial().get(i).getName());

            linearLayout.addView(view);
        }
        final View view2=vi.inflate(R.layout.item_nguyenlieu_btn_add, null);
        Button btn_add= (Button) view2.findViewById(R.id.btn_receipt_add_all);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Thông báo !")
                        .setMessage("Bạn có muốn thêm tất cả nguyên liệu trên vào ghi chú ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new RetrieveMaterial().execute();
                            }})
                        .setNegativeButton("No", null).show();

            }
        });
        linearLayout.addView(view2);
        tempMaterialModel.closeDB();

        return v;
    }
    private class RetrieveMaterial extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            noteModel=new NoteModel(getActivity());
            noteMaterialModel=new NoteMaterialModel(getActivity());
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
                    PacketItem.url_retrieve_material+receipt_id+"?user_id="+user_id, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                error=response.getString("error");
                                message=response.getString("message");
                                object_message=response.getJSONObject("message");
                            } catch (JSONException e) {
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
                    headers.put( "Authorization", api_key);
                    return headers;
                }

            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //mRequestQueue.add(jsonObjReq);
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

                        String title=object_message.getString("title");
                        noteModel.insertNote(title, Integer.parseInt(user_id));
                        int note_id=noteModel.getIdNoteMax();
                        array_material=object_message.getJSONArray("material");
                        for(int i=0;i<array_material.length();i++){
                            String name=array_material.getString(i);
                            noteMaterialModel.insertNote(name, 0, note_id);
                        }
                    }catch (Exception ex){
                        Log.e("Detail", "Error in get User JsonArray: "+ex.toString());
                    }
                }else{
                    error=null;
                //    message="Xảy ra lỗi, thử lại sau !";
                }
            }else{
                message="Không có phản hồi từ máy chủ, vui lòng thử lại sau !";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            noteModel.closeDB();
            noteMaterialModel.closeDB();
            progress.dismiss();
            if(message!=null){
                showDialog(message);
            }else{
                showDialog("Đã thêm nguyên liệu vào ghi chú !");
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
}
