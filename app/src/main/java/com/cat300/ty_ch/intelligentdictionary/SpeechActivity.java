package com.cat300.ty_ch.intelligentdictionary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class SpeechActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    HistoryDBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        button = (Button)findViewById(R.id.speech);
        textView = (TextView)findViewById(R.id.textView);
        myDb = new HistoryDBHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if(intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String word;

        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null) {
                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                    StringTokenizer stringTokenizer = new StringTokenizer(result.get(0), " \\,");
                    word = stringTokenizer.nextToken();
                    textView.setText(word);
                    Toast.makeText(this, "Press the word to get meaning", Toast.LENGTH_SHORT).show();

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDb.insertData(String.valueOf(textView.getText()));
                            String textText = String.valueOf(textView.getText());
                            Intent intent = new Intent(view.getContext(), LemmatronActivity.class);
                            intent.putExtra("Term",textText);
                            startActivity(intent);
                        }
                    });
                }
                break;
        }
    }
}
