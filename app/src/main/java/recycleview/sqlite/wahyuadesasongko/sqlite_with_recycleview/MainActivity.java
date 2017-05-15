package recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.DatabaseHandler;
import recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database.News;

public class MainActivity extends Activity {
    FloatingActionButton add_news, change;
    RecyclerView news_list;
    NewsAdapter list_news_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news_list = (RecyclerView)findViewById(R.id.news_list);

        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<News> data_news_in_adapter = new ArrayList<News>(Arrays.asList(db.readAllNews()));
        list_news_adapter = new NewsAdapter(data_news_in_adapter, MainActivity.this, this);
        db.close();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);

        news_list.setLayoutManager(mLayoutManager);
        news_list.setAdapter(list_news_adapter);

        add_news = (FloatingActionButton)findViewById(R.id.add_news);
        change = (FloatingActionButton)findViewById(R.id.change);

        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog add = new Dialog(MainActivity.this);
                add.setContentView(R.layout.activity_add_news);
                add.setTitle("Add News");
                add.show();

                final EditText title = (EditText)add.findViewById(R.id.title);
                final EditText body = (EditText)add.findViewById(R.id.body);
                Button save = (Button)add.findViewById(R.id.save_button);
                Button cancel = (Button)add.findViewById(R.id.cancel_button);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().isEmpty() && !body.getText().toString().isEmpty()){
                            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                            News new_news = new News(title.getText().toString(),body.getText().toString(), new Date().getTime(),0,0);
                            String id = db.createNews(new_news);
                            new_news.setId(Integer.parseInt(id));
                            list_news_adapter.addNewsModel(new_news);

                            list_news_adapter.notifyDataSetChanged();
                            add.dismiss();
                            Toast.makeText(MainActivity.this, "News was successfully added !", Toast.LENGTH_SHORT).show();
                            db.close();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Field can't empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.dismiss();
                    }
                });
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog change = new Dialog(MainActivity.this);
                change.setContentView(R.layout.change_data);
                change.setTitle("Change data");
                change.show();

                final EditText set = (EditText)change.findViewById(R.id.set);
                final EditText val = (EditText)change.findViewById(R.id.val);
                Button change_button = (Button)change.findViewById(R.id.change_button);
                Button cancel_button = (Button)change.findViewById(R.id.cancel_button);

                change_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!set.getText().toString().isEmpty() && !val.getText().toString().isEmpty()){
                            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                            list_news_adapter.replaceDataNews(new ArrayList<News>(Arrays.asList(db.setSomeString(set.getText().toString(), val.getText().toString()))));
                            db.close();
                            Toast.makeText(MainActivity.this, "Data successfull changed !", Toast.LENGTH_SHORT).show();
                            change.dismiss();
                        }
                    }
                });

                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
