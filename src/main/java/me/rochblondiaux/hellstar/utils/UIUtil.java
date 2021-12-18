package me.rochblondiaux.hellstar.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class UIUtil {

    public static Optional<Pane> load(@NonNull String name) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(UIUtil.class.getClassLoader().getResource("scene/" + name + ".fxml")));
            if (Objects.nonNull(pane))
                return Optional.of(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Image> loadImage(@NonNull String name) {
        return getResource("img/" + name)
                .map(Image::new);
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
