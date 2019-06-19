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
                String datei=null;
                if (cmd.hasOption("r")) {
                    String[] arg = cmd.getOptionValues("r");
                    System.out.println("DateiPfad : " + arg[0]);
                    datei=arg[0];
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

                File folder = new File(datei);
                if(!folder.exists()){
                    System.out.println("Datei nicht vorhanden");
                    System.exit(1);
                }
                URL[] ua = new URL[]{folder.toURI().toURL()};
                URLClassLoader ucl = URLClassLoader.newInstance(ua);

                ArrayList<File> classFiles = getClassFiles(folder);


                //Nach Prios sortieren
                ArrayList<Token> prioA = new ArrayList<Token>();
                ArrayList<Token> prioB = new ArrayList<Token>();
                ArrayList<Token> prioC = new ArrayList<Token>();
                ArrayList<Token> prio1 = new ArrayList<Token>();
                ArrayList<Token> prio2 = new ArrayList<Token>();
                ArrayList<Token> prio3 = new ArrayList<Token>();
                Token catchAll = null;

                for(File currentFile : classFiles){
                    System.out.println(currentFile.getName());
                    String[] splittedFileName = currentFile.getName().split("[.]");
                    String classname = "";
                    try {
                        classname = splittedFileName[0];
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("Datei hat keine Dateityperweiterung im Namen");
                    }
                    Class<?> c1 = null;
                    try {
                        //Todo: Frage? Wird wirklich geschaut ob die Klasse im Lexer Package ist?
                        c1 = Class.forName("lexer."+classname, true, ucl);
                    }catch (ClassNotFoundException e){
                        System.out.println("Nix gefunden");
                    }
                    try{
                        Token annotation = (Token) c1.newInstance();
                        Annotation[] lol = annotation.getClass().getDeclaredAnnotations();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    //Todo: Nach Annotations schauen und zuordnen
                }




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

    public static ArrayList<File> getClassFiles(final File folder) {
        ArrayList<File> files = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                files.addAll(getClassFiles(fileEntry));
            } else {
                String filename = fileEntry.getName();
                String[] splittedFileName = filename.split("[.]");
                String classname = "";
                try {
                    classname = splittedFileName[splittedFileName.length-1];
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Datei hat keine Dateityperweiterung im Namen");
                }
                if(classname.equals("class")) files.add(fileEntry);
            }
        }
        return files;
    }

}
