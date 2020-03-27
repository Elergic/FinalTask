package com.example.finaltask;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.myViewHolder> {
    private Context context;
    private ArrayList<String> titles;
    private OnItemClickListener onItemClickListener;
    private int[] ids = new int[]{
            R.mipmap.news1,
            R.mipmap.news2,
            R.mipmap.news3,
            R.mipmap.news4,
            R.mipmap.news5,
            R.mipmap.news6,
            R.mipmap.news7,
            R.mipmap.news8,
            R.mipmap.news9,
            R.mipmap.news10,
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainFragmentAdapter(Context context,ArrayList<String> titles){
        this.context = context;
        this.titles = titles;
    }

    @NonNull
    @Override
    public MainFragmentAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context,R.layout.item,null);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.myViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
        holder.imageView.setImageResource(ids[position % 10]);
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private Button button;

        public myViewHolder(final View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
            textView = itemView.findViewById(R.id.item_text);
            //button = itemView.findViewById(R.id.item_button);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(v,titles.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view,String data);
    }
}
