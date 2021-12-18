package me.rochblondiaux.hellstar.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane centerPane;

    @FXML
    private Button generateBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIUtil.load("open").ifPresent(pane -> centerPane.getChildren().add(pane));
        generateBtn.setVisible(false);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void generate() {

    }

    @FXML
    public void loadConfigurationPane(ActionEvent event) {
        generateBtn.setVisible(true);

    }
}
