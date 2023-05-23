package com.example.ips2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Review extends RecyclerView.Adapter<Adapter_Review.MyViewHolder> {

    Context contect_rev;

    ArrayList<ReviewPost> list_rev;

    // 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(ReviewPost reviewPost);
    }

    private OnItemClickListener listener;
    public Adapter_Review(Context contect_rev, ArrayList<ReviewPost> list_rev, Adapter_Review.OnItemClickListener listener) {
        this.contect_rev = contect_rev;
        this.list_rev = list_rev;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contect_rev).inflate(R.layout.item_review,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReviewPost reviewpost = list_rev.get(position);
        holder.title.setText(reviewpost.getTitle());
        holder.content.setText(reviewpost.getContent());
        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(reviewpost);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_rev.size();
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