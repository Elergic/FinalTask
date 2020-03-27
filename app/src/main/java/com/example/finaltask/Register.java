package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usertel = (EditText) findViewById(R.id.usertel);
        final EditText userpwd = (EditText) findViewById(R.id.userpwd);
        final EditText userpwd_again = (EditText) findViewById(R.id.userpwd_again);
        final EditText useremail = (EditText) findViewById(R.id.useremail);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText userid = (EditText) findViewById(R.id.userid);
        final EditText userschool = (EditText) findViewById(R.id.userschool);
        final RadioGroup nRg = findViewById(R.id.rg_reg);
        Button register = (Button) findViewById(R.id.register);

        nRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = group.findViewById(checkedId);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tel = usertel.getText().toString().trim();
                final String pwd = userpwd.getText().toString().trim();
                final String pwd_again = userpwd_again.getText().toString().trim();
                final String email = useremail.getText().toString().trim();
                final String name = username.getText().toString().trim();
                final String id = userid.getText().toString().trim();
                final String school = userschool.getText().toString().trim();
                final String character = (String) radioButton.getText().toString().trim();

                if (tel == null){
                    Toast.makeText(Register.this,"电话号码不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (pwd == null){
                    Toast.makeText(Register.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (email == null){
                    Toast.makeText(Register.this,"邮箱不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (name == null){
                    Toast.makeText(Register.this,"姓名不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (id == null){
                    Toast.makeText(Register.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (school == null){
                    Toast.makeText(Register.this,"学校不能为空！",Toast.LENGTH_SHORT).show();
                }

                if (character == null){
                    Toast.makeText(Register.this,"请选择您当前的属性！",Toast.LENGTH_SHORT).show();
                }

                if (!pwd_again.equals(pwd)){
                    Toast.makeText(Register.this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("UserTel",tel)
                                    .add("UserPwd",pwd)
                                    .add("UserEmail",email)
                                    .add("UserName",name)
                                    .add("UserId",id)
                                    .add("UserSchool",school)
                                    .add("UserCharacter",character)
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://192.168.2.106:8080/FinalTask/Register")
                                    .post(requestBody)
                                    .build();

                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();

                            if (responseData.equals("register successfully!")){
                                Looper.prepare();
                                Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
