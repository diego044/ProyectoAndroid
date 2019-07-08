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
import com.example.delacruz.finalandroid.adapter.PaqueteAdapter;
import com.example.delacruz.finalandroid.adapter.RemitenteAdapter;
import com.example.delacruz.finalandroid.model.Remitente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class VerRemitentesFragment extends Fragment {

    RecyclerView rv;
    DatabaseReference myRef;
    List<Remitente> Remitentes;
    PaqueteAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ver_remitente, container, false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegRemitenteFragment fr=new RegRemitenteFragment();
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

        myRef = database.getReference("Remitente");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Remitentes = new ArrayList<Remitente>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    Remitente value = dataSnapshot1.getValue(Remitente.class);

                    Remitente remi = new Remitente();
                    remi.setCodigo(value.getCodigo());
                    remi.setNombre(value.getNombre());
                    remi.setApellido(value.getApellido());
                    remi.setDocumento(value.getDocumento());
                    remi.setN_documento(value.getN_documento());
                    remi.setDireccion(value.getDireccion());
                    remi.setCelular(value.getCelular());
                    remi.setCorreo(value.getCorreo());
                    Remitentes.add(remi);

                }

                RemitenteAdapter recyclerAdapter = new RemitenteAdapter(Remitentes,rootView.getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(rootView.getContext(),2);
                rv.setAdapter(recyclerAdapter);

                recyclerAdapter.setOnclickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //getId from Items sent to RecyclerView
                        Remitente t = new Remitente();
                        t.setCodigo(Remitentes.get(rv.getChildAdapterPosition(view)).getCodigo());
                        t.setNombre(Remitentes.get(rv.getChildAdapterPosition(view)).getNombre());
                        t.setApellido(Remitentes.get(rv.getChildAdapterPosition(view)).getApellido());
                        t.setDocumento(Remitentes.get(rv.getChildAdapterPosition(view)).getDocumento());
                        t.setN_documento(Remitentes.get(rv.getChildAdapterPosition(view)).getN_documento());
                        t.setDireccion(Remitentes.get(rv.getChildAdapterPosition(view)).getDireccion());
                        t.setCelular(Remitentes.get(rv.getChildAdapterPosition(view)).getCelular());
                        t.setCorreo(Remitentes.get(rv.getChildAdapterPosition(view)).getCorreo());

                        DetailRemitenteFragment fr=new DetailRemitenteFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("remit_id",t.getCodigo());
                        bundle.putString("remit_nom",t.getNombre());
                        bundle.putString("remit_ap",t.getApellido());
                        bundle.putString("remit_doc",t.getDocumento());
                        bundle.putString("remit_nro_doc",t.getN_documento());
                        bundle.putString("remit_direc",t.getDireccion());
                        bundle.putString("remit_cell",t.getCelular());
                        bundle.putString("remit_correo",t.getCorreo());
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
