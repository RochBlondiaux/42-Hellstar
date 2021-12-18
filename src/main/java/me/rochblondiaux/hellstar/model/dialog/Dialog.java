package me.rochblondiaux.hellstar.model.dialog;

import lombok.Data;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class Dialog {

    private final DialogType type;
    private final String message;
    public final Runnable onExit;

}
