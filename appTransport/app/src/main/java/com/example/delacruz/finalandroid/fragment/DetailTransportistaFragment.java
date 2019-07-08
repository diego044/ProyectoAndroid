package com.example.delacruz.finalandroid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Transportista;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class DetailTransportistaFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText detailNom, detailAp, detailDoc, detailNroDoc, detailDirec, detailVehic;
    String transport_id;
    String mColeccion = "Transportista";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_transportista, container, false);





        detailNom = (EditText)rootView.findViewById(R.id.detail_nom);
         detailAp = (EditText)rootView.findViewById(R.id.detail_ap);
         detailDoc = (EditText)rootView.findViewById(R.id.detail_doc);
         detailNroDoc = (EditText)rootView.findViewById(R.id.detail_nro_doc);
         detailDirec = (EditText)rootView.findViewById(R.id.detail_direc);
         detailVehic = (EditText)rootView.findViewById(R.id.detail_vehic);

        ImageView imagen_1 = (ImageView) rootView.findViewById(R.id.imagen_1);
        Picasso.with(getContext()).load(R.drawable.transportista).into(imagen_1);

        Toolbar toolbar =  rootView.findViewById(R.id.toolbar_detail);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        transport_id =  this.getArguments().getString("transport_id");
        String transport_nom =  this.getArguments().getString("transport_nom");
        String transport_ap =  this.getArguments().getString("transport_ap");
        String transport_direc =  this.getArguments().getString("transport_direc");
        String transport_nro_doc =  this.getArguments().getString("transport_nro_doc");
        String transport_vehic =  this.getArguments().getString("transport_vehic");
        String transport_doc =  this.getArguments().getString("transport_doc");

        detailNom.setText(transport_nom);
        detailAp.setText(transport_ap);
        detailDoc.setText(transport_doc);
        detailNroDoc.setText(transport_nro_doc);
        detailDirec.setText(transport_direc);
        detailVehic.setText(transport_vehic);

        Toast.makeText(getContext()," Transportista: " + transport_nom + " " + transport_ap,Toast.LENGTH_SHORT).show();


        initFirebase();
        Button fab = (Button) rootView.findViewById(R.id.btnupdtransp);

        if (!getArguments().getString("roluser").equals("admin")){
            fab.setEnabled(false);
        }

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
                                InicioFragment fr=new InicioFragment();
                                Bundle b = new Bundle();

                                b.putString("roluser",getArguments().getString("roluser"));
                                fr.setArguments(b);
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

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab);

        if (!getArguments().getString("roluser").equals("admin")){
            fab1.hide();
        }


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                    alertDialogBuilder.setTitle("Confirmar");

                    alertDialogBuilder
                            .setMessage("¿Eliminar transportista?")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    delDatos();
                                    InicioFragment fr=new InicioFragment();
                                    Bundle b = new Bundle();

                                    b.putString("roluser",getArguments().getString("roluser"));
                                    fr.setArguments(b);
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

        Transportista t = new Transportista();
        t.setCodigo(transport_id);
        t.setNombre(detailNom.getText().toString().trim());
        t.setApellido(detailAp.getText().toString().trim());
        t.setDocumento(detailDoc.getText().toString().trim());
        t.setN_documento(detailNroDoc.getText().toString().trim());
        t.setDireccion(detailDirec.getText().toString().trim());
        t.setVehiculo(detailVehic.getText().toString().trim());

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

        Toast.makeText(getContext(),"Actualizando. . .",Toast.LENGTH_SHORT).show();

    }





    public void delDatos(){

        databaseReference.child(mColeccion).child(transport_id).removeValue();

        Toast.makeText(getContext(),"Eliminando. . .",Toast.LENGTH_SHORT).show();
    }



}
