package com.example.cse_461_lab04_part2_rannumgen_client;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.lang.InterruptedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText lowerB;
    EditText upperB;
    EditText amtNums;
    TextView ranNums;
    Button getNums;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lowerB = (EditText)findViewById(R.id.lowerBound);
        upperB = (EditText)findViewById(R.id.upperBound);
        amtNums = (EditText)findViewById(R.id.amtNumbers);
        ranNums = (TextView)findViewById(R.id.randomNumbers);
        getNums = (Button)findViewById(R.id.getRanNums);

        getNums.setOnClickListener(this);
    }

    public void onClick( View view )
    {
        Client rClient = new Client();
        rClient.Message += (lowerB.getText() + "," + upperB.getText() + "," + amtNums.getText());
        rClient.GetRandomNums(this);
    }
}
