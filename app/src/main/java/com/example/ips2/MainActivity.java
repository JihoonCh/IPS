package com.example.ips2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnHome, btnMypage;
    ImageButton btnKeyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("MENU");

        // 버튼변수에 버튼객체 대입
        Button btnCommunity = (Button) findViewById(R.id.btnCommunity);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnMypage = (Button) findViewById(R.id.btnMyPage);
        btnKeyWord = (ImageButton) findViewById(R.id.btnKeyWord);

        // 사용자 위치 화면
        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuActivity.class);
                startActivity(intent);
            }
        });

        // 커뮤니티 화면
        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CommunityActivity.class);
                startActivity(intent);
            }
        });

        // 홈화면
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        // 마이페이지 연결
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SubActivity.class);
                startActivity(intent);
            }
        });
    }
}
























// MainActivity.java-로그아웃에서 사용하자
/*package com.example.ips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;

import com.google.firebase.auth.FirebaseAuth;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 7, 0},
        k = 1,
        d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"},
        d2 = {"Lcom/example/test2/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class MainActivity extends ComponentActivity {
    private FirebaseAuth mFirebaseAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();



        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃하기
                mFirebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //탈퇴 처리
        //mFirebaseAuth.getCurrentUser().delete();
    }
}

 */