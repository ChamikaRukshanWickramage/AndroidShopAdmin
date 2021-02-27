package com.solutzoid.codefestshopadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DashboardActivity extends AppCompatActivity {

    Button btn_goListProduct,btn_goViewProduct,btn_log_out;
    private String userDocId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        Bundle bundle = getIntent().getExtras();
         userDocId = bundle.getString("userDocId");

        btn_goListProduct = findViewById(R.id.btn_go_list_product);
        btn_goViewProduct = findViewById(R.id.btn_go_view_product);
        btn_log_out = findViewById(R.id.btn_log_out);

        btn_goListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,HomeActivity.class);
                intent.putExtra("userDocId",userDocId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn_goViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,ViewProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent loginIntent = new Intent(DashboardActivity.this,MainActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(loginIntent);
                        finish();
                    }
                });
        // [END auth_fui_signout]
    }

}