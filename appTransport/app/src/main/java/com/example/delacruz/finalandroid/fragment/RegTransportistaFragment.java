package com.example.delacruz.finalandroid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Transportista;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class RegTransportistaFragment extends Fragment {

    String mColeccion = "Transportista";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText nombre, apellido, documento, n_documento, direccion, vehiculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reg_transportista, container, false);

        nombre = (EditText) rootView.findViewById(R.id.txtnombre);
        apellido = (EditText) rootView.findViewById(R.id.txtapellido);
        documento = (EditText) rootView.findViewById(R.id.txtdocumento);
        n_documento = (EditText) rootView.findViewById(R.id.txtndocumento);
        direccion = (EditText) rootView.findViewById(R.id.txtdireccion);
        vehiculo = (EditText) rootView.findViewById(R.id.txtvehiculo);

        initFirebase();

        Button fab = (Button) rootView.findViewById(R.id.btnregtransp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirmar");
                alertDialogBuilder
                        .setMessage("¿Registrar transportista?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                addDatos();

                            }
                        })
                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
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

        Transportista t = new Transportista();
        t.setCodigo(UUID.randomUUID().toString().trim());
        t.setNombre(nombre.getText().toString().trim());
        t.setApellido(apellido.getText().toString().trim());
        t.setDocumento(documento.getText().toString().trim());
        t.setN_documento(n_documento.getText().toString().trim());
        t.setDireccion(direccion.getText().toString().trim());
        t.setVehiculo(vehiculo.getText().toString().trim());

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

        Toast.makeText(getContext(),"Registrando. . .",Toast.LENGTH_SHORT).show();

    }



}
