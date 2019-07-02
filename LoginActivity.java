package com.example.priti.smartbin;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText etUserName;
    EditText etPassword;
    RelativeLayout rellay1;
    Handler handler =new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
           rellay1.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rellay1=(RelativeLayout)findViewById(R.id.rellay1);
        handler.postDelayed(runnable,2000);

        etUserName=findViewById(R.id.et_user_name);

        etPassword=findViewById(R.id.et_password);

        if(QueryPreferences.isRemeberUserAndPassword(this)){
            etUserName.setText(QueryPreferences.getUserName(this));
            etPassword.setText(QueryPreferences.getUserPassword(this));
        }

        login=findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().equals(QueryPreferences.getAppPassword(LoginActivity.this))){
                if(QueryPreferences.isRemeberUserAndPassword(LoginActivity.this)){
                    QueryPreferences.setUserNameAndPassword(LoginActivity.this,etUserName.getText().toString(),etPassword.getText().toString());
                }
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                finish();}
                else{
                    Toast.makeText(LoginActivity.this,"App Password is Incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
