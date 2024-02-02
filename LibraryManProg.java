package a2_2001040230;

import a2_2001040230.page.SwingPageUI;

import java.awt.*;

public class LibraryManProg {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SwingPageUI swingUI = new SwingPageUI();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
