package me.rochblondiaux.hellstar.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.NonNull;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.model.ui.UIScene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class UIUtil {

    public static Optional<UIScene> loadScene(@NonNull String name) {
        try {
            URL url = UIUtil.class.getClassLoader().getResource("scene/" + name + ".fxml");
            if (Objects.isNull(url) || Objects.isNull(url.getPath())) {
                new DialogBuilder()
                        .setMessage(String.format("Couldn't load %s window from resources folder!", name))
                        .setType(DialogType.ERROR)
                        .build();
                return Optional.empty();
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            if (Objects.nonNull(parent))
                return Optional.of(new UIScene(loader, parent));
            else
                System.out.println("PARENT IS NULL Path: " + name);
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage(String.format("Couldn't load %s window from resources folder!", name))
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Pane> load(@NonNull String name) {
        try {
            URL url = UIUtil.class.getClassLoader().getResource("scene/" + name + ".fxml");
            if (Objects.isNull(url) || Objects.isNull(url.getPath())) {
                new DialogBuilder()
                        .setMessage(String.format("Couldn't load %s window from resources folder!", name))
                        .setType(DialogType.ERROR)
                        .build();
                return Optional.empty();
            }
            Pane pane = FXMLLoader.load(url);
            if (Objects.nonNull(pane))
                return Optional.of(pane);
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage(String.format("Couldn't load %s window from resources folder!", name))
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Image> loadImage(@NonNull String name) {
        return getResource("img/" + name)
                .map(Image::new);
    }

    public static File loadFile(@NonNull String path) throws URISyntaxException {
        URL url = UIUtil.class
                .getClassLoader()
                .getResource(path);

        if (url == null)
            throw new IllegalArgumentException(path + " is not found 1");
        return new File(url.getFile());
    }

    public static Optional<InputStream> getResource(@NonNull String path) {
        InputStream stream = UIUtil.class.getClassLoader().getResourceAsStream(path);
        if (Objects.isNull(stream))
            return Optional.empty();
        return Optional.of(stream);
    }

    public static String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
    }
}
