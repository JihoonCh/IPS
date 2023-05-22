package com.example.ips2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Queue;

public class Adapter_recommend extends RecyclerView.Adapter<Adapter_recommend.CustomViewHolder> {

    //Item들을 담을 배열 list 생성
    private ArrayList<RecommendPost> arrayList;

    public Adapter_recommend(ArrayList<RecommendPost> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    // viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성 =>화면 출력 최초에 생성될 때 생명주기를 의미
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    //position(위치)에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.content.setText(arrayList.get(position).getContent());

        holder.itemView.setTag(position);
        //viewHolder 클릭 시 이벤트 구현
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.title.getText().toString();
                Toast.makeText(view.getContext(), "curName", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder. getBindingAdapterPosition());
                return true;
            }
        });
    }

    @Override
    //전체 아이템 갯수 리턴
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //
        protected TextView title;
        protected TextView content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.content = (TextView) itemView.findViewById(R.id.content);

        }
    }

}