package me.rochblondiaux.hellstar.utils;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class FileUtils {

    public static void replace(@NonNull File file, @NonNull String key, @NonNull String value) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        lines = lines.stream()
                .map(s -> s.replace(key, value))
                .collect(Collectors.toList());
        Files.write(file.toPath(), lines);
    }

    public static void updateLine(@NonNull File file, int index, String line) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        lines.set(index, line);
        Files.write(file.toPath(), lines);
    }

    public static void insertLine(@NonNull File file, int index, String line) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        lines.add(index, line);
        Files.write(file.toPath(), lines);
    }

    public static void copy(@NonNull File source, @NonNull File destinationFile) throws IOException {
        Files.copy(FileUtils.class.getResourceAsStream("/TEMPLATE.md"), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static int getIndex(@NonNull File file, @NonNull String key) throws IOException {
        return Files.readAllLines(file.toPath()).indexOf(key);
    }

    public static String formatPath(@NonNull File dataFolder, @NonNull Path path) {
        String a = path.toString();
        a = a.replace(dataFolder.getAbsolutePath(), "");
        a = a.replace("\\", "/");
        return a;
    }

    public static List<File> walk(@NonNull File dataFolder) {
        try {
            return Files.walk(dataFolder.toPath(), 1)
                    .map(Path::toFile)
                    .filter(File::isDirectory)
                    .filter(file -> !file.equals(dataFolder))
                    .sorted((o1, o2) -> o2.getName().compareTo(o1.getName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @SneakyThrows
    public static List<String> getFunctions(@NonNull File file) {
        Pattern pattern = Pattern.compile("^(?!return|while|if|switch|else)^(.(\\s+)?){2,}\\([^!@#$+%^]+?\\)");
        return Files.readAllLines(file.toPath())
                .stream()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(0))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
