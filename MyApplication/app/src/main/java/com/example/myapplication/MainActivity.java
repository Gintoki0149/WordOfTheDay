package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView randomWord;
    TextView phonetic;
    TextView meaning;
    String word;
    String []wordList = {"Abject","Abnormal","Abrade","Acquit","Callous","Cantankerous","Clandestine","Cumbersome","Debility","Denunciation","Dormant","Elucidate","Fastidious","Formidable","Forsake","Fraught","Gauche","Haughty","Hovered","Impasse","Incorrigible","Inextricable","Knotty","Ligature","Macabre","Modalities","Nullify","Ostensible","Oust","Overt","Pacify","Palatial","Penance","Pretence","Query","Queue","Quiet","Quintessential","Quip","Radical","Rampage","Rapid","Rapport","Recalcitrant","Reliant","Robust","Rogue","Sanguine","Startling","Stationary","Stealth","Unravelled","Uproarious","Urbane","Urgent","Wretchedness","Wrought","Wry","Zany","Zenith","Zombie"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomWord = findViewById(R.id.randomWord);
        phonetic = findViewById(R.id.phonetic);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://random-word-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<List<String>> call = retrofitApi.getModelClass();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                //code if data is successuflly extracted
                if(!response.isSuccessful()){ //if there is no data in the response object or there is a null operation
                    randomWord.setText("Error!");
                }
                List<String> data = response.body();
                for(int i = 0;i<data.size();i++){
                    System.out.println(data.get(i));
                }
                randomWord.setText((CharSequence) data.get(0));
                word = data.get(0);
//                phonetic.setText(data.get(0).getPhonetic());
//                meaning.setText(data.get(0).getMeanings());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println(t);
            }
        });

        Retrofit retrofit1 = new Retrofit.Builder().baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetMeaning getMeaning = retrofit1.create(GetMeaning.class);
        Call<List<ModelClass>> data = getMeaning.getMeaning(word);
        data.enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                List<ModelClass>modelClass = response.body();
                phonetic.setText(modelClass.get(0).getPhonetic());
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"FUCK!!", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }

}