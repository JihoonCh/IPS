package com.example.ips2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private ListView favResList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favResList = findViewById(R.id.FavResList);
        List<String> favoriteResList = loadFavoriteResList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteResList);
        favResList.setAdapter(adapter);
    }
    private List<String> loadFavoriteResList() {
        List<String> favoriteResList = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput("favoriteList.json");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            isr.close();
            fis.close();

            String jsonString = sb.toString();
            JSONArray favoritesArray = new JSONArray(jsonString);

            for (int i = 0; i < favoritesArray.length(); i++) {
                JSONObject favoriteObject = favoritesArray.getJSONObject(i);
                if (favoriteObject.has("ResName")) {
                    String resName = favoriteObject.getString("ResName");
                    favoriteResList.add(resName);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return favoriteResList;
    }
}
