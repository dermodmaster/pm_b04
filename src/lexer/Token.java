package lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Token {
    protected Pattern pattern;
    protected Matcher matcher;

    /**
     * Erzeugt aus dem Input String und dem internen Pattern ein Matcher Objekt.
     * Liefert unter verwendung von getToken() eine neue Instanz des konkreten
     * Tokens zurück.
     *
     * @param input - Eingabestring
     * @return - eine neue Instanz des konkreten Tokens
     */
    public final Token match(String input) {
        //Erstelle einen Matcher
        this.matcher = pattern.matcher(input);
        //Gebe eine neue Instanz des konkreten lexer.Token zurück
        return this.getToken();
    }

    /**
     * Erstellt HTML Text aus dem lexer.Token
     *
     * @return setzt sich zusammen aus: htmlStart()+getContet()+htmlEnd()
     */
    public final String getHTML() {
        String output = "";
        if (htmlStart() != null) {
            output += htmlStart();
        }

        if (getContent() != null) {
            output += getContent();
        }
        if (htmlEnd() != null) {
            output += htmlEnd();
        }
        return output;
    }

    /**
     * Liefert unter Nutzung des durch lexer.Token#match angelegten Matcher-Objekts
     * eine neue Instanz des konkreten Tokens zurück ("null" sonst)
     *
     * @return
     */
    protected abstract Token getToken();

    protected abstract String htmlStart();

    protected abstract String htmlEnd();

    protected abstract String getContent();
}
