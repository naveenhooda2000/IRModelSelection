package org.modelselection.model;

import org.modelselection.index.Indexer;
import org.modelselection.ranking.DocumentSet;
import org.modelselection.ranking.DocumentSet;

import java.util.List;

public interface RetrievalModel {

    DocumentSet generateRanking(Indexer indexer, String query);
}
