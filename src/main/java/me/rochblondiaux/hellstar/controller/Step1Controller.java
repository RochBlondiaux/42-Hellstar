package me.rochblondiaux.hellstar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.model.project.Project;

import java.io.File;
import java.io.IOException;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene/step2.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            new DialogBuilder()
                    .setType(DialogType.ERROR)
                    .setMessage("An error occurred while displaying data.")
                    .build();
            ex.printStackTrace();
            return;
        }
        Step2Controller controller = fxmlLoader.getController();
        controller.setProject(new Project(name.getText(), briefDescription.getText(), description.getText(), aim.getText(), dataFolder));
        Pane pane1 = (Pane) mainPane.getParent();
        pane1.getChildren().add(root);
        pane1.getChildren().remove(mainPane);
    }

}
