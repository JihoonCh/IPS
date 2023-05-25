package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;

public class MenuActivity extends AppCompatActivity {
    private ListView menuList;  //메뉴들을 출력할 리스트뷰
    private TextView text_resName, text_resAddress, text_resPhoneNum;
    private String selectedResName; // 선택된 식당의 ResName을 저장할 변수
    private String selectedResAddress; // 선택된 식당의 LotAddress를 저장할 변수
    private int selectedResID; // 선택된 식당의 ResID를 저장할 변수
    private String selectedResPhoneNum; // 선택된 식당의 PhoneNum을 저장할 변수
    private ImageView addFav;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        selectedResName = intent.getStringExtra("Selected ResName");
        selectedResAddress = intent.getStringExtra("Selected ResAddress");
        selectedResID = intent.getIntExtra("Selected ResID", -1);
        selectedResPhoneNum = intent.getStringExtra("Selected ResPhoneNum");

        text_resName = findViewById(R.id.ResName);
        text_resName.setText("Restaurant Name: " + selectedResName);
        text_resAddress = findViewById(R.id.ResAddress);
        text_resAddress.setText("Address: " + selectedResAddress);
        text_resPhoneNum = findViewById(R.id.ResPhoneNum);
        text_resPhoneNum.setText("Phone Number: " + selectedResPhoneNum);

        addFav = findViewById(R.id.addFavorite);
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //즐겨찾기 목록에 추가하는 함수 호출
                addToFavorites(selectedResName);
            }
        });

        menuList = findViewById(R.id.Menu);

        // assets에서 "menu.json" 파일 읽기
        String jsonString = readJsonFromAssets("menu.json");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> menuDataList = new ArrayList<>();

            // "menu.json"의 데이터를 파싱하여 ResID가 선택된 식당의 ResID와 일치하는 경우에만 메뉴 이름을 가져와서 리스트에 추가
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int menuResID = jsonObject.getInt("ResID");

                if (menuResID == selectedResID) {
                    String menuName = jsonObject.getString("MenuName");
                    String menuPrice = jsonObject.getString("Price");
                    String menuData = menuName + "\nPrice: " + menuPrice; // Combine menu name and price
                    menuDataList.add(menuData);
                }
            }

            // 리스트뷰에 데이터를 표시하기 위해 ArrayAdapter 사용
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuDataList);
            menuList.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // assets 폴더에서 파일을 읽어서 문자열로 반환하는 메서드
    private String readJsonFromAssets(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addToFavorites(String resName) {
        try {
            JSONObject favoriteObject = new JSONObject();
            favoriteObject.put("ResName", resName);

            //기존의 즐겨찾기 목록 파일을 불러옴
            JSONArray favoritesArray = loadFavoritesFromFile();

            //즐겨찾기 목록에 이미 존재하는지 체크
            if (!isRestaurantInFavorites(favoritesArray, resName)) {
                //식당을 즐겨찾기에 삽입
                favoritesArray.put(favoriteObject);

                //업데이트된 즐겨찾기 목록을 파일에 저장
                saveFavoritesToFile(favoritesArray);

                //즐겨찾기에 추가됐음을 알리는 팝업
                Toast.makeText(MenuActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                // 변경할 이미지 리소스
                Drawable newDrawable = getResources().getDrawable(R.drawable.heart);
                // ImageView의 srcCompat 변경
                addFav.setImageDrawable(newDrawable);
            } else {
                // Remove the restaurant from favorites
                favoritesArray = removeRestaurantFromFavorites(favoritesArray, selectedResName);

                // Update the favorites file
                saveFavoritesToFile(favoritesArray);

                // Show toast message indicating the restaurant is removed from favorites
                Toast.makeText(MenuActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();

                // Change the image resource of addFav to staricon_before
                Drawable newDrawable = getResources().getDrawable(R.drawable.heart_before);
                addFav.setImageDrawable(newDrawable);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray loadFavoritesFromFile() {
        JSONArray favoritesArray = new JSONArray();

        try {
            InputStream inputStream = getApplicationContext().openFileInput("favoriteList.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            if (!jsonString.isEmpty()) {
                favoritesArray = new JSONArray(jsonString);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return favoritesArray;
    }

    private void saveFavoritesToFile(JSONArray favoritesArray) {
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("favoriteList.json", Context.MODE_PRIVATE);
            outputStream.write(favoritesArray.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private JSONArray removeRestaurantFromFavorites(JSONArray favoritesArray, String resName) {
        JSONArray updatedFavoritesArray = new JSONArray();

        for (int i = 0; i < favoritesArray.length(); i++) {
            try {
                JSONObject favoriteObject = favoritesArray.getJSONObject(i);
                if (favoriteObject.has("ResName")) {
                    String favoriteResName = favoriteObject.getString("ResName");
                    if (!favoriteResName.equals(resName)) {
                        updatedFavoritesArray.put(favoriteObject);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return updatedFavoritesArray;
    }

    private boolean isRestaurantInFavorites(JSONArray favoritesArray, String resName) {
        for (int i = 0; i < favoritesArray.length(); i++) {
            try {
                JSONObject favoriteObject = favoritesArray.getJSONObject(i);
                if (favoriteObject.has("ResName")) {
                    String favoriteResName = favoriteObject.getString("ResName");
                    if (favoriteResName.equals(resName)) {
                        return true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}

