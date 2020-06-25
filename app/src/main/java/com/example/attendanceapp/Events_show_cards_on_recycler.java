package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Events_show_cards_on_recycler extends AppCompatActivity {

    DatabaseReference mDatabase;
    RecyclerView recycleview;
    Button addeve;
    ArrayList<String> events = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_c_show);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);

        recycleview = (RecyclerView)findViewById(R.id.recyclerview);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new LinearLayoutManager(this));

        addeve = findViewById(R.id.addeve);
        addeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(Events_show_cards_on_recycler.this, Event_details.class);
                startActivity(addEvent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Events_show_Helper, Events_show_cards_on_recycler.EventViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Events_show_Helper,EventViewHolder>
                (Events_show_Helper.class,R.layout.activity_event_c_show, EventViewHolder.class, mDatabase){
            @Override
            protected void populateViewHolder(EventViewHolder eventViewHolder, Events_show_Helper model, int i) {
                eventViewHolder.seteventName(model.getEventName());
            }
        };
        recycleview.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        View view;

        public EventViewHolder(@NonNull View itemView, View view) {
            super(itemView);
            this.view = view;
        }

        public void seteventName(String eventName){
            TextView eventname = (TextView) view.findViewById(R.id.eventName);
            eventname.setText(eventName);
        }




    }
}
