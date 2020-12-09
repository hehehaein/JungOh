package com.example.jungoh;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.jung_oh.R;


import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    Context context;
    List<ImageUploadInfo> MainImageUploadInfoList;


    public RecyclerViewAdapter(Context context, List<ImageUploadInfo> TempList) {


        this.MainImageUploadInfoList = TempList;


        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_closet, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageUploadInfo UploadInfo = MainImageUploadInfoList.get(position);



        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getImageURL()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {


        return MainImageUploadInfoList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageView;



        public ViewHolder(View itemView) {
            super(itemView);


            imageView = (ImageView) itemView.findViewById(R.id.Outer_view);


        }
    }
}