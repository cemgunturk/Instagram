package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userCommentFromFB;
    ArrayList<String> userImageFromFB;
    ArrayList<String> postDateFromFB;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerViewFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        initialize();
        getDataFromFirestore();

        recyclerViewAdapter = new RecyclerViewAdapter(userEmailFromFB, userCommentFromFB, postDateFromFB, userImageFromFB);
        recyclerViewFeed.setAdapter(recyclerViewAdapter);
    }

    public void getDataFromFirestore(){
        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if (value != null){
                    for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                        Map<String,Object> data = documentSnapshot.getData();
                        String comment = (String) data.get("comment");
                        String userEmail = (String) data.get("userEmail");
                        String downloadUrl = (String) data.get("downloadUrl");
                        String date = (String) data.get("data");
                        userEmailFromFB.add(userEmail);
                        userImageFromFB.add(downloadUrl);
                        postDateFromFB.add(date);
                        userCommentFromFB.add(comment);

                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.postEkle) {
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.cıkısYap) {
            firebaseAuth.signOut();
            Intent intent = new Intent(FeedActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialize() {
        userCommentFromFB = new ArrayList<>();
        userEmailFromFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();
        postDateFromFB = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));
    }
}
