package fr.volvo.utc.utoosee;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class presentationComponent extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_component);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);








        Button summary = (Button) findViewById(R.id.summary);
        summary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent (presentationComponent.this, Summary.class);
                startActivity(intent);

            }
        });

        Button BottomRoller = (Button) findViewById(R.id.BottomRoller);
        BottomRoller.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                    Intent intent = new Intent (presentationComponent.this, Measuring.class);
                    startActivity(intent);
                }



        });



        Button tripleGrouserShoe = (Button) findViewById(R.id.tripleGrouserShoe);
        /*tripleGrouserShoe.setOnClickListener(new View.OnClickListener(){
            @Override
          public void onClick (View view){
                Intent intent = new Intent (presentationComponent.this, Measuring.class);
                startActivity(intent);

            }
        });*/


    }

}
