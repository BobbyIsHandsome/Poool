package PoolGame.Items;

import PoolGame.Observer.Observer;
import javafx.scene.shape.Line;

/*
    CueStick for the pool table
 */
public class CueStick implements Observer {
    Ball ball;
    Line cueStick;

    /**
     * Initialise the CueStick based on the provided value
     * @param ball         the ball
     */
    public CueStick(Ball ball) {
        this.ball = ball;
        this.cueStick = new Line();
        this.cueStick.setVisible(false);
    }

    /*
    * returns a Line as a cuestick */
    public Line getCueStick(){
        return this.cueStick;
    }


    /*is called when there is a state change i.e. when the cue ball is dragged
      updates the position of the cueStick in the game
      by calling registerCueBallMouseAction()
    */
    @Override
    public void update() {
        this.ball.registerCueBallMouseAction();
    }
}