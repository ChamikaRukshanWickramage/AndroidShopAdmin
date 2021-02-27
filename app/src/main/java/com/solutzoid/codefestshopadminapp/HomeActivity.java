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
import com.solutzoid.codefestshopadminapp.model.Product;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText productIdTxt,productNameTxt,productPriceTxt;
    Button list_Button, product_img_btn;
    ImageView productImageView;
    StorageReference storageReference;


    private int product_Pic_Req_Code = 104;

    Uri profileImageUri;
    private String selectedRbText;
    private String userDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        userDocId = bundle.getString("userDocId");

        storageReference = FirebaseStorage.getInstance().getReference();

        productIdTxt = findViewById(R.id.editTextPid);
        productNameTxt = findViewById(R.id.editTextPname);
        productPriceTxt = findViewById(R.id.editTextPprice);

        list_Button = findViewById(R.id.btn_list_product);
        product_img_btn = findViewById(R.id.btn_product_pic);

        productImageView = findViewById(R.id.imageViewProduct);

        product_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Profile Photo"), product_Pic_Req_Code);
            }
        });


        list_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = productIdTxt.getText().toString();
                String name = productNameTxt.getText().toString();
                String price = productPriceTxt.getText().toString();


                Product product =  new Product();
                product.setProductId(id);
                product.setProductName(name);
                product.setProductPrice(price);
                product.setProductState("Buy");
                product.setAdminDocId(userDocId);


                String profileImageUrlPath = "Product_Image_"+id;
                setProductImage(product,profileImageUrlPath);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == product_Pic_Req_Code){
            if(resultCode == RESULT_OK){
                profileImageUri = data.getData();
                Picasso.with(HomeActivity.this).load(profileImageUri).into(productImageView);
            }
        }
    }

    public void setProductImage(Product product, String imgUrl){

        storageReference.child("ProductImage").child(imgUrl).putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(HomeActivity.this, "Product Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Product Image Upload Error", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.child("ProductImage/"+imgUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        product.setProductImageUrl(uri.toString());
                        registerProduct(product);
                    }
                });
            }
        });

    }

    public void registerProduct(Product product) {

        db.collection("Product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String id = documentReference.getId();

                documentReference.update("productDocId",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomeActivity.this, "Listing Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this,ViewProductActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}