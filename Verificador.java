
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Verificador{




    public static void main(String[] args) {
        

       //Pedimos los datos por consola y se los pasamos a los hijos como argumento
       String dni = pedirDni();
       String iban = pedirIban();


       try {
        //El padre pasas esos datos al hijo como argumento
        ProcessBuilder pbDni = new ProcessBuilder("java", "ValidadorDNI", dni);
        ProcessBuilder pbIban = new ProcessBuilder("java", "ValidadorIBAN", iban);

        //Inicializo los procesos hijo
        Process procesoDni = pbDni.start();
        Process procesoIban = pbIban.start();

        //Leo el stdout antes del waitFor (evito deadlock)
        BufferedReader outDni = new BufferedReader
        (new InputStreamReader(procesoDni.getInputStream()));
        //Lo guardo
        String stdoutdni = outDni.readLine();

        BufferedReader outIban = new BufferedReader
        (new InputStreamReader(procesoIban.getInputStream()));
        String stdoutiban = outIban.readLine();

        //El stdout de los hijos: solo recibo de dni la letra correcta en caso
        //de introducir una letra errónea

        //Ya está leido el stodout
        //Ahora compruebo los tipos de código de salida
        int codigoSalidaDni = procesoDni.waitFor();
        int codigoSalidaIban = procesoIban.waitFor();

        //Uso los métodos que analizan los códigos de retorno de cada hijo, muestra el error 
        //correspondiente y los datos correctos esperado
        int retornodni = mostrarResultadoDni(stdoutdni, codigoSalidaDni, dni);
        int retornoiban = mostrarResultadoIban(stdoutiban, codigoSalidaIban, iban);


        //Si ambos son correctos, muestra el mensaje de validación exitosa
        int sumaRetornos = retornodni + retornoiban;

        switch (sumaRetornos) {
            case 0:
                System.out.println("Todos los datos son correctos!");
                break;
            case 2:
                System.out.println("Se encontraron "+sumaRetornos+ " errores. Corríjalos e inténtelo de nuevo");
        }
        
           
       } catch (Exception e) {

        System.out.println("ERROR "+e.getLocalizedMessage());
       }
         
    }   

    public static String pedirDni(){

        Scanner teclado = new Scanner (System.in);

        System.out.println("Introduce el DNI: ");
        String dni = teclado.nextLine();
        return dni;


    }
    public static String pedirIban(){

        Scanner teclado = new Scanner (System.in);

        System.out.println("Introduce el IBAN: ");
        String iban = teclado.nextLine();
        return iban;
    }

    public static int mostrarResultadoDni (String stdout, int codigoSalida, String dni){

        int retorno = 0;

        switch (codigoSalida) {
            case -1: 
                System.out.println("DNI "+dni+" : FORMATO INCORRECTO \n"+
                "\t -> El DNI debe tener 8 dígitos seguidos de 1 letra mayúscula \n"+
            "\t -> Ejemplo válido: 12345678Z");
                return retorno =1;
            case -2:
                System.out.println("DNI "+dni+" : LETRA INCORRECTA \n"+
                "\t -> La letra correcta para el número "+dni+ " es " +stdout+"\n"+
            "\t -> Ejemplo válido: 12345678Z");
            return retorno =1;
            case 0:
                System.out.println("DNI "+dni+" :VÁLIDO \n");
                return retorno =0;
            
        }
        return retorno;

    }

     public static int mostrarResultadoIban (String stdout, int codigoSalida, String iban){

        int retorno = 0;

        switch (codigoSalida) {
            case -1: 
                System.out.println("IBAN "+iban+" : FORMATO INCORRECTO \n"+
                "\t -> El IBAN español debe empezar por 'ES' seguido de 22 dígitos \n"+
            "\t -> Ejemplo válido: ES7620770024003102575067");
            return retorno=1;
            case -2:
                System.out.println("IBAN "+iban+" : DÍGITOS DE CONTROL INCORRECTOS \n"+
                "\t -> Los dígitos de control correctos son: "+stdout+"\n"+
            "\t -> IBAN correcto: ES7620770024003102575067");
            return retorno=1;
            case 0:
                System.out.println("IBAN "+iban+" : VÁLIDO \n");
                return retorno=0;
          
               
        }
        return retorno;

    }
}
        
    
