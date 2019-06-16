package lexer;

import org.apache.commons.cli.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        // create Options object

        Options options = new Options();

        // add t option
        options.addOption("h", false, "hilfe ausgeben");
        options.addOption("l",true,"Deaktivierung/Aktivieren logger(0 Deaktiviert,1 Aktiviert)");
        Option option=new Option("r",true,"Eingabe eines Ordners, aus dem die Token per Reﬂection geladen werden sollen und Art der Priorisierung(0 Nach Typ,1 Nach Wert)");
        option.setArgs(2);
        options.addOption(option);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();

                formatter.printHelp("Optionen", options);
            } else {
                if (cmd.hasOption("r")) {
                    String[] arg = cmd.getOptionValues("r");
                    System.out.println("DateiPfad : " + arg[0]);
                    if (arg[1].equals("0")) {
                        System.out.println("Reihenfolge nach Typ");
                    } else if (arg[1].equals("1")) {
                        System.out.println("Reihenfolge nach Wert");
                    } else {
                        System.out.println("Falscher Wert bei der Priorisierung");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Bitte Einen Ordner angeben und die Priorisierung");
                    System.exit(0);
                }

                if (cmd.hasOption("l")) {
                    if (cmd.getOptionValue("l").equals("0")) {
                        System.out.println("Logger Deaktiviert");
                    } else if (cmd.getOptionValue("l").equals("1")) {
                        System.out.println("Logger Aktiviert");
                    } else {
                        System.out.println("Falsches Argument");
                    }
                }


                String code = "";
                List<Token> token = new ArrayList<Token>();

                File folder = new File("C:\\Users\\levic\\IdeaProjects\\pm_b04_tokens\\out\\production\\pm_b04_tokens\\");
                URL[] ua = new URL[]{folder.toURI().toURL()};
                URLClassLoader ucl = URLClassLoader.newInstance(ua);
                Class<?> c1 = null;
                try {
                    c1 = Class.forName("lexer.Annotation", true, ucl);
                }catch (ClassNotFoundException e){
                    System.out.println("Nix gefunden");
                }

                Token annotation = (Token) c1.newInstance();
                Annotation[] lol = annotation.getClass().getDeclaredAnnotations();
                showMethods(c1);
                System.out.println(c1.getDeclaredAnnotations());

                /*
                token.add(new Comment());
                token.add(new MultilineComment());
                token.add(new JavaDocComment());
                token.add(new StringContent());
                token.add(new CharacterContent());
                token.add(new KeyWord());
                token.add(new Annotation());
                token.add(new NewLine());
                CatchAll catchAll = new CatchAll("");
                */
                //Todo: add found token

                Lexer lexer = new Lexer();
                //Todo: register found Tokens
                /*
                lexer.registerToken(token);
                lexer.registerCatchAll(catchAll);
                */

                LexerPanel lp = new LexerPanel(code, lexer);


            }
        }
        catch(Exception pe) {
            System.out.println(pe.getMessage());
        }
    }

    private static void showMethods(Class<?> c) {
        Method[] allMethods = c.getDeclaredMethods();  // all methods (excl. inherited)

        for (Method m : allMethods) {
            System.out.println(m.toGenericString());
            System.out.println("  Modifiers:  " + Modifier.toString(m.getModifiers()));
        }
        System.out.println();
    }
}
