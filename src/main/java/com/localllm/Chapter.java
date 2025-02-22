package com.localllm;

public class Chapter {

    private String chapterTitle;
    private String chapterContent;

    public Chapter() {
    }

    public Chapter(String chapterTitle, String chapterContent) {
        this.chapterTitle = chapterTitle;
        this.chapterContent = chapterContent;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterTitle='" + chapterTitle + '\'' +
                ", chapterContent='" + chapterContent + '\'' +
                '}';
    }
}
