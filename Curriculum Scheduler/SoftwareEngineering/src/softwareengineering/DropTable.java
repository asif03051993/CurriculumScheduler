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
import java.awt.datatransfer.*;  
import java.awt.dnd.*;  
import java.io.IOException;  
import java.util.ArrayList;
import javax.swing.*;  
import javax.swing.table.*;  
   
public class DropTable extends JTable {  
    DropTarget dropTarget;  
    DropTargetMonitor dropMonitor;  
    int dropActions = DnDConstants.ACTION_MOVE;  
    
    /** Indicator for which semester, the table represents 
     *  example: SemNo = 1 : 1st semester
     */
    public int SemNo ;  
    public String err_msg = null;
    public static MainClass mainClass;
    public static UndoRedo UndoManager;
    public ArrayList <constraint> coursesList = new ArrayList <constraint> () ;
    public constraint select = new constraint();
    public constraint chk = new constraint();
    public ArrayList <constraint> check_for = new ArrayList <constraint> ();    

    public DropTable(int rows,int columns) {  
        super(rows,columns);
        setRowSelectionAllowed(false);  
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        dropMonitor = new DropTargetMonitor();  
        dropTarget = new DropTarget(this, dropActions, dropMonitor, true);
        getColumnModel().getColumn(0).setHeaderValue(" CourseName ");
        getColumnModel().getColumn(1).setHeaderValue(" CourseNo ");
        getColumnModel().getColumn(2).setHeaderValue(" CourseType ");
        getColumnModel().getColumn(3).setHeaderValue(" Credits ");
    }  

    
    private void addData(String data[], int row) {  
        // Find next empty row.  
        int emptyRow = getNextEmptyRow(row);  
        if(emptyRow != -1) {  
            shiftValuesDown(row, emptyRow);  
        } else {  
            row = getRowCount();  
            DefaultTableModel model = (DefaultTableModel)getModel();  
            model.setRowCount(getRowCount()+1);  
        }  
        setValueAt(data[0], row, 0);  
        setValueAt(data[1], row, 1);
        setValueAt(data[2], row, 2);
        setValueAt(data[3], row, 3);
    }  
   
    private int getNextEmptyRow(int start) {  
        for(int j = start; j < getRowCount(); j++) {  
            if(getValueAt(j, 0) == null)  
                return j;  
        }  
        return -1;  
    }  
   
    private void shiftValuesDown(int start, int end) {  
        for(int j = end; j > start; j--) {  
            setValueAt(getValueAt(j-1, 0), j, 0);  
            setValueAt(getValueAt(j-1, 1), j, 1);
            setValueAt(getValueAt(j-1, 0), j, 2);  
            setValueAt(getValueAt(j-1, 1), j, 3);
        }  
    }  
   
    class DropTargetMonitor implements DropTargetListener {  
        public void dragEnter(DropTargetDragEvent e) {  
            System.out.println("DropTargetMonitor dragEnter");  
            if(isDragOK(e))  
                e.acceptDrag(e.getDropAction());  
            else  
                e.rejectDrag();  
        }  
   
        public void dragExit(DropTargetEvent e) {  
            System.out.println("DropTargetMonitor dragExit");  
            clearSelection();  
        }  
   
        public void dragOver(DropTargetDragEvent e) {  
            System.out.println("DropTargetMonitor dragOver");  
            Point loc = e.getLocation();  
            int row = rowAtPoint(loc);  
            setRowSelectionInterval(row, row);  
            if(isDragOK(e))  
                e.acceptDrag(e.getDropAction());  
            else  
                e.rejectDrag();  
        }  
   
