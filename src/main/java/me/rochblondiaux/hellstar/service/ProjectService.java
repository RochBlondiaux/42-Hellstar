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
        int index = FileUtils.getIndex(readme, "%index_header%");
        int contentIndex = FileUtils.getIndex(readme, "%index%");
        FileUtils.replace(readme, "%index_header%", "`@root`\n");

        walkDirectory(dataFolder, dataFolder, readme, index, contentIndex, 1);
        FileUtils.replace(readme, "%index%", "\n");
    }

    private void walkFile(@NonNull File dataFolder, @NonNull File root, @NonNull File readme, @NonNull int contentIndex) throws IOException {
        Files.walk(root.toPath(), 1)
                .filter(s -> !root.toPath().equals(s))
                .filter(path -> !path.toFile().isDirectory())
                .forEach(s -> {
                    try {
                        FileUtils.getFunctions(s.toFile())
                                .forEach(s1 -> {
                                    try {
                                        FileUtils.insertLine(readme, contentIndex, String.format("* `%s` - %s.", s1, "A rly cool functions"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                        FileUtils.insertLine(readme, contentIndex, String.format("\n`@%s`", FileUtils.formatPath(dataFolder, s)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void walkDirectory(@NonNull File dataFolder, @NonNull File root, @NonNull File readme, @NonNull int index, @NonNull int contentIndex, @NonNull int depth) {
        FileUtils.walk(root)
                .forEach(file -> {
                    try {
                        FileUtils.insertLine(readme, index + 1,
                                String.format("\t".repeat(depth - 1) + "* [**\uD83D\uDCC1 %s:**](%s/) A common folder.", file.getName(), FileUtils.formatPath(dataFolder, file.toPath())));
                        walkFile(dataFolder, file, readme, FileUtils.getIndex(readme, "%index%"));
                        walkDirectory(dataFolder, file, readme, index + 1, contentIndex, depth + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
