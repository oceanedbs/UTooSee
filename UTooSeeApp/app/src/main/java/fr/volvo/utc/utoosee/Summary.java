package fr.volvo.utc.utoosee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button measure = (Button) findViewById(R.id.measure);
        measure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent (Summary.this, presentationComponent.class);
                startActivity(intent);

            }
        });



    }

}
