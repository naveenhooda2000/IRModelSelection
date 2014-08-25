package org.modelselection.model;

import org.modelselection.index.Indexer;
import org.modelselection.ranking.DocumentSet;

import java.util.List;

public class LanguageModel implements RetrievalModel {

    @Override
    public DocumentSet generateRanking(Indexer indexer, String query) {
        return null;
    }
}
