package me.rochblondiaux.hellstar.service;

import lombok.NonNull;
import me.rochblondiaux.hellstar.model.dialog.DialogBuilder;
import me.rochblondiaux.hellstar.model.dialog.DialogType;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
public class ConfigurationService {

    private final File dataFolder;
    private final Properties configuration;
    private File configurationFile;

    public ConfigurationService() {
        this.dataFolder = createConfigurationFolder();
        this.configuration = createOrLoadConfigurationFile("config.properties");

    }

    public Path getLastPath() {
        return Path.of(configuration.getProperty("last_path", System.getProperty("user.home")));
    }

    public void setLastPath(@NonNull Path path) {
        configuration.setProperty("last_path", path.getParent().toString());
        save();
    }

    public void save() {
        if (Objects.nonNull(configuration)) {
            try {
                configuration.store(new FileOutputStream(configurationFile), null);
            } catch (IOException e) {
                new DialogBuilder()
                        .setMessage("Couldn't save properties to configuration file!")
                        .setType(DialogType.WARNING)
                        .build();
                e.printStackTrace();
            }
        }
    }

    private Properties createOrLoadConfigurationFile(@NonNull String name) {
        Properties properties = new Properties();
        configurationFile = new File(dataFolder, name);
        if (!configurationFile.exists()) {
            try {
                configurationFile.createNewFile();
            } catch (IOException e) {
                new DialogBuilder()
                        .setMessage("Couldn't create configuration file!")
                        .setType(DialogType.WARNING)
                        .build();
                e.printStackTrace();
            }
        }
        try {
            properties.load(new FileInputStream(configurationFile));
            return properties;
        } catch (IOException e) {
            new DialogBuilder()
                    .setMessage("Couldn't load properties from configuration file!")
                    .setType(DialogType.WARNING)
                    .build();
            e.printStackTrace();
        }
        return null;
    }

    private File createConfigurationFolder() {
        File file = new File(AppDirsFactory.getInstance().getUserConfigDir("hellstar", "0.1", "Roch Blondiaux"));
        if (file.exists() && !file.isDirectory())
            file.delete();
        if (!file.exists())
            file.mkdirs();
        return file;
    }
}
