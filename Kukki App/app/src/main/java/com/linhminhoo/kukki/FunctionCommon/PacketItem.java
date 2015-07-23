package com.linhminhoo.kukki.FunctionCommon;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by linhminhoo on 6/21/2015.
 */
public class PacketItem {

    public static final String local="10.20.5.7";
    public static final String url_register="http://"+local+"/Laravel/Kukki-Server/api/account";
    public static final String url_login="http://"+local+"/Laravel/Kukki-Server/api/account/login";
    public static final String url_newFeed_all="http://"+local+"/Laravel/Kukki-Server/api/account/newfeed-all";
    public static final String url_newFeed_care="http://"+local+"/Laravel/Kukki-Server/api/account/newfeed-care";
    public static final String url_update_avatar="http://"+local+"/Laravel/Kukki-Server/api/account/update-avatar/";
    public static final String url_get_speciality="http://"+local+"/Laravel/Kukki-Server/api/account/speciality/";
    public static final String url_create_receipt="http://"+local+"/Laravel/Kukki-Server/api/account/create-receipt";
    public static final String url_getReceipt_detail="http://"+local+"/Laravel/Kukki-Server/api/account/receipt/";
    public static final String url_like_post="http://"+local+"/Laravel/Kukki-Server/api/account/like-post";
    public static final String url_unlike_post="http://"+local+"/Laravel/Kukki-Server/api/account/unlike-post";
    public static final String url_retrieve_comment="http://"+local+"/Laravel/Kukki-Server/api/account/retrieve-comment/";
    public static final String url_send_comment="http://"+local+"/Laravel/Kukki-Server/api/account/comment";
    public static final String url_retrieve_profile="http://"+local+"/Laravel/Kukki-Server/api/account/retrieve-profile/";
    public static final String url_retrieve_profile_other="http://"+local+"/Laravel/Kukki-Server/api/account/retrieve-profile-other/";
    public static final String url_newfeed_profile="http://"+local+"/Laravel/Kukki-Server/api/account/newfeed-profile";
    public static final String url_follow_one="http://"+local+"/Laravel/Kukki-Server/api/account/follow";
    public static final String url_unfollow_one="http://"+local+"/Laravel/Kukki-Server/api/account/unfollow";
    public static final String url_retrieve_material="http://"+local+"/Laravel/Kukki-Server/api/account/note/";
    public static final String code_register="28418s2Ff?4SaM4G0epYK01va:BPw";

    public PacketItem() {
    }
    public int ReturnScreenWidth(Context context){

        int width=0;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width=display.getWidth();

        return Math.round(width*5/6);

    }

}
