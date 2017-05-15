package recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.DatabaseHandler;
import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.News;

public class DetailNews extends Activity {
    TextView title,body,date,likes,dislikes;
    LinearLayout like,dislike;
    News data_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent data = getIntent();
        final int id  = Integer.parseInt(data.getStringExtra("id"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        title = (TextView)findViewById(R.id.title);
        body = (TextView)findViewById(R.id.body);
        date = (TextView)findViewById(R.id.date);
        likes = (TextView)findViewById(R.id.likes);
        dislikes = (TextView)findViewById(R.id.dislikes);

        like = (LinearLayout)findViewById(R.id.like);
        dislike = (LinearLayout)findViewById(R.id.dislike);

        DatabaseHandler db = new DatabaseHandler(this);
        data_news = db.readNews(id);
        db.close();

        title.setText(data_news.getTitle());
        body.setText(data_news.getBody());
        date.setText(DateFormat.format("EEEE, dd MMM yyyy", data_news.getDate()));
        likes.setText(Integer.toString(data_news.getLikes()));
        dislikes.setText(Integer.toString(data_news.getDislikes()));

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int likes_num = data_news.getLikes();
                data_news.setLikes(++likes_num);
                DatabaseHandler db = new DatabaseHandler(DetailNews.this);
                if(db.addLike(id, Integer.toString(likes_num))){
                    likes.setText(Integer.toString(likes_num));
                }
                db.close();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dislikes_num = data_news.getDislikes();
                data_news.setDislikes(++dislikes_num);
                DatabaseHandler db = new DatabaseHandler(DetailNews.this);
                if(db.addDislike(id, Integer.toString(dislikes_num))){
                    dislikes.setText(Integer.toString(dislikes_num));
                }
                db.close();
            }
        });
    }
}
