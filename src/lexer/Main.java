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
    public static void main(String[] args) {
        // create Options object

        Options options = new Options();

        // add t option
        options.addOption("h", false, "hilfe ausgeben");
        options.addOption("l", true, "Deaktivierung/Aktivieren logger(0 Deaktiviert,1 Aktiviert)");
        Option option = new Option("r", true, "Eingabe eines Ordners, aus dem die Token per Reﬂection geladen werden sollen und Art der Priorisierung(0 Nach Typ,1 Nach Wert)");
        option.setArgs(2);
        options.addOption(option);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();

            formatter.printHelp("Optionen", options);
        } else {
            String datei = null;
            Boolean sortTyp = null;
            if (cmd.hasOption("r")) {
                String[] arg = cmd.getOptionValues("r");
                System.out.println("DateiPfad : " + arg[0]);
                datei = arg[0];
                if (arg[1].equals("0")) {
                    System.out.println("Reihenfolge nach Typ");
                    sortTyp = true;
                } else if (arg[1].equals("1")) {
                    System.out.println("Reihenfolge nach Wert");
                    sortTyp = false;
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

            File folder = new File(datei);
            if (!folder.exists()) {
                System.out.println("Datei nicht vorhanden");
                System.exit(1);
            }
            URL[] ua = null;
            try {
                ua = new URL[]{folder.toURI().toURL()};
            } catch (MalformedURLException mue) {
                System.out.println(mue.getMessage());
            }
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

            for (File currentFile : classFiles) {
                String[] splittedFileName = currentFile.getName().split("[.]");
                String classname = "";
                try {
                    classname = splittedFileName[0];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Datei hat keine Dateityperweiterung im Namen");
                }
                Class<?> c1 = null;
                try {
                    c1 = Class.forName("lexer." + classname, true, ucl);
                } catch (ClassNotFoundException e) {
                    System.out.println("Nix gefunden");
                }
                try {
                    Token token = (Token) c1.newInstance();
                    Annotation[] annotation = token.getClass().getDeclaredAnnotations();
                    if (annotation[0] instanceof CatchAll) {
                        catchAll = token;
                    } else if (annotation[0] instanceof PrioA) {
                        prioA.add(token);
                    } else if (annotation[0] instanceof PrioB) {
                        prioB.add(token);
                    } else if (annotation[0] instanceof PrioC) {
                        prioC.add(token);
                    }

                    Prio annotation1 = (Prio) annotation[1];
                    if (annotation1.value() == 1) {
                        prio1.add(token);
                    } else if (annotation1.value() == 2) {
                        prio2.add(token);
                    } else if (annotation1.value() == 3) {
                        prio3.add(token);
                    }
                } catch (ReflectiveOperationException e) {
                    System.out.println(e.getMessage());
                }
            }


            Lexer lexer = new Lexer();

            if (sortTyp) {
                lexer.registerToken(prioA);
                lexer.registerToken(prioB);
                lexer.registerToken(prioC);
            } else if (!sortTyp) {
                lexer.registerToken(prio1);
                lexer.registerToken(prio2);
                lexer.registerToken(prio3);
            }
            lexer.registerCatchAll(catchAll);
            LexerPanel lp = new LexerPanel(code, lexer);


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
                    classname = splittedFileName[splittedFileName.length - 1];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Datei hat keine Dateityperweiterung im Namen");
                }
                if (classname.equals("class")) files.add(fileEntry);
            }
        }
        return files;
    }

}
