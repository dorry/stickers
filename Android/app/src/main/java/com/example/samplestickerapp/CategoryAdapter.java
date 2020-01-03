package com.example.samplestickerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.PacksTypeViewHolder> {
    private List<Category> mTypeList;
    private IOnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(List<Category> TypeList,
                           IOnCategoryClickListener onCategoryClickListener) {
        mTypeList =TypeList;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public PacksTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        PacksTypeViewHolder vh = new PacksTypeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PacksTypeViewHolder holder, int position) {
        Category currentItem = mTypeList.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {

        return mTypeList.size();
    }

    public class PacksTypeViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
        public PacksTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.typeImage);
            mTextView = itemView.findViewById(R.id.typeText);
        }

    public void bind(final Category category){
        this.mImageView.setImageBitmap(category.getLogoBitMap());
        this.mTextView.setText(category.getName());
        this.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onCategoryClickListener.onClick(category);
            }
        });
        }
    }
}
