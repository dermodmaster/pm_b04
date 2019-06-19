package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import static lexer.Main.log;


public class Lexer {
    private List<Token> token = new ArrayList<Token>();
    private Token catchAll;

    public void registerToken(Token input){
        this.token.add(input);
    }


    public void registerToken(List<Token> inputList){
        for(Token element:inputList){
            this.token.add(element);
            //Der ConsolHandler wird erstellt und immer die aktuelle klasse genommen
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.FINER);
            handler.setFormatter(new Formatter("A",element.getClass().getName(),"registerToken"));
            log.addHandler(handler);
        }
    }

    public void registerCatchAll(Token input){
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        handler.setFormatter(new Formatter("A",input.getClass().getName(),"registerCatchAll"));
        log.addHandler(handler);
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
                //Wenn ja, dann den neuen lexer.Token in die Output Liste hinzufügen
                return newToken;
            }
        }
        Token newCatchAllToken = catchAll.match(input);
        return newCatchAllToken;
    }
}
