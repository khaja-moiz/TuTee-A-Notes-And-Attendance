package com.jpnce.tutee.tutor;

//270922ADDED

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//e
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpnce.tutee.Model.NotesModel;
import com.jpnce.tutee.R;
import com.jpnce.tutee.adapter.viewNotesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity {

    ArrayList<NotesModel> arrayList = new ArrayList<>();
    viewNotesAdapter adapter;
    RecyclerView rv_viewnotes;
    Intent intent;
    String childName = "";
    RecyclerView view;//270922ADDED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        view = findViewById(R.id.rv_viewnotes);//2709222ADDED

        rv_viewnotes = findViewById(R.id.rv_viewnotes);
        rv_viewnotes.setHasFixedSize(true);
        rv_viewnotes.setLayoutManager(new LinearLayoutManager(ViewNotes.this));

        intent = getIntent();
        childName = intent.getStringExtra("childName");
        Toast.makeText(ViewNotes.this, "child name : " + childName, Toast.LENGTH_SHORT).show();
        loadNotes();

        //270922added
        // After clicking here alert box will come
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CharSequence options[] = new CharSequence[]{
                        "Download",
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we will be downloading the pdf
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(childName));
                            startActivity(intent);
                        }
                        // We will view the pdf
                        if (which == 1) {
                            Intent intent = new Intent(v.getContext(), ViewPdfActivity.class);
                            intent.putExtra("url", childName);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });//eneded Here
    }

    private void loadNotes() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notes").child(firebaseUser.getUid());
        Log.d("TAG1", "loadNotes: " + reference.getKey());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NotesModel model = dataSnapshot.getValue(NotesModel.class);
                    Log.d("TAG1", "onDataChange: " + model);
                    arrayList.add(model);
                }
                Log.d("TAG1", "arraylist size : " + arrayList.size());
                adapter = new viewNotesAdapter(ViewNotes.this, arrayList ,childName);
                rv_viewnotes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // we are showing that error message in toast
                Toast.makeText(ViewNotes.this, "Error Loading Notes", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //back button to previous Activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TutorRealDashboard.class));
    }
}