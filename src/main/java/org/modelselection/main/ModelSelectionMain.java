package org.modelselection.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.modelselection.index.TwitterIndexer;
import org.modelselection.model.LanguageModel;
import org.modelselection.model.RetrievalModel;
import org.modelselection.ranking.Document;
import org.modelselection.ranking.DocumentSet;

import java.io.*;

/**
 * Created by Naveen Kumar on 8/24/14.
 */

public class ModelSelectionMain {

    public static String dataPath = "C:\\Users\\nkumar\\Microblog\\corpus";
    public static String indexPath = "C:\\Users\\nkumar\\Microblog\\index";
    public static String outputFile = "C:\\Users\\nkumar\\Microblog\\output\\DefaultModelOutput.txt";
    public static String queryFile = "C:\\Users\\nkumar\\Microblog\\queries\\queries.txt";

    public static void main(String args[]) {
        try {
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
                        String tokens[] = line.trim().split(" ", 2);
                        if(tokens.length == 2) {
                            LanguageModel model = new LanguageModel();
                            int queryNum = Integer.parseInt(tokens[0].replace("MB","").trim());
                            DocumentSet documentSet = model.generateRanking(indexPath, Integer.toString(queryNum), tokens[1]);
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
