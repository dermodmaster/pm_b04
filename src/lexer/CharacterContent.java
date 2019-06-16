package lexer;

import java.util.regex.Pattern;

public class CharacterContent extends Token {
    String content;
    Pattern pattern = Pattern.compile("(')(.*)(')");
    public CharacterContent(){
        this.content="";
        super.pattern=pattern;
    }

    public CharacterContent(String content) {
        this.content = content;
        super.pattern=pattern;
    }

    @Override
    protected Token getToken() {
        String output="";
        if(matcher.find() && matcher.start() == 0){
            output = matcher.group(1)+matcher.group(2)+matcher.group(3);
        }
        return new CharacterContent(output);
    }
    @Override
    protected String htmlStart() {
        return "<font color=\"#006400\"><b>";
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
