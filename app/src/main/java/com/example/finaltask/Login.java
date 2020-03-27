package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usertel = (EditText) findViewById(R.id.usertel);
        final EditText userpwd = (EditText) findViewById(R.id.userpwd);
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        Button forget = (Button) findViewById(R.id.forget);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tel = usertel.getText().toString().trim();
                final String pwd = userpwd.getText().toString().trim();

                if (tel == null || pwd == null){
                    Toast.makeText(Login.this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = "http://192.168.2.106:8080/FinalTask/Login?UserTel="+tel+"&UserPwd="+pwd;
                        try {
                            try {
                                URL url = new URL(path);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.setReadTimeout(8000);
                                connection.setConnectTimeout(8000);

                                InputStream in = connection.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                String result = reader.readLine();
                                if (result.equals("login successfully!")){
                                    Looper.prepare();
                                    Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    Looper.loop();
                                }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(Login.this,"账号或密码输入错误",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }

                                connection.disconnect();
                            }
                            catch (MalformedURLException e){
                                e.printStackTrace();
                            }
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Forget.class);
                startActivity(intent);
            }
        });
    }
}
