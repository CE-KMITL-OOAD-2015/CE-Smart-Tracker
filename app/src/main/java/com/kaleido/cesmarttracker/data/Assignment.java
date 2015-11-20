package com.kaleido.cesmarttracker.data;

public class Assignment extends Event {
    private int maxScore;
    private int Score;
    private String url;


    public Assignment(String title, String content, String courseName, String dueDate,int maxScore) {
        super(title, content, courseName, dueDate,dueDate,"Assignment");
        this.maxScore = maxScore;
        this.Score = 0;
        url = "";
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSubmitted(){
        if(getUrl().contentEquals("")){
            return false;
        }
        else return true;
    }

    public String getTitle() {
        return super.getTitle();
    }
}