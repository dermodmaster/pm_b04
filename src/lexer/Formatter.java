package lexer;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class Formatter extends SimpleFormatter {
    private String name;
    private String klasse;
    private String methode;

    public Formatter(String n,String klasse,String methode) {
        name = n;
        this.klasse=klasse;
        this.methode=methode;
    }

    @Override
    public String format(LogRecord record) {
        return "---- " + "\nLogger :"+name + "\n\tLevel :"+record.getLevel()+"\n\tClass:" +klasse+"\n\tMethode: "+methode+"\n\tMessage:" +record.getMessage() + "\n ----\n";
    }
}
