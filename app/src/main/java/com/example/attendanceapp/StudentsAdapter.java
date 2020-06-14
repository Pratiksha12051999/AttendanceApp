package com.example.attendanceapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.subjectName.setText(studentList.get(position).getSubjects());
        holder.subjectAttendance.setText(studentList.get(position).getTotal_Attendance());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView subjectName, subjectAttendance;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.text_view_subjectname);
            subjectAttendance = itemView.findViewById(R.id.text_view_subjectatt);
        }
    }
}
