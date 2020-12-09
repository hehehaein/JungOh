package com.example.jungoh;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jung_oh.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public ImageView Outer, Top, Bottom, Accessary;
    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        Outer = itemView.findViewById(R.id.Outer_view);
        Top = itemView.findViewById(R.id.Top_view);
        Bottom = itemView.findViewById(R.id.Bottom_view);
        Accessary = itemView.findViewById(R.id.Accessary_view);
    }
}
