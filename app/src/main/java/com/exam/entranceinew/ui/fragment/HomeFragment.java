package com.exam.entranceinew.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.CategoryData;
import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.HomeAdapter;
import com.exam.entranceinew.adapter.StudyNotesAdapter;
import com.exam.entranceinew.ui.activity.studynotes.StudyNotesScreen;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {
    CardView cv_study_notes;
    RecyclerView rvHome;
    HomeAdapter homeAdapter;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    String TAG ="dashboard";
    private ArrayList<HashMap<String, String>> arr_sections;
    private ArrayList<HashMap<String, String>> arr_api_param;
    TextView tv_name,tv_email,tv_mobile,tv_class;
    ArrayList<Integer> colors_array;
    RoundedImageView roundedImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize_view(view);
        functions();


        return view;

    }

    private void initialize_view(View view) {
        globalClass = (GlobalClass) Objects.requireNonNull(getActivity()).getApplicationContext();
        shared_preference = new Shared_Preference(getActivity());
        shared_preference.loadPrefrence();
        mView = new ViewDialog(getActivity());
        cv_study_notes = view.findViewById(R.id.cv_study_notes);
        rvHome = view.findViewById(R.id.rvHome);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        tv_class = view.findViewById(R.id.tv_class);
        roundedImageView = view.findViewById(R.id.roundedImageView);
    }

    private void functions() {
        arr_sections = new ArrayList<>();
        arr_api_param = new ArrayList<>();
        colors_array = new ArrayList<>();


        colors_array.add( R.color.darkblue);
        colors_array.add( R.color.darkpurple);
        colors_array.add( R.color.darkgreen);
        colors_array.add( R.color.darkpink);
        colors_array.add( R.color.darkorange);
        colors_array.add( R.color.darkred);
        colors_array.add( R.color.blue);
        colors_array.add( R.color.d_green);
        colors_array.add( R.color.darkblue);
        colors_array.add( R.color.darkpurple);
        colors_array.add( R.color.darkgreen);
        colors_array.add( R.color.darkpink);
        colors_array.add( R.color.darkorange);
        colors_array.add( R.color.darkred);
        colors_array.add( R.color.blue);
        colors_array.add( R.color.d_green);

        colors_array.add( R.color.darkblue);
        colors_array.add( R.color.darkpurple);
        colors_array.add( R.color.darkgreen);
        colors_array.add( R.color.darkpink);
        colors_array.add( R.color.darkorange);
        colors_array.add( R.color.darkred);
        colors_array.add( R.color.blue);
        colors_array.add( R.color.d_green);
        colors_array.add( R.color.darkblue);
        colors_array.add( R.color.darkpurple);
        colors_array.add( R.color.darkgreen);
        colors_array.add( R.color.darkpink);
        colors_array.add( R.color.darkorange);
        colors_array.add( R.color.darkred);
        colors_array.add( R.color.blue);
        colors_array.add( R.color.d_green);

        tv_name.setText(globalClass.getF_name()+" "+globalClass.getL_name());
        tv_email.setText(globalClass.getEmail());
        tv_mobile.setText(globalClass.getPhone_number());

        String currentString = globalClass.getClass_name();
        Log.d(TAG, "functions: current"+currentString);

        String[] arrayString = currentString.split(" ");

        String class_name = arrayString[0];
        String class_val = arrayString[1];
      //  String[] separated = currentString.split(" ");
         //separated[0];
       //  separated[1];

        Log.d(TAG, "functions: 1"+class_val);
        tv_class.setText(class_val);



      /*  ArrayList<CategoryData> arrayList = new ArrayList<CategoryData>();
        arrayList.add(new CategoryData("NCERT Solutions", R.mipmap.my_reader, R.color.darkred));
        arrayList.add(new CategoryData("Reference Books", R.mipmap.book, R.color.darkgreen));
        //arrayList.add(new CategoryData("Online Test", R.mipmap.my_exam, R.color.darkpurple));
        arrayList.add(new CategoryData("Study Notes", R.mipmap.post_it, R.color.darkorange));
        arrayList.add(new CategoryData("Sample Papers", R.mipmap.paper, R.color.darkpurple));*/
     //   arrayList.add(new CategoryData("Study Materials", R.mipmap.material, R.color.darkred));

        user_profile();



      /*  cv_study_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudyNotesScreen.class);
                startActivity(intent);
            }
        });*/

    }


    private void dashboard_data() {
       // mView.showDialog();
        final String tag_string_req = "dashboard_url";
        String url = ApplicationConstants.dashboard_url;
        Log.d(TAG, "dashboard_url: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("user_token", globalClass.getUser_token());
            js.put("device", "mobile");

            Log.d(TAG, "dashboard_url: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "dashboard_url Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                  //  JSONObject data = jobj.getJSONObject("data");
                                 //   String class_name =data.getString("name");
                                    JSONArray data = jobj.getJSONArray("data");


                                    for( int i = 0 ; i < data.length() ; i++ ) {

                                        JSONObject obj_data = data.getJSONObject(i);
                                        String id = obj_data.getString("id");
                                        String name = obj_data.getString("name");
                                        String short_description = obj_data.getString("short_description");
                                        String next_api_url = obj_data.getString("next_api_url");
                                        String next_section_code = obj_data.getString("next_section_code");
                                        String image = obj_data.getString("image");
                                        JSONArray next_api_param = obj_data.getJSONArray("next_api_param");
                                        for( int j = 0 ; j < next_api_param.length() ; j++ ) {
                                            JSONObject obj_param = next_api_param.getJSONObject(j);
                                            String name_val = obj_param.getString("name");
                                            String value = obj_param.getString("value");
                                            HashMap<String, String> hashMap = new HashMap<>();

                                            hashMap.put("name_val", name_val);
                                            hashMap.put("value", value);

                                            arr_api_param.add(hashMap);
                                        }

                                        Log.d(TAG, "onResponse:name>>>> " + name);


                                        HashMap<String, String> hashMap = new HashMap<>();

                                        hashMap.put("id", id);
                                        hashMap.put("name", name);
                                        hashMap.put("short_description", short_description);
                                        hashMap.put("next_api_url", next_api_url);
                                        hashMap.put("next_section_code", next_section_code);
                                        hashMap.put("image", image);
                                        arr_sections.add(hashMap);
                                    }

                                    rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                    homeAdapter = new HomeAdapter(getActivity(), arr_sections, arr_api_param,colors_array);
                                    rvHome.setAdapter(homeAdapter);


                                    homeAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "dashboard_url Error: " + error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();

                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                  /*  params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");*/


                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void user_profile() {
        // Tag used to cancel the request

        final String tag_string_req = "user_profile";

        mView.showDialog();
        String url = ApplicationConstants.user_profile;
        Log.d(TAG, "user_profile: url >> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("device", "mobile");
            js.put("user_token", globalClass.getUser_token());
            //  js.put("country_code", ccp.getSelectedCountryCode());

            Log.d(TAG, "user_profile: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "user_profile Response: " + jobj);


                            Gson gson = new Gson();

                            try {


                                String result =jobj.getString("result");
                                String message =jobj.getString("message");
                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONObject data = jobj.getJSONObject("data");
                                    String id = data.getString("id");
                                    String first_name = data.getString("first_name");
                                    String last_name = data.getString("last_name");
                                    String email = data.getString("email");
                                    String country_code = data.getString("country_code");
                                    String mobile = data.getString("mobile");
                                    String student_class_name = data.getString("student_class_name");

                                    Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                                    Log.d(TAG, "onResponse:request_token>>> " + country_code);

                                    String[] arrayString = student_class_name.split("Class ");

                                    String precedent = arrayString[0];
                                    String successor = arrayString[1];
                              //      String body = arrayString[2];


                                    //globalClass.setRequest_token(request_token);
                                    globalClass.setPhone_number(mobile);
                                    globalClass.setCountry_code(country_code);
                                    globalClass.setClass_name(student_class_name);
                                    shared_preference.savePrefrence();


                                    tv_name.setText("Hi, "+first_name+" "+last_name);
                                    tv_mobile.setText(country_code+" "+mobile);
                                    tv_email.setText(email);
                                    tv_class.setText(successor);
                                /*    edit_fname.setText(first_name);
                                    edit_lname.setText(last_name);
                                    edit_phn.setText(country_code+" "+mobile);
                                    edit_email.setText(email);
                                    qty_spinner.setText(student_class_name);*/

                                    dashboard_data();

                                    //mView.hideDialog();
                                   // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSuccess:id "+message);
                                }else {

                                    mView.hideDialog();
                                 //   Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "user_profile Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();


                    return params;
                }

            };

            // Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(jsonObjReq);
            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}