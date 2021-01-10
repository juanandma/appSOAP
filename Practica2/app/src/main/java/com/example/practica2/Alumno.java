package com.example.practica2;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Locale;

public class Alumno implements KvmSerializable{



    private int id;
    private String nombre;
    private String apellidos;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        if(nombre==null){
            nombre="";
        }
        if(apellidos==null){
            apellidos="";
        }
        if(email==null){
            apellidos="";
        }

        return id+","+nombre+","+apellidos+","+email;

        /*
        String[] words = line.split(" ");
        System.out.println(Arrays.toString(words));
         */
    }


    /**
     * Get the property at the given index
     *
     * @param index
     */
    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0: return String.valueOf(id);
            case 1: return String.valueOf(nombre);
            case 2: return String.valueOf(apellidos);
            case 3: return String.valueOf(email);
        }
        return null;
    }

    /**
     * @return the number of serializable properties
     */
    @Override
    public int getPropertyCount() {
        return 4;
    }

    /**
     * Sets the property with the given index to the given value.
     *
     * @param index the index to be set
     * @param value the value of the property
     */
    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0: id = Integer.parseInt((String) value);
                break;
            case 1: nombre = (String) value;
                break;
            case 2: apellidos = (String) value;
                break;
            case 3: email = (String) value;
                break;
        }
    }

    /**
     * Fills the given property info record.
     *
     * @param index      the index to be queried
     * @param properties information about the (de)serializer.  Not frequently used.
     * @param info       The return parameter, to be filled with information about the
     */
    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

        switch (index) {
            case 0: info.type = Integer.valueOf(id).getClass();
                info.name = "id";
                break;
            case 1: info.type = String.valueOf(nombre).getClass(); //PropertyInfo.STRING_CLASS;
                info.name = "nombre";
                break;
            case 2: info.type = String.valueOf(nombre).getClass(); //PropertyInfo.STRING_CLASS;
                info.name = "apellidos";
                break;
            case 3: info.type = String.valueOf(nombre).getClass(); //PropertyInfo.STRING_CLASS;
                info.name = "email";
                break;
        }
    }
}
