package com.example.delacruz.finalandroid.adapter;

import android.content.Context;
import android.sax.TextElementListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Envios;
import java.util.List;

public class EnvioAdapter extends RecyclerView.Adapter<EnvioAdapter.PaqueteViewHolder> implements View.OnClickListener{

    List<Envios> Envio;
    Context context;
    private View.OnClickListener listener;

    public EnvioAdapter(List<Envios> envio, Context context) {

        this.Envio = envio;
        this.context = context;
    }

    @Override
    public PaqueteViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_envio_recycler, parent, false);
        //PaqueteViewHolder holder = new PaqueteViewHolder(v);
        v.setOnClickListener(this);
        return new PaqueteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaqueteViewHolder holder, int i) {
        Envios envios = Envio.get(i);
        holder.txtcodigo.setText(envios.getCodigo());
        holder.txtdatos.setText("N° envio : "+envios.getN_envio() +" - Peso : "+ envios.getPeso()+"kg");
        holder.txtremitente.setText("Remitente : "+envios.getRemitente());
        holder.txtdestinatario.setText("Destinatario : "+envios.getDestinatario());
        holder.txtdescripcion.setText(envios.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return Envio.size();
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
        TextView txtdatos;
        TextView txtremitente;
        TextView txtdestinatario;
        TextView txtdescripcion;


        public PaqueteViewHolder(View itemView) {
            super(itemView);
            txtcodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            txtdatos = (TextView)itemView.findViewById(R.id.txtdatos);
            txtdescripcion = (TextView) itemView.findViewById(R.id.txtdescripcion);
            txtremitente = (TextView)itemView.findViewById(R.id.txtremitente);
            txtdestinatario = (TextView)itemView.findViewById(R.id.txtdestinatario);

        }

    }

}


