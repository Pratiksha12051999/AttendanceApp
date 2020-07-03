package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowAssignment extends AppCompatActivity {
    DatabaseReference assignment;
    RecyclerView mAsList;
    Button addbutton;
    private ArrayList<String> mAssign = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_showassignment);
        assignment = FirebaseDatabase.getInstance().getReference().child("Assignment");
        assignment.keepSynced(true);

        mAsList=(RecyclerView)findViewById(R.id.recyclerview);
        mAsList.setHasFixedSize(true);
        mAsList.setLayoutManager(new LinearLayoutManager(this));

        addbutton = findViewById(R.id.AddAssign);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddAssignment = new Intent(ShowAssignment.this,Assignment.class);
                startActivity(AddAssignment);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<AssignmentHelper, ShowAssignment.AssignViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AssignmentHelper, ShowAssignment.AssignViewHolder>
                (AssignmentHelper.class,R.layout.activity_c_showassignment, AssignViewHolder.class,assignment){
            @Override
            protected void populateViewHolder(ShowAssignment.AssignViewHolder assignViewHolder, AssignmentHelper model, int i) {
                assignViewHolder.setAssno(model.getAssno());
                assignViewHolder.setStartdate(model.getStartdate());
                assignViewHolder.setEnddate(model.getEnddate());
                assignViewHolder.setQuestion(model.getQuestion());
            }



        };
        mAsList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class AssignViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public AssignViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setAssno(String Assno) {
            TextView assno = (TextView) mView.findViewById(R.id.Assno);
            assno.setText(Assno);
        }

        public void setStartdate(String Startdate) {
            TextView startdate = (TextView) mView.findViewById(R.id.Startdate);
            startdate.setText(Startdate);
        }

        public void setEnddate(String Enddate) {
            TextView enddate = (TextView) mView.findViewById(R.id.Enddate);
            enddate.setText(Enddate);
        }

        public void setQuestion(String Question) {
            TextView question = (TextView) mView.findViewById(R.id.Question);
            question.setText(Question);
        }
    }
}
