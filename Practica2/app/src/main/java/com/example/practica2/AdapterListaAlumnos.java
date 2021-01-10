package com.example.practica2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterListaAlumnos extends RecyclerView.Adapter<AdapterListaAlumnos.MyViewHolder> {

    private List<Alumno> alumnosList;
    private MainActivity mainActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }


    public AdapterListaAlumnos(MainActivity mainActivity, List<Alumno> alumnosList) {
        this.alumnosList=alumnosList;
        this.mainActivity=mainActivity;
    }

    @Override
    public AdapterListaAlumnos.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText("Nombre : "+alumnosList.get(position).getNombre()+", Apellidos : "+alumnosList.get(position).getApellidos());

    }

    @Override
    public int getItemCount() {
        return alumnosList.size();
    }
}
