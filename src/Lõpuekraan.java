import javax.swing.*;

public class Lõpuekraan {
    static void lõpp(char kumb) {
        Object[] valikud = {"Välju"};
        if (kumb == 'X' || kumb == 'O') {
            int valik = JOptionPane.showOptionDialog(null, "Kas soovite uuesti mängida või väljuda?", kumb + " võitis!",//Kui mäng on läbi, saab kasutaja valiku, kas mängida uuesti või väljuda mängust.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            int valik = JOptionPane.showOptionDialog(null, "Kas soovite uuesti mängida või väljuda?", "Viik!",//Kui mäng on läbi, saab kasutaja valiku, kas mängida uuesti või väljuda mängust.
                    JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, valikud, null);
            if (valik == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
