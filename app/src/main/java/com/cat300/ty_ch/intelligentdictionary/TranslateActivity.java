package com.cat300.ty_ch.intelligentdictionary;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView2;
    Spinner spinner, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        button = (Button) findViewById(R.id.translate);
        editText = (EditText) findViewById(R.id.inputText);
        textView2 = (TextView) findViewById(R.id.showText);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        String term = getIntent().getStringExtra("Term");

        final String english[] = {"test", "dictionary", "eat", "word", "draw", "attitude", "development", "knowledge", "abandon", "launch", "apple", "banana", "grape", "water", "food", "telephone", "television", "cup", "computer", "fish"};
        final String malay[] = {"ujian", "kamus", "makan", "perkataan", "lukis", "sikap", "perkembangan", "pengetahuan", "meninggalkan", "melancarkan", "epal", "pisang", "anggur", "air", "makanan", "telefon", "televisyen", "cawan", "komputer", "ikan"};
        final String chinese[] = {"试", "字典", "吃", "字", "画画", "态度", "发展", "知识", "放弃", "发射", "苹果", "香蕉", "葡萄", "水", "食物", "电话", "电视机", "杯", "电脑", "鱼"};

        if(term != null)
            editText.setText(term);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(spinner.getSelectedItem()).equals("English") && String.valueOf(spinner2.getSelectedItem()).equals("Chinese")) {
                    for(int i = 0; i < english.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(english[i])){
                            textView2.setText(chinese[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else if(String.valueOf(spinner.getSelectedItem()).equals("English") && String.valueOf(spinner2.getSelectedItem()).equals("Malay")){
                    for(int i = 0; i < english.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(english[i])){
                            textView2.setText(malay[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else if (String.valueOf(spinner.getSelectedItem()).equals("Chinese") && String.valueOf(spinner2.getSelectedItem()).equals("English")){
                    for(int i = 0; i < chinese.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(chinese[i])){
                            textView2.setText(english[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else if(String.valueOf(spinner.getSelectedItem()).equals("Chinese") && String.valueOf(spinner2.getSelectedItem()).equals("Malay")){
                    for(int i = 0; i < chinese.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(chinese[i])){
                            textView2.setText(malay[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else if(String.valueOf(spinner.getSelectedItem()).equals("Malay") && String.valueOf(spinner2.getSelectedItem()).equals("English")){
                    for(int i = 0; i < malay.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(malay[i])){
                            textView2.setText(english[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else if(String.valueOf(spinner.getSelectedItem()).equals("Malay") && String.valueOf(spinner2.getSelectedItem()).equals("Chinese")){
                    for(int i = 0; i < malay.length; i++){
                        if(String.valueOf(editText.getText()).toLowerCase().equals(malay[i])){
                            textView2.setText(chinese[i]);
                            break;
                        }else {
                            textView2.setText("");
                        }
                    }
                }else
                    textView2.setText(editText.getText());

            }
        });
    }
}
