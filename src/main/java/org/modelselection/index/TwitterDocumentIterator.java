package org.modelselection.index;

import java.io.*;
import java.util.regex.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * Created by Naveen Kumar on 9/5/2014.
 */
public class TwitterDocumentIterator implements DocumentIterator {

    private static String MalInformedInput = "MalInformed Input";

    private BufferedReader bufferedReader;
    private Pattern docNoPattern;
    private Pattern timePattern;
    private Pattern statusPattern;
    private Pattern titlePattern;
    private Pattern textPattern;

    public void closeFile(){
        try {
            getBufferedReader().close();
        }
        catch(Exception E) {
            System.out.println("Exception in closeFile is "+E.toString());
        }
    }

    public TwitterDocumentIterator(String fileName) {
        try {
            File file = new File(fileName);
            setBufferedReader(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
            setDocNoPattern(Pattern.compile("<DOCNO>(.*)</DOCNO>"));
            setTimePattern(Pattern.compile("<Tweettime>(.*)</Tweettime>"));
            setStatusPattern(Pattern.compile("<TweetStatus>(.*)</TweetStatus>"));
            setTitlePattern(Pattern.compile("<TITLE>(.*)</TITLE>"));
            setTextPattern(Pattern.compile("<TEXT>(.*)</TEXT>"));
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    @Override
    public Document next() throws Exception {
        if(getBufferedReader() != null) {
            String line = getBufferedReader().readLine();
            boolean isInDocument = false;
            String docNo = new String();
            String title = new String();
            String text = new String();
            String status = new String();
            String time = new String();
            while (line != null) {
                if (line.matches("<DOC>")) {
                    isInDocument = true;
                } else if (line.matches("</DOC>")) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Document document = new Document();
                        document.add(new TextField(TwitterIndexFieldConstants.ID, docNo, Field.Store.YES));
                        document.add(new TextField(TwitterIndexFieldConstants.TITLE, title, Field.Store.YES));
                        document.add(new TextField(TwitterIndexFieldConstants.TEXT, text, Field.Store.YES));
                        document.add(new TextField(TwitterIndexFieldConstants.STATUS, status, Field.Store.YES));
                        document.add(new TextField(TwitterIndexFieldConstants.TIME, time, Field.Store.YES));
                        return document;
                    }
                } else if (docNoPattern.matcher(line).matches()) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Matcher m = docNoPattern.matcher(line);
                        m.find();
                        docNo = new String(m.group(1));
                    }
                } else if (timePattern.matcher(line).matches()) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Matcher m = timePattern.matcher(line);
                        m.find();
                        time = new String(m.group(1));
                    }
                } else if (statusPattern.matcher(line).matches()) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Matcher m = statusPattern.matcher(line);
                        m.find();
                        status = new String(m.group(1));
                    }
                } else if (titlePattern.matcher(line).matches()) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Matcher m = titlePattern.matcher(line);
                        m.find();
                        title = new String(m.group(1));
                    }
                } else if (textPattern.matcher(line).matches()) {
                    if (isInDocument == false) {
                        throw new Exception(MalInformedInput);
                    } else {
                        Matcher m = textPattern.matcher(line);
                        m.find();
                        text = new String(m.group(1));
                    }
                }
                line = getBufferedReader().readLine();
            }
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not Supported for Twitter Dataset");
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public Pattern getDocNoPattern() {
        return docNoPattern;
    }

    public void setDocNoPattern(Pattern docNoPattern) {
        this.docNoPattern = docNoPattern;
    }

    public Pattern getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(Pattern timePattern) {
        this.timePattern = timePattern;
    }

    public Pattern getStatusPattern() {
        return statusPattern;
    }

    public void setStatusPattern(Pattern statusPattern) {
        this.statusPattern = statusPattern;
    }

    public Pattern getTitlePattern() {
        return titlePattern;
    }

    public void setTitlePattern(Pattern titlePattern) {
        this.titlePattern = titlePattern;
    }

    public Pattern getTextPattern() {
        return textPattern;
    }

    public void setTextPattern(Pattern textPattern) {
        this.textPattern = textPattern;
    }

}
