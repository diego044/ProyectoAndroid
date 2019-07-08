package com.example.delacruz.finalandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.adapter.DestinatarioAdapter;
import com.example.delacruz.finalandroid.adapter.EnvioAdapter;
import com.example.delacruz.finalandroid.model.Destinatario;
import com.example.delacruz.finalandroid.model.Envios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class VerEnviosFragment extends Fragment {

    RecyclerView rv;
    DatabaseReference myRef;
    List<Envios> Envio;
    EnvioAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ver_envios,container,false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if (!getArguments().getString("roluser").equals("admin")){
            fab.hide();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegEnviosFragment fr=new RegEnviosFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });


        rv = (RecyclerView)rootView.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Envios");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Envio = new ArrayList<Envios>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    Envios value = dataSnapshot1.getValue(Envios.class);
                    Envios e = new Envios();
                    e.setCodigo(value.getCodigo());
                    e.setN_envio(value.getN_envio());
                    e.setTransportista(value.getTransportista());
                    e.setDestinatario(value.getDestinatario());
                    e.setRemitente(value.getRemitente());
                    e.setDescripcion(value.getDescripcion());
                    e.setFecha(value.getFecha());
                    e.setPeso(value.getPeso());
                    e.setDescripcion(value.getDescripcion());
                    Envio.add(e);

                }

                EnvioAdapter recyclerAdapter = new EnvioAdapter(Envio,rootView.getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(rootView.getContext(),2);
                rv.setAdapter(recyclerAdapter);

                recyclerAdapter.setOnclickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //getId from Items sent to RecyclerView
                        Envios e = new Envios();
                        e.setCodigo(Envio.get(rv.getChildAdapterPosition(view)).getCodigo());
                        e.setN_envio(Envio.get(rv.getChildAdapterPosition(view)).getN_envio());
                        e.setTransportista(Envio.get(rv.getChildAdapterPosition(view)).getTransportista());
                        e.setRemitente(Envio.get(rv.getChildAdapterPosition(view)).getRemitente());
                        e.setFecha(Envio.get(rv.getChildAdapterPosition(view)).getFecha());
                        e.setPeso(Envio.get(rv.getChildAdapterPosition(view)).getPeso());
                        e.setDescripcion(Envio.get(rv.getChildAdapterPosition(view)).getDescripcion());
                        e.setDestinatario(Envio.get(rv.getChildAdapterPosition(view)).getDestinatario());

                        DetailEnvioFragment fr = new DetailEnvioFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("envio_id",e.getCodigo());
                        bundle.putString("envio_env",e.getN_envio());
                        bundle.putString("envio_tra",e.getTransportista());
                        bundle.putString("envio_rem",e.getRemitente());
                        bundle.putString("envio_fec",e.getFecha());
                        bundle.putString("envio_pes",e.getPeso());
                        bundle.putString("envio_des",e.getDescripcion());
                        bundle.putString("envio_dest",e.getDestinatario());
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
