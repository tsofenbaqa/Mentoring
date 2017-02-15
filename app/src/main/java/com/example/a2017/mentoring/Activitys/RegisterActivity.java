package com.example.a2017.mentoring.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.a2017.mentoring.R;

public class RegisterActivity extends AppCompatActivity {
  EditText fnameet, lnameet, emailet, pass1et, pass2et;
  Spinner type;
  Button ok;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.register_fragment);
    fnameet = (EditText) findViewById(R.id.fname);
    lnameet = (EditText) findViewById(R.id.lname);
    emailet = (EditText) findViewById(R.id.email);
    pass1et = (EditText) findViewById(R.id.pass1);
    pass2et = (EditText) findViewById(R.id.pass2);
    type = (Spinner) findViewById(R.id.type);
    ok = (Button)findViewById(R.id.btnok);
    ok.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if((isValidEmail(emailet.getText())&&
            (isValidname(fnameet.getText().toString(),"First"))&&
            (isValidname(lnameet.getText().toString(),"Last")&&
                (isValidPassword(pass1et.getText().toString(),pass2et.getText().toString())))))
        {
          Toast.makeText(getApplicationContext(), "Please Wait",
              Toast.LENGTH_LONG).show();
        }

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
  public final boolean isValidname(String s,String n)
  {
    if(s.isEmpty())
    {
      Toast.makeText(getApplicationContext(), n+" name was left empty",
          Toast.LENGTH_LONG).show();
      return false;
    }
    {
      return true;
    }
  }
  public final boolean isValidPassword(String first,String second)
  {
    if((!first.isEmpty()&&(!second.isEmpty())))
    {
      if(first.equals(second))
      {
        return true;
      }
      else {

        Toast.makeText(getApplicationContext(), "Passwords doesn't match",
            Toast.LENGTH_LONG).show();
        return false;
      }
    }
    else
    {
      if(first.isEmpty()) {
        Toast.makeText(getApplicationContext(), "password was left empty", Toast.LENGTH_LONG).show();
        return false;
      }
      if(second.isEmpty()) {
        Toast.makeText(getApplicationContext(), " please re-type your password again",
            Toast.LENGTH_LONG).show();
        return false;
      }
      return false;
    }

  }
}