package org.modelselection.ranking;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public class DocumentSet {

    public Set<Document> documents = new TreeSet<Document>();

    void add(Document document) {
        documents.add(document);
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments() {
        this.documents = documents;
    }

}
