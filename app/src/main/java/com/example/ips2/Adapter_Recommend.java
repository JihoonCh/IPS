package com.example.ips2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Recommend extends RecyclerView.Adapter<Adapter_Recommend.MyViewHolder> {

    Context context_rec;

    ArrayList<RecommendPost> list_rec;

    // 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(RecommendPost recommendPost);
    }

    private OnItemClickListener listener;

    public Adapter_Recommend(Context context_rec, ArrayList<RecommendPost> list_rec, OnItemClickListener listener) {
        this.context_rec = context_rec;
        this.list_rec = list_rec;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context_rec).inflate(R.layout.item_recommend,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecommendPost recommendpost = list_rec.get(position);
        holder.title.setText(recommendpost.getTitle());
        holder.content.setText(recommendpost.getContent());
        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(recommendpost);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_rec.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

        }
    }
}