package com.example.inventario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryAdapter extends  RecyclerView.Adapter <InventoryAdapter.InventoryViewHolder>{
    ArrayList<Inventory> objInventory;

    public InventoryAdapter(ArrayList<Inventory> objInventory) {
        this.objInventory = objInventory;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventoryresouce,null,false);
        return new InventoryAdapter.InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        holder.Codigo.setText("Codigo: " + objInventory.get(position).getCodigo());
        holder.Nombre.setText("Nombre: " + objInventory.get(position).getNombre());
        holder.Cantidad.setText("Cantidad: " + objInventory.get(position).getCantidad());
        holder.valor.setText("Valor: " + objInventory.get(position).getValor());
        if (objInventory.get(position).getActivo().equalsIgnoreCase("si")) {
            holder.estado.setText("Estado: Activo");
        } else {
            holder.estado.setText("Estado: Inactivo");
        }
    }

    @Override
    public int getItemCount() {
        return this.objInventory.size();
    }

    public class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView Codigo, Nombre, Cantidad, valor, estado;
        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);

            Codigo = itemView.findViewById(R.id.tvCodigo);
            Nombre = itemView.findViewById(R.id.tvNombre);
            Cantidad = itemView.findViewById(R.id.tvCantidad);
            valor = itemView.findViewById(R.id.tvValor);
            estado = itemView.findViewById(R.id.tvActivo);
        }
    }
}
