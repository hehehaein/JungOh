package com.example.jung_oh;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//itemView를 받아 각각 바인딩해준다.
//전달받을 itemView는 <activity_item_view.xml>이다.
//데이터가 100개라면 그거에 맞춰 imageView를 생성해줌
public class VerticalViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public VerticalViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.picture_inmycloset);
    }
}
