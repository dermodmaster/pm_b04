package lexer;

import java.util.regex.Pattern;

public class CatchAll extends Token {
    String content;
    Pattern pattern = Pattern.compile("(.)");

    public CatchAll(){
        this.content = "";
        super.pattern = pattern;
    }

    public CatchAll(String content){
        this.content = content;
        super.pattern = pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1);
        }
        return new CatchAll(output);
    }

    @Override
    protected String htmlStart() {
        return null;
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
