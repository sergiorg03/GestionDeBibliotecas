package Utilities;

public class Functions {
    
    private final String[] LETRAS = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

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
        } catch (NumberFormatException e) {
            
        }finally{
            return valido;
        }
    }
    
}