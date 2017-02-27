package com.example.a2017.mentoring.Fragments;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;

import com.gospelware.liquidbutton.LiquidButton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
  EditText first_name, last_name, email, first_password, second_password, phone_number;
  String _first_name, _last_name, _email, _first_password, _second_password, _phone_number, _type;
  Spinner type;
  Button ok;
  ProgressBar progressBar;
  LiquidButton liquidButton;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    {
      super.onCreate(savedInstanceState);
      View view = inflater.inflate(R.layout.register_fragment, container, false);
      first_name = (EditText) view.findViewById(R.id.reg_first_name);
      last_name = (EditText) view.findViewById(R.id.reg_last_name);
      email = (EditText) view.findViewById(R.id.reg_email_address);
      first_password = (EditText) view.findViewById(R.id.reg_first_password);
      second_password = (EditText) view.findViewById(R.id.reg_second_password);
      phone_number = (EditText) view.findViewById(R.id.reg_phone_number);
      type = (Spinner) view.findViewById(R.id.reg_type);
      ok = (Button) view.findViewById(R.id.btnok);
      liquidButton=(LiquidButton)view.findViewById(R.id.reg_done_animation);
      progressBar = (ProgressBar)view.findViewById(R.id.reg_progressBar);
      getType();
      doAnimation();
      registerButton();
      return view;
    }
  }

  private void registerButton() {
    ok.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        readTextFromEditText();
        isAllValid();
        getType();

        if(isAllValid()) {
          Register register = new Register(_first_name, _last_name, _phone_number, _email, _first_password, _type);
          sendRegisterToServer(register);
        }


      }
    });
  }

  private void getType() {
    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        _type = type.getItemAtPosition(position).toString();
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  public boolean isValidPhoneNumber() {
    if((!android.util.Patterns.PHONE.matcher(phone_number.getText().toString()).matches())|| (_phone_number.length()<10)||(_phone_number.length()>10)) {
      //if((/*phone_number.getText().toString().length()<10)||*/(phone_number.getText().toString().length()>10)))
      phone_number.setError("Invalid Phone number");
      return false;
    } else {
      return true;
    }
  }

  public boolean isValidEmail() {
    CharSequence target = email.getText().toString();
//    if (TextUtils.isEmpty(target)) {
    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
    {
      email.setError("Invalid Email Address");
      return false;
    }
    {
      return true;
    }
  }

  public boolean isValidFirstName() {
    String regx = "[a-zA-Z]+\\.?";
    Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(first_name.getText().toString());
    if (!matcher.matches()) {
      first_name.setError("Invalid First Name");
      return false;
    }
    else return true;
  }

  public boolean isValidLastName() {
    String regx = "[a-zA-Z]+\\.?";
    Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(last_name.getText().toString());
    if (!matcher.matches()) {
      last_name.setError("Invalid Last Name");
      return false;
    }
    else return true;
  }

  public final boolean isValidPassword() {
    Pattern pattern;
    Matcher matcher;
    String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[@_.]).*$";
    pattern = Pattern.compile(PASSWORD_PATTERN);
    matcher = pattern.matcher(first_password.getText().toString());
    if(!matcher.matches())
    {
      first_password.setError("Invalid Password,Password must contain 0-9,A-Z and special characters");
      return false;

    }
    else return true;
  }

  public final boolean isValidPasswords()
  {

    if ((!first_password.getText().toString().isEmpty()) && (!second_password.getText()
        .toString()
        .isEmpty()))
    {
      if (isValidPassword())
        if (first_password.getText().toString().equals(second_password.getText().toString())) return true;
        else {
          second_password.setError("Passwords doesn't match");
          return false;
        }
    }
    else return true;
    return false;

  }

  public void sendRegisterToServer(final Register register) {
    progressBar.setVisibility(View.VISIBLE);
    ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
    Call<String> addNewUser = retrofit.addNewUser(register);
    addNewUser.enqueue(new Callback<String>() {
      @Override public void onResponse(Call<String> call, Response<String> response) {
        Log.d("onResponse: ", "done");
        if (response.code() == 200 || response.code() == 204) {

          liquidButton.startPour();
          liquidButton.setAutoPlay(true);
        } else if (response.code() == 302) {
          Toast.makeText(getActivity(), getResources().getString(R.string.userExists), Toast.LENGTH_LONG).show();

        } else {
          Toast.makeText(getActivity(), getResources().getString(R.string.error_general), Toast.LENGTH_LONG).show();

        }
        progressBar.setVisibility(View.GONE);
      }

      @Override public void onFailure(Call<String> call, Throwable t) {
        Toast.makeText(getActivity(), getResources().getString(R.string.failure), Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
      }
    });
  }

  private void readTextFromEditText() {
    _first_name = first_name.getText().toString();
    _last_name = last_name.getText().toString();
    _email = email.getText().toString();
    _first_password = first_password.getText().toString();
    _second_password = second_password.getText().toString();
    _phone_number = phone_number.getText().toString();
  }

  private void doAnimation() {
    liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
      @Override public void onPourFinish() {
        Toast.makeText(getContext(), "is done ", Toast.LENGTH_LONG).show();
        Intent i2 = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(i2);
        Preferences.setLogin(true, getContext());
      }

      @Override public void onProgressUpdate(float progress) {

      }
    });
  }

  public boolean isAllValid() {
    if (isValidFirstName()) {
      if (isValidLastName()) {
        if (isValidEmail()) {
          if (isValidPhoneNumber()) {
            if (isValidPasswords()) {
              return true;
            }
          }
        }
      }
    }
    else
      return false;
    return false;
  }
}

