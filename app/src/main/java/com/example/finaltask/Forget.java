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
import android.widget.Toast;

public class Forget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        final EditText usertel = (EditText) findViewById(R.id.usertel);
        final EditText userpwd = (EditText) findViewById(R.id.userpwd);
        Button forget = (Button) findViewById(R.id.forget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tel = usertel.getText().toString().trim();
                final String pwd = userpwd.getText().toString().trim();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("UserTel",tel)
                                    .add("UserPwd",pwd)
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://192.168.2.106:8080/FinalTask/Forget")
                                    .post(requestBody)
                                    .build();

                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();

                            if (responseData.equals("update successfully!")){
                                Looper.prepare();
                                Toast.makeText(Forget.this,"修改成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

                Intent intent = new Intent(Forget.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
