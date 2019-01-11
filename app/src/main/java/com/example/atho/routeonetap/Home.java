package com.example.atho.routeonetap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void CheckBusStop(View view){
        Intent intent = new Intent(this, BusStopEstimate.class);
        EditText editText = findViewById(R.id.UserInBS);

        String UserNum = editText.getText().toString();
        intent.putExtra("StopNum", UserNum);

        EditText routeText = findViewById(R.id.routeInput);
        String UserRoute = routeText.getText().toString();
        intent.putExtra("RouteNum",UserRoute);
        startActivity(intent);
    }
}