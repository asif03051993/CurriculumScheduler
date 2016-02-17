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
import java.awt.datatransfer.*;
import java.io.IOException;

// Transferable implentation for DragTree
class TreeTransferableHandler implements Transferable,
        ClipboardOwner {

    private constraint Course;
    public static final DataFlavor[] flavors = new DataFlavor[1];
    public static DataFlavor constraintFlavor = null;

    static {
        try {
            constraintFlavor = new DataFlavor(constraint.class,
                    "constraint");
            flavors[0] = constraintFlavor;
        } catch (Exception e) {
        }
    }

    public TreeTransferableHandler(constraint CourseIn) {
        this.Course = CourseIn;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (int j = 0; j < flavors.length; j++) {
            if (constraintFlavor == flavors[j]) {
                return true;
            }
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException,
            IOException {
        if (constraintFlavor != flavor) {
            throw new UnsupportedFlavorException(flavor);
        }
        String[] courseInfo = {"CSE","CS4321","CORE","3"};
        courseInfo[0] = Course.course_name;
        courseInfo[1] = Course.course_no;
        courseInfo[2] = Course.type;
        courseInfo[3] = String.valueOf(Course.credits);
        System.out.println("data sent from transferable");
        return courseInfo;
    }

    public String toString() {
        return "TreeTransferableHandler";
    }

    public void lostOwnership(Clipboard clipboard, Transferable t) {
        System.out.printf("TreeTransferableHandler lost ownership of %s data: %s%n",
                clipboard.getName(), t);
    }
}
