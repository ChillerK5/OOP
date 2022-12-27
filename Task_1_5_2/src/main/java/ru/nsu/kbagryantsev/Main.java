package ru.nsu.kbagryantsev;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Notebook console application.
 */
public final class Main {
    private Main() {
        //Utils class constructor prohibition
    }

    /**
     * Instantiates command line args structure.
     *
     * @param argv CLI arguments
     * @return {@link CommandLine} instance
     */
    public static CommandLine commandLineArgs(final String[] argv)
            throws ParseException {
        Options options = new Options();
        Option path = Option
                .builder("path")
                .desc("Specified filepath")
                .numberOfArgs(1)
                .argName("Notebook filepath")
                .build();
        Option add = Option
                .builder("add")
                .desc("Adds a new record to notebook")
                .numberOfArgs(2)
                .argName("Record's title and text")
                .build();
        Option remove = Option
                .builder("rm")
                .desc("Removes a record from notebook")
                .numberOfArgs(1)
                .argName("Record's title")
                .build();
        Option show = Option
                .builder("show")
                .desc("Shows notebook records")
                .optionalArg(true)
                .hasArgs()
                .argName("Time filter: from-to period, title's keywords filter")
                .build();
        options
                .addOption(add)
                .addOption(path)
                .addOption(remove)
                .addOption(show);
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, argv);
    }

    /**
     * Notebook console application. Executes a given action to a specified
     * notebook JSON file representation. Call -help to get detailed info.
     *
     * @param argv CLI args
     */
    public static void main(final String[] argv)
            throws IOException, ParseException, java.text.ParseException {
        CommandLine commandLine = commandLineArgs(argv);

        //Getting filepath from cli arguments
        String path;
        if (commandLine.hasOption("path")) {
            path = commandLine.getOptionValue("path");
        } else {
            path = "./src/main/resources/Notebook.json";
        }

        //Instantiating notebook deserializer
        //Getting current notebook version from JSON file
        FileReader fileReader = new FileReader(path);
        NotebookDeserializer notebookDeserializer;
        notebookDeserializer = new NotebookDeserializer(fileReader);
        Notebook notebook = notebookDeserializer.deserialize();

        //Handling given cli options
        if (commandLine.hasOption("add")) {
            String[] args = commandLine.getOptionValues("add");

            String title = args[0];
            String text = args[1];

            notebook.addRecord(title, text);

            FileWriter fileWriter = new FileWriter(path);
            NotebookSerializer notebookSerializer;
            notebookSerializer = new NotebookSerializer(fileWriter);
            notebookSerializer.serialize(notebook);

        } else if (commandLine.hasOption("rm")) {
            String title = commandLine.getOptionValue("rm");

            notebook.removeRecord(title);

            FileWriter fileWriter = new FileWriter(path);
            NotebookSerializer notebookSerializer;
            notebookSerializer = new NotebookSerializer(fileWriter);
            notebookSerializer.serialize(notebook);

        } else if (commandLine.hasOption("show")) {
            String[] args = commandLine.getOptionValues("show");

            if (args == null) {
                System.out.print(notebook.showRecords());
            } else {
                SimpleDateFormat dateFormat;
                dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                Date start;
                Date end;
                start = dateFormat.parse(args[0]);
                end = dateFormat.parse(args[1]);
                String[] keywords = Arrays.copyOfRange(args, 2, args.length);

                System.out.print(notebook.showRecords(start, end, keywords));
            }
        }
    }
}
