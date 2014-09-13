package org.modelselection.ranking;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public class Document implements Comparable<Document> {

    private String queryid;
    String name;
    int rank;
    double score;

    public Document(String queryid, String name, int rank, double score) {
        this.queryid = queryid;
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getQueryid() {
        return queryid;
    }

    public void setQueryid(String queryid) {
        this.queryid = queryid;
    }

    @Override
    public int compareTo(Document documentRank) {
        int compareRank = documentRank.getRank();
        return getRank() - compareRank;
    }
}
