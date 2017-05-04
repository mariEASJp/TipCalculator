package calculator.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {


    //Variable field
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();


    private double billAmount = 0.0; //Amount entered by user
    private double percent = 0.15; // initial tip percentage
    private TextView txtAmount;
    private TextView txtPercent;
    private TextView txtTip;
    private TextView txtTotal;
    private EditText etxtAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflate the GUI
        setContentView(R.layout.activity_main);

        //References to textviews
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtPercent = (TextView) findViewById(R.id.txtPercent);
        txtTip = (TextView) findViewById(R.id.txtTip);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTip.setText(currencyFormat.format(0));
        txtTotal.setText(currencyFormat.format(0));


        //Set etxtAmount TextWatcher
        etxtAmount = (EditText) findViewById(R.id.etxtAmount);
        etxtAmount.addTextChangedListener(etxtAmountWatcher);

        txtAmount.bringToFront();

        //set skbPercent's change listener
        SeekBar skbPercent = (SeekBar) findViewById(R.id.skbPercent);
        skbPercent.setOnSeekBarChangeListener(seekBarListener);

    }


    //Calculate and display tip and amount
    private void calculate() {
        //Format percent and display in txtPercent
        txtPercent.setText(percentFormat.format(percent));

        //Calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //Display tip and total formatted as currency
        txtTip.setText(currencyFormat.format(tip));
        txtTotal.setText(currencyFormat.format(total));
    }


    //Listener object for the seekbar's change
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        //update percent then calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0; //Set percent based on progress
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekbar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekbar) {
        }
    };


    private final TextWatcher etxtAmountWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //called when the user modifies the bill amount
            try { //get bill amount and display the currency formatted value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                txtAmount.setText(currencyFormat.format(billAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                txtAmount.setText("");
                billAmount = 0.0;
            }

            calculate(); //update the tip and txtTotal

        }




        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }


        @Override
        public void afterTextChanged(Editable s) { }
    };


}
