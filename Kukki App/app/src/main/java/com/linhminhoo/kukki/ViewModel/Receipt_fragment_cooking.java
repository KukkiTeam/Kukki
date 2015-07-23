package com.linhminhoo.kukki.ViewModel;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.linhminhoo.kukki.Items.TempContentItem;
import com.linhminhoo.kukki.Model.AreaContinentModel;
import com.linhminhoo.kukki.Model.ImageNetwork.AppController;
import com.linhminhoo.kukki.Model.ImageNetwork.FeedImageView;
import com.linhminhoo.kukki.Model.TempContentModel;
import com.linhminhoo.kukki.R;

/**
 * Created by linhminhoo on 7/10/2015.
 */
public class Receipt_fragment_cooking extends Fragment {

    ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chebien, container,false);

        TempContentModel tempContentModel=new TempContentModel(getActivity());
        AreaContinentModel areaContinentModel=new AreaContinentModel(getActivity());
        imageLoader = AppController.getInstance(getActivity()).getImageLoader();

        TextView tv_cook_style= (TextView) v.findViewById(R.id.tv_cook_style);
        TextView tv_cook_area= (TextView) v.findViewById(R.id.tv_cook_area);
        tv_cook_area.setText(areaContinentModel.getAreaName());
        tv_cook_style.setText(areaContinentModel.getContinentName());

        LayoutInflater vi= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.content_step);
        linearLayout.removeAllViews();



        for(int i=0;i<tempContentModel.getSize();i++){
            final View view=vi.inflate(R.layout.child_step_cook, null);

            FeedImageView feed_img_content= (FeedImageView) view.findViewById(R.id.feedImage_cook_receipt);
         //   EditText edt_description= (EditText) view.findViewById(R.id.edt_cook_step_description);
            TextView tv_des= (TextView) view.findViewById(R.id.tv_cook_des);

            feed_img_content.setImageUrl(tempContentModel.returnArrContent().get(i).getImg_api_url(), imageLoader);
            feed_img_content.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {
                }

                @Override
                public void onSuccess() {
                }
            });
            tv_des.setText((i+1)+". "+tempContentModel.returnArrContent().get(i).getDescription());
            linearLayout.addView(view);
        }

        areaContinentModel.closeDB();
        tempContentModel.closeDB();
        return v;
    }
}
