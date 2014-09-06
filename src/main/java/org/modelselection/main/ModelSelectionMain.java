package org.modelselection.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.modelselection.index.TwitterIndexer;

/**
 * Created by Naveen Kumar on 8/24/14.
 */

public class ModelSelectionMain {

    public static void main(String args[]) {
        CommandLineParser parser = new BasicParser();
        try {
            String dataPath = "";
            String indexPath = "";
            TwitterIndexer twitterIndexer = new TwitterIndexer(dataPath);
            if(twitterIndexer.generateIndex(indexPath)) {
                System.out.println("Index Build Successfully!!");
            } else {
                System.err.println("Could n't build index");
            }
        } catch (Exception exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

}
