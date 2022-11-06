package PoolGame.State;


import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.awt.*;

public class WinState implements StateControl{

    Label text;

    public WinState(Label text){
        this.text = text;
    }
    @Override
    public void display() {
        text.setText("Win and bye!");
    }
}
