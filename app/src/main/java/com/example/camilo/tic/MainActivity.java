package com.example.camilo.tic;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.camilo.tic.objetos.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by camilo on 21/02/18.
 */

//Creado por Camilo Enriquez y Daniela Gonzalez
//Estudiantes de la Universidad Mariana Pasto
//hoenriquez@umariana.edu.co

public class MainActivity extends AppCompatActivity {
    String datosFirebase = "";
    String datosFirebaseCopia = "";
    private Button mLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogoutBtn =(Button) findViewById(R.id.logout);


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.TUTORIAL_REFERENCE);
        myRef.addValueEventListener(new ValueEventListener() {
      @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                //Guardo en un HashMap todos los datos de la base de datos valgase la redundancia de Firebase.
                datosFirebase = String.valueOf(dataSnapshot.getValue());

                if (datosFirebase == null) {
                    datosFirebase = "";
                }

                //Si el clon esta vacio (Obviamente estara vacio la primera vez) haz una copia de lo primero que ha cogido de la base de datos.
                if (datosFirebaseCopia.isEmpty()) {

                    datosFirebaseCopia = datosFirebase;

                }
                //Si ambas son iguales, nada ha cambiado ( La primera vez obviamente nada ha cambiado)
                if (datosFirebase.equals(datosFirebaseCopia)) {
                    Log.d("DATOS", "NO HAY CAMBIOS.");
                }
                //Pero si la segunda vez que cheque si son iguales y no lo son, es que la base de datos ha cambiado
                else {
                    //Ahora si, echame la notificaciooon!
                    //Notificación , nada sorprendente.
                    NotificationCompat.Builder mBuilder;
                    NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                    int icono = R.mipmap.ic_launcher;
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

                    mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(icono)
                            .setContentTitle("SecretariaTicCamilo")
                    //Log.d("DATOS", datosFirebaseCopia+"");
                            .setContentText("Tu estado se a cambiado a" + " " +datosFirebase)
                            //.setVibrate(new long[]{100, 250, 100, 500})
                            .setAutoCancel(true);

                    mNotifyMgr.notify(1, mBuilder.build());
                    //-------------Fin del codigo notificacion.

                    datosFirebaseCopia = datosFirebase;
                }

                //Los valores de la base de datos, digo, por si los quieres ver , ¿Para que? , pues para nada, te ayuda a comprender un poco que se esta haciendo viendo algo graficamente, yo digo, ¿No? .-. .
                Log.d("TEST", "Value is: " + datosFirebase);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TEST", "No funciono", error.toException());
            }
        });

    }
}




