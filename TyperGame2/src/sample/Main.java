package sample;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
 }
    //*** member variables ***
    //grid variables
    private static final int NUM_ROWS = 15;
    private static final int NUM_COLS = 15;
    private Tile[][] grid = new Tile[NUM_ROWS][NUM_COLS]; //2d array of Tile objects

    //initialize player coordinates to a random value
    Random random = new Random();
    int playerX = random.nextInt(NUM_COLS);
    int playerY = random.nextInt(NUM_ROWS);

    //treasure tile coordinates
    int treasureX;
    int treasureY;

    //enemy tile coordinates and timer
    int enemyX;
    int enemyY;
    private static final int ENEMY_SPEED = 500; //time in milliseconds between enemy moves

    //timer and score system
    Text scoreText; //text to display player score
    public Integer gameScore = 0; //player score
    Clock timer = new Clock(gameScore); //timer system


    //*** member functions ***
    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true); //forces application.stop call when last window is closed
        Scene gameScene = new Scene(createGameContent(stage)); //create scene
        gameScene.setOnKeyPressed(e -> keyPressed(e.getCode().toString())); //handle keyboard inputs
        stage.setScene(gameScene); //add scene to stage
        stage.show(); //show stage
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    private void keyPressed(String letter){
        System.out.println("pressed: " + letter);
        movePlayer(letter);
        System.out.println("player coords: (" + playerX + ", " + playerY + ")");
    }

    boolean gameStartFlag = true;
    void movePlayer(String letter){
        if(gameStartFlag){ //start timer only when player moves
            timer.startTimer();
            gameStartFlag = false;
        }
        //check x neighbors
        if(playerX != 0 && letter.charAt(0) == (grid[playerY][playerX -1].getLetter())){ //left x
            grid[playerY][playerX].setRegular(); //set old player tile to a regular tile
            grid[playerY][playerX-1].setPlayer(); //set new tile to become player tile
            playerX--;
        }else if(playerX != NUM_COLS-1 && letter.charAt(0) == (grid[playerY][playerX + 1].getLetter())){ //right x
            grid[playerY][playerX].setRegular();
            grid[playerY][playerX + 1].setPlayer();
            playerX++;
        } //check y neighbors
        else if(playerY != 0 && letter.charAt(0) == (grid[playerY-1][playerX].getLetter())){ //down y
            grid[playerY][playerX].setRegular();
            grid[playerY-1][playerX].setPlayer();
            playerY--;
        }else if(playerY != NUM_ROWS-1 && letter.charAt(0) == (grid[playerY+1][playerX].getLetter())){ //up y
            grid[playerY][playerX].setRegular();
            grid[playerY+1][playerX].setPlayer();
            playerY++;
        }else{ System.out.println("Pressed non-neighbor"); }
        //check if player collects treasure
        if(playerX == treasureX && playerY == treasureY){
            System.out.println("Collected treasure!");
            timer.secondsLeft += 5; //add 5 seconds to time
            gameScore++; // increment player score
            timer.addScore();
            scoreText.setText("Score: " + gameScore);
            addTreasure(); //add new treasure
        }
    }

    //function generates a random character such that no tile can have more than 1 neighbor
    //with the same character value (ex: v b v)
    char generateRandomChar(Tile[][] grid, int x, int y){
        char c = (char) (random.nextInt(25) + 66);
        while(  (x >= 2 && c == grid[y][x-2].getLetter()) ||
                (y >= 2 && c == grid[y-2][x].getLetter()) ||
                (y != 0 && x != 0 && c == grid[y-1][x-1].getLetter()) ||
                (y != 0 && x != NUM_COLS-1 && c == grid[y-1][x+1].getLetter())
        ){
            c = (char) (random.nextInt(25) + 66);
        }
        return c;
    }

    void addTreasure(){
        treasureX = random.nextInt(NUM_COLS); //generate random x coord
        treasureY = random.nextInt(NUM_ROWS); //generate random y coord
        //ensure treasure is placed at least 3 tiles away from player
        while((Math.abs(playerX - treasureX) < 3) || Math.abs(playerY - treasureY) < 3){
            treasureX = random.nextInt(NUM_COLS);
            treasureY = random.nextInt(NUM_ROWS);
        }
        grid[treasureY][treasureX].setTreasure();
    }

    void addEnemy(){
        enemyX = random.nextInt(NUM_COLS); //generate random coordinates for enemy
        enemyY = random.nextInt(NUM_ROWS);
        //ensure enemy is placed at least 3 tiles away from treasure
        while((Math.abs(enemyX - treasureX) < 3) || Math.abs(enemyY - treasureY) < 3){
            enemyX = random.nextInt(NUM_COLS);
            enemyY = random.nextInt(NUM_ROWS);
        }
        grid[enemyY][enemyX].setEnemy(); //set tile in grid to enemy

        //add enemyTimer so that enemy moves at regular intervals
        Timer enemyTimer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(new Runnable(){
                    public void run(){
                        if(!gameStartFlag){ //enemy moves once player starts moving
                            moveEnemy();
                        }
                    }//end inner run
                }); //Runnable
            }//outer run
        }; //end of task
        enemyTimer.scheduleAtFixedRate(task, ENEMY_SPEED, ENEMY_SPEED);
    }

    void moveEnemy(){ //enemy moves towards treasure, first in the x direction, then y
        if(treasureX < enemyX){
            if(enemyX != playerX || enemyY != playerY){ //set old tile to regular
                grid[enemyY][enemyX].setRegular();      //unless old tile is player tile
            }
            grid[enemyY][enemyX -1].setEnemy(); //set new tile to become enemy tile
            enemyX--;
        }else if(treasureX > enemyX){
            if(enemyX != playerX || enemyY != playerY){
                grid[enemyY][enemyX].setRegular();
            }
            grid[enemyY][enemyX  + 1].setEnemy();
            enemyX ++;
        }else if(treasureY < enemyY){
            if(enemyX != playerX || enemyY != playerY){
                grid[enemyY][enemyX].setRegular();
            }
            grid[enemyY-1][enemyX].setEnemy();
            enemyY--;
        }else if(treasureY > enemyY){
            if(enemyX != playerX || enemyY != playerY){
                grid[enemyY][enemyX].setRegular();
            }
            grid[enemyY+1][enemyX].setEnemy();
            enemyY++;
        }
        if(enemyX == treasureX && enemyY == treasureY){
            addTreasure(); //if enemy reaches treasure, add a new treasure
        }
    }

    private Parent createGameContent(Stage stage){
        stage.setTitle("Game Scene");
        //creating grid window
        Pane root = new Pane();
        root.setPrefSize(800, 900);
        root.setLayoutX(50);

        //populating grid with tiles
        for(int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                char ch = generateRandomChar(grid, x, y);
                Tile tile = new Tile(x, y, ch); //create new tile
                tile.setTranslateX(50 * x); //moving tile to correct x
                tile.setTranslateY(50 * y); //moving tile to correct y

                //make one random tile the playerTile
                if (x == playerX && y == playerY) {
                    tile.setPlayer();
                }

                grid[y][x] = tile; //add tile to grid
                root.getChildren().add(tile); //adding to root to be displayed
            }
        }

        //add treasure tile
        addTreasure();

        //add enemy tile
        addEnemy();

        //add timer
        timer.setTranslateX(300);
        timer.setTranslateY(760);
        root.getChildren().add(timer);

        //add score
        scoreText = new Text("Score: " + gameScore.toString());
        scoreText.setTranslateX(345);
        scoreText.setTranslateY(850);
        scoreText.setFont(Font.font(20));
        root.getChildren().add(scoreText);

        return root;
    }
}//end main class

