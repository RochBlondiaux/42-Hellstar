package me.rochblondiaux.hellstar.service;

import javafx.scene.layout.Pane;
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
import java.util.List;
import java.util.Objects;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class ProjectService {

    public void generate(@NonNull Project project, @NonNull Pane pane) {
        File copy = new File(project.getDataFolder(), "README.md");
        if (copy.exists()) {
            new DialogBuilder()
                    .setMessage("A README.md file already exists!")
                    .setType(DialogType.ERROR)
                    .build();
        }
        try {
            copy.createNewFile();
            FileUtils.copy("TEMPLATE.md", copy);
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
                    .setMessage("Cannot generate index section!")
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
        try {
            generateUsage(copy, project);
        } catch (URISyntaxException | IOException e) {
            new DialogBuilder()
                    .setMessage("Cannot generate usage section!")
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
        try {
            generateScreenshots(copy, project);
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage("Cannot generate screenshots section!")
                    .setType(DialogType.ERROR)
                    .build();
            e.printStackTrace();
        }
        new DialogBuilder()
                .setMessage("Your README.md file has been generated successfully!")
                .setType(DialogType.SUCCESS)
                .onExit(() -> {
                    pane.getChildren().clear();
                    UIUtil.load("open")
                            .ifPresent(pane1 -> pane.getChildren().add(pane1));
                })
                .build();
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

    private void generateUsage(@NonNull File readme, @NonNull Project project) throws URISyntaxException, IOException {
        File tmp = Files.createTempFile("hellstar", null).toFile();
        tmp.createNewFile();
        tmp.deleteOnExit();
        FileUtils.copy("USAGE.md", tmp);

        if (Objects.isNull(project.getUsageCommand()) && Objects.isNull(project.getRequirements()))
            return;
        List<String> lines = Files.readAllLines(readme.toPath());
        lines.addAll(Files.readAllLines(tmp.toPath()));
        Files.write(readme.toPath(), lines);
        FileUtils.replace(readme, "%project_name%", project.getName());

        String usage = "Not set.";
        if (Objects.nonNull(project.getUsageCommand()))
            usage = project.getUsageCommand();
        FileUtils.replace(readme, "%usage_command%", usage);

        String requirements = "";
        if (Objects.nonNull(project.getRequirements()))
            requirements = project.getRequirements();
        FileUtils.replace(readme, "%requirements%", requirements);
    }

    private void generateScreenshots(@NonNull File readme, @NonNull Project project) throws IOException {
        if (project.getScreenshots().size() == 0)
            return;
        File tmp = Files.createTempFile("hellstar", null).toFile();
        tmp.createNewFile();
        tmp.deleteOnExit();
        FileUtils.copy("SCREENSHOTS.md", tmp);
        List<String> lines = Files.readAllLines(readme.toPath());
        lines.addAll(Files.readAllLines(tmp.toPath()));
        Files.write(readme.toPath(), lines);
        int index = FileUtils.getIndex(readme, "%screenshots%");

        project.getScreenshots()
                .forEach(path -> {
                    String formattedRaw = FileUtils.formatPath(project.getDataFolder(), path);
                    try {
                        FileUtils.insertLine(readme, index, String.format("<img alt=\"%s\" src=\"%s\"/>", path.getFileName(), formattedRaw));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        FileUtils.replace(readme, "%screenshots%", "\n");
    }

}
