package me.rochblondiaux.hellstar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.rochblondiaux.hellstar.model.dialog.Dialog;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class DialogController implements Initializable {

    @FXML
    private ImageView icon;
    @FXML
    private Pane iconBackground;
    @FXML
    private Text title;
    @FXML
    private Text text;
    @FXML
    private Button okBtn;

    private Dialog dialog;

    @FXML
    public void close(ActionEvent e) {
        if (Objects.nonNull(dialog) && Objects.nonNull(dialog.onExit))
            dialog.onExit.run();
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        title.setFill(dialog.getType().getColor());
        iconBackground.setStyle(String.format("-fx-background-color: %s", UIUtil.toHexString(dialog.getType().getColor())));
        text.setText(dialog.getMessage());
        title.setText(dialog.getType().getTitle());
        UIUtil.loadImage(dialog.getType().getIcon())
                .ifPresent(image -> icon.setImage(image));
    }

}
