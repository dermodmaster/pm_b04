package lexer;

import java.util.regex.Pattern;

public class JavaDocComment extends Token {
    String content;
    Pattern pattern = Pattern.compile("(\\/\\*\\*)(.|\\n)*?(\\*\\/)");

    public JavaDocComment(){
        content="";
        super.pattern=pattern;
    }
    public JavaDocComment(String content) {
        this.content = content;
        super.pattern=pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group();
        }
        return new JavaDocComment(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#00aaff\"><i><b>";
    }
    @Override
    protected String htmlEnd() {
        return "</b></i></font>";
    }

    @Override
    protected String getContent() {
        return content;
    }
}
