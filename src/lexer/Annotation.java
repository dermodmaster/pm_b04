package lexer;

import java.util.regex.Pattern;

public class Annotation extends Token {
    String content;
    Pattern pattern = Pattern.compile("(@[\\s]*[A-Za-z]*)");

    public Annotation(){
        this.content="";
        super.pattern=pattern;
    }

    public Annotation(String content) {
        this.content = content;
        super.pattern=pattern;
    }


    @ Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1);
        }
        return new Annotation(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#bdb76b\"><b>";
    }
    @Override
    protected String htmlEnd() {
        return "</b></font>";
    }

    @Override
    protected String getContent() {
        return content;
    }
}
