package me.rochblondiaux.hellstar.model.project;

import lombok.Data;

import java.io.File;

/**
 * @author Roch Blondiaux
 * www.roch-blondiaux.com
 */
@Data
public class Project {

    private final String name;
    private final String briefDescription;
    private final String description;
    private final String aim;
    private final File dataFolder;
     
}
