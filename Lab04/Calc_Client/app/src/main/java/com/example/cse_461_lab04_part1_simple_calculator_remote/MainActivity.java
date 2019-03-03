package com.example.cse_461_lab04_part1_simple_calculator_remote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cse_461_lab03_part2_simple_calculator.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText t1;
    EditText t2;
    ImageButton plus;
    ImageButton minus;
    ImageButton multiply;
    ImageButton divide;
    TextView displayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        plus = (ImageButton) findViewById(R.id.plus);
        minus = (ImageButton) findViewById(R.id.minus);
        multiply = (ImageButton) findViewById(R.id.multiply);
        divide = (ImageButton) findViewById(R.id.divide);
        displayResult = (TextView)findViewById(R.id.displayResult);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
    }

    public void onClick( View view ) {
        Client mClient = new Client();
        double num1 = 0;
        double num2 = 0;
        String result = "";
        String mathOp = "";

        if (TextUtils.isEmpty(t1.getText().toString()) || TextUtils.isEmpty(t2.getText().toString())) {
            return;
        }

        mathOp += t1.getText();
        switch ( view.getId() )
        {
            case R.id.plus:
                mathOp += "+";
                break;
            case R.id.minus:
                mathOp += "-";
                break;
            case R.id.multiply:
                mathOp += "*";
                break;
            case R.id.divide:
                mathOp += "/";
                break;
            default:
                break;
        }
        mathOp += t2.getText();

        mClient.Message = mathOp;
        result = mClient.SendMSG();

        displayResult.setText(mathOp + " = " + result);
    }
}

