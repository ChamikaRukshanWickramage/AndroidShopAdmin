package com.solutzoid.codefestshopadminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.solutzoid.codefestshopadminapp.model.Admin;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SIGN_IN_RQC = 104;
    private static final String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SignInButton signInButton;
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFCM();

        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSignIn();
            }
        });
    }

    private void initFCM() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        fcmToken = task.getResult();
                        Log.d(TAG, "Token  : "+fcmToken.toString());
                    }
                });
    }

    private void createSignIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                SIGN_IN_RQC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_RQC){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                String email = user.getEmail();
                String name = user.getDisplayName();
                String auth_id = user.getUid();

                db.collection("Admin").whereEqualTo("email",email).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();

                                if(documentSnapshots.size() > 0){
                                    DocumentSnapshot document = documentSnapshots.get(0);
                                    String admin_Id = document.getId();
                                    Admin admin = document.toObject(Admin.class);

                                    if(admin.getGoogleId() == null ){
                                        DocumentReference documentReference = db.collection("Admin").document(admin_Id);
                                        documentReference.update("googleId",admin_Id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainActivity.this, "Google Id & FCM ID Update Complete", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, "Update Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }

                                    updateFcmToken(admin_Id);
                                    Toast.makeText(MainActivity.this, "Please register Now", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                                    intent.putExtra("userDocId",admin.getGoogleId());
                                    intent.putExtra("authId",auth_id);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",name);
                                    intent.putExtra("image_url",admin.getImageUrl());
                                    intent.putExtra("mobile",admin.getMobile());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(MainActivity.this, "Sign In Complete", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                                    intent.putExtra("authId",auth_id);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",name);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });


            }else {
                Toast.makeText(this, "Sign In abort user", Toast.LENGTH_SHORT).show();
            }
        }
    } // // onActivityResult close


    private void updateFcmToken(String admin_Id) {
        Log.d(TAG,"Admin id : "+admin_Id);
        db.collection("Admin").document(admin_Id).update("fcmId",fcmToken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"FCM Token Update");
            }
        });
    }

}