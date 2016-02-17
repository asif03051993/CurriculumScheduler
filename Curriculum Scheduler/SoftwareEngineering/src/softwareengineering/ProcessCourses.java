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
 
import au.com.bytecode.opencsv.CSVWriter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static softwareengineering.DropTable.mainClass;

public class ProcessCourses extends JPanel
                       {
    
    private static boolean DEBUG = false;
    private DropTable[] semTables;
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = true;
    private static String lineStyle = "Horizontal";
    private static UndoRedo UndoManager;
    public static Action undoAction;
    public static Action redoAction;
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = true;
    private static final int GAP = 2;
    
    // Courses
    private ArrayList <constraint> all_courses = new ArrayList <constraint> () ;
    
    // MainData contains data of each courses and courses alloted for each semester.
    private static MainClass mainData;  
    
    private String errorTitle;
    private String errorMessage;
    
    public ProcessCourses() {
        
    }
    class MyRenderer extends DefaultTreeCellRenderer {     
        public MyRenderer(){  
        }  
   
        public Component getTreeCellRendererComponent(  
            JTree tree,  
            Object value,  
            boolean sel,  
            boolean expanded,  
            boolean leaf,  
            int row,  
            boolean hasFocus)  
        {  
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            //setSelection(false);
            if (value != null) {
                constraint tipKey;
                if (value instanceof DefaultMutableTreeNode) {
                    tipKey = (constraint) ((DefaultMutableTreeNode) value).getUserObject();
                    String ShowText = "Course Name: " + tipKey.course_name + "; " + "Course No: " + tipKey.course_no + "; " + "Course Type: " + tipKey.type + "; " + "Credits: " + tipKey.credits
                                   + "; " + "Min Sem: " + tipKey.minsem + "; " + "Max Sem: " + tipKey.maxsem ;  
                    setToolTipText(ShowText);  
                }
            }
        return this;  
    }  
   
     
} 
    public ProcessCourses(ArrayList <constraint> courses,MainClass mainClass) {
        super(new GridLayout(1,0));
        mainData = mainClass;
        /** Initializing Objects **/
        semTables = new DropTable[8];
        for(int i = 0; i < 8; i++)
        {
            semTables[i] = new DropTable(8,4);
        }
        UndoManager = new UndoRedo();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        
        JPanel testP = new JPanel();
        //create a BorderLayout-using JPanel
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        borderLayoutPanel.setBorder(BorderFactory.createTitledBorder("Academic Courses"));
        all_courses = courses;
        int CoursesCount = all_courses.size();
        //System.out.println(all_courses.size());
        int rows = (CoursesCount/5) + 1;
        if(CoursesCount%5 == 0)
            rows--;
        int cols = 5;
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for(int i=0 ; i < CoursesCount; i++){
            DefaultMutableTreeNode top = createNodes(all_courses.get(i));
            //Create a tree that allows one selection at a time.
            final JTree tree = new DragTree(top);
            String ShowText = "Course Name: " + all_courses.get(i).course_name + "; " + "Course No: " + all_courses.get(i).course_no + "; " + "Course Type: " + all_courses.get(i).type + "; " + "Credits: " + all_courses.get(i).credits
                                   + "; " + "Min Sem: " + all_courses.get(i).minsem + "; " + "Max Sem: " + all_courses.get(i).maxsem ;
            tree.setToolTipText(ShowText);
            ToolTipManager.sharedInstance().registerComponent(tree);
            MyRenderer renderer = new MyRenderer();
            tree.setCellRenderer(renderer);
            gridPanel.add(tree); // add to the GridLayout using JPanel
        }
        borderLayoutPanel.add(gridPanel, BorderLayout.CENTER); // add a Grid to it
        // set up the main JPanel 
        testP.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        testP.setLayout(new GridLayout(1, 0, GAP, GAP)); // grid with 1 row
        // and add the borderlayout using JPanel to it
        testP.add(borderLayoutPanel);
       
        //create a scroll Pane for Courses Panel
        JScrollPane CoursesView = new JScrollPane(testP);
        //Create the Semester Details viewing pane.
        JScrollPane SemesterView = new JScrollPane(CreateSemesterPanel());
         
        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(CoursesView);
        splitPane.setBottomComponent(SemesterView);

        Dimension minimumSize = new Dimension(100, 50);
        SemesterView.setMinimumSize(minimumSize);
        CoursesView.setMinimumSize(new Dimension(100, 50));
        splitPane.setDividerSize(8);
        splitPane.setDividerLocation(500); 
        splitPane.setPreferredSize(new Dimension(800, 500));

        //Add the split pane to this panel.
        add(splitPane);
    }
    
    /**To get Courses List. */
    ArrayList <constraint> getCourses (){
        return all_courses;
    }
    
    /*** Required to create bottom panel in GUI. */
    private JPanel CreateSemesterPanel(){
        JPanel GUI = new JPanel();
        GUI.setLayout(new BoxLayout(GUI, BoxLayout.LINE_AXIS));
        for(int i=0 ; i < 8 ; i++){
            JPanel sem = new JPanel();
            sem.setLayout(new BoxLayout(sem, BoxLayout.PAGE_AXIS));
            sem.setBorder(BorderFactory.createTitledBorder("Semester" + String.valueOf(i+1)));
            //Create a table with few empty rows and columns.
            //DropTable courses = new DropTable(4,4);
            semTables[i].SemNo = i+1;
            semTables[i].mainClass = mainData;
            semTables[i].UndoManager = UndoManager;
            semTables[i].coursesList = all_courses;
            // To make tables UnEditable
            semTables[i].setEnabled(false);
            //courses.setAutoscrolls(true);
            //courses.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            semTables[i].setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            semTables[i].setDropMode(DropMode.INSERT_ROWS);
            semTables[i].setFillsViewportHeight(true);
            sem.add(new JScrollPane (semTables[i]));
            //JScrollPane scrollpane_sem = new JScrollPane(sem);
            GUI.add(sem);
            //GUI.add(scrollpane_sem);
        }
        return GUI;
    }
    
    public void Export_to_CSV(){
        JFileChooser chooser = new JFileChooser();
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                // Use FileWriter constructor that specifies open for appending
                CSVWriter csvOutput = new CSVWriter(new FileWriter(chooser.getSelectedFile()+".csv", true), ',');
                //Create Header for CSV
                String header[] = {"Semester","Course Name","Course No","Type","Credits"};
                csvOutput.writeNext(header);
                for(int i=0; i < 8; i++){
                    String Row[] = {i+1+"","","","",""}; 
                    DefaultTableModel dtm = (DefaultTableModel)semTables[i].getModel();
                    for(int j=0; j < dtm.getRowCount(); j++){
                        if(dtm.getValueAt(j, 0) != null){
                            for(int k=0; k < dtm.getColumnCount(); k++){
                                Row[k+1] = (String) dtm.getValueAt(j, k);
                            }
                            csvOutput.writeNext(Row);
                        } 
                    }
                }
                csvOutput.flush();
                csvOutput.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
   
    public void Export_to_XLS(){
        JFileChooser chooser = new JFileChooser();
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                /* Define POI Spreadsheet objects */          
                HSSFWorkbook workbook = new HSSFWorkbook(); //create a blank workbook object
                HSSFSheet sheet = workbook.createSheet("XLS_Sheet");  //create a worksheet with caption score_details
                ArrayList <String[]> data = new ArrayList <String[]> ();
                String header[] = {"Semester","Course Name","Course No","Type","Credits"};
                data.add(header);
                for(int i=0; i < 8; i++){
                    String Row[] = {i+1+"","","","",""}; 
                    DefaultTableModel dtm = (DefaultTableModel)semTables[i].getModel();
                    for(int j=0; j < dtm.getRowCount(); j++){
                        if(dtm.getValueAt(j, 0) != null){
                            for(int k=0; k < dtm.getColumnCount(); k++){
                                Row[k+1] = (String) dtm.getValueAt(j, k);
                            }
                            data.add(Row);
                        } 
                    }
                }
                for (int i = 0; i < data.size(); i++) {
                    String[] ardata = data.get(i);
                    HSSFRow row = sheet.createRow((short) 0 + i);
                    for (int k = 0; k < ardata.length; k++) {
                        System.out.print(ardata[k]);
                        HSSFCell cell = row.createCell( k);
                        cell.setCellValue(ardata[k].toString());
                    }
                    System.out.println();
                }
                FileOutputStream fileOutputStream =  new FileOutputStream(chooser.getSelectedFile()+".xls");
                workbook.write(fileOutputStream);
                fileOutputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
      /** The Undo action  **/
  public class UndoAction extends AbstractAction {
    public void actionPerformed(ActionEvent evt) {
      try {
          if(UndoManager.Undo.empty()){
                errorMessage = "No Operation available to perform Undo";
                errorTitle = "Cannot Undo";
                showMessage(evt.getSource());
                return;
          }
          constraint course = mainClass.all_courses.get(mainClass.SearchIndex(UndoManager.Undo.peek().CourseNo, mainClass.all_courses));
          course.Sem_alloted = "";
          // ReSetting state for Fractals
          if(course.fractal_list.size() > 0){
                for (constraint fractal_list : course.fractal_list) {
                    //System.out.println("in fractal" + fractal_list.course_name);
                    ReSetStateForFractals(fractal_list,UndoManager.Undo.peek().SemNo);
                }
          }
          if(course.type.compareTo("Core") == 0){
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].core_courseslist.remove(course);
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].totalcredits_core();   
          }
          else if(course.type.compareTo("Free") == 0){
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].free_courseslist.remove(course);
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].totalcredits_free();
          }
          else if(course.type.compareTo("LA") == 0){
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].liberal_courseslist.remove(course);
                    mainClass.semlist[UndoManager.Undo.peek().SemNo-1].totalcredits_liberal();
          }
          mainClass.semlist[UndoManager.Undo.peek().SemNo-1].set_totalcredits();
          // Need to remove entry from corresponding table
             DefaultTableModel dtm = (DefaultTableModel)semTables[UndoManager.Undo.peek().SemNo-1].getModel();
             String searchedId = UndoManager.Undo.peek().CourseNo; //ID of the course to remove from the table
             int row = -1; //index of row or -1 if not found
             //search for the row based on the ID in the first column
             for(int i=0; i < dtm.getRowCount(); ++i){
                if(dtm.getValueAt(i, 1) == null)
                    continue;
                if(dtm.getValueAt(i, 1).equals(searchedId))
                {
                    row = i;
                    break;
                }
             }
             if(row != -1){
                //dtm.removeRow(row);//remove row
                dtm.setValueAt(null, row, 0);
                dtm.setValueAt(null, row, 1);
                dtm.setValueAt(null, row, 2);
                dtm.setValueAt(null, row, 3);
                System.out.println("Course removed from the Table");
             }
             else
                 System.out.println("Course Not Found in the Table");
          // Adding Actions to Redo and Removing from Undo stack
          UndoManager.Redo.push(UndoManager.Undo.peek());
          UndoManager.Undo.pop();
      } catch (CannotUndoException e) {
        Toolkit.getDefaultToolkit().beep();
      }
    }
  }
  
  /*Changes states of course and fractals after a course was added*/
  void SetStateForFractals(constraint course,int SemNo){
       if(course.fractal_list.size() > 0){
            for (constraint fractal_list : course.fractal_list) {
                //System.out.println("in fractal" + fractal_list.course_name);
                SetStateForFractals(fractal_list,SemNo);
            }
       }
       course.Sem_alloted = ""+SemNo;
       return;
  }
  
  /*Changes states of course and fractals after a course was removed*/
  void ReSetStateForFractals(constraint course,int SemNo){
       if(course.fractal_list.size() > 0){
            for (constraint fractal_list : course.fractal_list) {
                //System.out.println("in fractal" + fractal_list.course_name);
                SetStateForFractals(fractal_list,SemNo);
            }
       }
       course.Sem_alloted = "";
       return;
  }
        
  /** The Redo action **/
  public class RedoAction extends AbstractAction {
    public void actionPerformed(ActionEvent evt) {
      try {
           if(UndoManager.Redo.empty()){
                errorMessage = "No Operation available to perform Redo";
                errorTitle = "Cannot Redo";
                showMessage(evt.getSource());
                return;
          }
          constraint course = mainClass.all_courses.get(mainClass.SearchIndex(UndoManager.Redo.peek().CourseNo, mainClass.all_courses));
          //Checking Constraint before adding the Course
          DropTable T = new DropTable(2,4);
          T.SemNo = UndoManager.Redo.peek().SemNo;
          if(! T.dropMonitor.Constraint_Check(course.course_no)){
                T.err_msg = "Course Selected for Redo: " +T.err_msg;
                JOptionPane.showMessageDialog(null,T.err_msg);
                return;
          }
          course.Sem_alloted = String.valueOf(UndoManager.Redo.peek().SemNo);
          //Setting state for Fractals
          if(course.fractal_list.size() > 0){
                for (constraint fractal_list : course.fractal_list) {
                    //System.out.println("in fractal" + fractal_list.course_name);
                    SetStateForFractals(fractal_list,UndoManager.Redo.peek().SemNo);
                }
          }
          if(course.type.compareTo("Core") == 0){
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].core_courseslist.add(course);
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].totalcredits_core();   
          }
          else if(course.type.compareTo("Free") == 0){
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].free_courseslist.add(course);
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].totalcredits_free();
          }
          else if(course.type.compareTo("LA") == 0){
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].liberal_courseslist.add(course);
                    mainClass.semlist[UndoManager.Redo.peek().SemNo-1].totalcredits_liberal();
          }
          mainClass.semlist[UndoManager.Redo.peek().SemNo-1].set_totalcredits();
          // Need to remove entry from corresponding table
             DefaultTableModel dtm = (DefaultTableModel)semTables[UndoManager.Redo.peek().SemNo-1].getModel();
             int row = -1; //index of row or -1 if not found
             for(int i=0; i < dtm.getRowCount(); ++i){
                if(dtm.getValueAt(i, 1) == null)
                {
                    row = i;
                    break;
                }
             }
             if(row == -1){
                String RowValue[] = {course.course_name,course.course_no,course.type,String.valueOf(course.credits)};
                dtm.addRow(RowValue);       //add new row
                System.out.println("New row added to the Table");
             }
             else{
                 dtm.setValueAt(course.course_name, row, 0);
                 dtm.setValueAt(course.course_no, row, 1);
                 dtm.setValueAt(course.type, row, 2);
                 dtm.setValueAt(course.credits, row, 3);
                 System.out.println("Course added to the Table");
             }
          // Adding Actions to Undo and Removing from Redo stack
          UndoManager.Undo.push(UndoManager.Redo.peek());
          UndoManager.Redo.pop();
      } catch (CannotRedoException e) {
        Toolkit.getDefaultToolkit().beep();
      }
    }
  }
 
  protected void showMessage(Object source) {
      if (source instanceof Component) {
        JOptionPane.showMessageDialog((Component) source, errorMessage,
            errorTitle, JOptionPane.WARNING_MESSAGE);
      } else {
        System.err.println(errorMessage);
      }
  }
   

    private DefaultMutableTreeNode createNodes(constraint course) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(course);
        if(!course.main_course){
            return top;
        }
        for(int i=0; i < course.fractal_list.size() ;i++){
            top.add(createNodes( course.fractal_list.get(i) )); 
        }
        return top;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

        //Create and set up the window.
        JFrame frame = new JFrame("Process Courses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new ProcessCourses());

        //Display the window.
        frame.pack();
        frame.setVisible(true);  
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