        public void drop(DropTargetDropEvent e) {
            System.out.println("drop function starts");
            try {  
                Transferable t = e.getTransferable();  
                if(!t.isDataFlavorSupported(TreeTransferableHandler.constraintFlavor))  
                    e.rejectDrop();  
                String data[] =  
                    (String [])t.getTransferData(TreeTransferableHandler.constraintFlavor);  
                Point loc = e.getLocation();
                
                /* Constraint Check */
                if(!Constraint_Check(data[1])){
                    JOptionPane.showMessageDialog(null,err_msg);
                    return ;
                }
                
                select = mainClass.all_courses.get(mainClass.SearchIndex(data[1], mainClass.all_courses));
                boolean AddLab = false;
                constraint Lab = new constraint();
                String LabID = "";
                /*Drag Lab along with Course*/
                if(mainClass.dragLab){
                    /*Find Lab for current Course, if there*/
                    int no = (int) data[1].charAt(data[1].length()-1) - 48;
                    no++;
                    System.out.println("Labno: "+no);
                    LabID = data[1].substring(0, data[1].length()-1) + no;
                    System.out.println("LabID: "+LabID);
                    int index = mainClass.SearchIndex(LabID, mainClass.all_courses);
                    if(index == -1){
                        LabID = "";
                        AddLab = false;
                        System.out.println("No Lab Course Found");
                    }
                    else{
                        Lab = mainClass.all_courses.get(index);
                        /* Constraint Check */
                        if(!Constraint_Check(LabID)){
                            AddLab = false;
                            JOptionPane.showMessageDialog(null,err_msg);
                            return ;
                        }
                        AddLab = true;
                    }
                }
             
                int row = rowAtPoint(loc);  
                if(getValueAt(row, 0) == null ) {  
                    setValueAt(data[0], row, 0);  
                    setValueAt(data[1], row, 1);
                    setValueAt(data[2], row, 2);
                    setValueAt(data[3], row, 3);
                } else {  
                    addData(data, row);  
                }
                /*To add lab along with course*/
                if(AddLab){
                    String LabData[] = {Lab.course_name,Lab.course_no, Lab.type, ""+Lab.credits};
                    addData(LabData, row+1);
                    Lab.Sem_alloted = ""+SemNo;
                    if(LabData[2].compareToIgnoreCase("Core") == 0){
                        mainClass.semlist[SemNo-1].core_courseslist.add(Lab);
                        mainClass.semlist[SemNo-1].totalcredits_core();   
                    } 
                    else if(LabData[2].compareToIgnoreCase("Free") == 0){
                        mainClass.semlist[SemNo-1].free_courseslist.add(Lab);
                        mainClass.semlist[SemNo-1].totalcredits_free();
                    }
                    else if(LabData[2].compareToIgnoreCase("LA") == 0){
                        mainClass.semlist[SemNo-1].liberal_courseslist.add(Lab);
                        mainClass.semlist[SemNo-1].totalcredits_liberal();
                    }
                }
                
                select.Sem_alloted = ""+SemNo;
                constraint course = select;
                if(data[2].compareTo("Core") == 0){
                    mainClass.semlist[SemNo-1].core_courseslist.add(course);
                    mainClass.semlist[SemNo-1].totalcredits_core();   
                }
                else if(data[2].compareTo("Free") == 0){
                    mainClass.semlist[SemNo-1].free_courseslist.add(course);
                    mainClass.semlist[SemNo-1].totalcredits_free();
                }
                else if(data[2].compareTo("LA") == 0){
                    mainClass.semlist[SemNo-1].liberal_courseslist.add(course);
                    mainClass.semlist[SemNo-1].totalcredits_liberal();
                }
                mainClass.semlist[SemNo-1].set_totalcredits();
                // Setting state for Fractals
                if(course.fractal_list.size() > 0){
                    for (constraint fractal_list : course.fractal_list) {
                        SetStateForFractals(fractal_list,SemNo);
                    }
                }
                //Added Start
                // Add course to Undo Stack
                Actions Act = new Actions(select.course_no,SemNo); 
                UndoManager.Undo.push(Act);
                if(AddLab){
                    Actions ActLab = new Actions(Lab.course_no,SemNo); 
                    UndoManager.Undo.push(ActLab);
                }
                //Added End
                
                e.getDropTargetContext().dropComplete(true);  
            } catch(IOException ioe) {  
                e.rejectDrop();  
            } catch(UnsupportedFlavorException ufe) {  
                e.rejectDrop();  
            }  
        }
        /*Changes states after a course was added*/
        void SetStateForFractals(constraint course,int SemNo){
            if(course.fractal_list.size() > 0){
                for (constraint fractal_list : course.fractal_list) {
                    SetStateForFractals(fractal_list,SemNo);
                }
            }
            course.Sem_alloted = ""+SemNo;
            return;
        }
        public void check_fractal_selected(constraint fc)  {
            if(fc.fractal_list.size() > 0){
                for (constraint fractal_list : fc.fractal_list) {
                    System.out.println("in fractal" + fractal_list.course_name);
                    check_fractal_selected(fractal_list);
                }

            }
            System.out.println(fc.course_name);
            if(!(fc.Sem_alloted.equalsIgnoreCase(""))) {
                check_for.add(fc);
            }
        }
        
        public void prin(constraint c)  {
            for(int i=0;i<c.fractal_list.size();i++)    {
                if(c.fractal_list.get(i).fractal_list.isEmpty())    {
                    System.out.println("in prin\t"+c.fractal_list.get(i).course_name);
                }
                else    {
                    prin(c.fractal_list.get(i));
                }
            }
        }
        
        public void lab_check(constraint lab_chk)
        {
            String num = Integer.toString((int)lab_chk.course_no.charAt(lab_chk.course_no.length()-1)-48);//lab_chk.course_no;
            String ch = lab_chk.course_no.substring(0,lab_chk.course_no.length()-1);
            ch+=num;
            int ind = index_check(lab_chk.course_no);
            if(!(ind == -1))   {
                System.out.println("Lab exists");
                constraint course = mainClass.all_courses.get(ind);
                System.out.println("Name: "+course.course_name);
            }
            else
                System.out.println("Lab does not exist");
        }
        
        int index;
        public int index_check(String c_no) {
            index = mainClass.SearchIndex(c_no, mainClass.all_courses);
            return index;
        }
        
