package PoolGame.State;

import javafx.scene.control.Label;

public class TransitionState implements StateControl{

    Label text;

    public TransitionState(Label text){
        this.text = text;
        display();
    }
    @Override
    public void display() {
        text.setVisible(false);
        text.setText("");
    }
}
