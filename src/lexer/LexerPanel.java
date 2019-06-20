package lexer;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Das Panel welches den Lexer benutzt
 * @author LeventK
 */
public class LexerPanel {
    String code;
    String content;
    Lexer lexer;
    JTextPane textPane1 = new JTextPane();
    JTextPane editorPane1 = new JTextPane();
    JFrame f = new JFrame();

    /**
     * Konsturktor erstellet das Pannel
     * @param code Der Eingelesnen Code
     * @param lexer der Lexer
     */
    public LexerPanel(String code, Lexer lexer){
        this.lexer = lexer;
        this.code = code;

        textPane1.setContentType("text/html");
        textPane1.setText(this.content);

        editorPane1.setText(this.code);

        JButton button = new JButton("Aktualisieren");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
            }
        });

        editorPane1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                updateContent();
            }
        });

        JSplitPane splitpane = new JSplitPane();
        JSplitPane editorSplitPanel = new JSplitPane();

        editorSplitPanel.setTopComponent(editorPane1);
        editorSplitPanel.setBottomComponent(button);
        editorSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        editorSplitPanel.setDividerLocation(600);

        splitpane.setTopComponent(editorSplitPanel);
        splitpane.setBottomComponent(textPane1);
        splitpane.setDividerLocation(300);


        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(splitpane);
        f.setSize(1280, 720);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setTitle("Lexer v1.0 - Last preview update: -");

        updateContent();
    }

    /**
     * guckt sich die verändenrung im Code an und ruft die lexer methoden auf zur bestimmung der Toxen
     */
    public void updateContent(){
        this.code = this.editorPane1.getText();
        List<Token> tokens = lexer.tokenize(code);

        String bodycontent = "";
        for(Token current : tokens){
            bodycontent += current.getHTML();
        }

        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"de\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Titel der Seite | Name der Website</title>\n" +
                "  </head>\n" +
                "  <body style=\"font-size: 15px;\">\n" +
                bodycontent +
                "\n  </body>\n" +
                "</html>";
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = java.time.Clock.systemUTC().instant().toString();
        f.setTitle("Lexer v1.0 - Last preview update: "+time);
        System.out.println(time+": Lexer content refreshed...");
        this.textPane1.setText(content);
    }

}
