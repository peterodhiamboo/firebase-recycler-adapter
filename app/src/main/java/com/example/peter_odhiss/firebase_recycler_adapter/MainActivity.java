package com.example.peter_odhiss.firebase_recycler_adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Views
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Query query;
    private Button send;
    private EditText editText;
    private String name;

    //Firebase
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the App Bar into our Main Activity
        toolbar = (Toolbar) findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Start Here");

        send = findViewById(R.id.sendMessage);
        editText = findViewById(R.id.messageEditText);

        //initialize the recycler view
        recyclerView = findViewById(R.id.main_recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        query = FirebaseDatabase.getInstance().getReference().child("Messages");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        mNotification = FirebaseDatabase.getInstance().getReference().child("notifications");

        firebaseAuth = FirebaseAuth.getInstance();
        addToDatabase();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMessage();
            }
        });
    }

    private void postMessage() {
        final String main_message = editText.getText().toString();

        if (!main_message.isEmpty()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", main_message);
            map.put("sender", name);
            databaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Message saved", Toast.LENGTH_LONG).show();

                        //Setting the edit text field to empty after succesful persist the data into firebase database
                        editText.setText("");
                    } else
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        }else {
            //Retrieve Fname and Lname for posting into the message database
            FirebaseDatabase.getInstance().getReference().child("User_data")
                    .child(firebaseAuth.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User_data user_data = dataSnapshot.getValue(User_data.class);
                            name = user_data.getFirstName() + " " + user_data.getLastName();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    private void addToDatabase() {
        FirebaseRecyclerOptions<Messages> options = new FirebaseRecyclerOptions.Builder<Messages>().setQuery(query, Messages.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Messages, messageHolder>(options) {
            @NonNull
            @Override
            public messageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_layout, parent, false);
                return new messageHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull messageHolder holder, int position, @NonNull Messages model) {
                holder.message.setText(model.getMessage());
                holder.sender.setText(model.getSender());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    //Menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.app_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.exit_button:
                Log.d("EXIT", "Closing...");
                finish();
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
        }

        return true;
    }
}
