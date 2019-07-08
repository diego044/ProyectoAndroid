package com.example.delacruz.finalandroid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.delacruz.finalandroid.Hash;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Transportista;
import com.example.delacruz.finalandroid.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Pattern;


public class RegUsuarioFragment extends Fragment {

    String mColeccion = "Usuario";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private EditText e_correo, e_clave;
    String correo,clave;
    Spinner rol;
    String rolName;
    String estado = "ACTIVO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reg_user, container, false);

        e_correo = (EditText) rootView.findViewById(R.id.txtcorreo);
        e_clave = (EditText) rootView.findViewById(R.id.txtclave);
        rol = (Spinner) rootView.findViewById(R.id.rol);

        initFirebase();

        firebaseAuth = FirebaseAuth.getInstance();

        Button fab = (Button) rootView.findViewById(R.id.btnregtransp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validarEmail(e_correo.getText().toString())) {

                    e_correo.setError("Correo no valido");
                    return;
                }

                if (e_clave.length() < 6) {
                    e_clave.setError("Contraseña debe ser mayor a 6 caractéres");
                    return;
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirmar");
                alertDialogBuilder
                        .setMessage("¿Registrar usuario?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                correo = e_correo.getText().toString().trim();
                                clave = e_clave.getText().toString().trim();
                                rolName = rol.getItemAtPosition(rol.getSelectedItemPosition()).toString().toLowerCase();


                                firebaseAuth.createUserWithEmailAndPassword(correo, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            addDatos();
                                            Toast.makeText(getContext(),"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
            });


        return rootView;
    }

    public void initFirebase() {
        Context context = getContext();
        FirebaseApp.initializeApp(context);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void addDatos() {

        Usuario t = new Usuario();
        t.setCodigo(UUID.randomUUID().toString().trim());
        t.setCorreo(correo.trim());
        t.setClave(Hash.getHash(clave.trim(),"MD5"));
        t.setEstado(estado);
        t.setRol(rolName);

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


}
