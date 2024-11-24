package com.fca.calidad;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class MyClassTest {

    @Test
    void testThenReturn() {
        MyClass myClassMock = mock(MyClass.class);
        when(myClassMock.getClassNameUpperCase()).thenReturn("Hola");
        assertEquals("Hola", myClassMock.getClassNameUpperCase());
    }//este es el mockito donde probare hola (unitaria)

    @Test
    void testThenCallRealMethod() {
        // Usare un spy para llamar al metodo real con el nombre de la clase (MYCLASS)
        MyClass myClassSpy = spy(new MyClass());
        when(myClassSpy.getClassNameUpperCase()).thenCallRealMethod();
        assertEquals("MYCLASS", myClassSpy.getClassNameUpperCase());
    }

    @Test
    void testThenAnswer() {
    	//usare un lambda para poder usar parametros diferentes (agregarle string)
        MyClass myClassMock = mock(MyClass.class);
        when(myClassMock.getClassNameUpperCase()).thenAnswer(invocation -> {
            String className = MyClass.class.getSimpleName();
            return "Nombre de la clase: " + className;
        });
        assertEquals("Nombre de la clase: MyClass", myClassMock.getClassNameUpperCase());
    }
}
