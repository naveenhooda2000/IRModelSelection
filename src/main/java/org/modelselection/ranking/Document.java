package org.modelselection.ranking;

/**
 * Created by Naveen Kumar on 8/24/14.
 */
public class Document implements Comparable<Document> {

    String name;
    int rank;
    double score;

    public Document(int rank, double score) {
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


    @Override
    public int compareTo(Document documentRank) {
        int compareRank = documentRank.getRank();
        return compareRank - this.getRank();
    }
}
