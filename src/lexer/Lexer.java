package lexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Lexer {
    public static Logger log =Logger.getLogger(Lexer.class.getName());
    ConsoleHandler consoleHandler;
    FileHandler fileHandler;
    public Lexer(){
        log.setLevel(Level.FINER);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINER);
        log.addHandler(consoleHandler);
        consoleHandler.setFormatter(new Formatter("A","",""));
        try {
            fileHandler = new FileHandler();
        }
        catch (IOException ioe){
        }
        fileHandler.setLevel(Level.FINEST);
        log.addHandler(fileHandler);

    }


    private List<Token> token = new ArrayList<Token>();
    private Token catchAll;

    public void registerToken(Token input){
        this.token.add(input);
    }


    public void registerToken(List<Token> inputList){
        consoleHandler.setFormatter(new Formatter("A","Lexer","registerToken"));
        for(Token element:inputList){
            this.token.add(element);
            //Der ConsolHandler wird erstellt und immer die aktuelle klasse genommen
            log.info("Regestriere "+element.getClass().toString());
        }
    }

    public void registerCatchAll(Token input){
        log.info("Regestriere "+input.getClass().toString());
        this.catchAll = input;
    }

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
     *
     * @param input
     * @return
     */
    private Token testTokens(String input){
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
