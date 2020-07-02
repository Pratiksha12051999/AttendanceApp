package com.example.attendanceapp;
import android.content.Context;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Belal on 4/17/2018.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ArtistViewHolder> {

    private Context mCtx;
    private List<Student> studentList;
    ProgressBar progressBar3;

    public StudentsAdapter(Context mCtx, List<Student> studentsList) {
        this.mCtx = mCtx;
        this.studentList = studentsList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_artists, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.subject1.setText(student.subject1 + ":" + student.subjectatt1);
        holder.subject2.setText(student.subject2 + ":" + student.subjectatt2);
        holder.subject3.setText(student.subject3 + ":" + student.subjectatt3);
        holder.subject4.setText(student.subject4 + ":" + student.subjectatt4);
        holder.subject5.setText(student.subject5 + ":" + student.subjectatt5);
        holder.subject6.setText(student.subject6 + ":" + student.subjectatt6);
        holder.subject7.setText(student.subject7 + ":" + student.subjectatt7);
        //progressBar3.setProgress(Integer.parseInt(student.subject7));
        int last = 6;
      /*  if(student.subjectatt8 != " ") {
            holder.subject8.setText(student.subject8 + ":" + student.subjectatt8);
            last = 8;
        }else{
            holder.subject8.setText(" ");
            last = 7;
        }
        if(student.subjectatt9 != null) {
            holder.subject9.setText(student.subject9 + ":" + student.subjectatt9);
            last = 9;
        }else{
            last = 8;
            holder.subject9.setText(" ");
        }*/

        holder.date_text_view.setText(student.start_date + " to " + student.end_date);
        holder.month_text_view.setText(student.month + student.year);
        holder.name_text_view.setText(student.name);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView div_text_view, date_text_view, month_text_view, name_text_view;
        TextView subject1, subject2, subject3, subject4, subject5, subject6, subject7, subject8, subject9, subject10;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
            subject1 = itemView.findViewById(R.id.text_view_subject1);
            subject2 = itemView.findViewById(R.id.text_view_subject2);
            subject3 = itemView.findViewById(R.id.text_view_subject3);
            subject4 = itemView.findViewById(R.id.text_view_subject4);
            subject5 = itemView.findViewById(R.id.text_view_subject5);
            subject6 = itemView.findViewById(R.id.text_view_subject6);
            subject7 = itemView.findViewById(R.id.text_view_subject7);
            subject8 = itemView.findViewById(R.id.text_view_subject8);
            subject9 = itemView.findViewById(R.id.text_view_subject9);
            subject10 = itemView.findViewById(R.id.text_view_subject10);
            div_text_view = itemView.findViewById(R.id.div_text_view);
            month_text_view = itemView.findViewById(R.id.monthText);
            date_text_view = itemView.findViewById(R.id.date_text_view);
            name_text_view = itemView.findViewById(R.id.name_text_view);

        }
    }
}
