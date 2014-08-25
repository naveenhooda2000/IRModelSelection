package org.modelselection.index;

import org.apache.lucene.document.Document;

/**
 * Created by nkumar on 8/24/14.
 */
public interface DocumentIterator {
    Document next();
    boolean hasNext();
}