package com.example.instagram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PostHolder> {

    private ArrayList<String> userEmailList;
    private ArrayList<String> userCommentList;
    private ArrayList<String> userDateList;
    private ArrayList<String> userImageList;

    public RecyclerViewAdapter(ArrayList<String> userEmailList, ArrayList<String> userCommentList, ArrayList<String> userDateList, ArrayList<String> userImageList) {
        this.userEmailList = userEmailList;
        this.userCommentList = userCommentList;
        this.userDateList = userDateList;
        this.userImageList = userImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.dateText.setText(userDateList.get(position));
        holder.emailText.setText(userEmailList.get(position));
        holder.commentText.setText(userCommentList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView commentText,emailText,dateText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_image);
            commentText = itemView.findViewById(R.id.recycler_comment);
            emailText = itemView.findViewById(R.id.recycler_userNameText);
            dateText = itemView.findViewById(R.id.recycler_date);
        }
    }
}
