package com.example.mobee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class FirebaseUser
{
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;
    private User user;

    private Registration.Doctor doctor;
    private String docName;
    private String docName_2;
    private String password;
    private Context context;

    public interface DInfoCallback
    {
        void onResponse(ArrayList<MainActivity.DInfo> dInfos);
    }

    public FirebaseUser(User user, String docName, String password, Context context) {
        this.user = user;
        this.docName = docName;
        this.password = password;
        this.context = context;
    }

    public FirebaseUser(Registration.Doctor doctor, String docName, Context context) {
        this.doctor = doctor;
        this.docName = docName;
        this.context = context;
    }

    public FirebaseUser(Registration.Doctor doctor, String users, Registration context) {}







    public void doctorFirestoreReg()
    {
        if(doctor.fname.equals("___NULL_DEV___"))
        {
            return;
        }
        database = FirebaseFirestore.getInstance();
        database.collection("users") // specify the collection name here
                .add(doctor)
                // Add a success listener so we can be notified if the operation was successfuly.

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // If we are here, the app successfully connected to Firestore and added a new entry
                        Toast.makeText(context, "Data successfully added to your profile", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
                    }
                })
                // Add a failure listener so we can be notified if something does wrong
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If we are here, the entry could not be added for some reason (e.g no internet connection)
                        Toast.makeText(context, "Data was unable to be added. Check connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getDInfoData(String field, String query, final DInfoCallback callback)
    {
        if(query.equals("___NULL_DEV___"))
        {
            return;
        }
        database = FirebaseFirestore.getInstance();
        database.collection("doctor-data").whereEqualTo(field, query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    ArrayList<Registration.Doctor> doctors = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        doctors.add(doc.toObject(Registration.Doctor.class));
                    }

                    ArrayList<MainActivity.DInfo> mdinfoList = new ArrayList<>();

                    for(Registration.Doctor doc: doctors)
                    {
                        mdinfoList.add(new Registration.DInfo(doc.fname,doc.ID,doc.cell_no,doc.email));
                    }

                    callback.onResponse(mdinfoList);
                }
            }
        });
    }
}
