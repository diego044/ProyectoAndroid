package com.example.delacruz.finalandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Destinatario;
import com.example.delacruz.finalandroid.model.Remitente;

import java.util.List;

public class DestinatarioAdapter extends RecyclerView.Adapter<DestinatarioAdapter.PaqueteViewHolder>
        implements View.OnClickListener{
    List<Destinatario> Destinatarios;
    Context context;
    private View.OnClickListener listener;

    public DestinatarioAdapter(List<Destinatario> destinatarios, Context context) {

        this.Destinatarios = destinatarios;
        this.context = context;
    }

    @Override
    public PaqueteViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_destinatario_recycler, parent, false);
        //PaqueteViewHolder holder = new PaqueteViewHolder(v);
        v.setOnClickListener(this);
        return new PaqueteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaqueteViewHolder holder, int i) {
        Destinatario destinatario = Destinatarios.get(i);
        holder.txtcodigo.setText(destinatario.getCodigo());
        holder.txtnombre.setText(destinatario.getNombre()+" "+destinatario.getApellido());
        holder.txtdireccion.setText("Dirc : "+destinatario.getDireccion());
        holder.txtcelular.setText("Cel : "+destinatario.getCelular());
    }

    @Override
    public int getItemCount() {
        return Destinatarios.size();
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
        TextView txtdireccion;
        TextView txtcelular;

        public PaqueteViewHolder(View itemView) {
            super(itemView);
            txtcodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            txtnombre = (TextView) itemView.findViewById(R.id.txtnombre);
            txtdireccion =(TextView) itemView.findViewById(R.id.txtdireccion);
            txtcelular = (TextView) itemView.findViewById(R.id.txtcelular);

        }

    }

}
