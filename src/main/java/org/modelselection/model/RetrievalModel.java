package org.modelselection.model;

import org.modelselection.index.Indexer;
import org.modelselection.ranking.DocumentSet;
import org.modelselection.ranking.DocumentSet;

import java.util.List;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public interface RetrievalModel {

    DocumentSet generateRanking(String indexPath, String query);
}
