package lexer;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Formatter des loggers
 * @author TimoK
 */
public class Formatter extends SimpleFormatter {
    private String name;
    private String klasse;
    private String methode;

    /**
     * Konstruktor
     * @param n Der Name des Formatters
     * @param klasse Die Klasse in der der Formatter ist
     * @param methode Die Methode in der der Formatter ist
     */
    public Formatter(String n,String klasse,String methode) {
        name = n;
        this.klasse=klasse;
        this.methode=methode;
    }

    /**
     * dass Format
     * @param record der LogRecord
     * @return das Format als String
     */
    @Override
    public String format(LogRecord record) {
        return "---- " + "\nLogger :"+name + "\n\tLevel :"+record.getLevel()+"\n\tClass:" +klasse+"\n\tMethode: "+methode+"\n\tMessage:" +record.getMessage() + "\n ----\n";
    }
}
