package com.example.gaurav.puzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button easybutton=(Button)findViewById(R.id.easy);
        Button normalbutton=(Button)findViewById(R.id.normal);
        Button hardbutton=(Button)findViewById(R.id.hard);

        easybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,PuzzleActivity.class);
                intent.putExtra("key",1);
                startActivity(intent);

                }

            
        });

        normalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,PuzzleActivity.class);
                intent.putExtra("key",2);
                startActivity(intent);

            }
        });

        hardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,PuzzleActivity.class);
                intent.putExtra("key",3);
                startActivity(intent);

            }
        });
    }

    public void exit(View view)
    {
        finish();
    }


}
