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
import com.example.delacruz.finalandroid.adapter.PaqueteAdapter;
import com.example.delacruz.finalandroid.adapter.UserAdapter;
import com.example.delacruz.finalandroid.model.Transportista;
import com.example.delacruz.finalandroid.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class VerUsuarioFragment extends Fragment {

    RecyclerView rv;
    DatabaseReference myRef;
    List<Usuario> Usuarios;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ver_usuario, container, false);



        rv = (RecyclerView)rootView.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Usuario");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuarios = new ArrayList<Usuario>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    Usuario value = dataSnapshot1.getValue(Usuario.class);

                    Usuario tran = new Usuario();
                    tran.setCodigo(value.getCodigo());
                    tran.setCorreo(value.getCorreo());
                    tran.setClave(value.getClave());
                    tran.setRol(value.getRol());
                    tran.setEstado(value.getEstado());
                    Usuarios.add(tran);

                }

                UserAdapter recyclerAdapter = new UserAdapter(Usuarios,rootView.getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(rootView.getContext(),2);
                rv.setAdapter(recyclerAdapter);

                recyclerAdapter.setOnclickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //getId from Items sent to RecyclerView
                        Usuario t = new Usuario();
                        t.setCodigo(Usuarios.get(rv.getChildAdapterPosition(view)).getCodigo());
                        t.setCorreo(Usuarios.get(rv.getChildAdapterPosition(view)).getCorreo());
                        t.setClave(Usuarios.get(rv.getChildAdapterPosition(view)).getClave());
                        t.setRol(Usuarios.get(rv.getChildAdapterPosition(view)).getRol());
                        t.setEstado(Usuarios.get(rv.getChildAdapterPosition(view)).getEstado());

                        DetailUsuarioFragment fr=new DetailUsuarioFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("user_id",t.getCodigo());
                        bundle.putString("user_correo",t.getCorreo());
                        bundle.putString("user_rol",t.getRol());
                        bundle.putString("user_estado",t.getEstado());
                        bundle.putString("user_clave",t.getClave());
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
