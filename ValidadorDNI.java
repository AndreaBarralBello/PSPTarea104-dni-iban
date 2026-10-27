


public class ValidadorDNI {

    private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static void main(String[] args) {

        //recibimos el dni que se le pasó al padre por argumento
        String dni = args[0];

        //comprobamos que no esté vacío
        if(dni.isEmpty()){
            System.out.println("FALTA EL DNI");
            System.exit(-1);
        }


        //usamos el método para comprobar si es un dni válido
        System.exit(validarDNI(dni));
    }  

    // método para calcular que un dni es válido
     /* Valida un DNI y devuelve:
     *   0  → válido
     *  -1  → formato incorrecto
     *  -2  → letra incorrecta (imprime la correcta por stdout)
     */
    public static int validarDNI (String dni) {

        
        // Primero valida si es un formato de 8 dígitos + 1 letra
        if(!dni.matches("\\d{8}[A-Z]")) {
            return -1;
        }

        //Separa el número de la letra para hacer el cálculo
        int numero = Integer.parseInt(dni.substring(0,8));
        char letraIntroducida = dni.charAt(8);

        //Calcula la letra correcta
        char letraCorrecta = LETRAS.charAt(numero % 23);

        //Compara si la letra introducida es la letra correcta correspondiente a ese número
        if(letraIntroducida != letraCorrecta){

         

            System.out.println(String.valueOf(letraCorrecta));
               return -2;
        }

        //Si es válido
        return 0;

    }

}


