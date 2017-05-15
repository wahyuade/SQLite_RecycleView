package recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database;

/**
 * Created by Wahyu Ade Sasongko on 5/14/2017.
 */
// Model to projection the table of news
public class News {
    private int id;
    private String title;
    private String body;
    private long date;
    private int likes;
    private int dislikes;

    //usually without id to save to database (table news)
    public News(String title, String body, long date, int likes, int dislikes) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    //usually using id to read data table
    public News(int id, String title, String body, long date, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }
}
