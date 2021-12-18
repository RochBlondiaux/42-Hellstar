package me.rochblondiaux.hellstar.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import me.rochblondiaux.hellstar.HellStar;
import me.rochblondiaux.hellstar.service.ConfigurationService;

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
            /* data is dragged over the target */
            System.out.println("onDragOver");

            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }

            event.consume();
        });

        pane.setOnDragDropped(event -> {
            /* data dropped */
            System.out.println("onDragDropped");

            Dragboard db = event.getDragboard();

            if (db.hasFiles()) {

                for (File file : db.getFiles()) {
                    String absolutePath = file.getAbsolutePath();
                    Image dbimage = new Image(absolutePath);
                    ImageView dbImageView = new ImageView();
                    dbImageView.setImage(dbimage);
                    pane.getChildren().add(dbImageView);
                }

                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }

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
    }
}
