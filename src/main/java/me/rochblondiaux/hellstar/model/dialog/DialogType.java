package me.rochblondiaux.hellstar.model.dialog;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Getter
@RequiredArgsConstructor
public enum DialogType {
    INFO("info.png", "INFORMATION", Color.rgb(30, 144, 255)),
    SUCCESS("success.png", "SUCCESS", Color.rgb(46, 213, 115)),
    WARNING("warning.png", "WARNING", Color.rgb(255, 127, 80)),
    ERROR("error.png", "ERROR", Color.rgb(232, 65, 24)),
    ;

    private final String icon;
    private final String title;
    private final Color color;

}
