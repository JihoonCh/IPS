package com.example.ips2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

public class ScrapActivity extends AppCompatActivity {

    private RecyclerView scrapRecyclerView;
    private ScrapAdapter scrapAdapter;
    private List<ScrapPost> scrapList;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        scrapRecyclerView = findViewById(R.id.scrapRecyclerView);
        scrapRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        scrapList = new ArrayList<>();
        scrapAdapter = new ScrapAdapter(scrapList);
        scrapRecyclerView.setAdapter(scrapAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        loadScrapPosts();
    }

    private void loadScrapPosts() {
        DatabaseReference scrapRef = databaseReference.child("Scrap").child(currentUser.getUid());
        scrapRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scrapList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ScrapPost scrapPost = dataSnapshot.getValue(ScrapPost.class);
                    if (scrapPost != null) {
                        scrapList.add(scrapPost);
                    }
                }
                scrapAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScrapActivity.this, "Failed to load scrapped posts.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the item click listener for ScrapAdapter
        scrapAdapter.setOnItemClickListener(new ScrapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ScrapPost scrapPost, int whichPost) {
                String postId = scrapPost.getPostId();

                if (whichPost == 1) {
                    // OpenPostActivity로 이동하면서 postId 전달
                    Intent intent = new Intent(ScrapActivity.this, OpenPostActivity.class);
                    intent.putExtra("postId", postId);
                    startActivity(intent);
                } else if (whichPost == 2) {
                    // OpenPostRecommendActivity로 이동하면서 postId 전달
                    Intent intent = new Intent(ScrapActivity.this, OpenPostRecommendActivity.class);
                    intent.putExtra("postId", postId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ScrapActivity.this, OpenPostTipActivity.class);
                    intent.putExtra("postId", postId);
                    startActivity(intent);
                }
            }
        });
    }
}
