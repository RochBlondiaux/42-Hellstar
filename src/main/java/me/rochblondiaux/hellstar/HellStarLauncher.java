package me.rochblondiaux.hellstar;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.rochblondiaux.hellstar.utils.UIUtil;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class HellStarLauncher extends Application {

    private double xOffset, yOffset;

    @Override
    public void start(Stage stage) throws Exception {
        new HellStar();

        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = UIUtil.load("home").orElseThrow();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);
        stage.setTitle("Hellstar v0.1");
        stage.getIcons().add(UIUtil.loadImage("icon-white.png").orElseThrow());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
