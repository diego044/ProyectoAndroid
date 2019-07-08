package com.example.delacruz.finalandroid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.delacruz.finalandroid.Hash;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Transportista;
import com.example.delacruz.finalandroid.model.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class DetailUsuarioFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText detailCorreo, detailEstado;
    Spinner rol;
    String rolName;
    String estado = "ACTIVO";
    String user_id;
    String mColeccion = "Usuario";


    String user_correo;
    String user_rol;
    String user_estado;
    String user_clave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_user, container, false);

         detailCorreo = (EditText)rootView.findViewById(R.id.txtcorreo);
         detailEstado = (EditText)rootView.findViewById(R.id.estado);
         rol = (Spinner) rootView.findViewById(R.id.rol);

        ImageView imagen_1 = (ImageView) rootView.findViewById(R.id.imagen_1);
        Picasso.with(getContext()).load(R.drawable.usersidebar).into(imagen_1);

        Toolbar toolbar =  rootView.findViewById(R.id.toolbar_detail);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        user_id =  this.getArguments().getString("user_id");
        user_correo =  this.getArguments().getString("user_correo");
        user_rol =  this.getArguments().getString("user_rol");
        user_estado =  this.getArguments().getString("user_estado");
        user_clave =  this.getArguments().getString("user_clave");

        detailCorreo.setText(user_correo);
        detailEstado.setText(user_estado);

        initFirebase();
        Button fab = (Button) rootView.findViewById(R.id.btnupdtransp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                alertDialogBuilder.setTitle("Confirmar");

                alertDialogBuilder
                        .setMessage("¿Actualizar datos?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                updDatos();
                                InicioUsuariosFragment fr=new InicioUsuariosFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.contenedor,fr)
                                        .addToBackStack(null)
                                        .commit();

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


    public void updDatos() {

        Usuario t = new Usuario();
        t.setCodigo(user_id);
        t.setCorreo(user_correo);
        t.setClave(user_clave);
        t.setRol(rol.getItemAtPosition(rol.getSelectedItemPosition()).toString());
        t.setEstado(user_estado);

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

        Toast.makeText(getContext(),"Actualizando. . .",Toast.LENGTH_SHORT).show();

    }


}
