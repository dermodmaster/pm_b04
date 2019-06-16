package lexer;

import java.util.regex.Pattern;

public class Comment extends Token {
    String content;
    Pattern pattern = Pattern.compile("(\\/{2}.*)");

    public Comment(){
        this.content = "";
        super.pattern = pattern;
    }

    public Comment(String content) {
        if(content == null){
            this.content = "";
        }
        this.content = content;
        super.pattern = pattern;
    }

    @Override
    protected Token getToken() throws IllegalStateException {
        String output="";
        if(matcher.find() && matcher.start() == 0){
                output = matcher.group(1);
        }
        return new Comment(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#A9A9A9\"><i>";
    }
    @Override
    protected String htmlEnd() {
        return "</i></font>";
    }

    @Override
    protected String getContent() {
        return content;
    }
}
