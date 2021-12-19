package me.rochblondiaux.hellstar.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import lombok.NonNull;
import me.rochblondiaux.hellstar.HellStar;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.service.ConfigurationService;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class OpenController implements Initializable {

    @FXML
    private Pane dragPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGestureTarget(dragPane);
    }

    void setupGestureTarget(final Pane pane) {
        pane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles())
                event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            if (!db.hasFiles()) {
                event.setDropCompleted(false);
                event.consume();
                return;
            }
            db.getFiles()
                    .stream()
                    .filter(File::isDirectory)
                    .findFirst()
                    .ifPresentOrElse(this::callNextStep,
                            () -> new DialogBuilder()
                                    .setType(DialogType.WARNING)
                                    .setMessage("Cannot find folder! Please try again.")
                                    .build());
            event.setDropCompleted(true);
            event.consume();
        });

    }

    @FXML
    public void openFileSelector(MouseEvent e) {
        ConfigurationService service = HellStar.get().getConfigurationService();
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select project folder");
        chooser.setInitialDirectory(service.getLastPath().toFile());
        File file = chooser.showDialog(null);
        if (Objects.isNull(file))
            return;
        service.setLastPath(file.toPath());
        callNextStep(file);
    }

    private void callNextStep(@NonNull File file) {
        UIUtil.load("step1").ifPresentOrElse(pane -> {
            Pane pane1 = (Pane) dragPane.getParent();
            pane1.getChildren().add(pane);
            pane1.getChildren().remove(dragPane);
        }, () -> new DialogBuilder()
                .setType(DialogType.ERROR)
                .setMessage("An error occurred.")
                .build());
    }
}
