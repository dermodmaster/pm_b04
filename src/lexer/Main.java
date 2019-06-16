package lexer;

//import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        // create Options object
        /*
        Options options = new Options();

        // add t option
        options.addOption("h", false, "hilfe ausgeben");
        options.addOption("l",true,"Deaktivierung/Aktivieren logger(0 Deaktiviert,1 Aktiviert)");
        Option option=new Option("r",true,"Eingabe eines Ordners, aus dem die Token per Reﬂection geladen werden sollen und Art der Priorisierung(0 Nach Typ,1 Nach Wert)");
        option.setArgs(2);
        options.addOption(option);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd=null;
        */
        try {/*
             cmd= parser.parse(options, args);

            if(cmd.hasOption("h")) {
               HelpFormatter formatter=new HelpFormatter();

               formatter.printHelp("Optionen",options);
            }
            else {
                if(cmd.hasOption("r")){
                    String[]arg=cmd.getOptionValues("r");
                    System.out.println("DateiPfad : "+arg[0]);
                    if(arg[1].equals("0")){
                        System.out.println("Reihenfolge nach Typ");
                    }
                    else if(arg[1].equals("1")){
                        System.out.println("Reihenfolge nach Wert");
                    }
                    else{
                        System.out.println("Falscher Wert bei der Priorisierung");
                        System.exit(0);
                    }
                }
                else{
                    System.out.println("Bitte Einen Ordner angeben und die Priorisierung");
                    System.exit(0);
                }

                if(cmd.hasOption("l")){
                    if(cmd.getOptionValue("l").equals("0")){
                        System.out.println("Logger Deaktiviert");
                    }
                    else if(cmd.getOptionValue("l").equals("1")){
                        System.out.println("Logger Aktiviert");
                    }
                    else{
                        System.out.println("Falsches Argument");
                    }
                }*/

                
                String code = "";
                List<Token> token = new ArrayList<Token>();
                token.add(new Comment());
                token.add(new MultilineComment());
                token.add(new JavaDocComment());
                token.add(new StringContent());
                token.add(new CharacterContent());
                token.add(new KeyWord());
                token.add(new Annotation());
                token.add(new NewLine());
                CatchAll catchAll = new CatchAll("");

                Lexer lexer = new Lexer();
                lexer.registerToken(token);
                lexer.registerCatchAll(catchAll);


                LexerPanel lp = new LexerPanel(code, lexer);


        }
        catch(Exception pe) {
            System.out.println(pe.getMessage());
        }
    }
}
