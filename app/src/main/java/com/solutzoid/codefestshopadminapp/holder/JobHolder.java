package com.solutzoid.codefestshopadminapp.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.solutzoid.codefestshopadminapp.FCMHelper.FcmClient;
import com.solutzoid.codefestshopadminapp.R;
import com.solutzoid.codefestshopadminapp.model.Admin;
import com.solutzoid.codefestshopadminapp.model.Product;


public class JobHolder extends RecyclerView.ViewHolder{

    public TextView productNameTxt,productPriceTxt;
    public ImageView productImg;

    public Product product;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public JobHolder(View itemView){
        super(itemView);

        productNameTxt = itemView.findViewById(R.id.t_v_productName);
        productPriceTxt = itemView.findViewById(R.id.t_v_productPrice);

        productImg = itemView.findViewById(R.id.i_v_productImg);


    }
}
