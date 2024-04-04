package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;

public class Bookmark {
   
    private int bookmarkId;
    private String userId;
    private String toiletId;
    private Date lastAdded;

    public Bookmark() {
    }

    public Bookmark(int bookmarkId, String userId, String toiletId, Date lastAdded) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.toiletId = toiletId;
        this.lastAdded = lastAdded;
    }

    public int getBookmarkId() { return bookmarkId; }
    public void setBookmarkId(int bookmarkId) { this.bookmarkId = bookmarkId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getToiletId() { return toiletId; }
    public void setToiletId(String toiletId) { this.toiletId = toiletId; }
    public Date getLastAdded() { return lastAdded; }
    public void setLastAdded(Date lastAdded) { this.lastAdded = lastAdded; }

    @Override
    public String toString() {
        return "Bookmark{bookmarkId=%s, userId=%s, toiletId=%s, lastAdded=%s}"
        .formatted(bookmarkId, userId, toiletId, lastAdded.toString());
    }

}
