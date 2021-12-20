package me.rochblondiaux.hellstar;

import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.NonNull;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.service.ConfigurationService;
import me.rochblondiaux.hellstar.service.ProjectService;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class HellStar {

    private static HellStar instance;

    private final ConfigurationService configurationService;
    private final ProjectService projectService;

    public HellStar() {
        instance = this;

        this.configurationService = new ConfigurationService();
        this.projectService = new ProjectService();
    }

    public static HellStar get() {
        return instance;
    }

    public void generate(@NonNull Project project, Pane parent) {
        this.projectService.generate(project, parent);
    }
}
