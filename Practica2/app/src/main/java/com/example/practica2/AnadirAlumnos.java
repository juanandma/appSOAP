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

public class AnadirAlumnos extends AppCompatActivity {

    private Button btnguardar;
    private EditText etnombre, etapellidos, etemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alumnos);

        btnguardar = (Button) findViewById(R.id.btn_guardar);
        etnombre = (EditText) findViewById(R.id.et_nombre);
        etapellidos = (EditText) findViewById(R.id.et_apellidos);
        etemail = (EditText) findViewById(R.id.et_email);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Alumno nuevo = new Alumno();

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

                /*
                nuevo.setId(String.valueOf(MainActivity.Alumnoid++));

                nuevo.setNombre(nombre);
                nuevo.setApellidos(etapellidos.getText().toString());
                nuevo.setEmail(etemail.getText().toString());

                MainActivity.AlumnosList.add(nuevo);

                 */





                AlumnoWebService_JavaWS ServerJava = new AlumnoWebService_JavaWS();
                ServerJava.execute("anadirAlumno",nombre,apellidos,email);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

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
                case "anadirAlumno":

                    request.addProperty("nombre", strings[1]);
                    request.addProperty("apellidos", strings[2]);
                    request.addProperty("email", strings[3]);

                    break;
            }
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL, 2000);
            transporte.debug = true; //Para obtener los mensajes xml request y Response
            try {
                transporte.call(SOAP_ACTION, envelope);

                switch (METHOD_NAME) {
                    case "anadirAlumno":
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

                case "anadirAlumno":
                    if (values[2].equals("true"))
                        Sal=String.format("El cliente ha sido añadido",values[1]);
                    else
                        Sal=String.format("Error al añadir",values[1]);
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
                Toast.makeText(getBaseContext(), "Tarea añadir finalizada", Toast.LENGTH_SHORT).show();
            }

        }



    }
}