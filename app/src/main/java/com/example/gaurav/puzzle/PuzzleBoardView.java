package com.example.gaurav.puzzle;

/**
 * Created by gaurav on 5/11/17.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;


public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();
    private PuzzleActivity puzzleActivity;

    public PuzzleBoardView(PuzzleActivity context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }
    private Comparator<PuzzleBoard> comparator = new Comparator<PuzzleBoard>() {
        @Override
        public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
            return lhs.priority() - rhs.priority();
        }
    };

    //private comparater write
    public void initialize(Bitmap imageBitmap, RelativeLayout container) {
        int width = getWidth();
        puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {

            for(int i =0; i<20; i++){
                ArrayList<PuzzleBoard> neighbours = puzzleBoard.neighbours();  //get neighbours

                int randomInt = random.nextInt(neighbours.size());

                PuzzleBoard newPuzzleBoard = neighbours.get(randomInt);  //return a possible puzzleboard state
                puzzleBoard = newPuzzleBoard;
            }
            invalidate();

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            TextView textView=(TextView)findViewById(R.id.timer);
                            //textView.setText("done!");
                            textView.setVisibility(INVISIBLE);
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();

                            //puzzleActivity.stop();
                            Button shufflebutton=(Button)findViewById(R.id.shuffle_button);
                            Button solvebutton=(Button)findViewById(R.id.solve_button);
                            //shufflebutton.setEnabled(false);
                           // solvebutton.setEnabled(false);




                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {
        PriorityQueue<PuzzleBoard> queue = new PriorityQueue<>(1,comparator);

        PuzzleBoard current = new PuzzleBoard(puzzleBoard, -1);
        current.setPrevBoard(null);
        queue.add(current);
        while (!queue.isEmpty()){
            PuzzleBoard bestState = queue.poll();
            if(bestState.resolved()){
                ArrayList<PuzzleBoard> steps = new ArrayList<>();
                while (bestState.getPrevBoard() != null){
                    steps.add(bestState);
                    bestState = bestState.getPrevBoard();
                }
                Collections.reverse(steps);
                animation = steps;
                invalidate();
                break;
            }
            else {
                queue.addAll(bestState.neighbours());
            }
        }
        Button photobutton=(Button)findViewById(R.id.photo_button);
        Button shufflebutton=(Button)findViewById(R.id.shuffle_button);
       // shufflebutton.setEnabled(false);

        //code something
    }
}

