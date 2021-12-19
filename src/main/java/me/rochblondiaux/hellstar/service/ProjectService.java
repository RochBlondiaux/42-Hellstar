package me.rochblondiaux.hellstar.service;

import lombok.NonNull;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import me.rochblondiaux.hellstar.model.project.Project;
import me.rochblondiaux.hellstar.utils.FileUtils;
import me.rochblondiaux.hellstar.utils.UIUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class ProjectService {

    private final File template;

    public ProjectService() throws URISyntaxException {
        this.template = UIUtil.loadFile("TEMPLATE.md");
    }

    public void generate(@NonNull Project project) {
        File copy = new File(project.getDataFolder(), "README.md");
        if (copy.exists()) {
            new DialogBuilder()
                    .setMessage("A README.md file already exists!")
                    .setType(DialogType.ERROR)
                    .build();
        }
        try {
            copy.createNewFile();
            FileUtils.copy(template, copy);
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage("Cannot copy template to destination file!")
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }

        try {
            FileUtils.replace(copy, "%project_name%", project.getName());
            FileUtils.replace(copy, "%brief_description%", project.getBriefDescription());
            FileUtils.replace(copy, "%description%", project.getDescription());
            FileUtils.replace(copy, "%aim%", project.getAim());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
