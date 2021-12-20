package me.rochblondiaux.hellstar.model.project;

import lombok.Data;
import lombok.Setter;

import java.io.File;
import java.net.URL;
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

    private String requirements;
    private String usageCommand;

    private final List<URL> screenshots = new ArrayList<>();

}
