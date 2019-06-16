package lexer;

import java.util.regex.Pattern;

public class MultilineComment extends Token {
    String content;
     Pattern pattern = Pattern.compile("(\\/\\*[^*])(.|\\n)*?(\\*\\/)");

     public MultilineComment(){
         this.content="";
         super.pattern=pattern;
     }

    public MultilineComment(String content) {
        this.content = content;
        super.pattern=pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group();
        }
        return new MultilineComment(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#708090\"><i>";
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
