import javax.swing.*;
//Klass, mis loob mängu lõpus akna, mis kuvab mängu lõpptulemust.
public class Lõpuekraan {
    static void lõpp(char kumb) {
        Object[] valikud = {"Välju"};//Nupu kiri
        if (kumb == 'X') {//Kui mängu võitis mängija, kes juhib märki X, siis väljastab selle mängija võitu.
            int valik = JOptionPane.showOptionDialog(null, Traps.esimene + " võitis!", "Tulemus",//Kui mäng on läbi, kuvatakse nupp, et väljuda.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);//Kui vajutatakse nuppu, sulgub programm.
            }
        }
        if (kumb == 'O') {//Kui mängu võitis mängija, kes juhib märki O, siis väljastab selle mängija võitu.
            int valik = JOptionPane.showOptionDialog(null, Traps.teine + " võitis!", "Tulemus",//Kui mäng on läbi, kuvatakse nupp, et väljuda.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);//Kui vajutatakse nuppu, sulgub programm.
            }
        }else {//Kui mäng jääb viiki, siis väljastatakse vastav kiri.
            int valik = JOptionPane.showOptionDialog(null, "Viik!", "Tulemus",//Kui mäng on läbi, kuvatakse nupp, et väljuda.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);//Kui vajutatakse nuppu, sulgub programm.
            }
        }
    }
}
