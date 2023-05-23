package com.example.ips2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private ListView favResList;

    private String location;
    private String selectedResName; // 선택된 식당의 ResName을 저장할 변수
    private int selectedResID; // 선택된 식당의 ResID를 저장할 변수
    private String selectedLotAddress; // 선택된 식당의 LotAddress를 저장할 변수
    private String selectedPhoneNum; // 선택된 식당의 PhoneNum을 저장할 변수
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favResList = findViewById(R.id.FavResList);
        List<String> favoriteResList = loadFavoriteResList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteResList);
        favResList.setAdapter(adapter);

        //assets에서 "res.json" 파일 읽기
        String jsonString = readJsonFromAssets("res.json");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> dataList = new ArrayList<>();
            favResList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedResName = (String) parent.getItemAtPosition(position); // Get the clicked ResName

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String resName = jsonObject.getString("ResName");

                            if (resName.equals(selectedResName)) {
                                selectedResID = Integer.parseInt(jsonObject.getString("ResID"));
                                selectedLotAddress = jsonObject.getString("LotAddress");
                                selectedPhoneNum = jsonObject.getString("PhoneNum");
                                // 이후에 할 작업을 여기에 추가
                                Intent intent = new Intent(FavoriteActivity.this, FavoriteMenuActivity.class);
                                intent.putExtra("Selected ResName", selectedResName);
                                intent.putExtra("Selected ResID", selectedResID);
                                intent.putExtra("Selected ResAddress", selectedLotAddress);
                                intent.putExtra("Selected ResPhoneNum", selectedPhoneNum);
                                startActivity(intent);
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private String readJsonFromAssets(String fileName) {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
