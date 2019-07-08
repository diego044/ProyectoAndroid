package com.example.delacruz.finalandroid.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Destinatario;
import com.example.delacruz.finalandroid.model.Envios;
import com.example.delacruz.finalandroid.model.Remitente;
import com.example.delacruz.finalandroid.model.Transportista;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class DetailEnvioFragment extends Fragment {

    String mColeccion = "Envios";
    private ArrayList<String> transportistas = new ArrayList<String>();
    private ArrayAdapter<String> dataAdapter;
    Spinner transportista;
    Spinner destinatario;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button bfecha;
    private EditText n_envio, fecha, peso, descripcion;
    Spinner remitente;
    private int dia,mes,ano;
    String envio_id="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_envio, container, false);

        n_envio = (EditText) rootView.findViewById(R.id.txtnenvio);
        fecha = (EditText) rootView.findViewById(R.id.detail_fecha);
        peso = (EditText) rootView.findViewById(R.id.detail_peso);
        descripcion = (EditText) rootView.findViewById(R.id.txtdescripcion);
        transportista = (Spinner) rootView.findViewById(R.id.txttransportista);
        remitente = (Spinner) rootView.findViewById(R.id.txtremitente);
        destinatario = (Spinner) rootView.findViewById(R.id.txtdestinatario);
        bfecha = (Button)rootView.findViewById(R.id.btnfecha);
        descripcion.setImeOptions(EditorInfo.IME_ACTION_DONE);
        descripcion.setRawInputType(InputType.TYPE_CLASS_TEXT);

        ImageView imagen_1 = (ImageView) rootView.findViewById(R.id.imagen_1);
        Picasso.with(getContext()).load(R.drawable.paquete).into(imagen_1);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_detail);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initFirebase();


        final String envio_tra = this.getArguments().getString("envio_tra");
        final String envio_rem = this.getArguments().getString("envio_rem");
        final String envio_dest = this.getArguments().getString("envio_dest");


        Query q = databaseReference.child("Transportista");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> transportistas = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transportista t = snapshot.getValue(Transportista.class);
                    transportistas.add(t.getNombre());

                }
                if (getContext()!=null) {
                    ArrayAdapter<String> transportistaArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, transportistas);
                    transportistaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    transportista.setAdapter(transportistaArrayAdapter);
                    selectSpinnerValue(transportista,envio_tra);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Query q1 = databaseReference.child("Remitente");
        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> remitentes = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Remitente t = snapshot.getValue(Remitente.class);
                    remitentes.add(t.getNombre());

                }
                if (getContext()!=null) {
                    ArrayAdapter<String> remitenteArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, remitentes);
                    remitenteArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    remitente.setAdapter(remitenteArrayAdapter);
                    selectSpinnerValue(remitente,envio_rem);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Query q2 = databaseReference.child("Destinatario");
        q2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> destinatarios = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Destinatario t = snapshot.getValue(Destinatario.class);
                    destinatarios.add(t.getNombre());

                }
                if (getContext()!=null) {
                    ArrayAdapter<String> destinatarioArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, destinatarios);
                    destinatarioArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    destinatario.setAdapter(destinatarioArrayAdapter);
                    selectSpinnerValue(destinatario,envio_dest);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        envio_id = this.getArguments().getString("envio_id");
        String envio_num = this.getArguments().getString("envio_env");

        String envio_fec = this.getArguments().getString("envio_fec");
        String envio_pes = this.getArguments().getString("envio_pes");
        String envio_des = this.getArguments().getString("envio_des");

        n_envio.setText(envio_num);



        fecha.setText(envio_fec);
        peso.setText(envio_pes);
        descripcion.setText(envio_des);

        Toast.makeText(getContext(), "Envios: " + envio_rem + " // " + envio_dest+ " // " + envio_tra, Toast.LENGTH_SHORT).show();


        bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==bfecha){
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    ano=c.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    } ,ano,mes,dia);
                    datePickerDialog.show();
                }
            }
        });

        Button fab = (Button) rootView.findViewById(R.id.btnupdtransp);

        if (!getArguments().getString("roluser").equals("admin")){
            fab.setEnabled(false);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            public void onClick(View v) {
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


    private void initFirebase() {
        Context context = getContext();
        FirebaseApp.initializeApp(context);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void updDatos() {

        Envios e = new Envios();
        e.setCodigo(envio_id);
        e.setN_envio(n_envio.getText().toString().trim());
        e.setTransportista(transportista.getSelectedItem().toString().trim());
        e.setRemitente(remitente.getSelectedItem().toString().trim());
        e.setDestinatario(destinatario.getSelectedItem().toString().trim());
        e.setFecha(fecha.getText().toString().trim());
        e.setPeso(peso.getText().toString().trim());
        e.setDescripcion(descripcion.getText().toString().trim());

        databaseReference.child(mColeccion).child(e.getCodigo()).setValue(e);

        Toast.makeText(getContext(),"Actualizando. . .",Toast.LENGTH_SHORT).show();
    }

    public void delDatos() {
        databaseReference.child(mColeccion).child(envio_id).removeValue();
        Toast.makeText(getContext(),"Eliminando. . .",Toast.LENGTH_SHORT).show();
    }


    private void selectSpinnerValue(Spinner spinner, String myString) {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

}