        public void pre_Requisite_check(constraint chk_for_pr)    {
            if(chk_for_pr.parent.size() > 0)    {
                System.out.println("parent size > 0");
                for(int i=0;i<chk_for_pr.parent.size();i++) {
                    System.out.println("in for loop"+chk_for_pr.parent.get(i).course_name);
                    pre_Requisite_check(chk_for_pr.parent.get(i));
                }
            }
            System.out.println(chk_for_pr.course_name);
            if(chk_for_pr.Sem_alloted.equalsIgnoreCase(""))     {
                System.out.println("not alloted"+chk_for_pr.course_name);
                //chk = chk_for_pr;
                check_for.add(chk_for_pr);
            }
        }
        
        public boolean Constraint_Check(String courseNo){
            index_check(courseNo);
            if(index_check(courseNo) == -1)
                System.out.println("Course Index not Found in Course List");
            /* If this course or other course with this as elementary, is already alloted to some semester. 
            */
            constraint course = mainClass.all_courses.get(index);
            if(!(course.Sem_alloted.equalsIgnoreCase(""))){
                err_msg = null;
                err_msg = course.course_name+"\n is already selected in\n"+course.Sem_alloted;
                return false;
            }
            
            /** Min Sem - Max Sem  **/
            if( !(course.minsem <= SemNo && course.maxsem >= SemNo) ){
                if(course.minsem > SemNo){
                    err_msg = null;
                    err_msg = course.course_name+" should not be selected before "+course.minsem+" sem";
                }
                else    {
                    err_msg = null;
                    err_msg = course.course_name+" should not be selected after "+course.maxsem+" sem";
                }
                return false;
            }
                
            System.out.println("asdf"+course.course_name);
            
            /** If max credits for semester is exceeding  **/
            if(mainClass.semlist[SemNo-1].set_totalcredits() + course.credits > mainClass.semlist[SemNo-1].maxcredits ){
                err_msg = null;
                err_msg = "Minimun credits should not be greater than Maximum credits";
                return false;
            }
            System.out.println("dres");
            /** If its exceeding max credits based on its type **/
            if(course.type.compareTo("Core") == 0){
                if(mainClass.semlist[SemNo-1].core + course.credits > mainClass.semlist[SemNo-1].max_core){
                    err_msg = null;
                    err_msg = "Exceeds Max Core Credits";
                    return false;
                }
            }
            else if(course.type.compareTo("Free") == 0){
                if(mainClass.semlist[SemNo-1].free + course.credits > mainClass.semlist[SemNo-1].max_free){
                    err_msg = null;
                    err_msg = "Exceeds Max Free Credits";
                    return false;
                }
            }
            else if(course.type.compareTo("LA") == 0){
                if(mainClass.semlist[SemNo-1].liberal + course.credits > mainClass.semlist[SemNo-1].max_liberal){
                    err_msg = null;
                    err_msg = "Exceeds Max Liberal Credits";
                    return false;
                }
            }
            //For labs
            System.out.println("check for labs");
            int lab_chk = course.course_no.charAt(course.course_no.length()-1) - 48;
            lab_chk++;
            String lab_last= Integer.toString(lab_chk);
            String lab = course.course_no.substring(0,course.course_no.length()-1);
            lab +=lab_last;
            
            System.out.println("lab    "+course.course_no+"    "+lab);

            //if fractal --- chck if all fractals of its parent are selected. if true assign the parent to one of its children
            
            
            
           
            String fra_sel = "";
            if( !(course.fractal_list.isEmpty()))   {
                //chk = null;
                check_for.clear();
                fra_sel = "";
                check_fractal_selected(course);
                if (!check_for.isEmpty())    {
                    for (constraint check_for1 : check_for) {
                        fra_sel = fra_sel+check_for1.course_name + " is already alloted in semester "+check_for1.Sem_alloted+"\n";
                    }
                    err_msg = null;
                    err_msg = course+"\n cannot be selected as one of its fractal course\n"+fra_sel;
                    return false;
                }
                
            }
            
            String pre_sel="";
            if(course.has_prerequisite) {
                System.out.println("prepreprepre"+course.course_name);
                check_for.clear();
                pre_sel = "";
                System.out.println("parent size: "+course.parent.size()); 
                for(int i=0; i<course.parent.size(); i++) {
                    System.out.println("check pre"+course.parent.get(i).course_name);
                    pre_Requisite_check(course.parent.get(i));
                }
                if(!check_for.isEmpty())  {
                    for (constraint check_for1 : check_for) {
                        pre_sel = pre_sel+check_for1.course_name + "\n";
                    }
                    err_msg = null;
                    err_msg = course+"\n cannot be selected as all of its pre-requisites are not selected: "+pre_sel;
                    return false;
                }
            }
            
            return true;
        }

        public void dropActionChanged(DropTargetDragEvent e) {  
            if(isDragOK(e))  
                e.acceptDrag(e.getDropAction());  
            else  
                e.rejectDrag();  
        }  
   
        private boolean isDragOK(DropTargetDragEvent e) { 
            if(e.isDataFlavorSupported(TreeTransferableHandler.constraintFlavor) &&  
                   e.getDropAction() == dropActions)
               return true;   
            return false;  
        }  
    }  
}  
