package com.example.delacruz.finalandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delacruz.finalandroid.model.Usuario;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.zzv;
import com.google.firebase.auth.zzx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.UUID;

public class PruebaRegUsersActivity extends AppCompatActivity {


    String mColeccion = "Usuario";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText e_correo,e_clave,e_role;
    Button btnreg,btntestrole;
    String codUsu = "";
    String correo,clave;
    String rol ="";
    DatabaseReference colecUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_reg_users);

        btnreg = findViewById(R.id.reg);
        btntestrole = findViewById(R.id.testrole);
        e_correo = findViewById(R.id.correo);
        e_clave = findViewById(R.id.pass);
        e_role = findViewById(R.id.rol);
        initFirebase();

        firebaseAuth = FirebaseAuth.getInstance();
        colecUsuario = FirebaseDatabase.getInstance().getReference("Transportista");


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = e_correo.getText().toString().trim();
                clave = e_clave.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            addDatos();
                            Toast.makeText(PruebaRegUsersActivity.this, "Usuario REGISTRADO", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PruebaRegUsersActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btntestrole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = e_correo.getText().toString().trim();
                buscar();
            }
        });
    }


    public void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    public void addDatos() {
        correo = e_correo.getText().toString().trim();
        clave = e_clave.getText().toString().trim();
        rol = e_role.getText().toString().trim();

        Usuario t = new Usuario();
        t.setCodigo(UUID.randomUUID().toString().trim());
        t.setCorreo(correo.trim());
        t.setClave(Hash.getHash(clave.trim(),"MD5"));
        t.setRol(rol.trim());

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

        Toast.makeText(this,"Lo datos Fueron registrados en la coleccion "
                +mColeccion+" de la base de datos "+databaseReference.getDatabase().toString()+" en Firebase",Toast.LENGTH_LONG).show();

    }


    public void buscar(){

        Query q = databaseReference.child("Usuario");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Usuario u = dataSnapshot1.getValue(Usuario.class);
                    String rol = "admin";
                    if (correo.equals(u.getCorreo())&& !rol.equals(u.getRol())){
                        Toast.makeText(PruebaRegUsersActivity.this,
                                "Codigo: " + u.getCodigo() + " \nestado: " + u.getRol() ,Toast.LENGTH_LONG).show();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
