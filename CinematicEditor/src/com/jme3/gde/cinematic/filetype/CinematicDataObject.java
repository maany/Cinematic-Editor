/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.cinematic.filetype;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;

@Messages({
    "LBL_Cinematic_LOADER=Files of Cinematic"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_Cinematic_LOADER",
        mimeType = "application/jme3cinematic",
        extension = {"j3c"})
@DataObject.Registration(
        mimeType = "application/jme3cinematic",
        iconBase = "com/jme3/gde/cinematic/icons/cinematic_clip_icon.png",
        displayName = "#LBL_Cinematic_LOADER",
        position = 300)
@ActionReferences({
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300),
    @ActionReference(
            path = "Loaders/application/jme3cinematic/Actions",
            id =
            @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400)
})
public class CinematicDataObject extends MultiDataObject implements Savable{
    private String data = "sample data";
    private transient boolean modified = false;
    private Lookup lookup;
    private ProxyLookup proxyLookup;
    private InstanceContent content;
    public CinematicDataObject(FileObject fileObject, MultiFileLoader loader) throws DataObjectExistsException, IOException, ClassNotFoundException {
        super(fileObject, loader);
        registerEditor("application/jme3cinematic", false);
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
        proxyLookup = new ProxyLookup(getCookieSet().getLookup(),lookup,super.getLookup());
        File file = FileUtil.toFile(fileObject);
        if (file != null) {
            try{
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            CinematicDataObject fakeCinematic = (CinematicDataObject) oin.readObject();
            initialize(fakeCinematic);
            } catch(java.io.StreamCorruptedException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO,"StreamCurropted for serialized data of the Cinematic Clip(*.j3c)"
                    + " Ignore this if you are not opening any previously saved j3c",ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"File is null. see contructor of CinematicDataObject if you were trying to open a j3c");
        }
    }
    
    private void initialize(CinematicDataObject fakeCinematic){
        this.data = fakeCinematic.getData();
        
    }
    
    @Override
    protected int associateLookup() {
        return 1;
    }
    
    public void setModified(boolean modified,SaveCookie saveCookie){
        this.modified = modified;
        
        if(modified ==true){
            content.add(saveCookie);
            getCookieSet().add(saveCookie);
        } else {
            getCookieSet().remove(saveCookie);
        }
    }
    public boolean isModified(){
        return modified;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
        OutputCapsule out = je.getCapsule(this);
        out.write(data,"data","original data not put but retrieval went fine");
        
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
        InputCapsule in = ji.getCapsule(this);
        data = in.readString(data, "nothing retrieved");
    }

    @Override
    public Lookup getLookup(){
        return proxyLookup;
    }
    
}
