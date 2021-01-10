package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import static com.example.practica2.MainActivity.AlumnosList;

public class Activity_Modificar extends AppCompatActivity {

    private Alumno alumno;
    private EditText etnombre, etapellidos, etemail;
    private Button btnupdate, btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);


        Intent intent = getIntent();
        final int id = intent.getIntExtra("alumno",0);


        etnombre = (EditText) findViewById(R.id.et_nombre_mod);
        etapellidos = (EditText) findViewById(R.id.et_apellidos_mod);
        etemail = (EditText) findViewById(R.id.et_email_mod);
        btndelete = (Button) findViewById(R.id.btn_eliminar);
        btnupdate = (Button) findViewById(R.id.btn_actualizar);


        alumno=buscarAlumno(id,AlumnosList);

        etnombre.setText(alumno.getNombre());
        etapellidos.setText(alumno.getApellidos());
        etemail.setText(alumno.getEmail());




        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlumnoWebService_JavaWS ServerJava = new AlumnoWebService_JavaWS();
                ServerJava.execute("eliminarAlumno",String.valueOf(id));

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etnombre.getText().toString();
                if (TextUtils.isEmpty(nombre)){
                    etnombre.setError("Nombre vacio");
                    etnombre.requestFocus();
                    return;
                }
                String apellidos = etapellidos.getText().toString();
                if (TextUtils.isEmpty(apellidos)){
                    etapellidos.setError("Nombre vacio");
                    etapellidos.requestFocus();
                    return;
                }
                String email = etemail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    etemail.setError("Nombre vacio");
                    etemail.requestFocus();
                    return;
                }

                AlumnoWebService_JavaWS ServerJava = new AlumnoWebService_JavaWS();
                ServerJava.execute("editarAlumno",String.valueOf(id),nombre,apellidos,email);

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private Alumno buscarAlumno(int id, List<Alumno> lista){
        boolean encontrado=false;
        int i=0;
        while(!encontrado && i<lista.size()){

            if(lista.get(i).getId()==id){
                encontrado=true;
                return lista.get(i);
            }
            i++;
        }
        return null;
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
                case "eliminarAlumno":
                    request.addProperty("id", strings[1]);

                    break;
                case "editarAlumno":
                    request.addProperty("id", strings[1]);
                    request.addProperty("nombre", strings[2]);
                    request.addProperty("apellidos", strings[3]);
                    request.addProperty("email", strings[4]);

                    break;
            }
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL, 2000);
            transporte.debug = true; //Para obtener los mensajes xml request y Response
            try {
                transporte.call(SOAP_ACTION, envelope);

                switch (METHOD_NAME) {
                    case "eliminarAlumno":
                        resultado_xml = (SoapPrimitive) envelope.getResponse();
                        Salidas[1] = strings[1];
                        Salidas[2] = resultado_xml.toString();

                        break;
                    case "editarAlumno":

                        resultado_xml = (SoapPrimitive) envelope.getResponse();
                        Salidas[1] = strings[1];
                        Salidas[2] = resultado_xml.toString();

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

                case "eliminarAlumno":
                    if (values[2].equals("true"))
                        Sal=String.format("El cliente ha sido eliminado",values[1]);
                    else
                        Sal=String.format("Error al eliminar",values[1]);
                    Toast.makeText(getBaseContext(),Sal,Toast.LENGTH_LONG).show();
                    break;
                case "editarAlumno":
                    if (values[2].equals("true"))
                        Sal=String.format("El cliente ha sido editado",values[1]);
                    else
                        Sal=String.format("Error al editar",values[1]);
                    Toast.makeText(getBaseContext(),Sal,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getBaseContext(), "Tarea finalizada", Toast.LENGTH_SHORT).show();
            }

        }



    }
}