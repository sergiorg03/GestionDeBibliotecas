package Utilities;

import java.util.HashMap;
import java.util.Map;

public class Functions {
    
    private final String[] LETRAS = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
    public final Map<String, String> COLORES = new HashMap<String, String>(){
        {
            put("RESET", "\u001B[0m");
            put("NEGRO", "\u001B[30m");
            put("ROJO", "\u001B[31m");
            put("VERDE", "\u001B[32m");
            put("AMARILLO", "\u001B[33m");
            put("AZUL", "\u001B[34m");
            put("MORADO", "\u001B[35m");
            put("CIAN", "\u001B[36m");
            put("BLANCO", "\u001B[37m");

            put("NEGRO_FONDO", "\u001B[40m");
            put("ROJO_FONDO", "\u001B[41m");
            put("VERDE_FONDO", "\u001B[42m");
            put("AMARILLO_FONDO", "\u001B[43m");
            put("AZUL_FONDO", "\u001B[44m");
            put("MORADO_FONDO", "\u001B[45m");
            put("CIAN_FONDO", "\u001B[46m");
            put("BLANCO_FONDO", "\u001B[47m");

            put("NEGRITA", "\u001B[1m");
            put("APAGADO_NEGRITA", "\u001B[21m");
            put("SUBRAYADO", "\u001B[4m");
            put("APAGADO_SUBRAYADO", "\u001B[24m");
            put("INVERTIDO", "\u001B[7m");
            put("APAGADO_INVERTIDO", "\u001B[27m");

            // Colores de texto intensos
            put("NEGRO_INTENSO", "\u001B[90m");
            put("ROJO_INTENSO", "\u001B[91m");
            put("VERDE_INTENSO", "\u001B[92m");
            put("AMARILLO_INTENSO", "\u001B[93m");
            put("AZUL_INTENSO", "\u001B[94m");
            put("MORADO_INTENSO", "\u001B[95m");
            put("CIAN_INTENSO", "\u001B[96m");
            put("BLANCO_INTENSO", "\u001B[97m");

            // Colores de fondo intensos
            put("NEGRO_FONDO_INTENSO", "\u001B[100m");
            put("ROJO_FONDO_INTENSO", "\u001B[101m");
            put("VERDE_FONDO_INTENSO", "\u001B[102m");
            put("AMARILLO_FONDO_INTENSO", "\u001B[103m");
            put("AZUL_FONDO_INTENSO", "\u001B[104m");
            put("MORADO_FONDO_INTENSO", "\u001B[105m");
            put("CIAN_FONDO_INTENSO", "\u001B[106m");
            put("BLANCO_FONDO_INTENSO", "\u001B[107m");    
        }
    };

    public boolean comprobarDNI(String dni){
        boolean valido = false;
        
        if (dni.length() == 9) {
        
            String nums = dni.substring(0, 8);
            String letra = dni.substring(8);
            
            if (esNumerico(nums) && letra.equalsIgnoreCase(LETRAS[(Integer.parseInt(nums)%23)])) {
                valido = true;
            }
        }
        
        return valido;
    }
    
    public boolean esNumerico(String cadena){
        boolean valido = false;
        try {
            Integer.parseInt(cadena);
            valido = true;
        } catch (NumberFormatException e) {
        }finally{
            return valido;
        }
    }
    
}