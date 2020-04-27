import javax.swing.*;

public class Lõpuekraan {
    static void lõpp(char kumb) {
        Object[] valikud = {"Välju"};
        if (kumb == 'X') {
            int valik = JOptionPane.showOptionDialog(null, Traps.esimene + " võitis!", "Tulemus",//Kui mäng on läbi, saab kasutaja valiku, kas mängida uuesti või väljuda mängust.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        if (kumb == 'O') {
            int valik = JOptionPane.showOptionDialog(null, Traps.esimene + " võitis!", "Tulemus",//Kui mäng on läbi, saab kasutaja valiku, kas mängida uuesti või väljuda mängust.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }else {
            int valik = JOptionPane.showOptionDialog(null, "Viik!", "Tulemus",//Kui mäng on läbi, saab kasutaja valiku, kas mängida uuesti või väljuda mängust.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
