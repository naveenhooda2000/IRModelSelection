package org.modelselection.index;

import org.apache.lucene.document.Document;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public interface DocumentIterator {
    Document next() throws Exception;
    boolean hasNext();
}