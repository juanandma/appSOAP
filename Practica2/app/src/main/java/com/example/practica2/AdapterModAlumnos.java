package com.example.practica2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterModAlumnos extends RecyclerView.Adapter<AdapterModAlumnos.MyViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Alumno item);
    }

    private final OnItemClickListener listener;
    private List<Alumno> alumnosList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView nombre;
        private TextView apellidos;
        private TextView email;
        public MyViewHolder(View v) {
            super(v);
            nombre=(TextView) v.findViewById(R.id.nombre_item);
            apellidos=(TextView) v.findViewById(R.id.apellidos_item);
            email=(TextView) v.findViewById(R.id.email_item);

        }

        public void bind(final Alumno item, final OnItemClickListener listener) {

            //TODO
            nombre.setText("Nombre: "+item.getNombre());
            apellidos.setText("Apellidos: "+item.getApellidos());
            email.setText("Email: "+item.getEmail());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    public AdapterModAlumnos(OnItemClickListener listener, List<Alumno> alumnosList) {

        this.alumnosList=alumnosList;
        this.listener=listener;

    }

    @NonNull
    @Override
    public AdapterModAlumnos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno, parent, false);

        AdapterModAlumnos.MyViewHolder vh = new AdapterModAlumnos.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(alumnosList.get(position), listener);
    }




    @Override
    public int getItemCount() {
        return alumnosList.size();
    }
}
