/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softwareengineering;

  /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
import java.awt.*;  
import java.awt.event.*;  
import java.awt.datatransfer.*;  
import java.awt.dnd.*;  
import javax.swing.*;  
import javax.swing.tree.*;  
   
public class DragTree extends JTree  {  
    DragSource dragSource;  
    DragSourceMonitor dragSourceMonitor;  
    DragGestureMonitor dragGestureMonitor;  
    int dragAction = DnDConstants.ACTION_MOVE;  
   
    public DragTree(DefaultMutableTreeNode top) {  
        super(top);  
        setSelectionPath(null);
        dragSource = DragSource.getDefaultDragSource();  
        dragSourceMonitor = new DragSourceMonitor();  
        dragGestureMonitor = new DragGestureMonitor();  
        DragGestureRecognizer dgr =  
            dragSource.createDefaultDragGestureRecognizer(this, dragAction,  
                                                          dragGestureMonitor);  
        // Disable popupTrigger for dragSource.  
        dgr.setSourceActions(dgr.getSourceActions() & ~InputEvent.BUTTON3_MASK);  
    }  
   
    class DragGestureMonitor implements DragGestureListener {  
        public void dragGestureRecognized(DragGestureEvent e) {  
            // If a node has been selected get a reference to it.  
            Point p = e.getDragOrigin();  
            TreePath path = getPathForLocation(p.x, p.y);  
            DefaultMutableTreeNode node = null;  
            if(path != null)  
                node = (DefaultMutableTreeNode)path.getLastPathComponent();  
            if(node != null) {  
                System.out.printf("node = %s%n", node);  
                // If node is being edited, stop edit.  
                if(isEditing()) {  
                    TreePath editPath = getEditingPath();  
                    DefaultMutableTreeNode editNode =  
                        (DefaultMutableTreeNode)editPath.getLastPathComponent();  
                    if(node == editNode)  
                        stopEditing();  
                } 
                constraint course = (constraint)node.getUserObject();
                Transferable transferable = new TreeTransferableHandler(course);
                Cursor cursor = selectCursor (e.getDragAction());
                dragSource.startDrag(e, cursor, transferable, dragSourceMonitor);
                
            }  
        }
        private Cursor selectCursor (int action) {
         return (action == DnDConstants.ACTION_MOVE) ?
           DragSource.DefaultMoveDrop : DragSource.DefaultCopyDrop;
       }
    }  
   
    class DragSourceMonitor implements DragSourceListener  {  
        // To remove node from Tree after Dragging
        public void dragDropEnd(DragSourceDropEvent e) {
            setSelectionPath(null);
            if(!e.getDropSuccess()) {  
                System.out.println("drop failed");  
                return;  
            }  
            System.out.println("DragSourceMonitor dragDropEnd");
            //If execution reached till here, Drag drop ended successfully
        }  
   
        public void dragEnter(DragSourceDragEvent e) {  
            //System.out.println("DragSourceMonitor dragSource");  
            setSelectionPath(null);
            DragSourceContext context = e.getDragSourceContext();  
            Cursor cursor = DragSource.DefaultMoveNoDrop;  
            if((e.getDropAction() & dragAction) != 0)  
                cursor = DragSource.DefaultMoveDrop;  
            context.setCursor(cursor);  
        }  
   
        public void dragExit(DragSourceEvent e) {  
            //System.out.println("DragSourceMonitor dragSource");  
            DragSourceContext context = e.getDragSourceContext();  
            context.setCursor(DragSource.DefaultMoveNoDrop);  
        }  
   
        public void dragOver(DragSourceDragEvent e) {  
           // System.out.println("DragSourceMonitor dragOver");  
        }  
   
        public void dropActionChanged(DragSourceDragEvent e) {  
            DragSourceContext context = e.getDragSourceContext();  
            context.setCursor(DragSource.DefaultMoveNoDrop);  
        }  
    }  
}  