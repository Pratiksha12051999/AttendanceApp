package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Events_show_cards_on_recycler extends AppCompatActivity {

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("Events");
    RecyclerView recyclerview;
    Button addeve, click_expand;
    private ArrayList<String> events = new ArrayList<>();

   public Events_show_cards_on_recycler(){

   }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_r_list);
        mDatabase.keepSynced(true);

        recyclerview = (RecyclerView)findViewById(R.id.recycleview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

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
        FirebaseRecyclerAdapter<Events_show_Helper, Events_show_cards_on_recycler.EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Events_show_Helper, EventViewHolder>
                (Events_show_Helper.class, R.layout.activity_event_c_show, EventViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(EventViewHolder eventViewHolder, Events_show_Helper model, int i) {
                eventViewHolder.setEventName(model.getEventName());

            }
        };
        recyclerview.setAdapter(firebaseRecyclerAdapter);
    }


    public static class EventViewHolder extends RecyclerView.ViewHolder{
        View view;

        public EventViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }

        public void setEventName(String eventName) {
            TextView EventName = (TextView) view.findViewById(R.id.EventName);
            EventName.setText(eventName);
        }





    }
}
