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

/*

    public class ButtonAdapter extends BaseAdapter {
        private  mContext;

        // Gets the context so it can be used later
        public ButtonAdapter(c) {
            mContext = c;
        }
    public String[] filesnames = {
            "1st Left Measure",
            "1st Right Measure",
            "Roflcopters"
    };
        // Total number of things contained within the adapter
        public int getCount() {
            return filesnames.length;
        }

        // Require for structure, not really used in my code.
        public Object getItem(int position) {
            return null;
        }

        // Require for structure, not really used in my code. Can
        // be used to get the id of an item in the adapter for
        // manual control.
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,
                            View convertView, ViewGroup parent) {
            Button btn;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
                btn.setLayoutParams(new GridView.LayoutParams(100, 55));
                btn.setPadding(8, 8, 8, 8);
            }
            else {
                btn = (Button) convertView;
            }
            exus
            btn.setText(filesnames[position]);
            // filenames is an array of strings
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.button);
            btn.setId(position);

            return btn;
        }
    }
*/


    Button camera = (Button) findViewById(R.id.camera);
    camera.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent intent = new Intent(Measuring.this, Summary.class);
        startActivity(intent);

    }
    });
}
}

