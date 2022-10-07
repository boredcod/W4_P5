package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView currentLetters;
    TextView usedLetters;
    ImageView imageView;
    TextView wonText;
    TextView lostText;
    Button newGame;
    ConstraintLayout words;
    String [] wordList;
    String currentWord = "";
    int numbersWrong =0;
    int correctChars =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        currentLetters = findViewById(R.id.currentLetters);
        imageView = findViewById(R.id.imageView);
        wonText = findViewById(R.id.Won);
        lostText = findViewById(R.id.Lost);
        newGame = findViewById(R.id.newGame);
        words = findViewById(R.id.letterList);

        wordList = new String[] {"orange",
                "strike",
                "poetry",
                "betray",
                "injury",
                "barrel",
                "create",
                "useful",
                "waiter",
                "person",
                "animal",
                "pastel",
                "factor",
                "salmon",
                "marine",
                "global",
                "switch",
                "acquit",
                "sodium",
                "period"};
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
        ArrayList<View> allChild = getAllChildren(words);
        for (View child: allChild) {
            if (child instanceof Button) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkCharacters(view);
                    }
                });
            }
        }
        startNewGame();


    }

    private void checkCharacters(View curr) {
        Button d = (Button)curr;
        String cC = d.getText().toString().toLowerCase();
        char currentCharacter = cC.charAt(0);
        String currLetter = currentLetters.getText().toString();
        d.setVisibility(View.INVISIBLE);
        System.out.println("char " + currentCharacter);
        System.out.println("String " + currentWord);
        int id = currentWord.indexOf(currentCharacter);
        System.out.println("index of " + id);
        if (id != -1) {
            char [] cL = currLetter.toCharArray();
            cL[id] = currentCharacter;
            currLetter = String.valueOf(cL);
            currentLetters.setText(currLetter);
            System.out.println("current letters" + currentLetters.getText().toString());
            String result = currentWord.substring(0,id) + "*" + currentWord.substring(id+1);
            currentWord = result;
            correctChars++;
            System.out.println("currentWord " + result);
        }
        else {
            numbersWrong++;
            imageView.setImageResource(getHangman());
            System.out.println(numbersWrong);
        }
        if (numbersWrong == 7) {
            lostEvent();
        }
        if (correctChars == 6) {
            winEvent();
        }
    }
    private void lostEvent(){
        lostText.setVisibility(View.VISIBLE);
        words.setVisibility(View.INVISIBLE);
        numbersWrong =0;
        correctChars =0;
    }
    private void winEvent(){
        wonText.setVisibility(View.VISIBLE);
        words.setVisibility(View.INVISIBLE);
        numbersWrong =0;
        correctChars =0;
    }
    private int getHangman() {
        switch(numbersWrong) {
            case 0:
                return R.drawable.game0;
            case 1:
                return R.drawable.game1;
            case 2:
                return R.drawable.game2;
            case 3:
                return R.drawable.game3;
            case 4:
                return R.drawable.game4;
            case 5:
                return R.drawable.game5;
            case 6:
                return R.drawable.game6;
            default:
                return R.drawable.game7;
        }

    }
    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    private void startNewGame() {
        ArrayList<View> allChild = getAllChildren(words);
        for (View v : allChild){
            v.setVisibility(View.VISIBLE);
        }
        wonText.setVisibility(View.INVISIBLE);
        lostText.setVisibility(View.INVISIBLE);
        imageView.setImageResource(R.drawable.game0);
        currentLetters.setText("______");
        Random rand = new Random();
        String nW = wordList[(rand.nextInt(wordList.length))];
        currentWord = nW;
        System.out.println("heyeyeyeyeyey " + currentWord);

    }
}