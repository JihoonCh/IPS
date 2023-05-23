package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    private ListView restaurantList;  //식당 리스트 출력할 리스트뷰
    private String location;
    private String selectedResName; // 선택된 식당의 ResName을 저장할 변수
    private int selectedResID; // 선택된 식당의 ResID를 저장할 변수
    private String selectedLotAddress; // 선택된 식당의 LotAddress를 저장할 변수
    private String selectedPhoneNum; // 선택된 식당의 PhoneNum을 저장할 변수
    private TextView text_category;
    private TextView text_location;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        restaurantList = findViewById(R.id.resList);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        String food_category = intent.getStringExtra("Food Category");
        Intent intent2 = getIntent(); //전달할 데이터를 받을 Intent
        location = intent2.getStringExtra("User Address");

        text_category = findViewById(R.id.foodCategory);
        text_category.setText("Category: " + food_category);
        text_location = findViewById(R.id.location);
        text_location.setText("Location: " + location);

        //assets에서 "res.json" 파일 읽기
        String jsonString = readJsonFromAssets("res.json");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> dataList = new ArrayList<>();

            // "res.json"의 데이터를 파싱하여 ResType이 "foodcg"와 일치하고 ResID가 10020보다 작고, LotAddress에 location 값이 포함된 경우에만 ResName을 가져와서 리스트에 추가
            int count = 0; // 가져온 데이터 개수를 카운트하기 위한 변수
            for (int i = 0; i < jsonArray.length(); i++) {
                if (count >= 40) {
                    break; // 최대 40개까지만 가져오기 위해 반복문 종료
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
            //식당이 없는 경우 안내문구 표시
            if (count == 0) dataList.add("\n\n\n\n\nThere are no restaurants that meet the conditions.\n\n\n\n" +
                    "Please change the conditions and search again.");

            // 리스트뷰에 데이터를 표시하기 위해 ArrayAdapter 사용
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            restaurantList.setAdapter(adapter);

            restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
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

