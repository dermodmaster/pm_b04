package lexer;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HtmlFormatter extends Formatter{

    public HtmlFormatter(String n, String klasse, String methode) {
        super(n, klasse, methode);
    }
    @Override
    public String format(LogRecord record) {
        String msg = super.format(record).replace("\n", "<br>");
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
