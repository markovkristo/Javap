import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logitamine {
    public static void logitamine(String mängija, int staatus) {
        File logi = new File("logi.txt");
        try {
            if(staatus == 0){
                if (!logi.exists()) {
                    logi.createNewFile();
                }
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " sisestas käigu ruudule " + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();}
            else{
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " võitis." + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();
            }

        } catch (IOException e) {
            System.out.println("Logi error");
        }
    }
}
