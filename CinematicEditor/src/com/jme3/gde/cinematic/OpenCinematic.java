/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.cinematic;

import com.jme3.gde.cinematic.filetype.CinematicDataObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Cinematic",
        id = "com.jme3.gde.cinematic.OpenCinematic")
@ActionRegistration(
        iconBase = "com/jme3/gde/cinematic/icons/cinematic_editor_icon.png",
        displayName = "#CTL_OpenCinematic")
@ActionReferences({
    @ActionReference(path = "Toolbars/SceneComposer-Tools", position = 250),
    @ActionReference(path = "Loaders/application/jme3cinematic/Actions", position = 0)
})
@Messages("CTL_OpenCinematic=Open in CInematic Editor")
public final class OpenCinematic implements ActionListener {

    private final CinematicDataObject context;

    public OpenCinematic(CinematicDataObject context) {
        this.context = context;
        JOptionPane.showMessageDialog(null, "Loaded Data : " + context.getData());
        CinematicEditorManager.getInstance().setCurrentDataObject(context);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
