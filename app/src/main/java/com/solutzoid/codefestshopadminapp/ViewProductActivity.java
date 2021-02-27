package com.solutzoid.codefestshopadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.solutzoid.codefestshopadminapp.holder.JobHolder;
import com.solutzoid.codefestshopadminapp.model.Product;
import com.squareup.picasso.Picasso;

public class ViewProductActivity extends AppCompatActivity {

    Button btn_logout;
    EditText searchText;
    ImageButton search_btn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView productLList;
    private CollectionReference productCollectionReference;
    private FirestoreRecyclerAdapter<Product, JobHolder> fsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        productLList = findViewById(R.id.recycle_view_product);
        productLList.setLayoutManager(new LinearLayoutManager(this));

        productCollectionReference = db.collection("Product");



        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(productCollectionReference, Product.class).build();

        fsRecyclerAdapter = new FirestoreRecyclerAdapter<Product,JobHolder>(recyclerOptions) {

            @NonNull
            @Override
            public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
                return new JobHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull JobHolder holder, int position, @NonNull Product model) {
                holder.productNameTxt.setText("Name : "+model.getProductName());
                holder.productPriceTxt.setText("Price : "+model.getProductPrice());

                Picasso.with(ViewProductActivity.this).load(model.getProductImageUrl()).into(holder.productImg);

                holder.product = model;

            }
        };
        productLList.setAdapter(fsRecyclerAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        fsRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fsRecyclerAdapter.startListening();
    }


}