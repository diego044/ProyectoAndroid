package com.example.delacruz.finalandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.bean.Transportista;

import java.util.List;

public class PaqueteAdapter extends RecyclerView.Adapter<PaqueteAdapter.PaqueteViewHolder>{
    List<Transportista> Paquetes;

    public PaqueteAdapter(List<Transportista> paquetes) {
        Paquetes = paquetes;
    }

    @NonNull
    @Override
    public PaqueteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler, viewGroup, false);
        PaqueteViewHolder holder = new PaqueteViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaqueteViewHolder holder, int i) {
        Transportista transportista = Paquetes.get(i);
        holder.textViewCodigo.setText(transportista.getId_cliente());
        holder.textViewNombre.setText(transportista.getNombre());
    }

    @Override
    public int getItemCount() {
        return Paquetes.size();
    }

    public static class PaqueteViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCodigo;
        TextView textViewNombre;

        public PaqueteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtnombre);

        }

    }

}
