package me.rochblondiaux.hellstar;

import lombok.Data;
import lombok.NonNull;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.service.ConfigurationService;
import me.rochblondiaux.hellstar.service.ProjectService;

import java.net.URISyntaxException;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class HellStar {

    private static HellStar instance;

    private final ConfigurationService configurationService;
    private ProjectService projectService;

    public HellStar() {
        instance = this;

        this.configurationService = new ConfigurationService();
        try {
            this.projectService = new ProjectService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void generate(@NonNull Project project) {
        this.projectService.generate(project);
    }

    public static HellStar get() {
        return instance;
    }
}
