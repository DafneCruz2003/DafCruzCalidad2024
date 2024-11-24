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
    }

    @Test
    void testThenCallRealMethod() {
        // Usar un spy en lugar de un mock
        MyClass myClassSpy = spy(new MyClass());
        when(myClassSpy.getClassNameUpperCase()).thenCallRealMethod();
        assertEquals("MYCLASS", myClassSpy.getClassNameUpperCase());
    }

    @Test
    void testThenAnswer() {
        MyClass myClassMock = mock(MyClass.class);
        when(myClassMock.getClassNameUpperCase()).thenAnswer(invocation -> {
            String className = MyClass.class.getSimpleName();
            return "Nombre de la clase: " + className;
        });
        assertEquals("Nombre de la clase: MyClass", myClassMock.getClassNameUpperCase());
    }
}
