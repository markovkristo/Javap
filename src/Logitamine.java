import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Klass, mis logitab mängijate käike ning mängu lõpptulemust.
public class Logitamine {
    public static void logitamine(String mängija, int staatus) {
        File logi = new File("logi.txt");//Kasutab faili "logi.txt".
        try {
            if(staatus == 0){//Staatus on lipp. Kui staatus on 0, siis see tähendab, et logitakse käiku. Kui staatus on 1, siis logitakse mängutulemust.
                if (!logi.exists()) {//Kui ei ole olemas faili "logi.txt", siis programm loob selle.
                    logi.createNewFile();
                }
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " sisestas käigu ruudule " + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();}
            else{//Kuna staatus ei ole 0, siis programm logitab mängu lõpptulemust.
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " võitis." + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();
            }

        } catch (IOException e) {//Kui toimub sisend-väljund erind, siis väljastab terminali "Logi error".
            System.out.println("Logi error");
        }
    }
}
