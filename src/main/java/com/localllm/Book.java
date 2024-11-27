package com.localllm;

import java.util.List;

public class Book {

    private String title;
    private List<Chapter> chapterList;

    public Book() {
    }

    public Book(List<Chapter> chapterList, String title) {
        this.chapterList = chapterList;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", chapterList=" + chapterList +
                '}';
    }
}
