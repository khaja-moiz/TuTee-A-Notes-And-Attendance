package com.jpnce.tutee.tutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jpnce.tutee.MainActivity;
import com.jpnce.tutee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class TutorSignin extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login;
    TextView tv_registerBtn;

    TextView tv_forgotPassword;

    LinearLayout ll_loginWithGoogle;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            Toast.makeText(TutorSignin.this, "Already Logged In",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TutorSignin.this, TutorRealDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_tutor_signin);

            et_email = findViewById(R.id.et_email);
            et_password = findViewById(R.id.et_password);
            btn_login = findViewById(R.id.btn_login);
            tv_registerBtn = findViewById(R.id.tv_registerButton);
            tv_forgotPassword = findViewById(R.id.tv_forgotPassword);

            tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TutorSignin.this, TutorResetPasswordActivity.class));
                }
            });

            ll_loginWithGoogle = findViewById(R.id.ll_loginWithGoogle);

            progressDialog = new ProgressDialog(this);

            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performLogin();
                }
            });

            tv_registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TutorSignin.this, TutorSignUp.class));
                }
            });

            ll_loginWithGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TutorSignin.this, TutorSignInWithGoogle.class));
                }
            });

        }
    }

    private void performLogin() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty()) {
            et_email.setError("Please Enter Email");
            return;
        } else if (!pat.matcher(email).matches()) {
            et_email.setError("Please Enter a valid Email");
            return;
        } else if (password.isEmpty()) {
            et_password.setError("Please input Password");
            return;
        } else if (password.length() < 6) {
            et_password.setError("Password too short");
            return;
        } else {
            progressDialog.setMessage("Login in to your Account....");
            progressDialog.setTitle("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToMainActivity(mAuth.getUid(), email, password);
                        Toast.makeText(TutorSignin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(TutorSignin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToMainActivity(String userId, String email, String password) {
        Intent intent = new Intent(TutorSignin.this, TutorRealDashboard.class);
        intent.putExtra("email", email);
        intent.putExtra("userId", userId);
        intent.putExtra("password", password);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        return;
    }
}