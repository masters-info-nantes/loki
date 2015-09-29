package fr.alma.middleware;

import fr.alma.middleware.ui.MainWindow;
import java.awt.Color;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        MainWindow w = new MainWindow("Loki client");
        w.setNickname("pseudo");
        w.setMessage("Something like that");
        System.out.println(w.getNickname());
        System.out.println(w.getMessage());
        w.appendToHistory("Ahah !!",Color.RED);
        w.appendToHistory("\nDoctor : ",Color.DARK_GRAY);
        w.appendToHistory("TARDIS",Color.GREEN);
    }
}
