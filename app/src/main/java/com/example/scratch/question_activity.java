package com.example.scratch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.*;

import java.util.*;

public class question_activity extends AppCompatActivity {

    //views
    Button buttonTrue, buttonFalse, restart;
    TextView score_Text, question_text, id_index;
    ProgressBar progressBar;


    // var
    static int score, questionIndex;
    Random random = new Random();
    LogicStatement tempObj;
    HashSet<Integer> noRepeat = new HashSet<>();

    private LogicStatement[] allQuestion = {
            new LogicStatement(R.string.question_0, true),
            new LogicStatement(R.string.question_1, false),
            new LogicStatement(R.string.question_2, false),
            new LogicStatement(R.string.question_3, true),
            new LogicStatement(R.string.question_4, true),
            new LogicStatement(R.string.question_5, false),
            new LogicStatement(R.string.question_6, true),
            new LogicStatement(R.string.question_7, false),
            new LogicStatement(R.string.question_8, true),
            new LogicStatement(R.string.question_9, true),
            new LogicStatement(R.string.question_10, true),
            new LogicStatement(R.string.question_11, true),
            new LogicStatement(R.string.question_12, true),
            new LogicStatement(R.string.question_13, true),
            new LogicStatement(R.string.question_14, false),
            new LogicStatement(R.string.question_15, true),
            new LogicStatement(R.string.question_16, true),
            new LogicStatement(R.string.question_17, true),
            new LogicStatement(R.string.question_18, false),
            new LogicStatement(R.string.question_19, true),
            new LogicStatement(R.string.question_20, false),
            new LogicStatement(R.string.question_21, false),
            new LogicStatement(R.string.question_22, false),
            new LogicStatement(R.string.question_23, true),
            new LogicStatement(R.string.question_24, false),
            new LogicStatement(R.string.question_25, true),
            new LogicStatement(R.string.question_26, false),
            new LogicStatement(R.string.question_27, true),
            new LogicStatement(R.string.question_28, false),
            new LogicStatement(R.string.question_29, true),
            new LogicStatement(R.string.question_30, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiating View
        buttonTrue = findViewById(R.id.true_button);
        buttonFalse = findViewById(R.id.false_button);
        restart = findViewById(R.id.restart);
        progressBar = findViewById(R.id.progressBar);
        score_Text = findViewById(R.id.score);
        id_index = findViewById(R.id.id_index);
        question_text = findViewById(R.id.question_text);


        // Controlls
        startAll();
        buttonHolder(tempObj);
    }

    private void buttonHolder(LogicStatement logicStatement) {
        buttonTrue.setOnClickListener((view) -> {
            questionUpdate(true);
        });
        buttonFalse.setOnClickListener((view) -> {
            questionUpdate(false);
        });
        restart.setOnClickListener((view) -> {
            soundStart("any");
            startAll();
        });

    }

    private void questionUpdate(Boolean answer) {
        checkAnswer(answer);
        setNewTempObj();
    }

    private void checkAnswer(Boolean answer) {
        boolean isCorrect = tempObj.isAnswer();
        if (answer == isCorrect) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            progressBar.incrementProgressBy(13);
            updateScore();
            soundStart("plus");
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            soundStart("wrong");
        }

    }

    public void setNewTempObj() {
        int numRand = random.nextInt(31);
        if (!noRepeat.contains(numRand)) {
            noRepeat.add(numRand);
            tempObj = allQuestion[numRand];
            ++questionIndex;
            id_index.setText("Question #" + questionIndex);
        } else {
            setNewTempObj();
        }
        System.out.println(questionIndex);
        question_text.setText(tempObj.getQuestionID());
        System.out.println(numRand);
        clearIn10();
    }

    private void clearIn10() {
        if (questionIndex == 11) {
            soundStart("restart");
            alertShow();
            startAll();
        }

    }

    private void startAll() {
        noRepeat.clear();
        questionIndex = 0;
        score = 0;
        id_index.setText("");
        score_Text.setText(R.string.default_score);
        progressBar.setProgress(0);
        setNewTempObj();
    }

    private void updateScore() {
        ++score;
        score_Text.setText("Score : " + score + "/10");
    }

    private void alertShow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        if (score >= 8) {
            alert.setTitle("You Passed!!");
            alert.setMessage("You scored : " + score);
            alert.setPositiveButton("back menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(question_activity.this, "nice", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            alert.setTitle("You failed");
            alert.setMessage("The passing score is 8 \nYou scored : " + score);
            alert.setPositiveButton("retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(question_activity.this, "nice", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        alert.show();
    }

    private void soundStart(String file) {
        MediaPlayer mp = new MediaPlayer();
        if (file.equals("plus")) {
            mp = MediaPlayer.create(this, R.raw.win_sound);
        } else if (file.equals("wrong")) {
            mp = MediaPlayer.create(this, R.raw.quit_sound);
        } else if (file.equals("restart")) {
            mp = MediaPlayer.create(this, R.raw.game_over);
        } else {
            mp = MediaPlayer.create(this, R.raw.clicksounds);
        }
        mp.start();
    }


}