package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends StackPane {
    private Rectangle timer;
    private static final Integer INIT_TIME = 15;
    public Integer secondsLeft = INIT_TIME;

    public Clock() {
        //create timer rectangle
        timer = new Rectangle(150, 50);
        timer.setFill(null);
        timer.setStroke(Color.WHITE);
    }

    public void startTimer(){
        Text timerText = new Text(INIT_TIME.toString());
        timerText.setFont(Font.font(45));
        setAlignment(Pos.CENTER);
        getChildren().addAll(timer, timerText);

        Timer gameTimer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                secondsLeft--;
                timerText.setText(secondsLeft.toString());
                //text color warning
                if(secondsLeft < 4){
                    timerText.setFill(Color.RED);
                }else{
                    timerText.setFill(Color.BLACK);
                }
                if(secondsLeft == 0){
                    timerText.setText("You lost!");
                    gameTimer.cancel();
                }
            }
        }; //end of task
        gameTimer.scheduleAtFixedRate(task, 10, 1000);
    } //end of startTimer method
}