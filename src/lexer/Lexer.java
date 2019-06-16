package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private List<Token> token = new ArrayList<Token>();
    private Token catchAll;

    public void registerToken(Token input){
        this.token.add(input);
    }

    public void registerToken(List<Token> inputList){
        for(Token element:inputList){
            this.token.add(element);
        }
    }

    public void registerCatchAll(Token input){
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
