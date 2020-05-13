package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    private int advancedScore;
    private TextView scoreView;
    private int currentMole = 0;
    CountDownTimer ReadyTimer;
    CountDownTimer PlaceMoleTimer;

    private String TAG = "Whack-A-Mole 2.0";

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        final Toast myToast = Toast.makeText(getApplicationContext(), "Get Ready In 10 seconds", Toast.LENGTH_SHORT);

        ReadyTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                myToast.setText("Get Ready In " + millisUntilFinished/1000 + " seconds");
                myToast.show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                setNewMole();
                placeMoleTimer();
                ReadyTimer.cancel();
            }
        };
        ReadyTimer.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        PlaceMoleTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                setNewMole();
                Log.v(TAG, "New Mole Location!");
                PlaceMoleTimer.start();
            }
        };
        PlaceMoleTimer.start();
    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // get score from first level
        Intent startAdvanced = getIntent();
        advancedScore = startAdvanced.getIntExtra("score", 0);

        scoreView = findViewById(R.id.scoreView);
        scoreView.setText(Integer.toString(advancedScore));

        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));
        readyTimer();

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    doCheck(button);
                }
            });
            button.setText("O");
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (currentMole != 0){
            if (checkButton.getId() == currentMole){
                advancedScore += 1;
                Log.v(TAG, "Hit, score added!");
            }
            else{
                if (advancedScore > 0){
                    advancedScore -= 1;
                }
                Log.v(TAG, "Missed, point deducted!");
            }
            scoreView.setText(Integer.toString(advancedScore));
            PlaceMoleTimer.cancel();
            PlaceMoleTimer.start();
            setNewMole();
        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        if (currentMole != 0){
            Button currentMoleButton = findViewById(currentMole);
            currentMoleButton.setText("O");
        }
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        currentMole = BUTTON_IDS[randomLocation];
        Button newMoleButton = findViewById(currentMole);
        newMoleButton.setText("*");
    }
}

