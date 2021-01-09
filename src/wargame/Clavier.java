package wargame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Clavier implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        int key = e.getKeyChar();
        System.out.println(key);
        if (key == 'a') {
            System.out.println("Test");
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("yubhj");

        int key = e.getKeyCode();
                if (key == KeyEvent.VK_E) {
                    System.out.println("Réussite");
                }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("vgthbbbjygubhj");

        int key = e.getKeyCode();
                System.out.print(key);
                if (key == KeyEvent.VK_B) {
                    System.out.println("Zoulou");
                }

    }
    
}
