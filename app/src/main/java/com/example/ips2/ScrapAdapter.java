package com.example.ips2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScrapAdapter extends RecyclerView.Adapter<ScrapAdapter.ViewHolder> {

    private List<ScrapPost> scrapList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ScrapPost scrapPost, int whichPost);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ScrapAdapter(List<ScrapPost> scrapList) {
        this.scrapList = scrapList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scrap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScrapPost scrapPost = scrapList.get(position);
        holder.bind(scrapPost);
    }

    @Override
    public int getItemCount() {
        return scrapList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.scrapTitle);
            contentTextView = itemView.findViewById(R.id.scrapContent);

            // Attach click listener to itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        ScrapPost clickedScrapPost = scrapList.get(position);
                        int whichPost = clickedScrapPost.whichPost(); // 추천글 여부 가져오기
                        onItemClickListener.onItemClick(clickedScrapPost, whichPost);
                    }
                }
            });
        }

        public void bind(ScrapPost scrapPost) {
            String title = scrapPost.getTitle();
            String content = scrapPost.getContent();
            titleTextView.setText(title);
            contentTextView.setText(content);
        }
    }
}
