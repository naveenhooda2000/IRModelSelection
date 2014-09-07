package org.modelselection.model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.modelselection.index.Indexer;
import org.modelselection.index.TwitterIndexFieldConstants;
import org.modelselection.ranking.DocumentSet;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public class LanguageModel implements RetrievalModel {

    @Override
    public DocumentSet generateRanking(String indexpath, String strQuery) {
        try {
            Directory directory = FSDirectory.open(new File(indexpath));
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
            QueryParser parser = new QueryParser(Version.LUCENE_47, "text", analyzer);
            Query query = parser.parse(strQuery);
            System.out.println(indexpath);
            System.out.println(query.toString());
            TopDocs hits = isearcher.search(query, 100);
            System.out.println("Total Hits " + hits.totalHits);
            DocumentSet documentSet = new DocumentSet();
            for (int i = 0; i < hits.totalHits; i++) {
                ScoreDoc scoreDoc = hits.scoreDocs[i];
                Document hit = isearcher.doc(scoreDoc.doc);
                String docno = hit.getField("id").stringValue();
                String tweetText = hit.getField("text").stringValue();
                org.modelselection.ranking.Document document = new org.modelselection.ranking.Document(i+1, 0.9);
                System.out.println("MB001" + " Q0 " + docno + " " + (i + 1)
                        + " " + (i+100) + " Luc");
                System.out.println(tweetText);
            }
            ireader.close();
            directory.close();
        } catch (IOException e) {
            System.out.println("Exception occured " + e.toString());
        } catch (ParseException e) {

        }
        return null;
    }
}
