package recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.DatabaseHandler;
import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.News;

/**
 * Created by Wahyu Ade Sasongko on 5/15/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsList>{
    private ArrayList<News> data_news;
    private Activity main_parent;
    private MainActivity activity;

    public NewsAdapter(ArrayList<News> data_news, Activity main_parent, MainActivity activity) {
        this.data_news = data_news;
        this.main_parent = main_parent;
        this.activity = activity;
    }

    public class NewsList extends ViewHolder {
        public LinearLayout list_news;
        public TextView date;
        public TextView title;
        public TextView body;
        public LinearLayout like;
        public LinearLayout dislike;
        public TextView likes;
        public TextView dislikes;
        public NewsList(View view){
            super(view);
            list_news = (LinearLayout) view.findViewById(R.id.list_news);
            date = (TextView)view.findViewById(R.id.date);
            title = (TextView)view.findViewById(R.id.title);
            body = (TextView)view.findViewById(R.id.body);
            like = (LinearLayout)view.findViewById(R.id.like);
            dislike = (LinearLayout)view.findViewById(R.id.dislike);
            likes = (TextView)view.findViewById(R.id.likes);
            dislikes = (TextView)view.findViewById(R.id.dislikes);
        }
    }

    @Override
    public NewsList onCreateViewHolder(ViewGroup parent, int viewType) {
        View news_list = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);
        return new NewsList(news_list);
    }

    @Override
    public void onBindViewHolder(final NewsList holder, final int position) {
        holder.list_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail_news = new Intent(main_parent, DetailNews.class);
                detail_news.putExtra("id", Integer.toString(data_news.get(position).getId()));
                main_parent.startActivity(detail_news);
            }
        });

        holder.list_news.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder selection = new AlertDialog.Builder(main_parent);
                CharSequence list_selection[] = {"Delete", "Report"};
                selection.setTitle("Options");
                selection.setItems(list_selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                DatabaseHandler db = new DatabaseHandler(main_parent);
                                if(db.deleteNews(data_news.get(position).getId())){
                                    data_news.remove(position);
                                    activity.list_news_adapter.notifyDataSetChanged();
                                }
                                break;
                            case 1:
                                Toast.makeText(main_parent, "The selected news was reported to server !", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                selection.show();
                return false;
            }
        });

        holder.date.setText(DateFormat.format("EEEE, dd MMM yyyy", data_news.get(position).getDate()));
        holder.body.setText(data_news.get(position).getBody());
        holder.title.setText(data_news.get(position).getTitle());
        holder.likes.setText(Integer.toString(data_news.get(position).getLikes()));
        holder.dislikes.setText(Integer.toString(data_news.get(position).getDislikes()));

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int likes = data_news.get(position).getLikes();
                data_news.get(position).setLikes(++likes);
                DatabaseHandler db = new DatabaseHandler(main_parent);
                if(db.addLike(data_news.get(position).getId(), Integer.toString(likes))){
                    holder.likes.setText(Integer.toString(likes));
                }
                db.close();
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dislikes = data_news.get(position).getDislikes();
                data_news.get(position).setDislikes(++dislikes);
                DatabaseHandler db = new DatabaseHandler(main_parent);
                if(db.addDislike(data_news.get(position).getId(), Integer.toString(dislikes))){
                    holder.dislikes.setText(Integer.toString(dislikes));
                }
                db.close();
            }
        });
    }

    public void addNewsModel(News data_insert){
        data_news.add(data_insert);
    }

    public void replaceDataNews(ArrayList<News> data){
        data_news = data;
        activity.list_news_adapter.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data_news.toArray().length;
    }
}
