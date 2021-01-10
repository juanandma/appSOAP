package com.example.practica2;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.Vector;

public class FactorySOAP {

    public static Alumno[] GetArrayComplejos(SoapSerializationEnvelope envelope) throws SoapFault {
        Alumno[] Array = null;
        Object Obj = envelope.getResponse();
        if (Obj != null) {
            if (Obj instanceof Vector) {
                Vector datos = (Vector) Obj;
                int Max = datos.size();
                Array = new Alumno[Max];
                for (int i = 0; i < Max; i++) {
                    Array[i] = new Alumno();
                    SoapObject XmlResponse = (SoapObject) datos.get(i);
                    Array[i].setId(XmlResponse.getPrimitivePropertyAsString("id"));
                    Array[i].setNombre(XmlResponse.getPrimitivePropertyAsString("nombre"));
                    Array[i].setApellidos(XmlResponse.getPrimitivePropertyAsString("apellidos"));
                    Array[i].setEmail(XmlResponse.getPrimitivePropertyAsString("email"));
                }
            } else if (Obj instanceof SoapObject) {
                Array = new Alumno[1];
                Array[0] = new Alumno();
                SoapObject XmlResponse = (SoapObject) Obj;
                Array[0].setId(XmlResponse.getPrimitivePropertyAsString("id"));
                Array[0].setNombre(XmlResponse.getPrimitivePropertyAsString("nombre"));
                Array[0].setNombre(XmlResponse.getPrimitivePropertyAsString("apellidos"));
                Array[0].setNombre(XmlResponse.getPrimitivePropertyAsString("email"));
            }
        }
        return Array;
    }
}
