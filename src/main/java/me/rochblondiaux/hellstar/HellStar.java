package me.rochblondiaux.hellstar;

import lombok.Data;
import me.rochblondiaux.hellstar.service.ConfigurationService;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class HellStar {

    private static HellStar instance;

    private final ConfigurationService configurationService;

    public HellStar() {
        instance = this;

        this.configurationService = new ConfigurationService();
    }

    public static HellStar get() {
        return instance;
    }
}
