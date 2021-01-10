package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import static com.example.practica2.MainActivity.AlumnosList;

public class ModAlumnos extends AppCompatActivity {

    private RecyclerView listarAlumnosview;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_alumnos);


        listarAlumnosview = (RecyclerView) findViewById(R.id.rv_alumnos_m);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        listarAlumnosview.setLayoutManager(layoutManager);


        listarAlumnosview.setAdapter(new AdapterModAlumnos(new AdapterModAlumnos.OnItemClickListener() {
                    @Override public void onItemClick(Alumno item) {

                        Intent intent = new Intent(ModAlumnos.this,Activity_Modificar.class);
                        //intent.putExtra("alumno",item);
                        intent.putExtra("alumno",item.getId());
                        startActivity(intent);
                    }
                }, AlumnosList));

    }
}