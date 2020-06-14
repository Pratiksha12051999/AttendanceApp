package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Announcements extends AppCompatActivity {
    DatabaseReference announcements;
    RecyclerView mAnList;
    Button addButton;
    private ArrayList<String> mAnnounce = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        announcements = FirebaseDatabase.getInstance().getReference().child("Announcements");
        announcements.keepSynced(true);

        mAnList=(RecyclerView)findViewById(R.id.recycleview);
        mAnList.setHasFixedSize(true);
        mAnList.setLayoutManager(new LinearLayoutManager(this));

        addButton = findViewById(R.id.AddAnnouncement);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddAnnouncement = new Intent(Announcements.this,AddAnnouncement.class);
                startActivity(AddAnnouncement);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Blog,BlogViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog,BlogViewHolder>
                (Blog.class,R.layout.activity_announcement,BlogViewHolder.class,announcements){
            @Override
            protected void populateViewHolder(BlogViewHolder blogViewHolder, Blog blog, int i) {
                blogViewHolder.setSubject(blog.getSubject());
                blogViewHolder.setContent(blog.getContent());
            }

        };
        mAnList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public void setSubject(String Subject){
            TextView subject=(TextView)mView.findViewById(R.id.Subject);
            subject.setText(Subject);
        }
        public void setContent(String Content){
            TextView content=(TextView)mView.findViewById(R.id.Content);
            content.setText(Content);
        }
    }
}