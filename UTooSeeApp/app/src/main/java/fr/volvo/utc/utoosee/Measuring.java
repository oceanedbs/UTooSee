package fr.volvo.utc.utoosee;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.app.ListActivity;

import android.app.ProgressDialog;

import android.content.Context;

import android.os.AsyncTask;

import android.os.Bundle;

import android.app.Activity;

import android.util.Log;

import android.view.Menu;

import android.widget.LinearLayout;
import android.widget.ListAdapter;

import android.widget.ListView;

import android.widget.SimpleAdapter;
import android.widget.TextView;


import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Scanner;


public class Measuring extends AppCompatActivity  {

    private int clic[]=new int[100];





    private void parseJson(String s) {
        TextView txtDisplay=findViewById(R.id.textDisplay);
        Button button_piece=findViewById(R.id.button_piece);
        LinearLayout ll=(LinearLayout)findViewById(R.id.button_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        StringBuilder builder=new StringBuilder();

        try {
            JSONObject root= new JSONObject(s);
            JSONObject piece=root.getJSONObject("pieces");
            builder.append("Name : ")
                    .append(piece.getString("name")).append("\n");
            builder.append("Number : ")
                    .append(piece.getString("number of pieces")).append("\n").append("\n").append("\n");
            JSONArray  number =piece.getJSONArray("name of the pieces");


            for(int i=0; i<number.length(); i++) {
                StringBuilder builder2=new StringBuilder();

                Button btn=new Button(this);

                JSONObject namepiece = number.getJSONObject(i);
                builder2.append(namepiece.getString("name"))
                        .append("\n");
                btn.setId(i);
                btn.setText(builder2.toString());
                if(clic[i]==1){
                    btn.setTextColor(R.color.colorAccent);
                }
                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View view){
                       // clic[i]=1;
                        Intent intent = new Intent (Measuring.this, OpenCvActivity.class);
                        startActivity(intent);

                    }
                });
                ll.addView(btn);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        txtDisplay.setText(builder.toString());
    }


    public void loadPieces(){
        Resources res=getResources();
        InputStream is=res.openRawResource(R.raw.data_base);
        Scanner scanner=new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());

        }
        parseJson(builder.toString());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measuring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for(int i=0; i<100; i++)
            clic[i]=0;

        loadPieces();

        Button camera = (Button) findViewById(R.id.button_piece);
        camera.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
            Intent intent = new Intent (Measuring.this, OpenCvActivity.class);
        startActivity(intent);

    }
    });

        Button end = (Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                            Intent intent = new Intent(Measuring.this, Summary.class);
                            startActivity(intent);
                    }

            });


    }


}