package com.solutzoid.codefestshopadminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.solutzoid.codefestshopadminapp.model.Admin;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nicTxt,nameTxt,emailTxt,mobileTxt,addressTxt;
    Button registerButton, profile_btn;
    ImageView profileImageView;
    StorageReference storageReference;

    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;

    private int Profile_Pic_Req_Code = 105;

    Uri profileImageUri;
    private String selectedRbText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        storageReference = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        String authId = bundle.getString("authId");
        String email = bundle.getString("email");
        String name = bundle.getString("name");

        profileImageView = findViewById(R.id.imageViewProduct);

        radioGroup = findViewById(R.id.radioGroup1);
        nicTxt = findViewById(R.id.editTextPid);
        nameTxt = findViewById(R.id.editTextPname);
        emailTxt = findViewById(R.id.editTextPprice);
        mobileTxt = findViewById(R.id.editTextMobile);
        addressTxt = findViewById(R.id.editTextAddress);

        registerButton = findViewById(R.id.btn_list_product);
        profile_btn = findViewById(R.id.btn_product_pic);

        nameTxt.setText(name);
        emailTxt.setText(email);

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Profile Photo"), Profile_Pic_Req_Code);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nic = nicTxt.getText().toString();
                String name = nameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String mobile = mobileTxt.getText().toString();
                String address = addressTxt.getText().toString();

                // Admin(String authId, String name, String email, String mobile, String address, String userStatus)

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedRadioButton = findViewById(selectedRadioButtonId);
                    selectedRbText = selectedRadioButton.getText().toString();
                }

                Admin admin = new Admin();
                admin.setNic(nic);
                admin.setName(name);
                admin.setEmail(email);
                admin.setGender(selectedRbText);
                admin.setMobile(mobile);
                admin.setAddress(address);
                admin.setUserStatus("1");
                admin.setAuthId(authId);

                String profileImageUrlPath = "admin_image_"+nic;
                setProfileImage(admin,profileImageUrlPath);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Profile_Pic_Req_Code){
            if(resultCode == RESULT_OK){
                profileImageUri = data.getData();
                Picasso.with(RegisterActivity.this).load(profileImageUri).into(profileImageView);
            }
        }

    }

    public void setProfileImage(Admin admin, String imgUrl){

        storageReference.child("AdminImage").child(imgUrl).putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegisterActivity.this, "Admin Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Admin Image Upload Error", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.child("AdminImage/"+imgUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        admin.setImageUrl(uri.toString());
                        registerAdmin(admin);
                    }
                });
            }
        });

    }

    public void registerAdmin(Admin admin) {

        db.collection("Admin").add(admin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String id = documentReference.getId();

                documentReference.update("googleId",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(RegisterActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}