package PoolGame;

import java.util.ArrayList;
import java.util.List;

import PoolGame.Builder.BallBuilderDirector;
import PoolGame.Config.BallConfig;
import PoolGame.Config.PocketConfig;
import PoolGame.Config.PocketsConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.Pocket;
import PoolGame.Items.PoolTable;
import PoolGame.State.LoseState;
import PoolGame.State.StateControl;
import PoolGame.State.WinState;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** The game class that runs the game */
public class Game {

    private PoolTable table;
   // private boolean shownWonText = false;
    //private final Text winText = new Text(50, 50, "Win and Bye");
    private boolean attached = false;
    Label score;

    //display win message
    Label winText;
    StateControl gameState;
    /**
     * Initialise the game with the provided config
     * @param config The config parser to load the config from
     */
    public Game(ConfigReader config) {
        this.setup(config);
    }


    private void setup(ConfigReader config) {
        this.table = new PoolTable(config.getConfig().getTableConfig());

        List<BallConfig> ballsConf = config.getConfig().getBallsConfig().getBallConfigs();
        List<Ball> balls = new ArrayList<>();
        BallBuilderDirector builder = new BallBuilderDirector();
        builder.registerDefault();
        for (BallConfig ballConf: ballsConf) {
            Ball ball = builder.construct(ballConf);
            if (ball == null) {
                System.err.println("WARNING: Unknown ball, skipping...");
            } else {
                balls.add(ball);
            }
        }

        this.table.setupBalls(balls);

        this.winText = new Label();
        this.winText.setFont(Font.font(50));
        this.winText.setVisible(false);

    }
    public void resetConfig(ConfigReader config){
        this.setup(config);
    }
    /**
     * Get the window dimension in the x-axis
     * @return The x-axis size of the window dimension
     */
    public double getWindowDimX() {
        return this.table.getDimX();
    }

    /**
     * Get the window dimension in the y-axis
     * @return The y-axis size of the window dimension
     */
    public double getWindowDimY() {
        return this.table.getDimY();
    }

    /**
     * Get the pool table associated with the game
     * @return The pool table instance of the game
     */
    public PoolTable getPoolTable() {
        return this.table;
    }

    /** Add all drawable object to the JavaFX group
     * @param root The JavaFX `Group` instance
    */
    public void addDrawables(Group root) {
        ObservableList<Node> groupChildren = root.getChildren();
        table.addToGroup(groupChildren);
        this.winText.setVisible(false);
        groupChildren.add(this.winText);
    }

    /** Reset the game */
    public void reset() {
        this.table.reset();
    }

    /** Code to execute every tick. */
    public void tick() {
        if (table.hasWon()) {
            gameState = new WinState(this.winText);
            gameState.display();
        }
        table.checkPocket(this);
        table.handleCollision();
        this.table.applyFrictionToBalls();
        for (Ball ball : this.table.getBalls()) {
            ball.move();
            if (ball.getBallType() == Ball.BallType.CUEBALL && attached == false) {
                ball.attachObserver(ball.getCueStickObject());
                attached = true;
            }
            if(ball.getBallType() == Ball.BallType.CUEBALL){
                boolean status = ball.recordUndo();
                if(status){
                    record();
                }
            }
        }
        //winText.setVisible(false);
    }
    public void record(){
        boolean allStopped = true;
        for (Ball ball : this.table.getBalls()) {
                ball.recordPosition();
        }
    }
    public void undo(){
        for (Ball ball : this.table.getBalls()) {
            ball.unDo();
        }
    }
    public void removeBall(String color){
        this.table.removeBall(color, this);
    }
    public void setScoreLabel(Label score){

        this.score = score;
    }
    public void addScore(int score){
        int currentScore = Integer.parseInt(this.score.getText());
        this.score.setText(String.valueOf(currentScore+score));
    }
}
