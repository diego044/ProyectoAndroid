package com.example.delacruz.finalandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Remitente;
import com.example.delacruz.finalandroid.model.Transportista;

import java.util.List;

public class RemitenteAdapter extends RecyclerView.Adapter<RemitenteAdapter.PaqueteViewHolder>
        implements View.OnClickListener{
    List<Remitente> Remitentes;
    Context context;
    private View.OnClickListener listener;

    public RemitenteAdapter(List<Remitente> remitentes, Context context) {

        this.Remitentes = remitentes;
        this.context = context;
    }

    @Override
    public PaqueteViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_remitente_recycler, parent, false);
        //PaqueteViewHolder holder = new PaqueteViewHolder(v);
        v.setOnClickListener(this);
        return new PaqueteViewHolder(v);
    }



    @Override
    public void onBindViewHolder(PaqueteViewHolder holder, int i) {
        Remitente remitente = Remitentes.get(i);
        holder.txtcodigo.setText(remitente.getCodigo());
        holder.txtnombre.setText(remitente.getNombre() +" "+ remitente.getApellido());
        holder.txtcorreo.setText("Correo : "+remitente.getCorreo());
        holder.txttelefono.setText("Cel : "+remitente.getCelular());
    }

    @Override
    public int getItemCount() {
        return Remitentes.size();
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
        TextView txtcorreo;
        TextView txttelefono;

        public PaqueteViewHolder(View itemView) {
            super(itemView);
            txtcodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            txtnombre = (TextView) itemView.findViewById(R.id.txtnombre);
            txtcorreo = (TextView)itemView.findViewById(R.id.txtcorreo);
            txttelefono = (TextView) itemView.findViewById(R.id.txttelefono);

        }

    }

}
