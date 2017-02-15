package com.example.pcp.request_meeting;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.morphingbutton.MorphingButton;

import java.util.Calendar;

import static android.R.attr.animation;
import static android.R.attr.color;
import static android.R.attr.shape;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RequestFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    Button reqBtn,datebtn;
    TextView meetingType,toolbar_title;
    TextView inValid_meetingType,inValid_dateAndTime,inValid_topic;
    Toolbar toolbar;
    CheckBox faceToface , call;
    EditText topic,publicFeedback,privateFeedback;

    int day,month,year , hour , minute;
    int day_final,month_final,year_final,hour_final,minute_final;
    int validDate = 1;
    int validMeetingType = 1;
    int validTopic = 1;

    MorphingButton morphingButton;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meeting_request, container, false);
        morphingButton = (MorphingButton) view.findViewById(R.id.morph_sendBtn_id);
        //morphingButton.setBackgroundResource(R.drawable.button_custom2);
        setUpFields(view); // binding
        setFoucsEditText(view);

        dateAndtimebuttonHandler(); // do event when clicked
        sendRequestHandler();
        return view;
    }

    private void setUpFields(View view) {
        datebtn = (Button) view.findViewById(R.id.dateBtn_id);
        meetingType = (TextView) view.findViewById(R.id.meeting_type_text);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        faceToface = (CheckBox) view.findViewById(R.id.facetoface_checkbox_id);
        call = (CheckBox) view.findViewById(R.id.call_checkbox_id);
        toolbar_title = (TextView) view.findViewById(R.id.toolbar_title);
        topic = (EditText) view.findViewById(R.id.summary_id);
        publicFeedback = (EditText) view.findViewById(R.id.public_feedback_id);
        privateFeedback = (EditText) view.findViewById(R.id.private_feedback_id);
        inValid_meetingType = (TextView) view.findViewById(R.id.meetingType_error_id);
        inValid_dateAndTime = (TextView) view.findViewById(R.id.dateBtn_error_id);
        inValid_topic = (TextView) view.findViewById(R.id.topic_error_id);
        morphingButton = (MorphingButton)view.findViewById(R.id.morph_sendBtn_id);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); //attach toolbar to fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setTextDirection(View.TEXT_DIRECTION_RTL);
        setHasOptionsMenu(true); // show toolbar
        toolbar_title.setTextColor(Color.parseColor("#FFFFFF"));
    }


    public  void setFoucsEditText(View view){
        topic.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                topic.setAlpha(1.0f);
                return false;
            }
        });
        publicFeedback.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                publicFeedback.setAlpha(1.0f);
                return false;
            }
        });
        privateFeedback.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                privateFeedback.setAlpha(1.0f);
                return false;
            }
        });
    }


    public void sendRequestHandler(){
        morphingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInformations()){
                    Toast.makeText(getActivity(),"Request was sent Successfull",Toast.LENGTH_SHORT).show();
                    hideErrors();
                    success();
                }else{
                    Toast.makeText(getActivity(),"invalid info, try again...",Toast.LENGTH_SHORT).show();
                    showErrors();
                }
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int res_id = item.getItemId();
        if(res_id == R.id.action_back){
            Toast.makeText(getContext()," selected back option",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestBtnHandler(){
        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"request sent",Toast.LENGTH_SHORT).show();
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

        String date = "Date:"+day_final+"/"+month_final+"/"+year_final+"   ";
        String time = "Time:"+hour_final+"/"+minute_final;
        Toast.makeText(getActivity(),date+time,Toast.LENGTH_LONG).show();
    }

    public boolean validInformations(){
        Calendar cal = Calendar.getInstance();
        int temp_year = cal.get(Calendar.YEAR);
        int temp_month = cal.get(Calendar.MONTH)+1;
        int temp_day   = cal.get(Calendar.DAY_OF_MONTH);
        int topic_size = topic.getText().toString().length();

        if( (year_final < temp_year) || (year_final <= temp_year && month_final < temp_month)
                || (year_final <= temp_year && month_final <= temp_month && day_final <= temp_day)){
            validDate = 0;
        }else{
            validDate = 1;
        }
        if(topic_size <= 0){
            validTopic = 0;
        }else{
            validTopic = 1;
        }
        if((faceToface.isChecked() && !call.isChecked()) || (!faceToface.isChecked() && call.isChecked())) {
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
}
