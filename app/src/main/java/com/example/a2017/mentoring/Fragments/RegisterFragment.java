package com.example.a2017.mentoring.Fragments;


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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
  EditText fnameet, lnameet, emailet, pass1et, pass2et,phone;
  String sfnameet, slnameet, semailet, spass1et, spass2et,sphone,stype;
  Spinner type;
  Button ok;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    {
      super.onCreate(savedInstanceState);
      View view = inflater.inflate(R.layout.register_fragment, container, false);
      fnameet = (EditText) view.findViewById(R.id.fname);
      lnameet = (EditText) view.findViewById(R.id.lname);
      emailet = (EditText) view.findViewById(R.id.email);
      pass1et = (EditText) view.findViewById(R.id.pass1);
      pass2et = (EditText) view.findViewById(R.id.pass2);
      phone=(EditText)view.findViewById(R.id.phone2) ;
      type = (Spinner) view.findViewById(R.id.type);
      ok = (Button) view.findViewById(R.id.btnok);
      getType();
      registerButoon();
      return view;
    }
  }
  private void registerButoon()
  {
    ok.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {
        readTextFromEditText();

        if ((isValidEmail(emailet.getText()) &&
                  (isValidname(fnameet.getText().toString(), "First")) &&
                  (isValidname(lnameet.getText().toString(), "Last") &&
                          (isValidPassword(pass1et.getText().toString(), pass2et.getText().toString())))))
          {

        Register register = new Register(semailet,spass1et,sfnameet,slnameet,stype,sphone);
        sendRegisterToServer(register);
      }

      }
    });
  }
  private void getType()
  {
    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
      {
        stype=type.getItemAtPosition(position).toString();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent)
      {

      }
    });
  }

  public final static boolean isValidEmail(CharSequence target) {
    if (TextUtils.isEmpty(target)) {
      return false;
    } else {
      return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
  }

  public final boolean isValidname(String s, String n) {
    if (s.isEmpty()) {
      fnameet.setError("wrong name");
      return false;
    }
    {
      return true;
    }
  }

  public final boolean isValidPassword(String first, String second) {
    if ((!first.isEmpty() && (!second.isEmpty()))) {
      if (first.equals(second)) {
        return true;
      } else {
        pass1et.setError("wrong password");
        return false;
      }
    }
    return false;
  }

  public void sendRegisterToServer(Register register)
  {
    ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
    Call<String> addNewUser = retrofit.addNewUser(register);
    addNewUser.enqueue(new Callback<String>()
    {
      @Override
      public void onResponse(Call<String> call, Response<String> response)
      {
        Log.d( "onResponse: ","done");
        if(response.code() == 200 ||response.code() == 204)
        {
          Toast.makeText(getContext(),"is done " , Toast.LENGTH_LONG).show();

        }
        else if(response.code() == 302 )
        {
          Toast.makeText(getActivity(),"we are sorry the user exists try another user " , Toast.LENGTH_LONG).show();
        }
        else
        {
          Toast.makeText(getActivity(),"error " , Toast.LENGTH_LONG).show();

        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t)
      {
        Toast.makeText(getActivity(),"onFailure" , Toast.LENGTH_LONG).show();
      }
    });
  }

  private void readTextFromEditText()
  {
    sfnameet = fnameet.getText().toString();
    slnameet=lnameet.getText().toString();
    semailet=emailet.getText().toString();
    spass1et=pass1et.getText().toString();
    spass2et=pass2et.getText().toString();
    sphone=phone.getText().toString();
  }
}

