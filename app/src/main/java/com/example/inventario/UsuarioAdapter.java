package com.example.inventario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsuarioAdapter extends RecyclerView.Adapter <UsuarioAdapter.UsuarioViewHolder> {


    ArrayList<Usuario> objUsuario;

    public UsuarioAdapter(ArrayList<Usuario> objUsuario) {
        this.objUsuario = objUsuario;
    }

    @NonNull
    @Override
    public UsuarioAdapter.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registrosresouces,null,false);
        return new UsuarioAdapter.UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdapter.UsuarioViewHolder holder, int position) {
        holder.usuario.setText("Usuario: " + objUsuario.get(position).getUsuario());
        holder.nombre.setText("Nombre: " + objUsuario.get(position).getNombre());
        holder.correo.setText("Correo: " + objUsuario.get(position).getCorreo());

        if (objUsuario.get(position).getEstado().equalsIgnoreCase("si")) {
            holder.estado.setText("Estado: Activo");
        } else {
            holder.estado.setText("Estado: Inactivo");
        }
    }

    @Override
    public int getItemCount() {
        return objUsuario.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView usuario, nombre, correo, estado;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.tvUsuario);
            nombre = itemView.findViewById(R.id.tvNombre);
            correo = itemView.findViewById(R.id.tvCorreo);
            estado = itemView.findViewById(R.id.tvActivoUsuario);
        }
    }
}