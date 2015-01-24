/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.cinematic;

import com.jme3.export.binary.BinaryExporter;
import com.jme3.gde.cinematic.filetype.CinematicDataObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.filesystems.FileAlreadyLockedException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 * Manages the data object and content displayed by the cinematic editor
 * @author MAYANK
 */
public class CinematicEditorManager {

    private static CinematicEditorManager instance;
    private CinematicDataObject currentDataObject;

    private CinematicEditorManager() {
        // empty private constructor for singleton class
    }

    public static CinematicEditorManager getInstance() {
        if (instance == null) {
            instance = new CinematicEditorManager();
        }
        return instance;
    }

    public void saveCinematic(CinematicDataObject dataObject,boolean serialize) {
        String userHome = System.getProperty("user.home");
        if (serialize == false) {
            BinaryExporter exporter = BinaryExporter.getInstance();
            File file = new File(userHome + "/Cinematic/" + "MyCinematic.j3c");
            try {
                exporter.save(dataObject, file);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error : Failed to save cinematic!", ex);
            }
        } else {
            try {
                FileObject primaryFile = dataObject.getPrimaryFile();
                File file = FileUtil.toFile(primaryFile);
                FileOutputStream fout = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(dataObject);
                fout.close();
                Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Cinematic successfully saved !! Location : " + file.getAbsolutePath());
            } catch (FileAlreadyLockedException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error : Failed to save cinematic clip !!",ex);
            }
        }
    }
    
    public CinematicDataObject getCurrentDataObject() {
        return currentDataObject;
    }

    public void setCurrentDataObject(CinematicDataObject currentDataObject) {
        this.currentDataObject = currentDataObject;
    }


    
}
