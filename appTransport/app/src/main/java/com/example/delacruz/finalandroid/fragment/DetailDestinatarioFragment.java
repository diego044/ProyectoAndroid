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
import android.widget.Toast;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Destinatario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class DetailDestinatarioFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText detailNom, detailAp, detailDoc, detailNroDoc, detailDirec, detailCell, detailCorreo;
    String destinatario_id;
    String mColeccion = "Destinatario";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_destinatario, container, false);

             detailNom = (EditText)rootView.findViewById(R.id.detail_nom);
             detailAp = (EditText)rootView.findViewById(R.id.detail_ap);
             detailDoc = (EditText)rootView.findViewById(R.id.detail_doc);
             detailNroDoc = (EditText)rootView.findViewById(R.id.detail_nro_doc);
             detailDirec = (EditText)rootView.findViewById(R.id.detail_direc);
             detailCell = (EditText)rootView.findViewById(R.id.detail_celular);
             detailCorreo = (EditText)rootView.findViewById(R.id.detail_correo);

        ImageView imagen_1 = (ImageView) rootView.findViewById(R.id.imagen_1);
        Picasso.with(getContext()).load(R.drawable.destinatario).into(imagen_1);

        Toolbar toolbar =  rootView.findViewById(R.id.toolbar_detail);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        destinatario_id =  this.getArguments().getString("dest_id");
        String remitente_nom =  this.getArguments().getString("dest_nom");
        String remitente_ap =  this.getArguments().getString("dest_ap");
        String remitente_direc =  this.getArguments().getString("dest_direc");
        String remitente_nro_doc =  this.getArguments().getString("dest_nro_doc");
        String remitente_doc =  this.getArguments().getString("dest_doc");
        String remitente_correo =  this.getArguments().getString("dest_correo");
        String remitente_cell =  this.getArguments().getString("dest_cell");

        detailNom.setText(remitente_nom);
        detailAp.setText(remitente_ap);
        detailDoc.setText(remitente_doc);
        detailNroDoc.setText(remitente_nro_doc);
        detailDirec.setText(remitente_direc);
        detailCell.setText(remitente_cell);
        detailCorreo.setText(remitente_correo);

        Toast.makeText(getContext()," Destinatario: " + remitente_nom + " " + remitente_ap,Toast.LENGTH_SHORT).show();


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
                            .setMessage("¿Eliminar destinatario?")
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

        Destinatario t = new Destinatario();
        t.setCodigo(destinatario_id);
        t.setNombre(detailNom.getText().toString().trim());
        t.setApellido(detailAp.getText().toString().trim());
        t.setDocumento(detailDoc.getText().toString().trim());
        t.setN_documento(detailNroDoc.getText().toString().trim());
        t.setDireccion(detailDirec.getText().toString().trim());
        t.setCelular(detailCell.getText().toString().trim());
        t.setCorreo(detailCorreo.getText().toString().trim());

        databaseReference.child(mColeccion).child(t.getCodigo()).setValue(t);

        Toast.makeText(getContext(),"Actualizando. . .",Toast.LENGTH_SHORT).show();

    }





    public void delDatos(){

        databaseReference.child(mColeccion).child(destinatario_id).removeValue();

        Toast.makeText(getContext(),"Eliminando. . .",Toast.LENGTH_SHORT).show();
    }



}
