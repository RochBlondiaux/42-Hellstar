package me.rochblondiaux.hellstar.model.project;

import lombok.Data;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
@Setter
public class Project {

    private final String name;
    private final String briefDescription;
    private final String description;
    private final String aim;
    private final File dataFolder;
    private final List<Path> screenshots = new ArrayList<>();
    private String requirements;
    private String usageCommand;

}
