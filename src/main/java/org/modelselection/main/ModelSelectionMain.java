package org.modelselection.main;

import org.apache.commons.cli.*;
import org.modelselection.index.TwitterIndexer;
import org.modelselection.model.LanguageModel;
import org.modelselection.model.RetrievalModel;
import org.modelselection.ranking.Document;
import org.modelselection.ranking.DocumentSet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveen Kumar on 8/24/14.
 */

public class ModelSelectionMain {

    public static String dataPath = "/home/raghav/Microblog/data/data/data/collections/microblog2011/corpus";
    public static String indexPath = "/home/raghav/Microblog/index";
    public static String outputFile = "/home/raghav/Microblog/output/LMJelinekMercerSimilarity.txt";
    public static String queryFile = "/home/raghav/Microblog/topics/queries.txt";

    public static void main(String args[]) {

        try {
            CommandLineParser parser = new BasicParser();
            Option dataDirectoryOption = OptionBuilder.hasArgs(1).withArgName("data directory")
                    .withDescription("This is the directory which contains data. for example for microblog it contains xml files").isRequired(false)
                    .withLongOpt("datadir").create("data");

            Option indexDirectoryOption = OptionBuilder.hasArgs(1).withArgName("index directory")
                    .withDescription("This is the  directory where index exists or to be created").isRequired(false)
                    .withLongOpt("indexdir").create("index");

            Option queryFileOption = OptionBuilder.hasArgs(1).withArgName("query file")
                    .withDescription("This is the  query file which has all queries to be run for different models").isRequired(false)
                    .withLongOpt("queryfile").create("query");

            Option outputDirectoryOption = OptionBuilder.hasArgs(1).withArgName("models output directory")
                    .withDescription("This is the  query file which has all queries to be run for different models").isRequired(false)
                    .withLongOpt("outputdir").create("output");

            Option actionOption = OptionBuilder.hasArgs(1).withArgName("action to be done")
                    .withDescription("This is the  action to be perform by the program for e.g. index, run etc.").isRequired(true)
                    .withLongOpt("action").create("action");

            Options options = new Options();
            options.addOption(dataDirectoryOption);
            options.addOption(indexDirectoryOption);
            options.addOption(queryFileOption);
            options.addOption(outputDirectoryOption);
            options.addOption(actionOption);

            CommandLine commandLine = parser.parse(options, args);
            String performAction = commandLine.getOptionValue("action");

            ModelSelectionMain modelSelectionMain = new ModelSelectionMain();
            if(performAction.equals("index")) {
                System.out.println(commandLine.getOptionValue("action"));
            } else if(performAction.equals("run")) {
                String queryFile = commandLine.getOptionValue("query");
                String outputPath = commandLine.getOptionValue("output");
                if(queryFile == null || queryFile.trim().length() == 0) {
                    System.out.println("must specify query option, query can't be empty");
                }
                if(outputPath == null || outputPath.trim().length() == 0) {
                    System.err.println("Must specify output option for output directory");
                }
                List<String> models = new ArrayList<String>();
                models.add("");

            } else {
                System.out.println(performAction+"is not supported!!");
            }
            /*
            ModelSelectionMain modelSelectionMain = new ModelSelectionMain();
            boolean index = false;
            boolean retrieve = true;
            if(index) {
                modelSelectionMain.buildIndex(dataPath, indexPath);
            }
            if(retrieve) {
                if(!(new File(queryFile).exists())) {
                    System.out.println("Query File doesn't exists!!");
                    System.exit(0);
                } else {
                    BufferedReader br = new BufferedReader(new FileReader(queryFile));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        String tokens[] = line.trim().split("\t");
                        if(tokens.length == 3) {
                            LanguageModel model = new LanguageModel();
                            int queryNum = Integer.parseInt(tokens[0].replace("MB","").trim());
                            DocumentSet documentSet = model.generateRanking(indexPath, Integer.toString(queryNum), tokens[1],tokens[2]);
                            for(Document doc : documentSet.getDocuments()) {
                                bw.write(doc.getQueryid()+" Q0 "+doc.getName()+" "+doc.getRank()+" "+doc.getScore()+"" +
                                        " LangModel");
                                bw.newLine();
                                bw.flush();
                            }
                        }else {
                            System.out.println(line);
                            System.out.println("Not able to tokenize"+tokens.length);
                            System.exit(0);
                        }
                    }
                    bw.close();
                    br.close();
                }
            }*/
        } catch (Exception exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private List<String> getRetrievalModels () {
        List<String>
                 
    }
    private void buildIndex(String dataPath, String indexPath) throws Exception {
        TwitterIndexer twitterIndexer = new TwitterIndexer(dataPath);
        if(twitterIndexer.generateIndex(indexPath)) {
            System.out.println("Index Build Successfully!!");
        } else {
            System.err.println("Could n't build index");
        }
    }

}
