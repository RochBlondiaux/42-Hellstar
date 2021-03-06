package me.rochblondiaux.hellstar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import me.rochblondiaux.hellstar.HellStar;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class Step3Controller implements Initializable {

    @FXML
    private Pane mainPane;
    @FXML
    private Pane dragndrop;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox hBox;

    @Setter
    private Project project;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGestureTarget(mainPane);
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
                    .filter(File::isFile)
                    .findFirst()
                    .ifPresentOrElse(file -> {
                                if (!isImage(file)) {
                                    new DialogBuilder()
                                            .setType(DialogType.WARNING)
                                            .setMessage("File isn't an image!")
                                            .build();
                                    return;
                                }
                                hBox.getChildren().add(createImageView(file, hBox));
                                scrollPane.setContent(hBox);
                                project.getScreenshots().add(file.toPath());
                            },
                            () -> new DialogBuilder()
                                    .setType(DialogType.WARNING)
                                    .setMessage("Cannot find image! Please try again.")
                                    .build());
            event.setDropCompleted(true);
            event.consume();
        });
    }

    @FXML
    public void nextStep(ActionEvent e) {
        List<Path> screenshots = new ArrayList<>(project.getScreenshots())
                .stream()
                .map(path -> {
                    Path finalPath = new File(project.getDataFolder(), path.getFileName().toString()).toPath();
                    try {
                        Files.copy(path, finalPath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return null;
                    }
                    return finalPath;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        project.getScreenshots().clear();
        project.getScreenshots().addAll(screenshots);
        Pane parent = (Pane) mainPane.getParent();
        parent.getChildren().remove(mainPane);
        UIUtil.load("loading")
                .ifPresentOrElse(pane -> parent.getChildren().add(pane),
                        () -> new DialogBuilder()
                                .setType(DialogType.WARNING)
                                .setMessage("An error occurred while displaying loading screen!")
                                .build());
        HellStar.get().generate(project, parent);
    }

    @SneakyThrows
    private boolean isImage(@NonNull File file) {
        String mimetype = Files.probeContentType(file.toPath());
        return (mimetype != null && mimetype.split("/")[0].equals("image"));
    }

    @SneakyThrows
    private ImageView createImageView(@NonNull File imageFile, @NonNull Pane parent) {
        final Image image = new Image(new FileInputStream(imageFile));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(e -> {
            parent.getChildren().remove(imageView);
            project.getScreenshots().remove(imageFile.toPath());
        });
        return imageView;
    }
}
