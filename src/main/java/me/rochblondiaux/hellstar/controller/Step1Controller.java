package me.rochblondiaux.hellstar.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class Step1Controller implements Initializable {

    @FXML
    private Pane mainPane;
    @FXML
    private TextField name;
    @FXML
    private TextField briefDescription;
    @FXML
    private TextField aim;
    @FXML
    private TextArea description;
    @FXML
    private Button next;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private boolean isComplete() {
        return name.getText().isEmpty()
                && briefDescription.getText().isEmpty()
                && aim.getText().isEmpty()
                && description.getText().isEmpty();
    }

    @FXML
    public void nextStep(ActionEvent e) {
        if (!isComplete()) {
            new DialogBuilder()
                    .setMessage("Please fill all fields!")
                    .setType(DialogType.WARNING)
                    .build();
            return;
        }
        UIUtil.load("step2").ifPresentOrElse(pane -> {
            Pane pane1 = (Pane) mainPane.getParent();
            pane1.getChildren().add(pane);
            pane1.getChildren().remove(mainPane);
        }, () -> new DialogBuilder()
                .setType(DialogType.ERROR)
                .setMessage("An error occurred.")
                .build());
    }

}
