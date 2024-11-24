package com.fca.calidad;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class MyClassTest {

    @Test
    void testThenReturn() {
        // Crear un mock de MyClass
        MyClass myClassMock = mock(MyClass.class);

        // Configurar el mock para devolver un valor harcodeado
        when(myClassMock.getClassNameUpperCase()).thenReturn("Hola");

        // Comprobar que el método devuelve el valor esperado
        assertEquals("Hola", myClassMock.getClassNameUpperCase());
    }

    @Test
    void testThenCallRealMethod() {
        // Crear un mock de MyClass
        MyClass myClassMock = mock(MyClass.class);

        // Configurar el mock para llamar al método real
        when(myClassMock.getClassNameUpperCase()).thenCallRealMethod();

        // Comprobar que el método real devuelve el nombre de la clase en mayúsculas
        assertEquals("MYCLASS", myClassMock.getClassNameUpperCase());
    }

    @Test
    void testThenAnswer() {
        // Crear un mock de MyClass
        MyClass myClassMock = mock(MyClass.class);

        // Configurar el mock para devolver un valor dinámico con thenAnswer
        when(myClassMock.getClassNameUpperCase()).thenAnswer(invocation -> {
            String className = MyClass.class.getSimpleName();
            return "Nombre de la clase: " + className;
        });

        // Comprobar que el método devuelve el valor dinámico esperado
        assertEquals("Nombre de la clase: MyClass", myClassMock.getClassNameUpperCase());
    }
}
