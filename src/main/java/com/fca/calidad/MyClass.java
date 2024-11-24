package com.fca.calidad;

public class MyClass {
    public String getClassNameUpperCase() {
        // Devuelve el nombre de la clase en mayúsculas
        return this.getClass().getSimpleName().toUpperCase();
    }
}
