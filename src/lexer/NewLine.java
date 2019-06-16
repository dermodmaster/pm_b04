package lexer;

import java.util.regex.Pattern;

public class NewLine extends Token {
    String content;
    Pattern pattern = Pattern.compile("(\\r\\n|\\n)");;

    public NewLine(){
        this.content = "";
        super.pattern = pattern;
    }

    public NewLine(String content) {
        if(content == null){
            this.content = "";
        }
        this.content = content;
        super.pattern = pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1);
        }
        return new NewLine(output);
    }
    @Override
    protected String htmlStart() {
        return "<br>";
    }
    @Override
    protected String htmlEnd() {
        return null;
    }

    @Override
    protected String getContent() {
        return content;
    }
}
