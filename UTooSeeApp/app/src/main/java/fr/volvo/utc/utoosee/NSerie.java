package fr.volvo.utc.utoosee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NSerie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nserie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExcavatorDB excavatorDB=new ExcavatorDB(this);
        final EditText nSerieEdit=(EditText)findViewById(R.id.nSerieEdit);

        Excavator excavator = new Excavator(1234, "excavator 1");


      excavatorDB.open();


      excavatorDB.instertExcavator(excavator);

     /* Excavator excavatorFromDB = excavatorDB.getExcavatorWithNum(1234);



        if(excavatorFromDB != null){
            nSerieEdit.setText("Yeahhhh");
        }
*/



        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                final String Num=nSerieEdit.getText().toString();

                if(Num.length()==0){
                    nSerieEdit.requestFocus();
                    nSerieEdit.setError("FIELD CANNOT BE EMPTY");
                }
                else{
                    Intent intent = new Intent (NSerie.this, presentationComponent.class);
                    startActivity(intent);
                }


            }
        });


    }

}
