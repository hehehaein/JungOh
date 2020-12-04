package com.example.jung_oh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {

    List<List_data> dataList;

    public HelperAdapter(List<List_data> dataList) {
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_view, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        List_data data_List = dataList.get(position);
        viewHolderClass.title.setText(data_List.getName());
        Glide.with(viewHolderClass.image.getContext()).load(data_List.getImgUrl()).into(viewHolderClass.image);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        public ViewHolderClass(@NonNull View itemView){
            super(itemView);
            image=itemView.findViewById(R.id.Outer_back);

        }
    }
}
