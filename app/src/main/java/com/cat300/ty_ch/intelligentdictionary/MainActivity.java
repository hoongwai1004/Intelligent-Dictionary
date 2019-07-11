package com.cat300.ty_ch.intelligentdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public Button historyButton, favouriteButton, cameraButton, translateButton, searchButton, voiceButton;
    EditText editText;
    HistoryDBHelper myDb;


    public void initHistory()
    {
        historyButton = (Button)findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(MainActivity.this, History.class);
                startActivity(historyIntent);
            }
        });
    }

    public void initFavourites()
    {
        favouriteButton = (Button)findViewById(R.id.favouriteButton);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favouriteIntent = new Intent(MainActivity.this, Favourites.class);
                startActivity(favouriteIntent);
            }
        });
    }

    public void initCamera()
    {
        cameraButton = (Button)findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(cameraIntent);
            }
        });
    }

    public void initTranslate(){
        translateButton = (Button)findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent translateIntent = new Intent(MainActivity.this, TranslateActivity.class);
                startActivity(translateIntent);
            }
        });
    }

    public void initSpeech(){
        voiceButton = (Button)findViewById(R.id.voiceButton);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speechIntent = new Intent(MainActivity.this, SpeechActivity.class);
                startActivity(speechIntent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.textInput);
        searchButton = (Button)findViewById(R.id.search);
        myDb = new HistoryDBHelper(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length() > 0){
                    myDb.insertData(String.valueOf(editText.getText()));
                    String textText = String.valueOf(editText.getText());
                    Intent intent = new Intent(view.getContext(), LemmatronActivity.class);
                    intent.putExtra("Term",textText);
                    startActivity(intent);
                    editText.setText("");
                }

            }
        });

        initCamera();
        initHistory();
        initFavourites();
        initTranslate();
        initSpeech();
    }

}
