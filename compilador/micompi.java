import java.io.IOException;

public class micompi {
    nodo p;
    public static void main(String[] args)throws IOException {
        String archivo = "C:\\Users\\aronc\\Downloads\\codigo.txt";
        lexico lexico = new lexico(archivo);
        if(!lexico.ErroEncontrado){
            System.out.println("Analisis lexico terminado");
        }
    }
}
