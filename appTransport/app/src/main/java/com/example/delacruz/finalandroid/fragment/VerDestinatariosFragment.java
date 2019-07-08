package com.example.delacruz.finalandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.SideActivity;
import com.example.delacruz.finalandroid.adapter.DestinatarioAdapter;
import com.example.delacruz.finalandroid.adapter.PaqueteAdapter;
import com.example.delacruz.finalandroid.model.Destinatario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class VerDestinatariosFragment extends Fragment {


    RecyclerView rv;
    DatabaseReference myRef;
    List<Destinatario> Destinatarios;
    PaqueteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ver_destinatario, container, false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegDestinatarioFragment fr=new RegDestinatarioFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        if (!getArguments().getString("roluser").equals("admin")){
            fab.hide();
        }

        rv = (RecyclerView)rootView.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Destinatario");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Destinatarios = new ArrayList<Destinatario>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    Destinatario value = dataSnapshot1.getValue(Destinatario.class);
                    Destinatario dest = new Destinatario();

                    dest.setCodigo(value.getCodigo());
                    dest.setNombre(value.getNombre());
                    dest.setApellido(value.getApellido());
                    dest.setDocumento(value.getDocumento());
                    dest.setN_documento(value.getN_documento());
                    dest.setDireccion(value.getDireccion());
                    dest.setCelular(value.getCelular());
                    dest.setCorreo(value.getCorreo());
                    Destinatarios.add(dest);

                }

                DestinatarioAdapter recyclerAdapter = new DestinatarioAdapter(Destinatarios,rootView.getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(rootView.getContext(),2);
                rv.setAdapter(recyclerAdapter);

                recyclerAdapter.setOnclickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //getId from Items sent to RecyclerView
                        Destinatario t = new Destinatario();
                        t.setCodigo(Destinatarios.get(rv.getChildAdapterPosition(view)).getCodigo());
                        t.setNombre(Destinatarios.get(rv.getChildAdapterPosition(view)).getNombre());
                        t.setApellido(Destinatarios.get(rv.getChildAdapterPosition(view)).getApellido());
                        t.setDocumento(Destinatarios.get(rv.getChildAdapterPosition(view)).getDocumento());
                        t.setN_documento(Destinatarios.get(rv.getChildAdapterPosition(view)).getN_documento());
                        t.setDireccion(Destinatarios.get(rv.getChildAdapterPosition(view)).getDireccion());
                        t.setCelular(Destinatarios.get(rv.getChildAdapterPosition(view)).getCelular());
                        t.setCorreo(Destinatarios.get(rv.getChildAdapterPosition(view)).getCorreo());

                        DetailDestinatarioFragment fr=new DetailDestinatarioFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("dest_id",t.getCodigo());
                        bundle.putString("dest_nom",t.getNombre());
                        bundle.putString("dest_ap",t.getApellido());
                        bundle.putString("dest_doc",t.getDocumento());
                        bundle.putString("dest_nro_doc",t.getN_documento());
                        bundle.putString("dest_direc",t.getDireccion());
                        bundle.putString("dest_cell",t.getCelular());
                        bundle.putString("dest_correo",t.getCorreo());
                        bundle.putString("roluser",getArguments().getString("roluser"));
                        fr.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedor,fr)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value.", databaseError.toException());

            }
        });



        return rootView;
    }

}
