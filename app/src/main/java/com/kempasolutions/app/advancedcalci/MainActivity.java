package com.kempasolutions.app.advancedcalci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult= (EditText) findViewById(R.id.txtresult);
    }

    public void buttonPressed(View view) {
        Button pressedButton= (Button) view;
        txtResult.append(pressedButton.getText());
    }

    public void clearText(View view) {
        txtResult.setText("");
    }

    public void deleteText(View view) {
        String tempString=txtResult.getText().toString();
        int length=tempString.length();
        if(length>0){
            txtResult.setText(tempString.substring(0,length-1));
        }

    }

    public void calculateAndDisplayResult(View view) {
        float reslut=0f;
            try{
                if(txtResult.getText().length()==0) return;
                reslut=SolveExpression.solveExpression(txtResult.getText().toString());
                txtResult.setText(String.valueOf(reslut));
            }
            catch (SolveExpression.InvalidExpressionException ex){

                Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show();
            }catch (ArrayIndexOutOfBoundsException ex){
                Toast.makeText(this, "Incomplete expression", Toast.LENGTH_SHORT).show();
            }
    }

    public void shutdownApp(View view) {
        System.exit(0);
    }

    public void showScientificCalci(View view) {
        Toast.makeText(getApplicationContext(), "Work in progress. Get back to u soon", Toast.LENGTH_SHORT).show();

    }
}
