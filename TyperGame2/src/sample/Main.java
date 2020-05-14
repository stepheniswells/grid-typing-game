package sample;

import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
 }

    private static final int NUM_ROWS = 15;
    private static final int NUM_COLS = 15;
    private Tile[][] grid = new Tile[NUM_ROWS][NUM_COLS]; //2d array of Tile objects

    Random random = new Random();
    int playerX = random.nextInt(NUM_COLS);
    int playerY = random.nextInt(NUM_ROWS);


    @Override
    public void start(Stage stage) throws Exception {
        //gameScene
        Scene gameScene = new Scene(createGameContent(stage));
        gameScene.setOnKeyPressed(e -> keyPressed(e.getCode().toString()));

        stage.setScene(gameScene);
        stage.show();
    }

    private void keyPressed(String letter){
        System.out.println("pressed: " + letter);
        checkNeighbor(letter);
        System.out.println("playerX: " + playerX);
        System.out.println("playerY: " + playerY);
    }

    public void checkNeighbor(String letter){
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
        }else{
            System.out.println("Pressed non-neighbor");
        }
    }

    //function generates a random character such that no tile can have more than 1 neighbor
    //with the same character value (ex: v b v)
    public char generateRandomChar(Tile[][] grid, int x, int y){
        char c = (char) (random.nextInt(25) + 65);
        while(  (x >= 2 && c == grid[y][x-2].getLetter()) ||
                (y >= 2 && c == grid[y-2][x].getLetter()) ||
                (y != 0 && x != 0 && c == grid[y-1][x-1].getLetter()) ||
                (y != 0 && x != NUM_COLS-1 && c == grid[y-1][x+1].getLetter())
        ){
            c = (char) (random.nextInt(25) + 65);
        }
        return c;
    }

    private Parent createGameContent(Stage stage){
        stage.setTitle("Game Scene");
        //creating window
        Pane root = new Pane();
        root.setPrefSize(800, 800);
        root.setLayoutX(50);

        //populating grid with tiles
        for(int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                char ch = generateRandomChar(grid, x, y);
                Tile tile = new Tile(x, y, ch); //create new tile
                tile.setTranslateX(50 * x); //moving tile to correct x
                tile.setTranslateY(50 * y); //moving tile to correct y


                //make one random tile the playerTile
                if(x == playerX && y == playerY) {
                    tile.setPlayer();
                }
                grid[y][x] = tile; //add tile to grid
                root.getChildren().add(tile); //adding to root to be displayed
            }
        }
        return root;
    }
}//end main class
