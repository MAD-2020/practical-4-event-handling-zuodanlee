package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private Button[] ButtonList = new Button[3];
    private int Score = 0;
    private TextView ScoreView;
    private final String TAG = "Whack-A-Mole 1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonLeft = (Button) findViewById(R.id.ButtonLeft);
        ButtonMiddle = (Button) findViewById(R.id.ButtonMiddle);
        ButtonRight  = (Button) findViewById(R.id.ButtonRight);
        ButtonList[0] = ButtonLeft;
        ButtonList[1] = ButtonMiddle;
        ButtonList[2] = ButtonRight;
        ScoreView = (TextView) findViewById(R.id.ScoreView);

        ButtonLeft.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                doCheck(ButtonLeft);
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                doCheck(ButtonMiddle);
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                doCheck(ButtonRight);
            }
        });

        Log.v(TAG, "Finished Pre-Initialisation!");


    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        String buttonClicked;
        if (checkButton == ButtonLeft){
            buttonClicked = "Left";
        }
        else if (checkButton == ButtonRight){
            buttonClicked = "Right";
        }
        else{
            buttonClicked = "Middle";
        }
        if (checkButton.getText() == "*"){
            hit(buttonClicked);
            if (Score % 10 == 0){
                nextLevelQuery();
            }
        }
        else{
            miss(buttonClicked);
        }
        ScoreView.setText(Integer.toString(Score));
        setNewMole();
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Moles calling for backup!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Log.v(TAG, "Advance option given to user!");
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent advancedStage = new Intent(MainActivity.this, Main2Activity.class);
        advancedStage.putExtra("score", Score);
        startActivity(advancedStage);
    }

    private void setNewMole()
    {
        for (Button button : ButtonList){
            if (button.getText() == "*"){
                button.setText("O");
            }
        }

        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        ButtonList[randomLocation].setText("*");
    }

    private void hit(String buttonClicked)
    {
        Score += 1;
        Log.v(TAG, buttonClicked + " Button Clicked!\nHit, score added!");
    }

    private void miss(String buttonClicked)
    {
        if (Score > 0){
            Score -= 1;
        }
        Log.v(TAG, buttonClicked + " Button Clicked!\nMissed, point deducted!");
    }
}