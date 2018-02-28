package com.example.camilo.tic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by camilo on 21/02/18.
 */

//Creado por Camilo Enriquez y Daniela Gonzalez
//Estudiantes de la Universidad Mariana Pasto
//hoenriquez@umariana.edu.co

public class Main2Activity extends AppCompatActivity {


    private static final String TAG = "tutorialcam";
    private FirebaseAuth mAuth;
    private EditText mEmailField,mPasswordField;
    private Button mLoginBtn;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mLoginBtn = (Button) findViewById(R.id.login);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(Main2Activity.this, MainActivity.class));


                } else {
                    Toast.makeText(Main2Activity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }


        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogearUsuario();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }


    private void LogearUsuario(){

        String email =mEmailField.getText().toString();
        String password =mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWhitEmail:onComplete" + task.isSuccessful());

                        // Sign in success, update UI with the signed-in user's information
                        //    Log.d(TAG, "createUserWithEmail:success");
                        //  FirebaseUser user = mAuth.getCurrentUser();

                        if(!task.isSuccessful())
                        {

                            startActivity(new Intent(Main2Activity.this, MainActivity.class));

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Main2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}
