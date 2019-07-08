package com.example.delacruz.finalandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.model.Remitente;
import com.example.delacruz.finalandroid.model.Usuario;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsuarioViewHolder>
        implements View.OnClickListener{
    List<Usuario> Usuarios;
    Context context;
    private View.OnClickListener listener;

    public UserAdapter(List<Usuario> usuarios, Context context) {

        this.Usuarios = usuarios;
        this.context = context;
    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_recycler, parent, false);
        //PaqueteViewHolder holder = new PaqueteViewHolder(v);
        v.setOnClickListener(this);
        return new UsuarioViewHolder(v);
    }



    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int i) {
        Usuario usuario = Usuarios.get(i);
        holder.txtcodigo.setText(usuario.getCodigo());
        holder.txtcorreo.setText(usuario.getCorreo());
        holder.txtestado.setText("Activo");
    }

    @Override
    public int getItemCount() {
        return Usuarios.size();
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

    class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView txtcodigo;
        TextView txtcorreo;
        TextView txtestado;

        public UsuarioViewHolder(View itemView) {
            super(itemView);
            txtcodigo = (TextView) itemView.findViewById(R.id.txtcodigo);
            txtcorreo = (TextView) itemView.findViewById(R.id.txtcorreo);
            txtestado = (TextView) itemView.findViewById(R.id.txtestado);

        }

    }

}
