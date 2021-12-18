package me.rochblondiaux.hellstar.model.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
@Setter
@AllArgsConstructor
public class Dialog {

    private final DialogType type;
    private final String message;
    private Runnable onExit;

}
