package org.modelselection.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Created by nkumar on 8/24/14.
 */

public class ModelSelectionMain {

    public static void main(String args[]) {
        CommandLineParser parser = new BasicParser();
        try {
            // parse the command line arguments
            Options options = new Options();
            CommandLine line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

}
