package com.example.jungoh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jung_oh.R;
import com.example.jungoh.BackPressHandler;
import com.example.jungoh.List_data;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyCloset extends AppCompatActivity {
    //소이꺼
    RecyclerView recyclerView;
    List<List_data> dataList;
    DatabaseReference databaseReference;
    //HelperAdapter helperAdapter;
    String email = ((EditText) findViewById(R.id.login_email)).getText().toString().trim();
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private BackPressHandler backPressHandler = new BackPressHandler(this);

    private RecyclerView recyclerView1;
    private FirebaseDatabase database;
    private List<com.example.jung_oh.VerticalData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_closet);
        database = FirebaseDatabase.getInstance();


        // RecyclerView binding
        recyclerView1 = (RecyclerView) findViewById(R.id.Outer_list);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView1.setAdapter(recyclerViewAdapter);
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    com.example.jung_oh.VerticalData verticalData = snapshot1.getValue(com.example.jung_oh.VerticalData.class);
                    data.add(verticalData);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(data.get(position).imageURL).into(((CustomViewHolder)holder).imageView);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.picture_inmycloset);
            }
        }
    }
}

//소이꺼
        /*setContentView(R.layout.activity_my_closet);
        findViewById(R.id.Add_Button).setOnClickListener(onClickListener);
        findViewById(R.id.logout).setOnClickListener(onClickListener);
        navigationView=findViewById(R.id.nav);
        drawerLayout=findViewById(R.id.mypage);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.menu_home:
                        myStartActivity(MainActivity.class);//고쳐야함
                        finish();
                        break;

                    case R.id.menu_addClothes:
                        myStartActivity(AddClothesActivity.class);
                        finish();
                        break;

                    case R.id.menu_myPage:
                        myStartActivity(MyCloset.class);
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.logout:
                        firebaseAuth.getInstance().signOut();
                        myStartActivity(LoginActivity.class);
                        finish();
                        break;


                }
            }
        };



    }
    public void onBackPressed(){

        backPressHandler.onBackPressed("종료하려면 뒤로가기 버튼을 한번 더 누르세요", 3000);

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.Add_Button)
                myStartActivity(AddClothesActivity.class);
        }
    };
    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }*/