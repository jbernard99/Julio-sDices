/**
 * Copyright 2020 Julien Bernard
 *
 * First android app developped by Julien Bernard.
 */

package com.project.dicefinal;

//Google ads
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Declare View variables
    private TextView outputTextView;
    private EditText facesInput;
    private EditText dicesInput;
    private CheckBox logCheckbox;
    private Button rollButton;
    private TextView alertBox;
    private AdView mAdView;



    //Runs on app launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        // Assign the Views from the layout file to the corresponding variables
        outputTextView = findViewById(R.id.outputTextView);
        facesInput = findViewById(R.id.facesEditText);
        dicesInput = findViewById(R.id.dicesEditText);
        logCheckbox = findViewById(R.id.logCheckbox);
        rollButton = findViewById(R.id.rollButton);
        alertBox = findViewById(R.id.alertBox);

        //Google ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Grabs color default text color.
        final int DefaultButtonColor = alertBox.getTextColors().getDefaultColor();

        //Creates objects
        final DiceRoller diceRoll = new DiceRoller();
        final Verification verification = new Verification();

        //Creates OnClickListener for dice roller.
        View.OnClickListener diceRoller = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();

                //Retrieve nbDices and nbFaces inputs
                String rawFacesInput = facesInput.getText().toString();
                String rawDicesInput = dicesInput.getText().toString();

                //Initiate final input variables
                int nbFacesInput = 2;
                int nbDicesInput = 1;

                //Run input verification and formatting
                boolean verified = false;
                if (verification.checkInput(rawFacesInput, rawDicesInput)) {
                    nbFacesInput = Integer.parseInt(rawFacesInput);
                    nbDicesInput = Integer.parseInt(rawDicesInput);
                    verified = true;
                } else {
                    outputTextView.setText("");
                    alertBox.setTextColor(Color.RED);
                    alertBox.setText("Error with input!");
                }

                //If input went through verification successfully
                if (verified) {
                    //Retrieve roll list
                    List myRoll = diceRoll.getRoll(nbFacesInput, nbDicesInput);

                    //Retrieve classedResults
                    int[] classedResults = diceRoll.getClassedResults(myRoll);

                    //Initialize output variable
                    String output = "";

                    //If log in enabled, create log output
                    if (logCheckbox.isChecked()) {
                        for (int i = 0; i < myRoll.size(); i++) {
                            output += "Dice #" + (i + 1) + ": " +myRoll.get(i).toString() + "\n";
                        }
                    }

                    //Generate rest of output.
                    for (int i = 0; i < classedResults.length; i++) {
                        if (classedResults[i] != 0) {
                            output += classedResults[i] + " dices fell on " + (i + 1) + "\n";
                        } else {
                            output += "No dices fell on " + (i + 1) + "\n";
                        }
                    }
                    int total = diceRoll.getTotal(myRoll);
                    output += "Total of all the dices: " + total + "\n";

                    //Send the output to the view
                    outputTextView.setText(output);
                    alertBox.setTextColor(Color.GREEN); //Green
                    alertBox.setText("Rolled " + nbDicesInput + " dices with " + nbFacesInput + " faces");
                }
            }
        };

        //Adds dice roller OnClickListener on the rollButton
        rollButton.setOnClickListener(diceRoller);
    }

    private void closeKeyboard(){
        //Close keyboard on click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

