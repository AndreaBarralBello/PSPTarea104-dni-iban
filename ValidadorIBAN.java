import java.math.BigInteger;

public class ValidadorIBAN {

    public static void main(String[] args) {

        // recibimos el dni que se le pasó al padre por argumento
        String iban = args[0];

        // comprobamos que no esté vacío
        if (iban.isEmpty()) {
            System.out.println("FALTA EL IBAN");
            System.exit(-1);
        }

        // usamos el método para comprobar si es un dni válido
        System.exit(validarIBAN(iban));
    }

    // método para validar un IBAN español
    /*
     * Valida un DNI y devuelve:
     * 0 → válido
     * -1 → formato incorrecto
     * -2 → dígitos de control incorrectos. Muestra por stdout los correctos
     */
    public static int validarIBAN(String iban) {

        // Primero valida si es un formato ES + 22 dígitos
        if (!iban.matches("ES\\d{22}")) {
            return -1;
        }

        // Agoritmo que mueve los 4 primeros caracteres al final
        String ordenado = iban.substring(4) + iban.substring(0, 4);

        // Convierte las letras a números
        StringBuilder numerico = new StringBuilder();
        for (int i = 0; i < ordenado.toCharArray().length; i++) {
            // cada char
            char c = ordenado.charAt(i);
            if (Character.isLetter(c)) {
                numerico.append((int) c - 'A' + 10);
            } else {
                numerico.append(c);
            }
        }

        // Calcula el resto
        BigInteger numero = new BigInteger(numerico.toString());

        int resto = numero.mod(BigInteger.valueOf(97)).intValue();

        // Valida si el resto es válido
        if (resto == 1) {
            return 0;
        }

        // Si no es válido calcula los dígitos de control válidos
        // Contruyo un iban temporal con los dígitos de control a 00
        // Concateno de 0-2 + 00 + el resto de números desde el 4 (no incluido)
        String ibanSinControl = iban.substring(0, 2) + "00" + iban.substring(4);

        // Ahora los 4 primeros elementos se pasan al final
        String ordenado2 = ibanSinControl.substring(4) + ibanSinControl.substring(0, 4);

        // Ahora pasamos las letras ES a números
        StringBuilder num2 = new StringBuilder();
        for (int i = 0; i < ordenado2.length(); i++) {
            char c = ordenado2.charAt(i);
            if (Character.isLetter(c)) {
                num2.append((int) c - 'A' + 10);
            } else {
                num2.append(c);
            }
        }

        // Ahora con el número completo calculamos el %97
        //Se parsea el stringbuilder a string y después a BigInteger
        BigInteger n2 = new BigInteger(num2.toString());

        int resto2 = n2.mod(BigInteger.valueOf(97)).intValue();

        int controlCorrectos = 98 - resto2;
        //Los dígitos de control los queremos para que el padre los muestre por consola
        //Así que se lo mandamos al padre con stdout 

        //Los parseamos a dos cifras por si algún número sólo tiene una
        System.out.printf("%02d%n", controlCorrectos);
        return -2;

    }
}
