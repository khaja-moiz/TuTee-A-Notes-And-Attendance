package com.jpnce.tutee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jpnce.tutee.tutor.AddNotes;
import com.jpnce.tutee.tutor.TutorRealDashboard;
import com.jpnce.tutee.tutor.TutorSignUp;


public class popupwd extends AppCompatActivity {

    TextView tvhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);
        tvhome = findViewById(R.id.tvhome);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(popupwd.this, AddNotes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);            }
        });

    }

    //back button to previous Activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TutorRealDashboard.class));
        return;
    }
    public void home (View view){
        //back button to previous Activity
        startActivity(new Intent(this, MainActivity.class));
        return;
    }
}