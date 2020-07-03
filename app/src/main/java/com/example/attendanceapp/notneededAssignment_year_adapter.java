package com.example.attendanceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class notneededAssignment_year_adapter extends RecyclerView.Adapter<notneededAssignment_year_adapter.AssignmentViewHolder> {

     private ArrayList<notneededAssignment_yearwise> mYearList;

     public static class AssignmentViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView year;
        public Button btn1, btn2;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            year = itemView.findViewById(R.id.year);
            btn1 = itemView.findViewById(R.id.btn1);
            btn2 = itemView.findViewById(R.id.btn2);
        }
    }

    public notneededAssignment_year_adapter(ArrayList<notneededAssignment_yearwise> yearList){
        mYearList = yearList;

    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_year_c_assignment, parent, false);
        AssignmentViewHolder avh = new AssignmentViewHolder(view);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        notneededAssignment_yearwise currentYear = mYearList.get(position);
        holder.imageView.setImageResource(currentYear.getImg());
        holder.year.setText(currentYear.getText1());
        holder.btn1.setText(currentYear.getBtn1());
        holder.btn2.setText(currentYear.getBtn2());
    }

    @Override
    public int getItemCount() {
        return mYearList.size();
    }
}
