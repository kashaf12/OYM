package com.kfstudio.www.oym;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private BookingAdapter bookingAdapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference dbr = db.collection("Booking");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        Query query = dbr.orderBy("booking_time", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Booking> options = new FirestoreRecyclerOptions.Builder<Booking>()
                .setQuery(query, Booking.class).build();
        bookingAdapter = new BookingAdapter(options,currentUser.getPhoneNumber());
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookingAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        bookingAdapter.startListening();
    }
    @Override
    protected void onStart(){
        super.onStart();
        bookingAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        bookingAdapter.stopListening();
    }
    @Override
    protected void onPause(){
        super.onPause();
        bookingAdapter.stopListening();
    }
}
