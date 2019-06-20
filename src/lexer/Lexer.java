package lexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Der Lexer
 * @author TimoK,KochL
 */
public class Lexer {
    public static Logger log =Logger.getLogger(Lexer.class.getName());
    ConsoleHandler consoleHandler;
    FileHandler fileHandler;

    /**
     * Konstruktor
     * setzt die Logger das loglevel ,einene Start Formatter und fügt die handler dem log hinzu
     */
    public Lexer(){
        log.setUseParentHandlers(false);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        log.addHandler(consoleHandler);
        consoleHandler.setFormatter(new Formatter("Lexer","",""));
        try {
            fileHandler=new FileHandler("log.html");
            fileHandler.setLevel(Level.FINEST);
            fileHandler.setFormatter(new HtmlFormatter("Lexer","",""));
            log.addHandler(fileHandler);
        }
        catch (IOException ioe){
            System.out.println("nope");

        }


    }


    private List<Token> token = new ArrayList<Token>();
    private Token catchAll;

    /**
     * Registiert einen Token im Lexer
     * @param input ein Token
     */
    public void registerToken(Token input){
        this.token.add(input);
    }

    /**
     * Registriert eine Liste an Token im Lexer
     * @param inputList ein Liste mit Token
     */
    public void registerToken(List<Token> inputList){
        consoleHandler.setFormatter(new Formatter("A","Lexer","registerToken"));
        fileHandler.setFormatter(new HtmlFormatter("A","Lexer","registerToken"));
        for(Token element:inputList){
            this.token.add(element);
            //Der ConsolHandler wird erstellt und immer die aktuelle klasse genommen
            log.info("Regestriere "+element.getClass().toString());
        }
    }

    /**
     * Registiert einen Token im Lexer
     * @param input Den CatchAll token
     */
    public void registerCatchAll(Token input){
        consoleHandler.setFormatter(new Formatter("B","Lexer","registerCatchAll"));
        fileHandler.setFormatter(new HtmlFormatter("B","Lexer","registerCatchAll"));
        log.info("Regestriere "+input.getClass().toString());
        this.catchAll = input;
    }

    /**
     * Erstellt aus dem String die Tokens
     * @param input Das zu wandelnde String
     * @return Liste mit allen Tokens
     */
    public List<Token> tokenize(String input){
        List<Token> output = new ArrayList<Token>();

        while (input.length() != 0){
            Token currentToken = testTokens(input);
            input = input.substring(currentToken.getContent().length());
            output.add(currentToken);
        }
        return output;
    }

    /**
     *Überprüft welcher Token der String ist
     * @param input den zu erkennenden String
     * @return den gefundenen Token
     */
    private Token testTokens(String input){
        consoleHandler.setFormatter(new Formatter("C","Lexer","testTokens"));
        fileHandler.setFormatter(new HtmlFormatter("C","Lexer","testTokens"));
        for(Token currentToken : token){
            //Für jeden verfügbaren lexer.Token aus der Liste die Match Methode ausführen
            Token newToken = currentToken.match(input);
            //Gucken ob der Inhalt von dem neuen lexer.Token existiert
            if(newToken.getContent().length() != 0){
                log.finer("gefunden "+newToken.getClass().getName()+" Token");
                log.finest("Das Token ist :"+newToken.getContent());
                //Wenn ja, dann den neuen lexer.Token in die Output Liste hinzufügen
                return newToken;
            }
        }
        Token newCatchAllToken = catchAll.match(input);
        log.finer("gefunden "+newCatchAllToken.getClass().getName()+" Token");
        log.finest("Das Token ist :"+newCatchAllToken.getContent());
        return newCatchAllToken;
    }
}
