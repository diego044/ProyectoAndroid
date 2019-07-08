package com.example.delacruz.finalandroid.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.SideActivity;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RegEnviosFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_reg_envios, container, false);
        //dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (List<String>) transportista);

        n_envio = (EditText) rootView.findViewById(R.id.txtnenvio);
        fecha = (EditText) rootView.findViewById(R.id.txtfecha);
        peso = (EditText) rootView.findViewById(R.id.txtpeso);
        descripcion = (EditText) rootView.findViewById(R.id.txtdescripcion);
        transportista = (Spinner) rootView.findViewById(R.id.txttransportista);
        remitente = (Spinner) rootView.findViewById(R.id.txtremitente);
        destinatario = (Spinner) rootView.findViewById(R.id.txtdestinatario);
        bfecha = (Button)rootView.findViewById(R.id.btnfecha);
        descripcion.setImeOptions(EditorInfo.IME_ACTION_DONE);
        descripcion.setRawInputType(InputType.TYPE_CLASS_TEXT);

        initFirebase();
        Button fab = (Button) rootView.findViewById(R.id.btnregtransp);

        //databaseReference = FirebaseDatabase.getInstance().getReference();

        Query q0 = databaseReference.child("Envios");
        q0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cont=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cont++;

                }
                cont++;
                if (getContext()!=null) {
                    n_envio.setText("" + cont);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirmar");
                alertDialogBuilder
                        .setMessage("¿Registrar Envio?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addDatos();

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

        Envios e = new Envios();
        e.setCodigo(UUID.randomUUID().toString().trim());
        e.setN_envio(n_envio.getText().toString().trim());

        e.setTransportista(transportista.getSelectedItem().toString().trim());
        e.setDestinatario(destinatario.getSelectedItem().toString().trim());
        e.setRemitente(remitente.getSelectedItem().toString().trim());
        e.setFecha(fecha.getText().toString().trim());
        e.setPeso(peso.getText().toString().trim());
        e.setDescripcion(descripcion.getText().toString().trim());

        databaseReference.child(mColeccion).child(e.getCodigo()).setValue(e);
        Toast.makeText(getContext(),"Registrando. . .",Toast.LENGTH_SHORT).show();
    }



}
