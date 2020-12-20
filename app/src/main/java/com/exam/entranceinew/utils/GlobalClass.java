package com.exam.entranceinew.utils;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;


public class GlobalClass extends Application {

    public Boolean login_status = false;
    public Boolean install_first_time = false;
    String id,  f_name, l_name, email, phone_number, user_type, device_type, fcm_reg_token, deviceid, profle_image;
    String eid, jsondata, sliderpics, comments_1, chatfetch, mediafetch, eventfetch;
    public String notification;
    public String FCM;
    ArrayList<HashMap<String, String>> personal ;
    ArrayList<String> grid_images_1pos;
    Boolean check_agenda =false;
    String img_agenda;
    String keyStatus;
    String survey_link;
    String survey_event_id;
    String fcm_token_val;
    String request_key, request_token;
    String  user_token;
    private static RequestQueue mRequestQueue;
 //   private static RequestQueue mRequestQueue;
 public  <T> void addToRequestQueue(Context mContext, Request<T> request, String tag) {
     // set the default tag if tag is empty
     request.setTag(TextUtils.isEmpty(tag));
     getRequestQueue(mContext).add(request);
 }

    private RequestQueue getRequestQueue(Context mContext) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Overlock-Regular.ttf");
    }

    public String title = "";

    public String getFcm_token_val() {
        return fcm_token_val;
    }

    public void setFcm_token_val(String fcm_token_val) {
        this.fcm_token_val = fcm_token_val;
    }

    public String getSurvey_link() {
        return survey_link;
    }

    public void setSurvey_link(String survey_link) {
        this.survey_link = survey_link;
    }

    public String getSurvey_event_id() {
        return survey_event_id;
    }

    public void setSurvey_event_id(String survey_event_id) {
        this.survey_event_id = survey_event_id;
    }

    public Boolean getLogin_status() {
        return login_status;
    }

    public void setLogin_status(Boolean login_status) {
        this.login_status = login_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getFcm_reg_token() {
        return fcm_reg_token;
    }

    public void setFcm_reg_token(String fcm_reg_token) {
        this.fcm_reg_token = fcm_reg_token;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public ArrayList<HashMap<String, String>> getPersonal() {
        return personal;
    }

    public void setPersonal(ArrayList<HashMap<String, String>> personal) {
        this.personal = personal;
    }

    public String getSliderpics() {
        return sliderpics;
    }

    public void setSliderpics(String sliderpics) {
        this.sliderpics = sliderpics;
    }


    public String getComments_1() {
        return comments_1;
    }

    public void setComments_1(String comments_1) {
        this.comments_1 = comments_1;
    }

    public String getChatfetch() {
        return chatfetch;
    }

    public void setChatfetch(String chatfetch) {
        this.chatfetch = chatfetch;
    }

    public String getMediafetch() {
        return mediafetch;
    }

    public void setMediafetch(String mediafetch) {
        this.mediafetch = mediafetch;
    }

    public String getEventfetch() {
        return eventfetch;
    }

    public void setEventfetch(String eventfetch) {
        this.eventfetch = eventfetch;
    }

    ///public ArrayList<Integer> grid_images = new ArrayList<>();
    public ArrayList<String> grid_images = new ArrayList<>();

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }

    public String getNotification() {
        return notification;
    }

    public String getFCM() {
        return FCM;
    }

    public void setFCM(String FCM) {
        this.FCM = FCM;
    }

    public ArrayList<String> getGrid_images_1pos() {
        return grid_images_1pos;
    }

    public Boolean getCheck_agenda() {
        return check_agenda;
    }

    public void setCheck_agenda(Boolean check_agenda) {
        this.check_agenda = check_agenda;
    }

    public void setGrid_images_1pos(ArrayList<String> grid_images_1pos) {
        this.grid_images_1pos = grid_images_1pos;
    }

    public String getImg_agenda() {
        return img_agenda;
    }

    public void setImg_agenda(String img_agenda) {
        this.img_agenda = img_agenda;
    }

    public String getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(String keyStatus) {
        this.keyStatus = keyStatus;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getProfle_image() {
        return profle_image;
    }

    public void setProfle_image(String profle_image) {
        this.profle_image = profle_image;
    }

    public String getRequest_key() {
        return request_key;
    }

    public void setRequest_key(String request_key) {
        this.request_key = request_key;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
/*  public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }*/

/*

    public  void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
*/



}