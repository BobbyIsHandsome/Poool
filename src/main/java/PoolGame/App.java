/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package PoolGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import PoolGame.Items.Pocket;
import PoolGame.Items.PoolTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.json.simple.parser.ParseException;

import PoolGame.ConfigReader.ConfigKeyMissingException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;



/** The JavaFX application */
public class App extends Application {

    private final double FRAMETIME = 1.0 / 60.0;

    private Game game;
    private int countdown;
    private VBox vbox;
    private Group root;
    private Scene scene;

    HashMap<String, Game> gameModes = new HashMap<>();

    private ConfigReader loadConfig(List<String> args, String configPath) {
       // String configPath;
        boolean isResourcesDir = false;
        if (args.size() > 0) {
            configPath = args.get(0);
        } else {
            // configPath = "src/main/resources/config.json";
           // configPath = "/config_normal.json";
            isResourcesDir = true;
        }
        // parse the file:
        ConfigReader config = null;
        try {
            System.out.println("in try in app");
            config = new ConfigReader(configPath, isResourcesDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (ConfigKeyMissingException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        }
        return config;
    }

    public HBox addDisplayBox(){
        HBox hb = new HBox();

        countdown = 100;

        //score display
        Label scoreTextDisplay = new Label();
        scoreTextDisplay.setText("Score: 0");
        Label score = new Label();

        //time display
        Label timeTextDisplay = new Label("Time: 0");
        Label time = new Label();

        //timer
        Timeline timer= new Timeline(new KeyFrame(Duration.millis(1000), e -> timeTextDisplay.setText("Time: "+ countdown--)));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        scoreTextDisplay.setFont(Font.font(20));
        timeTextDisplay.setFont(Font.font(20));

        hb.getChildren().addAll(scoreTextDisplay, score, timeTextDisplay, time);

        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setAlignment(Pos.CENTER);

        return hb;
    }

    public HBox addBtnHBox(){
        //buttons
        HBox hb = new HBox();

        //cheat button
        Button cheatBtn = new Button("Cheat");
        TextInputDialog td = new TextInputDialog("blue");

        cheatBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {td.showAndWait();
                String color = td.getEditor().getText();
                game.removeBall(color);

            }
        });

        //undo button
        Button undoBtn = new Button("Undo");

        //easy game mode
        Button easyGameMode = new Button("EASY mode");
        easyGameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                game =  gameModes.get("easy");
                game = getEasyGameMode();
//                setup(game);
                reset(game);
            }
        });

        //normal game mode
        Button normalGameMode = new Button("NORMAL mode");
        normalGameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                game = gameModes.get("normal");
                game = getNormalGameMode();
//                setup(game);
                reset(game);
            }
        });


        //hard game mode
        Button hardGameMode = new Button("HARD mode");
        hardGameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                game = gameModes.get("hard");
                game = getHardGameMode();
//                setup(game);
                reset(game);
            }
        });

        //add button the hbox
        hb.getChildren().addAll(cheatBtn, undoBtn, easyGameMode, normalGameMode, hardGameMode);

        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setAlignment(Pos.CENTER);
        return hb;
    }

//    public Game getGameMode(){
//
//        //easy game mode set up
//        ConfigReader easyConfig = loadConfig(getParameters().getRaw(), "/config_easy.json");
//        Game easyGameMode = new Game(easyConfig);
//        gameModes.put("easy", easyGameMode);
//
//        //normal game mode set up
//        ConfigReader normalConfig = loadConfig(getParameters().getRaw(), "/config_normal.json");
//        Game normalGameMode = new Game(normalConfig);
//        gameModes.put("normal", normalGameMode);
//
//        //hard game mode set up
//        ConfigReader hardConfig = loadConfig(getParameters().getRaw(), "/config_hard.json");
//        Game hardGameMode = new Game(hardConfig);
//        gameModes.put("hard", hardGameMode);
//
//        return easyGameMode;
//    }
    public Game getEasyGameMode(){
        //hard game mode set up
        ConfigReader easyConfig = loadConfig(getParameters().getRaw(), "/config_easy.json");
        Game easyGameMode = new Game(easyConfig);


        return easyGameMode;
    }
    public Game getNormalGameMode(){
        //hard game mode set up
        ConfigReader normalConfig = loadConfig(getParameters().getRaw(), "/config_normal.json");
        Game normalGameMode = new Game(normalConfig);


        return normalGameMode;
    }
    public Game getHardGameMode(){
        //hard game mode set up
        ConfigReader hardConfig = loadConfig(getParameters().getRaw(), "/config_hard.json");
        Game hardGameMode = new Game(hardConfig);

        return hardGameMode;
    }

    private void setup(Game game){
        //canvas for pool table
        Canvas canvas = new Canvas(game.getWindowDimX(), game.getWindowDimY());
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        vbox.getChildren().removeAll();
        vbox.getChildren().add(canvas);
        vbox.getChildren().add(addDisplayBox());
        vbox.getChildren().add(addBtnHBox());

        game.addDrawables(root);

        run();

    }
    private void reset(Game game){
        Canvas canvas = new Canvas(game.getWindowDimX(), game.getWindowDimY());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        vbox.getChildren().removeAll();
        vbox.getChildren().add(canvas);
        vbox.getChildren().add(addDisplayBox());
        vbox.getChildren().add(addBtnHBox());

        game.addDrawables(root);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("PoolGame");

        vbox = new VBox();
        root = new Group();
        root.getChildren().add(vbox);
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

//        game = getGameMode();
        game = getEasyGameMode();
        setup(game);

        stage.setWidth(game.getWindowDimX());
        stage.setHeight(vbox.getMinHeight());

        run();

    }

    void run(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAMETIME),
                (actionEvent) -> {
                    game.tick();
                });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    /**
     * The entry point of the program
     * @param args CLI arguments
     */
    public static void main(String[] args) {
        System.out.println(12345);
        launch(args);
    }
}
