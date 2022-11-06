package PoolGame.State;


import javafx.scene.control.Label;

public class LoseState implements StateControl {

    Label text;

    public LoseState(Label text){
        this.text = text;
    }

    @Override
    public void display() {
        text.setText("you lost");

    }
}
