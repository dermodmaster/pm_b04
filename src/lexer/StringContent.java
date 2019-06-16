package lexer;

import java.util.regex.Pattern;

public class StringContent extends Token {
    String content;
    Pattern pattern = Pattern.compile("(\")(.*)(\")");

    public StringContent(){
        this.content="";
        super.pattern=pattern;
    }

    public StringContent(String content) {
        this.content = content;
        super.pattern=pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1)+ matcher.group(2)+matcher.group(3);
        }
        return new StringContent(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#32CD32\"><b>";
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
