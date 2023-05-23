package com.example.ips2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Tips extends RecyclerView.Adapter<Adapter_Tips.MyViewHolder> {

    Context context_tip;

    ArrayList<TipPost> list_tip;


    public Adapter_Tips(Context context_tip, ArrayList<TipPost> list_tip) {
        this.context_tip = context_tip;
        this.list_tip = list_tip;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context_tip).inflate(R.layout.item_tips,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TipPost tippost = list_tip.get(position);
        holder.title.setText(tippost.getTitle());
        holder.content.setText(tippost.getContent());

    }

    @Override
    public int getItemCount() {
        return list_tip.size();
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