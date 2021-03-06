package me.rochblondiaux.hellstar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.Setter;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class Step2Controller implements Initializable {

    @FXML
    private Pane mainPane;
    @FXML
    private TextArea requirements;
    @FXML
    private TextField usage;

    @Setter
    private Project project;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void generate(ActionEvent e) {
        if (!requirements.getText().isEmpty())
            project.setRequirements(requirements.getText());
        if (!usage.getText().isEmpty())
            project.setUsageCommand(usage.getText());

        UIUtil.loadScene("step3")
                .ifPresent(scene -> {
                    Step3Controller controller = scene.getLoader().getController();
                    controller.setProject(project);
                    Pane pane1 = (Pane) mainPane.getParent();
                    pane1.getChildren().add(scene.getRoot());
                    pane1.getChildren().remove(mainPane);
                });
    }

}
