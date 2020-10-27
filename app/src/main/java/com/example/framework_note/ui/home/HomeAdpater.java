package com.example.framework_note.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework_note.R;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/10/15 14:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/15 14:17
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class HomeAdpater extends RecyclerView.Adapter {

    List<String> mData;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View viewById = holder.itemView.findViewById(R.id.textView2);
        viewById.setBackgroundColor(position % 2 == 1 ? Color.GRAY : Color.RED);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
