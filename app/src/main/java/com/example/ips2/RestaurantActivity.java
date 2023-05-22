package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private ListView listView;  //식당 리스트 출력할 리스트뷰
    private String location;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        listView = findViewById(R.id.resList);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        String food_category = intent.getStringExtra("Food Category");
        Intent intent2 = getIntent(); //전달할 데이터를 받을 Intent
        String location = intent2.getStringExtra("User Address");

        TextView text_category = findViewById(R.id.foodCategory);
        text_category.setText("Category: " + food_category);

        //assets에서 "res.json" 파일 읽기
        String jsonString = readJsonFromAssets("res.json");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> dataList = new ArrayList<>();

            // "res.json"의 데이터를 파싱하여 ResType이 "foodcg"와 일치하고 ResID가 10020보다 작고, LotAddress에 location 값이 포함된 경우에만 ResName을 가져와서 리스트에 추가
            int count = 0; // 가져온 데이터 개수를 카운트하기 위한 변수
            for (int i = 0; i < jsonArray.length(); i++) {
                if (count >= 20) {
                    break; // 최대 20개까지만 가져오기 위해 반복문 종료
                }

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String resType = jsonObject.getString("ResType");
                int resID = jsonObject.getInt("ResID");
                String lotAddress = jsonObject.getString("LotAddress");

                if (resType.equals(food_category) && lotAddress.contains(location)) {
                    String resName = jsonObject.getString("ResName");
                    dataList.add(resName);
                    count++; // 데이터 개수 카운트 증가
                }
            }

            // 리스트뷰에 데이터를 표시하기 위해 ArrayAdapter 사용
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // assets 폴더에서 파일을 읽어서 문자열로 반환하는 메서드
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
