package lexer;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * HtmlFormatter des loggers
 * @author TimoK
 */
public class HtmlFormatter extends Formatter {
    private String name;
    private String klasse;
    private String methode;
    /**
     * Konstruktor
     * @param n Der Name des Formatters
     * @param klasse Die Klasse in der der Formatter ist
     * @param methode Die Methode in der der Formatter ist
     */
    public HtmlFormatter(String n, String klasse, String methode) {
        name=n;
        this.klasse=klasse;
        this.methode=methode;
    }
    @Override
    public String getHead(Handler h){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"de\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Titel der Seite | Name der Website</title>\n" +
                "  </head>\n" +
                "  <body style=\"font-size: 15px;\">\n";
    }

    @Override
    public String getTail(Handler h){
        return "\n  </body>\n" +
                "</html>";
    }
    /**
     * dass Format
     * @param record der LogRecord
     * @return das Format als html String
     */
    @Override
    public String format(LogRecord record) {
        String msg = "---- " + "<br>Logger :"+name + "<br>\tLevel :"+record.getLevel()+"<br>\tClass:" +klasse+"<br>\tMethode: "+methode+"<br>\tMessage:" +record.getMessage() + "<br> ----<br>";
        StringBuilder builder = new StringBuilder();
        if (record.getLevel() == Level.SEVERE) {
            builder.append("<font color=\"#FF0000\"><b>");
            builder.append(msg);
            builder.append("</b></font>");
        } else if(record.getLevel()==Level.WARNING){
            builder.append("<font color=\"#FFF700\"><b>");
            builder.append(msg);
            builder.append("</b></font>");
        }  else if(record.getLevel()==Level.INFO){
            builder.append("<font color=\"#00FF00\"><i>");
            builder.append(msg);
            builder.append("</i></font>");
        }   else if(record.getLevel()==Level.FINE){
            builder.append("<font color=\"#00DCFF\">");
            builder.append(msg);
            builder.append("</font>");
        }   else if(record.getLevel()==Level.FINER){
            builder.append("<font color=\"#0000FF\">");
            builder.append(msg);
            builder.append("</font>");
        }   else if(record.getLevel()==Level.FINEST){
            builder.append("<font color=\"#0074FF\">");
            builder.append(msg);
            builder.append("</font>");
        }

        return builder.toString();
    }
}
