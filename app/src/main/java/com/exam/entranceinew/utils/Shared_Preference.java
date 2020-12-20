package com.exam.entranceinew.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Developer on 7/19/17.
 */

public class Shared_Preference {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;

    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;

    GlobalClass global_class;
    private boolean pref_logInStatus;
    private String pref_name;
    private String pref_fname;
    private String pref_lname;
    private String pref_id;
    private String pref_email;
    private String pref_phone_number;
    private String pref_user_type;
    private String pref_user_profile;
    private String pref_user_token;
    private String jsonData;
    private String slider_pics;
    private String comments;
    private String chat_fetch;
    private String media_fetch;
    private String event_fetch;
    private String fcm;
    private String p_agenda;
    private String key_status;
    private String survey_link;
    private String survey_event_id;
    private String customer_token;
    private  boolean install_first_time ;
    private String pref_cart_no;

    private String b_pin;
    private String b_city;
    private String b_state;
    private String b_address;
    String request_key, request_token;

    private static final String PREFS_NAME = "preferences";
    private static final String PREFS_NAME2 = "preferences2";
    private static final String PREFS_NAME3 = "preferences2";
    private static final String PREF_logInStatus = "logInStatus";
    private static final String PREF_name = "name";
    private static final String PREF_fname = "f_name";
    private static final String PREF_lname = "l_name";
    private static final String PREF_email = "email";
    private static final String PREF_phone_number = "phone_number";
    private static final String PREF_user_type = "user_type";
    private static final String PREF_id = "id";
    private static final String PREF_profile_pic = "profile_pic";
    private static final String PREF_user_token = "user_token";
    private static final String JSON_data = "json_data";
    private static final String Slider_pics = "slider_pics";
    private static final String Comments = "comments";
    private static final String Chat_fetch = "chat_fetch";
    private static final String Media_fetch = "media_fetch";
    private static final String Event_fetch = "event_fetch";
    private static final String is_first = "is_first";
    private static final String f_cm = "fcm";
    private static final String P_agenda = "p_agenda";
    private static final String Key_status = "k_status";
    private static final String K_survey_link = "k_survey_link";
    private static final String K_survey_eid = "k_survey_eid";
    private static final String PREFcustomer_token = "customer_token";
    private static final String PREF_cart_no = "cart_no";
    private static final String PREF_b_pin = "b_pin";
    private static final String PREF_b_city = "b_city";
    private static final String PREF_b_state = "b_state";
    private static final String PREF_b_address = "b_address";
    private static final String PREF_request_key = "req_key";
    private static final String PREF_request_token = "req_tok" ;




    public Shared_Preference(Context context) {
        this.context = context;

        this.global_class = (GlobalClass) context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

        this.sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        this.editor2 = sharedPreferences2.edit();

        this.sharedPreferences3 = context.getSharedPreferences(PREFS_NAME3, Context.MODE_PRIVATE);
        this.editor3 = sharedPreferences3.edit();



    }

