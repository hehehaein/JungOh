package com.example.jung_oh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH=1000;
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null) {
                    Intent LoginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(LoginIntent);
                    SplashActivity.this.finish();
                }else{
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        },SPLASH_DISPLAY_LENGTH);
    }
}