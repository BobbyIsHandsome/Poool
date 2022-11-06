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
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/** The game class that runs the game */
public class Game {

    private PoolTable table;
    private boolean shownWonText = false;
    private final Text winText = new Text(50, 50, "Win and Bye");
    private boolean attached = false;
    Label score;
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

//        List<PocketConfig> pocketsConf = config.getConfig().getPocketsConfig().getPocketConfigs();
//        List<Pocket> pockets = new ArrayList<>();
//        for (PocketConfig pocketConfi: pocketsConf){
//            Pocket pocket = new Pocket(pocketConfi.getPositionConfig().getX(), pocketConfi.getPositionConfig().getY());
//            pockets.add(pocket);
//            System.out.println("pocket added "+ pocket);
//        }


        this.table.setupBalls(balls);
        this.winText.setVisible(false);
        this.winText.setX(table.getDimX() / 2);
        this.winText.setY(table.getDimY() / 2);
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
        groupChildren.add(this.winText);
    }

    /** Reset the game */
    public void reset() {
        this.winText.setVisible(false);
        this.shownWonText = false;
        this.table.reset();
    }

    /** Code to execute every tick. */
    public void tick() {
        if (table.hasWon() && !this.shownWonText) {
            System.out.println(this.winText.getText());
            this.winText.setVisible(true);
            this.shownWonText = true;
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
        System.out.println(currentScore);
        this.score.setText(String.valueOf(currentScore+score));
    }
}
