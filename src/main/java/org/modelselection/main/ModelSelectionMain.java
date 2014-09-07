package org.modelselection.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.modelselection.index.TwitterIndexer;
import org.modelselection.model.LanguageModel;
import org.modelselection.model.RetrievalModel;

import java.io.IOException;

/**
 * Created by Naveen Kumar on 8/24/14.
 */

public class ModelSelectionMain {

    public static String dataPath = "/var/lib/atguser/data/data/data/collections/microblog2011/corpus";
    public static String indexPath = "/var/lib/atguser/data/data/data/collections/microblog2011/index";
    public static void main(String args[]) {
        try {
            ModelSelectionMain modelSelectionMain = new ModelSelectionMain();
            boolean index = false;
            boolean retrive = true;
            if(index) {
                modelSelectionMain.buildIndex(dataPath, indexPath);
            }
            if(retrive) {
                LanguageModel model = new LanguageModel();
                model.generateRanking(indexPath, "sachin");
            }
        } catch (Exception exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    void buildIndex(String dataPath, String indexPath) throws Exception {
        TwitterIndexer twitterIndexer = new TwitterIndexer(dataPath);
        if(twitterIndexer.generateIndex(indexPath)) {
            System.out.println("Index Build Successfully!!");
        } else {
            System.err.println("Could n't build index");
        }
    }

}
