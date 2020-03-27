package com.example.finaltask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ImageBarnnerViewGroup.ImageBarnnerListener{

    private ImageBarnnerViewGroup mGroup;
    private int[] ids = new int[]{
            R.mipmap.news1,
            R.mipmap.news2,
            R.mipmap.news3
    };
    private View view;
    public RecyclerView recyclerView;
    private ArrayList<String> titles = new ArrayList<String>();
    private MainFragmentAdapter mainFragmentAdapter;
    private Handler handler;
    private String str = "";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        initRecyclerView();

        initData();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        mGroup = getView().findViewById(R.id.image_group);

        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setImageResource(ids[i]);

            mGroup.addView(iv);
        }

        mGroup.setListener(this);

//        Thread thread = new Thread(this);
//        thread.start();
//
//        handler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                if (msg.what == 1){
//                    str = (String) msg.obj;
//                }
//                super.handleMessage(msg);
//            }
//        };
    }

    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(getContext(),"pos=" + pos,Toast.LENGTH_SHORT).show();
    }

    private void initData(){
//        String[] s = str.split("\\|");
//        for (int i = 0; i < s.length; i++) {
//            titles.add(s[i]);
//        }

        for (int i = 0; i < 10; i++) {
            titles.add("找赞助" + i);
        }
        for (int i = 0; i < 10; i++) {
            titles.add("找合作" + i);
        }
    }

    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mainFragmentAdapter = new MainFragmentAdapter(getActivity(),titles);
        recyclerView.setAdapter(mainFragmentAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mainFragmentAdapter.setOnItemClickListener(new MainFragmentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String data) {
                Toast.makeText(getActivity(),"我是Item:" + data,Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void run() {
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url("http://192.168.2.106:8080/FinalTask/Event").build();
//            Response response = client.newCall(request).execute();
//            String responseData = response.body().string().trim();
//
//            Message msg = handler.obtainMessage(1);
//            msg.obj = responseData;
//            handler.sendMessage(msg);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
