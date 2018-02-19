package com.example.gaurav.puzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static com.example.gaurav.puzzle.R.drawable.img2;
import static com.example.gaurav.puzzle.R.id.image;


public class PuzzleActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap = null;
    private PuzzleBoardView boardView;
    private Uri fileUri;
    Button shufflebutton;
    Button solvebutton;
    TextView tv;
    long countdown;
    long millisec;
    Bundle bd;
    int count;
    CountDownTimer waittimer;

    private ImageView ivPhoto;
    RelativeLayout container;
    File f;
    public int end = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        bd=getIntent().getExtras();
        bd.getString("key");
        tv=(TextView)findViewById(R.id.timer);
        count=0;


        container = (RelativeLayout) findViewById(R.id.puzzle_container);
        boardView = new PuzzleBoardView(PuzzleActivity.this);
        // Some setup of the view.
        boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        container.addView(boardView);
        Button photo=(Button)findViewById(R.id.photo_button);
        shufflebutton=(Button)findViewById(R.id.shuffle_button);
         solvebutton=(Button)findViewById(R.id.solve_button);

       shufflebutton.setEnabled(false);
        solvebutton.setEnabled(false);



    }



    public void dispatchTakePictureIntent(final View view) {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this);
         shufflebutton=(Button)findViewById(R.id.shuffle_button);
         solvebutton=(Button)findViewById(R.id.solve_button);

        shufflebutton.setEnabled(true);
        solvebutton.setEnabled(true);

        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    fileUri=Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent,1);

                }
                else if (options[item].equals("Choose from Gallery"))
                {

                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);



                }

                else if (options[item].equals("cancel"))
                {
                    //Intent intent=new Intent(Intent.ACTION_PICK,getResources().getDrawable(R.drawable.img1));
                    /*Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //startActivityForResult(intent, REQUEST_CODE);

                    startActivityForResult(intent,3);*/

                    //Drawable d=getResources().getDrawable(R.drawable.img1,null);
                    //Bitmap bitmap= ((BitmapDrawable) d).getBitmap();
                    //
                    // Bitmap bitmap = BitmapFactory.decodeResource(PuzzleActivity.getResources(), R.drawable.img1);
                    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img1
                    //ImageView imageView=(ImageView)findViewById(R.id.image);
                    //imageView.buildDrawingCache();
                    //Bitmap bmap = BitmapFactory.decodeResource(getResources(),R.drawable.img1);
                    //Bitmap bmap = imageView.getDrawingCache();
                    //Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    //imageView.setImageBitmap(bmap);
                    /*ImageView imgView = (ImageView) findViewById(R.id.image);
                    imgView.setImageResource(R.drawable.img2);
                    imgView.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(imgView.getDrawingCache());*/
                    ImageView imgView = (ImageView) findViewById(R.id.image);
                    imgView.setImageResource(R.drawable.img3);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), img2);

                    boardView.initialize(bitmap,container);
                }

            }


        });
        builder.show();
        //write code

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1) {

            //Bundle extra=data.getExtras();
            BitmapFactory.Options options = new BitmapFactory.Options();

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            //Bitmap bit=(Bitmap)extra.get("data");
           // boardView.initialize(bitmap, container);
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);
              //  MyCount counter = new MyCount(5000,1000);
               // counter.start();


        } else if (requestCode == 2) {
            Uri selectedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);

            c.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            // Log.w("path of image from gallery......******************.........", picturePath+"");

            boardView.initialize(bitmap, container);
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);




        }
    }
        // write code
    }


    public void shuffleImage(View view) {
        count++;
        boardView.shuffle();
        if(count==1)
        {
            final CountDownTimer waittimer;
            long mill = 180000;
            long countdown = 1000;
            if (bd.getInt("key") == 1) {
                mill = 300000;
            } else if (bd.getInt("key") == 2) {
                mill = 180000;
            } else if (bd.getInt("key") == 3) {
                mill = 60000;
            }

            waittimer = new CountDownTimer(mill, countdown) {

                public void onTick(long millisUntilFinished) {
                    if(end==0) {
                        tv.setText("" + millisUntilFinished / 1000);

                    }
                    else {
                        this.cancel();



                    }


                    //here you can have your logic to set text to edittext
                }
//tv.setText("" + millisUntilFinished / 1000);
                public void onFinish() {

                    tv.setText("Time Over!");
                    shufflebutton.setEnabled(false);
                    solvebutton.setEnabled(false);
                    Toast toast = Toast.makeText(PuzzleActivity.this, "You lose", Toast.LENGTH_LONG);
                    toast.show();

                }

            }.start();

        }

    }

    public void solve(View view) {
        boardView.solve();
        shufflebutton.setEnabled(false);
        Toast toast=Toast.makeText(PuzzleActivity.this,"Try with different image",Toast.LENGTH_LONG);
        toast.show();
        tv.setText("Over");
        stop();




    }
    public void exit(View view)
    {
        finish();

    }


    public void stop() {
        end=1;
    }
}
