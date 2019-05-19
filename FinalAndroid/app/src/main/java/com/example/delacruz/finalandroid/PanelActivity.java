package com.example.delacruz.finalandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.UUID;

public class PanelActivity extends AppCompatActivity{

    public static final String user="names";
    TextView txtuser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText cod;
    private EditText cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        txtuser =(TextView)findViewById(R.id.txtuser);
        String user = getIntent().getStringExtra("names");
        txtuser.setText("¡Bienvenido "+ user +"!");

        cod = (EditText)findViewById(R.id.txtcod);
        cliente = (EditText)findViewById(R.id.txtcliente);
        initFirebase();
        //ListaPaquetes();
    }

    public void initFirebase() {
        Context context = getApplicationContext();
        FirebaseApp.initializeApp(context);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void AddDatos(View view) {
        String mColeccion = "Transportista";
        String uid = UUID.randomUUID().toString();
        HashMap<String, String> dataMap= new HashMap<String, String>();
        String mCod = cod.getText().toString();
        String mCliente = cliente.getText().toString();

        dataMap.put("cod", mCod);
        dataMap.put("cliente", mCliente);
        databaseReference.child(mColeccion).child(uid).setValue(dataMap);

        Toast.makeText(this,"El Codigo y el nombre "+ mCod + mCliente +" Fue registrado en la coleccion"
        +mColeccion+"de la base de datos "+databaseReference.getDatabase().toString()+" en Firebase",Toast.LENGTH_LONG).show();


    }
/*
    private void ListaPaquetes() {

        Intent intencion = new Intent(getApplication(), RecyclerActivity.class);
        startActivity(intencion);

    }*/
}
