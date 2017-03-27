package com.example.a2017.mentoring.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.example.a2017.mentoring.Model.Request;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    Button   reqBtn,datebtn;
    TextView meetingType,toolbar_title;
    TextView inValid_meetingType,inValid_dateAndTime,inValid_topic,public_hint,private_hint;
    Toolbar  toolbar;
    CheckBox faceToface , call;
    EditText topic,publicFeedback,privateFeedback;

    int day,month,year , hour , minute;
    int day_final,month_final,year_final,hour_final,minute_final;
    int validDate = 1;
    int validMeetingType = 1;
    int validTopic = 1;
    boolean isMentee ;
    int myId;
    int menteeId;
    int flag;
    Request request = null;

    String meeting_date_time,meeting_date,meeting_type,meeting_topic,meeting_public_feedback,meeting_private_feedback;
    MorphingButton morphingButton;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isMentee= Preferences.isMentee(getContext());
        flag = getArguments().getInt("flag");
        if(flag==1)
        {
           request = (Request) getArguments().getSerializable("request");
        }
        myId = Preferences.myId(getContext());
        if(!isMentee)
        {
            menteeId = getArguments().getInt("menteeId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meeting_request, container, false);
        morphingButton = (MorphingButton) view.findViewById(R.id.morph_sendBtn_id);
        setUpFields(view); // binding
        dateAndtimebuttonHandler(); // do event when clicked
        sendRequestHandler();
        updateUi();
        return view;
    }

    private void setUpFields(View view) {
        datebtn = (Button) view.findViewById(R.id.dateBtn_id);
        meetingType = (TextView) view.findViewById(R.id.meetingType_id);
        faceToface = (CheckBox) view.findViewById(R.id.facetoface_checkbox_id);
        call = (CheckBox) view.findViewById(R.id.call_checkbox_id);
        topic = (EditText) view.findViewById(R.id.summary_id);
        publicFeedback = (EditText) view.findViewById(R.id.public_feedback_id);
        privateFeedback = (EditText) view.findViewById(R.id.private_feedback_id);
        public_hint = (TextView) view.findViewById(R.id.public_hint);
        private_hint = (TextView) view.findViewById(R.id.private_hint);
        inValid_meetingType = (TextView) view.findViewById(R.id.meetingType_error_id);
        inValid_dateAndTime = (TextView) view.findViewById(R.id.dateBtn_error_id);
        inValid_topic = (TextView) view.findViewById(R.id.topic_error_id);
        morphingButton = (MorphingButton)view.findViewById(R.id.morph_sendBtn_id);

/*        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); //attach toolbar to fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setTextDirection(View.TEXT_DIRECTION_RTL);
        setHasOptionsMenu(true); // show toolbar
        toolbar_title.setTextColor(Color.parseColor("#FFFFFF"));*/
    }

    public void sendRequestHandler(){
        morphingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInformations()){
                   // Toast.makeText(getActivity(),"Request was sent Successfull",Toast.LENGTH_SHORT).show();
                    hideErrors();
                    Request myRequest = null;

                    if(isMentee)
                    {
                        if(flag==1)
                        {

                            myRequest  = new Request(request.getId(),0,myId,
                                    meeting_type,request.getMeetingDate_time(),meeting_topic,null,meeting_public_feedback,null,meeting_private_feedback);
                            Gson gson = new Gson();
                            Log.d( "onClick: ",gson.toJson(myRequest));
                        }
                        else
                        {
                            myRequest  = new Request(0,0,myId,
                                    meeting_type,meeting_date_time,meeting_topic,null,null,null,null);
                        }

                    }
                    else
                    {
                        if(flag==1)
                        {

                            myRequest  = new Request(request.getId(),0,myId,
                                    meeting_type,request.getMeetingDate_time(),meeting_topic,meeting_public_feedback,null,meeting_private_feedback,null);
                        }
                        else
                        {
                            myRequest  = new Request(0,0,myId,
                                    meeting_type,meeting_date_time,meeting_topic,null,null,null,null);
                        }
                    }

                    sendRequestMeetingToServer(myRequest);

                }else{
                    Toast.makeText(getActivity(),"invalid info, try again...",Toast.LENGTH_SHORT).show();
                    showErrors();
                }
            }
        });
    }


    public void dateAndtimebuttonHandler(){
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),RequestFragment.this,year,month,day);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_final = year;
        month_final = month+1;
        day_final = dayOfMonth;

        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),RequestFragment.this,
                hour,minute,false);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_final = hourOfDay;
        minute_final = minute;

        String date = day_final+"-"+month_final+"-"+year_final+"   ";
        String time = hour_final+":"+minute_final;
        meeting_date_time = date+time;

        meeting_date = date;

        Toast.makeText(getActivity(),date+time,Toast.LENGTH_LONG).show();
    }

    public boolean validInformations(){
        Calendar cal = Calendar.getInstance();
        int temp_year = cal.get(Calendar.YEAR);
        int temp_month = cal.get(Calendar.MONTH)+1;
        int temp_day   = cal.get(Calendar.DAY_OF_MONTH);
        int topic_size = topic.getText().toString().length();

        if(flag != 1 ) {
            if ((year_final < temp_year) || (year_final <= temp_year && month_final < temp_month)
                    || (year_final <= temp_year && month_final <= temp_month && day_final <= temp_day)) {
                validDate = 0;
            } else {
                validDate = 1;
            }
        }else if( flag == 1){
            validDate = 1;
        }
        if(topic_size <= 0){
            validTopic = 0;
        }else{
            meeting_topic = topic.getText().toString();
            validTopic = 1;
        }
        if(faceToface.isChecked() && !call.isChecked()) {
            meeting_type = "faceToface";
            validMeetingType = 1;
        }else if(!faceToface.isChecked() && call.isChecked()){
            meeting_type ="call";
            validMeetingType = 1;
        }else{
            validMeetingType = 0;
        }
        Log.d("Res: ",String.valueOf(validMeetingType)+" "+String.valueOf(validDate)+" "+String.valueOf(validTopic));
        return (validDate * validMeetingType * validTopic) == 1;
    }

    public void showErrors(){
        if(validTopic == 0) {
            inValid_topic.setVisibility(View.VISIBLE);
        }else{
            inValid_topic.setVisibility(View.INVISIBLE);
        }
        if(validMeetingType == 0) {
            inValid_meetingType.setVisibility(View.VISIBLE);
        }else{
            inValid_meetingType.setVisibility(View.INVISIBLE);
        }
        if(validDate == 0) {
            inValid_dateAndTime.setVisibility(View.VISIBLE);
        }else{
            inValid_dateAndTime.setVisibility(View.INVISIBLE);
        }
    }
    public void hideErrors(){
        inValid_topic.setVisibility(View.INVISIBLE);
        inValid_meetingType.setVisibility(View.INVISIBLE);
        inValid_dateAndTime.setVisibility(View.INVISIBLE);
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }
    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }
    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    public void success(){
         MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(dimen(R.dimen.mb_height_56)) // 56 dp
                .width(dimen(R.dimen.mb_height_56)) // 56 dp
                .height(dimen(R.dimen.mb_height_56)) // 56 dp
                .color(color(R.color.fbutton_color_green_sea))// normal state color
                // pressed state color
                .icon(R.drawable.ic_check_black_24dp); // icon
        morphingButton.morph(circle);

    }


    public void addMeetingToList(Request request){
         MeetingFragment meetingFragment = new MeetingFragment();
           ArrayList<Request> a = new ArrayList<>();
           a.add(request);
           meetingFragment.updateMeetings(a);
         Log.i("new","new");
         fragmentManager = getFragmentManager();
         transaction = fragmentManager.beginTransaction();
         transaction.replace(R.id.fragment_container, meetingFragment,"MEETING_FRAGMENT");
         transaction.commit();
    }

    public void sendRequestMeetingToServer(final Request request)
    {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.d("json: ", json);
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<Void> requestMeeting = retrofit.requestMeeting(request);
        requestMeeting.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
Gson gson = new Gson();
                Log.d("onResponse: ",gson.toJson(request));
                if (response.code() == 200 || response.code() == 204) {


                    goToMeettingFragment();
                    Log.d("onResponse: ", "done");
                } else if (response.code() == 302) {
                    Toast.makeText(getActivity(), "sending meeting request failed, sorry... ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "error ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUi()
    {
        if(request!=null)
        {
            if(flag == 1)
            {
                publicFeedback.setVisibility(View.VISIBLE);
                privateFeedback.setVisibility(View.VISIBLE);
                public_hint.setVisibility(View.VISIBLE);
                private_hint.setVisibility(View.VISIBLE);
            }
            meeting_type      = request.getMeetingType();
            meeting_date_time = request.getMeetingDate_time();
            meeting_topic     = request.getMeetingTopic();

            if(isMentee)
            {
                meeting_public_feedback = request.getMentee_notes();
                meeting_private_feedback= request.getMentee_private();
            }
            else
            {
                meeting_public_feedback = request.getMentor_notes();
                meeting_private_feedback = request.getMentor_private();
            }

            topic.setText(meeting_topic);
            publicFeedback.setText(meeting_public_feedback);
            privateFeedback.setText(meeting_private_feedback);

            if(meeting_type.equals("call"))
            {
                call.setChecked(true);
            }
            else
            {
                faceToface.setChecked(true);
            }
        }
    }

    private void goToMeettingFragment()
    {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MeetingFragment meetingFragment = new MeetingFragment();
        transaction.replace(R.id.fragment_container, meetingFragment,"MEETING_FRAGMENT");
        transaction.commit();
    }
}
