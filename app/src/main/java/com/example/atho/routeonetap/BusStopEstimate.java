package com.example.atho.routeonetap;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BusStopEstimate extends AppCompatActivity {

    private class paramContainer{
        String stop;
        String route;
        TextView ETA;

        paramContainer(String stop,String route,TextView ETA){
            this.stop = stop;
            this.route = route;
            this.ETA = ETA;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_estimate);

        Intent intent = getIntent();
        String StopNum = intent.getStringExtra("StopNum");
        String RouNum = intent.getStringExtra("RouteNum");

        TextView Header = findViewById(R.id.StopNumber);
        TextView RouteHeader = findViewById(R.id.RouteNumber);

        String tmp = "Route: " + RouNum;
        RouteHeader.setText(tmp);
        tmp = "Stop: " + StopNum;
        Header.setText(tmp);

        String result;
        TextView textView = findViewById(R.id.EstimatedWaitTimeBox);

        paramContainer PC = new paramContainer(StopNum,RouNum,textView);
        try{
            result = new getData().execute(PC).get();
            textView.setText(result);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }catch(ExecutionException f){
            f.printStackTrace();
        }

    }

    private static class getData extends AsyncTask<paramContainer,Void,String> {

        //String testurl = "http://www.ssaurel.com/blog";
        //String google = "http://www.google.com";
        // textView = findViewById(R.id.EstimatedWaitTimeBox);


        @Override
        protected String doInBackground(paramContainer... params){
            StringBuilder data = new StringBuilder();
            String url = "http://api.translink.ca/rttiapi/v1/stops/" + params[0].stop+"/estimates?apikey=USdWlKXRo15tq9SNjTsg&timeframe=300";
            if(!(params[0].route.equals("")) ){
                url += "&routeNo=" + params[0].route;
            }
            Document doc;
            Elements ExpectedLeaveTimes;

            Log.d("TestAct","Point A");
            try {
                Log.d("TestAct","URL ="+ url);
                doc = Jsoup.connect(url).get();
                ExpectedLeaveTimes = doc.select("ExpectedLeaveTime");

                for (Element selector : ExpectedLeaveTimes){
                    //Log.d("TestActPartA","This = "+selector.html());
                    data.append(selector.html().substring(0,6));
                    data.append("\n");
                }
                /*
                //TextView box = params[0].ETA;
                //params[0].ETA.setText(data.toString());
                //box.setText(data.toString());
                //params[0].ETA.setText("Hello");
                PostExeParams pep = new PostExeParams(data.toString(),params[0].ETA);*/
                return data.toString();
            } catch (IOException e) {

                e.printStackTrace();
            }
            //Log.d("TestAct","Doc = " + doc);
            //textView.setText("Something here.");
            return null;
        }

    }

}

