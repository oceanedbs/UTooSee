package fr.volvo.utc.utoosee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class Measuring extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measuring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GridView gridview = (GridView) findViewById(R.id.Measures);
        //gridview.setAdapter(new Button(this));

        ComponentsDB componentsDB=new ComponentsDB(this);
        componentsDB.open();

        Components components = new Components(1,"Left Bottom Roller 1", "2018/12/25", 1);
        componentsDB.insertPiece(components);

        //Components componentsFromDB=componentsDB.getComponantsWithNum(1);



        Button camera = (Button) findViewById(R.id.camera);
    camera.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
            Intent intent = new Intent (Measuring.this, MakePhotoActivity.class);
        startActivity(intent);

    }
    });

        Button end = (Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent (Measuring.this, Summary.class);
                startActivity(intent);

            }
        });
}
}