    public void savePrefrence() {
        if (global_class.getLogin_status()) {

            pref_logInStatus = global_class.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);



            pref_fname = global_class.getF_name();
            editor.putString(PREF_fname, pref_fname);

            pref_lname = global_class.getL_name();
            editor.putString(PREF_lname, pref_lname);

            pref_id= global_class.getId();
            editor.putString(PREF_id,pref_id);

            pref_user_profile= global_class.getProfle_image();
            editor.putString(PREF_profile_pic,pref_user_profile);

            pref_email= global_class.getEmail();
            editor.putString(PREF_email,pref_email);

            pref_phone_number= global_class.getPhone_number();
            editor.putString(PREF_phone_number,pref_phone_number);

            pref_user_type= global_class.getUser_type();
            editor.putString(PREF_user_type,pref_user_type);

            request_key= global_class.getRequest_key();
            editor.putString(PREF_request_key,request_key);

            request_token= global_class.getRequest_token();
            editor.putString(PREF_request_token,request_token);

            pref_user_token= global_class.getUser_token();
            editor.putString(PREF_user_token,pref_user_token);

         /*   customer_token= global_class.getCustomer_token();
            editor.putString(PREFcustomer_token,customer_token);

            b_pin= global_class.getB_pin();
            editor.putString(PREF_b_pin,b_pin);

            b_city= global_class.getB_city();
            editor.putString(PREF_b_city,b_city);

            b_state= global_class.getB_state();
            editor.putString(PREF_b_state,b_state);

            b_address= global_class.getB_address();
            editor.putString(PREF_b_address,b_address);*/

        /*    jsonData = global_class.getJsondata();
            editor.putString(JSON_data, jsonData);

            slider_pics = global_class.getSliderpics();
            editor.putString(Slider_pics, slider_pics);

            comments = global_class.getComments_1();
            editor.putString(Comments, comments);

            chat_fetch = global_class.getChatfetch();
            editor.putString(Chat_fetch, chat_fetch);

            media_fetch = global_class.getMediafetch();
            editor.putString(Media_fetch, media_fetch);

            event_fetch = global_class.getEventfetch();
            editor.putString(Event_fetch, event_fetch);

            fcm = global_class.getFCM();
            editor.putString(f_cm, fcm);

            p_agenda = global_class.getImg_agenda();
            editor.putString(P_agenda, p_agenda);

            survey_link = global_class.getSurvey_link();
            editor.putString(K_survey_link, survey_link);

            survey_event_id = global_class.getSurvey_event_id();
            editor.putString(K_survey_eid, survey_event_id);*/

/*
            pref_cart_no = global_class.getCart_item();
            editor.putString(PREF_cart_no, pref_cart_no);*/


            editor.commit();

        }else{
            // dont save anything, if user is logged out
            pref_logInStatus = global_class.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);
            editor.commit();
        }

    }

    public void loadPrefrence() {
        pref_logInStatus = sharedPreferences.getBoolean(PREF_logInStatus, false);
        global_class.setLogin_status(pref_logInStatus);

        Log.d("TV", global_class.getLogin_status() + "");
        if (global_class.getLogin_status()) {



            pref_fname = sharedPreferences.getString(PREF_fname, "");
            global_class.setF_name(pref_fname);

            pref_lname = sharedPreferences.getString(PREF_lname, "");
            global_class.setL_name(pref_lname);

            pref_id= sharedPreferences.getString(PREF_id,"");
            global_class.setId(pref_id);

            request_key= sharedPreferences.getString(PREF_request_key,"");
            global_class.setRequest_key(request_key);

            request_token= sharedPreferences.getString(PREF_request_token,"");
            global_class.setRequest_token(request_token);

            pref_user_token= sharedPreferences.getString(PREF_user_token,"");
            global_class.setUser_token(pref_user_token);

          /*  b_pin= sharedPreferences.getString(PREF_b_pin,"");
            global_class.setB_pin(b_pin);

            b_city= sharedPreferences.getString(PREF_b_city,"");
            global_class.setB_city(b_city);

            b_state= sharedPreferences.getString(PREF_b_state,"");
            global_class.setB_state(b_state);

            b_address= sharedPreferences.getString(PREF_b_address,"");
            global_class.setB_address(b_address);*/

            pref_user_profile= sharedPreferences.getString(PREF_profile_pic,"");
            global_class.setProfle_image(pref_user_profile);

            pref_phone_number=sharedPreferences.getString(PREF_phone_number,"");
            global_class.setPhone_number(pref_phone_number);

            pref_email=sharedPreferences.getString(PREF_email,"");
            global_class.setEmail(pref_email);

            pref_user_type=sharedPreferences.getString(PREF_user_type,"");
            global_class.setUser_type(pref_user_type);

          /*  customer_token=sharedPreferences.getString(PREFcustomer_token,"");
            global_class.setCustomer_token(customer_token);*/

    /*        jsonData=sharedPreferences.getString(JSON_data,"");
            global_class.setJsondata(jsonData);

            slider_pics=sharedPreferences.getString(Slider_pics,"");
            global_class.setSliderpics(slider_pics);

            comments=sharedPreferences.getString(Comments,"");
            global_class.setComments_1(comments);

            chat_fetch=sharedPreferences.getString(Chat_fetch,"");
            global_class.setChatfetch(chat_fetch);

            media_fetch=sharedPreferences.getString(Media_fetch,"");
            global_class.setMediafetch(media_fetch);

            event_fetch=sharedPreferences.getString(Event_fetch,"");
            global_class.setEventfetch(event_fetch);

            fcm=sharedPreferences.getString(f_cm,"");
            global_class.setFCM(fcm);

            p_agenda=sharedPreferences.getString(P_agenda,"");
            global_class.setImg_agenda(p_agenda);


            survey_link= sharedPreferences.getString(K_survey_link,"");
            global_class.setSurvey_link(survey_link);

            survey_event_id = sharedPreferences.getString(K_survey_eid , "");
            global_class.setSurvey_event_id(survey_event_id);*/

     /*       pref_cart_no=sharedPreferences.getString(PREF_cart_no,"");
            global_class.setCart_item(pref_cart_no);
*/

        //    Log.d("price", "loadPref: "+pref_name);
//            Log.d("rom", "slider_pics: "+slider_pics.toString());

        }
    }


    public void setFirstlogin(boolean b){
        editor2.putBoolean(is_first,b);
        editor2.commit();
    }
    public boolean IS_First_Time(){
        return sharedPreferences2.getBoolean(is_first, false);

    }

    public  void clear_notification(){
        editor3.clear();
        editor3.commit();

    }

 /*   public  void  save_noti(){
        key_status = global_class.getKeyStatus();
        editor3.putString(Key_status, key_status);

        editor3.commit();

    }

    public  void load_noti(){

        key_status=sharedPreferences3.getString(Key_status,"");
        global_class.setKeyStatus(key_status);
    }*/



    public void clearPrefrence(){

        editor.clear();
        editor.commit();


    }













}