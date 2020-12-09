package com.example.jung_oh;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//dapter는 RecyclerView.Adapter를 상속받으며 제네릭으로 위에서 작성한 VerticalViewHolder를 준다.
public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private ArrayList<VerticalData> arrayList;
    private Context context;

    public VerticalAdapter(ArrayList<VerticalData> verticalDatas, Context context) {
        this.arrayList = verticalDatas;
        this.context = context;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        VerticalViewHolder holder = new VerticalViewHolder(view);
        return holder;
    }
    //onCreateViewHolder에서 리턴한 ViewHolder에다가 데이터를 세팅해줄 수 있다.
    //verticalDatas.get(position) 을 이용해 데이터를 가져오고 (여기서의 position은 list item의 순서이다. 시작은 0)
    //holder에서 view들을 꺼네 데이터들을 세팅해준다.
    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImg())
                .into(holder.picture_inmycloset);
    }

    //item의 총 개수를 반환
    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        ImageView picture_inmycloset;

        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.picture_inmycloset = itemView.findViewById(R.id.picture_inmycloset);
        }
    }
}
