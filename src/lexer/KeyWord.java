package lexer;

import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.util.regex.Pattern;

public class KeyWord extends Token{
    String content;
    Pattern pattern = Pattern.compile("(import|class|public|private|final|static|return|if|else|while|try|catch|finally|void)");
    public KeyWord(){
        this.content="";
        super.pattern=pattern;
    }

    public KeyWord(String content) {
        this.content = content;
        super.pattern=pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1);
        }
        return new KeyWord(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#00008B\"><b>";
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
