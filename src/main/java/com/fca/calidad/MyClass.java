package com.fca.calidad;

public class MyClass {
    public String getClassNameUpperCase() {
        // es para el nombre de la clase en mayúsculas (o lo que se pruebe en el mock)
        return this.getClass().getSimpleName().toUpperCase();
    }
}
