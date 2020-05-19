package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends StackPane {
    private Rectangle timer;
    private static final Integer INIT_TIME = 20;
    public Integer secondsLeft = INIT_TIME;
    private int score;

    public Clock(int gameScore) {
        //create timer rectangle
        timer = new Rectangle(150, 50);
        timer.setFill(null);
        timer.setStroke(Color.WHITE);
        score = gameScore;
    }

    public void addScore(){
        score++;
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
                Platform.runLater(new Runnable(){
                    public void run(){
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
                            gameEnd();
                        }
                    }//end inner run
                }); //Runnable
            }//outer run
        }; //end of task
        gameTimer.scheduleAtFixedRate(task, 1000, 1000);
    } //end of startTimer method

    public void gameEnd(){
        Stage gameEnd = new Stage();
        gameEnd.initModality(Modality.APPLICATION_MODAL);
        gameEnd.initStyle(StageStyle.UNDECORATED);

        //text
        Text endMessage = new Text("Game over. Your final score was: " + score);
        endMessage.setTranslateX(180);
        endMessage.setTranslateY(100);
        endMessage.setFont(Font.font(25));

        Text options = new Text("Press 'Enter' to go on without timer. Press 'Esc' to exit game.");
        options.setTranslateX(15);
        options.setTranslateY(220);
        options.setFont(Font.font(25));

        Pane endPane = new Pane();
        endPane.getChildren().addAll(endMessage, options);
        Scene gameEndScene = new Scene(endPane, 700, 300);

        gameEndScene.setOnKeyPressed(e -> keyPressed(e.getCode().toString(), gameEnd)); //handle user input
        gameEnd.setScene(gameEndScene);
        gameEnd.show();
    }

    public void keyPressed(String key, Stage gameEnd){
        System.out.println(key);
        if(key == "ENTER"){ //keep playing
            gameEnd.close(); //close game end window
            getChildren().clear();
        }
        if(key == "ESCAPE"){ //exit game
            Platform.exit();
            System.exit(0);
        }
    }
}