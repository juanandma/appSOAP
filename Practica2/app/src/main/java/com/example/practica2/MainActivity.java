package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button anadirAlumno;
    Button modAlumno;

    private RecyclerView listarAlumnosview;
    private AdapterListaAlumnos adapterListaAlumnos;
    private RecyclerView.LayoutManager layoutManager;


    public static List<Alumno> AlumnosList = new ArrayList<>();



    public void anadirAlumnoActivity() {
        anadirAlumno = (Button) findViewById(R.id.btn_anadir_alumno);
        anadirAlumno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent anadirAlumnoi = new Intent(getBaseContext(), AnadirAlumnos.class);
                startActivity(anadirAlumnoi);
            }
        });
    }

    public void modAlumnoActivity() {



        modAlumno = (Button) findViewById(R.id.btn_modificar_alumno);
        modAlumno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent modAlumnoi = new Intent(getBaseContext(), ModAlumnos.class);
                startActivity(modAlumnoi);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlumnosList.clear();

        AlumnoWebService_JavaWS ServerJava = new AlumnoWebService_JavaWS();
        ServerJava.execute("listarAlumnos");

        anadirAlumnoActivity();
        modAlumnoActivity();

        listarAlumnosview = (RecyclerView) findViewById(R.id.rv_alumnos);




        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        listarAlumnosview.setLayoutManager(layoutManager);



        //Toast.makeText(getBaseContext(), "gay", Toast.LENGTH_LONG).show();





    }

    @Override
    protected void onStart() {
        super.onStart();
        // El servicio asincrono es muy lento, no le llega la lista de alumnos
        adapterListaAlumnos = new AdapterListaAlumnos(this, AlumnosList);
        listarAlumnosview.setAdapter(adapterListaAlumnos);


    }



    private class AlumnoWebService_JavaWS extends AsyncTask<String, String, Boolean> {

        String NAMESPACE = Configuracion.Linux_NAMESPACE;
        String URL = Configuracion.Linux_URL;
        String METHOD_NAME = "";
        String SOAP_ACTION = Configuracion.Linux_SOAP_ACTION;


        @Override
        protected Boolean doInBackground(String... strings) {
            SoapPrimitive resultado_xml;
            Boolean Correcto = false;
            int TamaVector;
            METHOD_NAME = strings[0];
            SOAP_ACTION = SOAP_ACTION + METHOD_NAME;


            String[] Salidas = new String[3];
            Salidas[0] = METHOD_NAME;

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            //No añade la definicion de los tipos en los parametros
            envelope.implicitTypes = true;
            //Elimina los las etiquetas id y root del envolope
            envelope.setAddAdornments(false);

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            switch (METHOD_NAME) {
                case "listarAlumnos":

                    break;
            }
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL, 2000);
            transporte.debug = true; //Para obtener los mensajes xml request y Response
            try {
                transporte.call(SOAP_ACTION, envelope);

                switch (METHOD_NAME) {
                    case "listarAlumnos":
                        /*Vector<Complejo> Lista=FactorySOAP.GetVectorComplejos(envelope);
                        TamaVector = Lista.size();
                        Salidas=new String[TamaVector+1];
                        Salidas[0]=METHOD_NAME;
                        for (int i = 0; i < TamaVector; i++) {
                            Salidas[i + 1] = Lista.get(i).toString();
                        }
                        */
                        Alumno[] Lista = FactorySOAP.GetArrayComplejos(envelope);
                        //MainActivity.AlumnosList= Arrays.asList(Lista);

                        Salidas = new String[Lista.length + 1];
                        Salidas[0] = METHOD_NAME;
                        int Pos = 1;
                        for (Alumno c : Lista) {
                            Salidas[Pos] = c.toString();
                            Pos++;
                        }

                        break;
                }


                Log.i("WS_IIS", transporte.requestDump);
                Log.i("WS_IIS", transporte.responseDump);
                Correcto = true;

            } catch (Exception e) {
                //METHOD_NAME="Error";
                Salidas[0] = "Error";
                Salidas[1] = e.getMessage();
            }
            publishProgress(Salidas);
            return Correcto;
        }


        @Override
        protected void onProgressUpdate(String... values) {

            String Sal;
            switch (values[0]) {

                case "listarAlumnos":
                    //Alumno[] Lista=new Alumno[values.length];
                    for (int i = 1; i < values.length; i++) {

                        String[] atributos = values[i].split(",");

                        Alumno a=new Alumno();
                        a.setId(atributos[0]);
                        a.setNombre(atributos[1]);
                        a.setApellidos(atributos[2]);
                        a.setEmail(atributos[3]);
                        AlumnosList.add(a);

                    }

                    break;

                default:
                    Toast.makeText(getBaseContext(), values[1], Toast.LENGTH_LONG).show();
                    Log.i("error", values[1]);
                    break;

            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(getBaseContext(), "Tarea listar finalizada", Toast.LENGTH_SHORT).show();
            }

        }



    }

}