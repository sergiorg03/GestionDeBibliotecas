/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Utilities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergi
 */
public class FunctionsTest {
    
    Functions f = new Functions();
    
    public FunctionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of comprobarDNI method, of class Functions.
     */
    @Test
    public void testComprobarDNI_valido() {
        System.out.println("comprobarDNI_valido");
        String dni = "12345678Z";
        
        boolean expResult = true;
        boolean result = f.comprobarDNI(dni);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testComprobarDNI_erroneo() {
        System.out.println("comprobarDNI_Erroneo");
        String dni = "12345678P";
        
        boolean expResult = false;
        boolean result = f.comprobarDNI(dni);
        assertEquals(expResult, result);
    }

    /**
     * Test of esNumerico method, of class Functions.
     */
    @Test
    public void testesNumerico_valido() {
        System.out.println("esNumerico_valido");
        String cadena = "12345678";
        boolean expResult = true;
        boolean result = f.esNumerico(cadena);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testesNumerico_erroneo() {
        System.out.println("esNumerico_erroneo");
        String cadena = "asdasdasd";
        boolean expResult = false;
        boolean result = f.esNumerico(cadena);
        assertEquals(expResult, result);
    }
    
}
