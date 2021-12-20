package me.rochblondiaux.hellstar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;
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

    public static File dataFolder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private boolean isInvalid() {
        return name.getText().isEmpty()
                || briefDescription.getText().isEmpty()
                || aim.getText().isEmpty()
                || description.getText().isEmpty();
    }

    @FXML
    public void nextStep(ActionEvent e) {
        if (isInvalid()) {
            new DialogBuilder()
                    .setMessage("Please fill all fields!")
                    .setType(DialogType.WARNING)
                    .build();
            return;
        } else if (Objects.isNull(dataFolder)) {
            new DialogBuilder()
                    .setMessage("An error occurred!")
                    .setType(DialogType.ERROR)
                    .build();
            return;
        }
        UIUtil.loadScene("step2")
                .ifPresent(scene -> {
                    Step2Controller controller = scene.getLoader().getController();
                    controller.setProject(new Project(name.getText(), briefDescription.getText(), description.getText(), aim.getText(), dataFolder));
                    Pane pane1 = (Pane) mainPane.getParent();
                    pane1.getChildren().add(scene.getRoot());
                    pane1.getChildren().remove(mainPane);
                });
    }

}
