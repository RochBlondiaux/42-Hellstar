package me.rochblondiaux.hellstar.model.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class UIScene {

    private final FXMLLoader loader;
    private final Parent root;

}
