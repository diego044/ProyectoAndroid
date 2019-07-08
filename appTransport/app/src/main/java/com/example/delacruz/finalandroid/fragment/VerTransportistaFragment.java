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
import com.example.delacruz.finalandroid.model.Transportista;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class VerTransportistaFragment extends Fragment {

    RecyclerView rv;
    DatabaseReference myRef;
    List<Transportista> Paquetes;
    PaqueteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_ver_transportista, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegTransportistaFragment fr=new RegTransportistaFragment();
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

        myRef = database.getReference("Transportista");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Paquetes = new ArrayList<Transportista>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    Transportista value = dataSnapshot1.getValue(Transportista.class);

                    Transportista tran = new Transportista();
                    tran.setCodigo(value.getCodigo());
                    tran.setNombre(value.getNombre());
                    tran.setApellido(value.getApellido());
                    tran.setDocumento(value.getDocumento());
                    tran.setN_documento(value.getN_documento());
                    tran.setDireccion(value.getDireccion());
                    tran.setVehiculo(value.getVehiculo());
                    Paquetes.add(tran);

                }


                PaqueteAdapter recyclerAdapter = new PaqueteAdapter(Paquetes,rootView.getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(rootView.getContext(),2);
                rv.setAdapter(recyclerAdapter);

                recyclerAdapter.setOnclickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //getId from Items sent to RecyclerView
                        Transportista t = new Transportista();
                        t.setCodigo(Paquetes.get(rv.getChildAdapterPosition(view)).getCodigo());
                        t.setNombre(Paquetes.get(rv.getChildAdapterPosition(view)).getNombre());
                        t.setApellido(Paquetes.get(rv.getChildAdapterPosition(view)).getApellido());
                        t.setDocumento(Paquetes.get(rv.getChildAdapterPosition(view)).getDocumento());
                        t.setN_documento(Paquetes.get(rv.getChildAdapterPosition(view)).getN_documento());
                        t.setDireccion(Paquetes.get(rv.getChildAdapterPosition(view)).getDireccion());
                        t.setVehiculo(Paquetes.get(rv.getChildAdapterPosition(view)).getVehiculo());

                        DetailTransportistaFragment fr=new DetailTransportistaFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("transport_id",t.getCodigo());
                        bundle.putString("transport_nom",t.getNombre());
                        bundle.putString("transport_ap",t.getApellido());
                        bundle.putString("transport_doc",t.getDocumento());
                        bundle.putString("transport_nro_doc",t.getN_documento());
                        bundle.putString("transport_direc",t.getDireccion());
                        bundle.putString("transport_vehic",t.getVehiculo());
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
