package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    //member variables
    private int x_coord;
    private int y_coord;
    private char letter;
    private Rectangle border;

    public Tile(int x, int y, char let){ //constructor
        //Assigning values to tile
        x_coord = x;
        y_coord = y;
        letter = let;

        //creating tile
        border = new Rectangle(50, 50);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        //adding text
        Text text = new Text(String.valueOf(let));
        text.setFont(Font.font("Verdana", 30));
        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
    }

    public void setPlayer(){
        border.setFill(Color.GREEN);
    }

    public void setRegular(){
        border.setFill(null);
    }

    public char getLetter(){return letter;}
}

