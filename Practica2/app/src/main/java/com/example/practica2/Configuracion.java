package com.example.practica2;

public final class Configuracion {


    public static final String LI_Router="http://10.0.2.2";

    public static final String LI=LI_Router; //modificar si en Movil o en Router quien da el punto de acceso a internet

    public static final String Linux_NAMESPACE = "http://paqueteComplejos/";
    public static final String Linux_URL=LI+":8080/SOAPAlumnos/OperacionesAlumno?WSDL";
    public static final String Linux_SOAP_ACTION = LI+":8080/SOAPAlumnos/";

}
