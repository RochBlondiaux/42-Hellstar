package me.rochblondiaux.hellstar.model.dialog;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.NonNull;
import me.rochblondiaux.hellstar.controller.DialogController;

import java.io.IOException;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class DialogBuilder {

    private String message = "Unset";
    private DialogType type = DialogType.INFO;
    private Runnable exit;

    public DialogBuilder onExit(@NonNull Runnable runnable) {
        this.exit = runnable;
        return this;
    }

    public DialogBuilder setType(@NonNull DialogType type) {
        this.type = type;
        return this;
    }

    public DialogBuilder setMessage(@NonNull String message) {
        this.message = message;
        return this;
    }

    public void build() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene/dialog.fxml"));

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        DialogController controller = fxmlLoader.getController();
        controller.setDialog(new Dialog(type, message, exit));
        Scene dialog = new Scene(root);
        Stage dialogWindow = new Stage();
        dialogWindow.initStyle(StageStyle.UNDECORATED);
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogWindow.setTitle(type.getTitle());
        dialogWindow.setScene(dialog);
        Platform.runLater(dialogWindow::showAndWait);
    }
}
