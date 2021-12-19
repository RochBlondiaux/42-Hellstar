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
import java.util.ArrayList;
import java.util.List;

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
        try {
            generateIndex(project.getDataFolder(), copy);
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage("Cannot generate index!")
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
    }

    public void generateIndex(@NonNull File dataFolder, @NonNull File readme) throws IOException {
        int index = FileUtils.getIndex(readme, "%index_header%") + 1;
        FileUtils.replace(readme, "%index_header%", "`@root`\n");

        /* Header */
        int depth = 1;
        FileUtils.walk(dataFolder)
                .forEach(file -> {
                    try {
                        FileUtils.insertLine(readme, index,
                                String.format("* [**\uD83D\uDCC1 %s:**](%s/) A common folder.", file.getName(), FileUtils.formatPath(dataFolder, file.toPath())));
                        walk(file,  readme, index, depth);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        /* Content */
    }

    private void walk(@NonNull File dataFolder, @NonNull File readme, @NonNull int index, @NonNull int depth) {
        FileUtils.walk(dataFolder)
                .forEach(file -> {
                    try {
                        FileUtils.insertLine(readme, index + 1,
                                String.format("\t".repeat(depth) + "* [**\uD83D\uDCC1 %s:**](%s/) A common folder.", file.getName(), FileUtils.formatPath(dataFolder, file.toPath())));
                        walk(file,  readme, index + 2, depth + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
