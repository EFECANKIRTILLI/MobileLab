package msku.ceng.madlab.week3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int POST_REQUEST = 1;
    ListView listView;
    List<Post> postList = new ArrayList<>();

    Button btnPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listview);
        ;
        PostAdapter postAdapter = new PostAdapter(this, postList);
        listView.setAdapter(postAdapter);
        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, POST_REQUEST);
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == POST_REQUEST && resultCode == Activity.RESULT_OK){
                Post post = new Post();
                post.setMessage(data.getCharSequenceExtra("msg").toString());
                post.setImage(data.getParcelableExtra("bitmap"));
                postList.add(post);
                ((PostAdapter)listView.getAdapter()).notifyDataSetChanged();

            }
        }}










