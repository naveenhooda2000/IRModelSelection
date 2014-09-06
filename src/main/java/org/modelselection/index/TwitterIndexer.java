package org.modelselection.index;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveen Kumar on 9/5/14.
 */

public class TwitterIndexer implements Indexer {

    private String dataPath;

    public TwitterIndexer(String dataPath) {
        this.dataPath = dataPath;
    }

    public boolean generateIndex(String indexPath) throws Exception {
        File[] fileNames = getXMLFiles(new File(dataPath));
        if (fileNames.length == 0) {
            return false;
        }
        Directory indexDir = FSDirectory.open(new File(indexPath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_47, null);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(indexDir, indexWriterConfig);
        for(int fileNumber = 0; fileNumber < fileNames.length; fileNumber++ ) {
            TwitterDocumentIterator twitterDocumentIterator = new TwitterDocumentIterator(fileNames[fileNumber].toString());
            Document document = twitterDocumentIterator.next();
            while (document != null) {
                indexWriter.addDocument(document);
                document = twitterDocumentIterator.next();
            }
        }
        indexWriter.close();
        return true;
    }

    public static File[] getXMLFiles(File folder) {
        List<File> aList = new ArrayList<File>();
        File[] files = folder.listFiles();
        for (File pf : files) {
            if (pf.isFile() && getFileExtensionName(pf).indexOf("xml") != -1) {
                aList.add(pf);
            }
        }
        return aList.toArray(new File[aList.size()]);
    }

    public static String getFileExtensionName(File f) {
        if (f.getName().indexOf(".") == -1) {
            return "";
        } else {
            return f.getName().substring(f.getName().length() - 3, f.getName().length());
        }
    }


}