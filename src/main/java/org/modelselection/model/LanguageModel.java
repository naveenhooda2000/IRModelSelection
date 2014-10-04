package org.modelselection.model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.modelselection.index.Indexer;
import org.modelselection.index.TwitterIndexFieldConstants;
import org.modelselection.ranking.DocumentSet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public class LanguageModel implements RetrievalModel {

    private Map<String, String> month = new HashMap<String, String>();

    public LanguageModel() {
        month.put("JAN",  "01");
        month.put("FEB",  "02");
        month.put("MAR",  "03");
        month.put("APR",  "04");
        month.put("MAY",  "05");
        month.put("JUN",  "06");
        month.put("JUL",  "07");
        month.put("AUG",  "08");
        month.put("SEP",  "09");
        month.put("OCT",  "10");
        month.put("NOV",  "11");
        month.put("DEC",  "12");
    }

    @Override
    public DocumentSet generateRanking(String indexpath, String queryId, String strQuery,String querytime) {
        DocumentSet documentSet = new DocumentSet();
        try {
            Directory directory = FSDirectory.open(new File(indexpath));
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
            QueryParser parser = new QueryParser(Version.LUCENE_47, "text", analyzer);
            Query query = parser.parse(strQuery);
            System.out.println(indexpath);
            System.out.println(query.toString());
            isearcher.setSimilarity(new LMJelinekMercerSimilarity(0.8f));
            //isearcher.setSimilarity(new LMDirichletSimilarity());
            //isearcher.setSimilarity(new BM25Similarity());
            //isearcher.setSimilarity(new DefaultSimilarity());
            TopDocs hits = isearcher.search(query, 100);
            System.out.println("Total Hits " + hits.totalHits);
            for (int i = 0; i < hits.totalHits && i < 100; i++) {
                ScoreDoc scoreDoc = hits.scoreDocs[i];
                Document hit = isearcher.doc(scoreDoc.doc);
                String docno = hit.getField("id").stringValue();
                String tweetText = hit.getField("text").stringValue();
                String qtime=hit.getField("time").stringValue();
                if(convertDate(qtime).getTime() < convertDate(querytime).getTime()) {
                    System.out.println(qtime);
                    org.modelselection.ranking.Document document = new org.modelselection.ranking.Document(queryId, docno, i + 1, i + 100);
                    documentSet.add(document);
                }
            }
            ireader.close();
            directory.close();
            System.out.println("Done with query " + queryId);
        } catch (Exception e) {
            System.out.println("Exception occured " + e.toString());
        }
        return documentSet;
    }

    public Date convertDate(String time) throws java.text.ParseException {
        String tokens[] = time.trim().split(" ");
        String formatedTime = month.get(tokens[1].toUpperCase())+"/"+tokens[2]+"/"+tokens[5]+" "+tokens[3];
        System.out.println(formatedTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = simpleDateFormat.parse(formatedTime);
        System.out.println(date.toString());
        return date;
    }

}


