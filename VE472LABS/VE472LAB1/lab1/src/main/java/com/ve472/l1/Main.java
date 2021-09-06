package com.ve472.l1;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static final Options parseOptions = new Options();
    private static CommandLine commandLine;

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        initCliArgs(args);
        String queryFile = commandLine.getParsedOptionValue("query").toString();
        String hallConfigDir = commandLine.getParsedOptionValue("hall").toString();
        Cinema cinema = new Cinema(hallConfigDir);
        cinema.handleQuery(queryFile);
    }

    private static void initCliArgs(String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();
        parseOptions.addOption(Option.builder("h").longOpt("help").type(String.class).desc("print this message").build());
        parseOptions.addOption(Option.builder().longOpt("hall").required().hasArg().argName("arg").type(String.class).desc("path of the hall config directory").build());
        parseOptions.addOption(Option.builder().longOpt("query").required().hasArg().argName("arg").type(String.class).desc("query of customer orders").build());
        try {
            commandLine = commandLineParser.parse(parseOptions, args);
            if (commandLine.hasOption("help")) {
                throw new ParseException("");
            }
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("cinema", parseOptions, false);
            System.exit(0);
        }
    }
}
