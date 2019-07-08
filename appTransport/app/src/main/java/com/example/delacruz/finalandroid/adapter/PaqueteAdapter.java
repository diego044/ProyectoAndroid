package com.example.delacruz.finalandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Transportista;


import java.util.List;

public class PaqueteAdapter extends RecyclerView.Adapter<PaqueteAdapter.PaqueteViewHolder>
        implements View.OnClickListener{
    List<Transportista> Paquetes;
    Context context;
    private View.OnClickListener listener;

    public PaqueteAdapter(List<Transportista> paquetes, Context context) {

        this.Paquetes = paquetes;
        this.context = context;
    }

    @Override
    public PaqueteViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transportista_recycler, parent, false);
        //PaqueteViewHolder holder = new PaqueteViewHolder(v);
        v.setOnClickListener(this);
        return new PaqueteViewHolder(v);
    }



    @Override
    public void onBindViewHolder(PaqueteViewHolder holder, int i) {
        Transportista transportista = Paquetes.get(i);
        holder.txtcodigo.setText(transportista.getCodigo());
        holder.txtnombre.setText(transportista.getNombre() +" "+ transportista.getApellido());
        holder.txtplaca.setText("Placa : "+transportista.getVehiculo());
        holder.txtndocumento.setText("Documento : "+transportista.getN_documento());
    }

    @Override
    public int getItemCount() {
        return Paquetes.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    class PaqueteViewHolder extends RecyclerView.ViewHolder{
        TextView txtcodigo;
        TextView txtnombre;
        TextView txtplaca;
        TextView txtndocumento;

        public PaqueteViewHolder(View itemView) {
            super(itemView);
            txtcodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            txtnombre = (TextView) itemView.findViewById(R.id.txtnombre);
            txtplaca = (TextView)itemView.findViewById(R.id.txtplaca);
            txtndocumento = (TextView)itemView.findViewById(R.id.txtdocumento);

        }

    }

}
