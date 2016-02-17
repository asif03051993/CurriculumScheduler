 /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
 
package softwareengineering;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import au.com.bytecode.opencsv.CSVReader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

public class Import extends JPanel implements ActionListener {
    private static JFrame frame;
    private JSplitPane splitPane;
    private ImageLabel WelcomeNote_LeftLabel;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem5;
    private JCheckBoxMenuItem DragLab;
    private static MainClass mainClass;
    private static Action undoAction;
    private static Action redoAction;
    private int imageNo;
    private boolean ImportCourses;
    private boolean ImportSemester;
    private String errorTitle;
    private String errorMessage;
    
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = true;
    public Import() throws IOException {
        super(new BorderLayout());
        mainClass = new MainClass();
        ImportCourses = false;
        ImportSemester = false;
        setSize( 500, 500 );
        setBackground( Color.WHITE );
        final JPanel leftPanel =  createVerticalBoxPanel();
        final JPanel rightPanel = createVerticalBoxPanel();
        
        //Create WelcomeNote Label
        WelcomeNote_LeftLabel = new ImageLabel("images/iith.png");
        imageNo = 1;
        leftPanel.add(createPanelForComponent(WelcomeNote_LeftLabel, "        Welcome Note     "));
        
        //Create Instructions Label
        ImageLabel welcomeImage = new ImageLabel("images/WelcomeNote.JPG");
        rightPanel.add(createPanelForComponent(welcomeImage, "     Instructions     "));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                              leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(8);
        splitPane.setDividerLocation(1500);
        add(splitPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        int oneSecondDelay = 5000;
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (imageNo){
                    case 1: 
                try {
                    WelcomeNote_LeftLabel.SetImageLabel("images/acad.jpg");
                    WelcomeNote_LeftLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
                }
                        imageNo = 2;  
                        break;
                    case 2:
                try {
                    WelcomeNote_LeftLabel.SetImageLabel("images/iit-hyderabad.jpg");
                    WelcomeNote_LeftLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
                }
                        imageNo = 3;  
                        break;
                     case 3:
                try {
                    WelcomeNote_LeftLabel.SetImageLabel("images/iith.png");
                    WelcomeNote_LeftLabel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
                }
                        imageNo = 1;  
                        break;
                                
                }
            }
        };
        new javax.swing.Timer(oneSecondDelay, task).start();
    }
    
    protected JPanel createVerticalBoxPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return p;
    }

    public JPanel createPanelForComponent(JComponent comp,
                                          String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comp, BorderLayout.CENTER);
        if (title != null) {
            panel.setBorder(BorderFactory.createTitledBorder(title));
        }
        return panel;
    }

     /**
     * Create an Actions menu to support different Operations.
     */
    public JMenuBar createMenuBar() {
        menuItem1 = null;
        menuItem2 = null;
        menuItem3 = null;
        menuItem5 = null;
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Actions");
        mainMenu.setMnemonic(KeyEvent.VK_E);
        
        menuItem1 = new JMenuItem("Semester Details");
        menuItem1.setActionCommand("menuItem1");
        menuItem1.addActionListener(this);
        mainMenu.add(menuItem1);
        
        menuItem5 = new JMenuItem("Import Semester Details From a File");
        menuItem5.setActionCommand("menuItem5");
        menuItem5.addActionListener(this);
        mainMenu.add(menuItem5);
        
        menuItem2 = new JMenuItem("Import Courses File");
        menuItem2.setActionCommand("menuItem2");
        menuItem2.addActionListener(this);
        mainMenu.add(menuItem2);
        
        menuItem3 = new JMenuItem("Process Courses");
        menuItem3.setActionCommand("menuItem3");
        menuItem3.addActionListener(this);
        mainMenu.add(menuItem3);
        
        menuBar.add(mainMenu);
        
        return menuBar;
    }
    
    protected SemInput makeTextPanel(String text) {
        SemInput panel = new SemInput();
        panel.jLabel6.setText(text);
        return panel;
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Import.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    private SemInput panel1 = null;
    private SemInput panel2 = null;
    private SemInput panel3 = null;
    private SemInput panel4 = null;
    private SemInput panel5 = null;
    private SemInput panel6 = null;
    private SemInput panel7 = null;
    private SemInput panel8 = null;
    private ProcessCourses processCourses;
    JLabel[] Sem_Lab = new JLabel[8];
 
    public void actionPerformed(ActionEvent e) {
        
        if ("menuItem1".equals(e.getActionCommand())) {
             
            // Add code for JTabbed Pane
            JTabbedPane tabbedPane = new JTabbedPane();
            ImageIcon icon = createImageIcon("images/book.jpg");

            panel1 = makeTextPanel("SEMESTER 1");
            tabbedPane.addTab("Semester 1", icon, panel1,
                  "Fill Semester 1 details");
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            panel2 = makeTextPanel("SEMESTER 2");
            tabbedPane.addTab("Semester 2", icon, panel2,
                  "Fill Semester 2 details");
            tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

            panel3 = makeTextPanel("SEMESTER 3");
            tabbedPane.addTab("Semester 3", icon, panel3,
                  "Fill Semester 3 details");
            tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

            panel4 = makeTextPanel(
                "SEMESTER 4");
            tabbedPane.addTab("Semester 4", icon, panel4,
                      "Fill Semester 4 details");
            tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
            panel5 = makeTextPanel("SEMESTER 5");
            tabbedPane.addTab("Semester 5", icon, panel5,
                  "Fill Semester 5 details");
            tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

            panel6 = makeTextPanel("SEMESTER 6");
            tabbedPane.addTab("Semester 6", icon, panel6,
                  "Fill Semester 6 details");
            tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);

            panel7 = makeTextPanel("SEMESTER 7");
            tabbedPane.addTab("Semester 7", icon, panel7,
                  "Fill Semester 7 details");
            tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);

            panel8 = makeTextPanel(
                "SEMESTER 8");
            tabbedPane.addTab("Semester 8", icon, panel8,
                      "Fill Semester 8 details");
            tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);
            //end of Jtabbed pane creation
            JPanel NewleftPanel =  createVerticalBoxPanel();
            JLabel BTechCredits = new JLabel();
            BTechCredits.setText("BTech Credits");
            String[] patternExamples = new String[101];
            for(int i=0; i < 101; i++){
                patternExamples[i] = 100+i+""; 
            }
            final JComboBox BTechCreditsList = new JComboBox(patternExamples);
            BTechCreditsList.setEditable(true);
            BTechCreditsList.addActionListener(this);
            JButton Submit = new JButton();
            Submit.setText("SUBMIT");
            Submit.setSize(40,40);
            Submit.setActionCommand("SemesterSubmit");
            Submit.addActionListener(this);
            
            
            Submit.addActionListener(new ActionListener() {
                int count = 0;
                public void actionPerformed (ActionEvent e) {
                   count = 0;
                   try {
                        mainClass.BtechCredits = Integer.parseInt( (String) BTechCreditsList.getSelectedItem());
                    } catch (NumberFormatException ne) {
                        System.out.println("Wrong number");
                        errorMessage = "Btech Credits should be in Integer (number) format";
                        errorTitle = "Wrong Format for BTech Credits";
                        showMessage(e.getSource());
                        return;
                   }
                    /** 1st Semester **/
                   mainClass.semlist[0].min_core = panel1.core[0];
                   //System.out.println("min_core"+mainClass.semlist[0].min_core);
                   mainClass.semlist[0].max_core = panel1.core[1];
                   mainClass.semlist[0].min_free = panel1.free[0];
                   mainClass.semlist[0].max_free = panel1.free[1];
                   mainClass.semlist[0].min_liberal = panel1.la[0];
                   mainClass.semlist[0].max_liberal = panel1.la[1];
                   mainClass.semlist[0].maxcredits = panel1.MaxSemCredits;
                   mainClass.semlist[0].setCreditsForSem();
                   
                    /** 2nd Semester **/
                   mainClass.semlist[1].min_core = panel2.core[0];
                   mainClass.semlist[1].max_core = panel2.core[1];
                   mainClass.semlist[1].min_free = panel2.free[0];
                   mainClass.semlist[1].max_free = panel2.free[1];
                   mainClass.semlist[1].min_liberal = panel2.la[0];
                   mainClass.semlist[1].max_liberal = panel2.la[1];
                   mainClass.semlist[1].maxcredits = panel2.MaxSemCredits;
                   mainClass.semlist[1].setCreditsForSem();
                 
                   /** 3rd Semester **/
                   mainClass.semlist[2].min_core = panel3.core[0];
                   mainClass.semlist[2].max_core = panel3.core[1];
                   mainClass.semlist[2].min_free = panel3.free[0];
                   mainClass.semlist[2].max_free = panel3.free[1];
                   mainClass.semlist[2].min_liberal = panel3.la[0];
                   mainClass.semlist[2].max_liberal = panel3.la[1];
                   mainClass.semlist[2].maxcredits = panel3.MaxSemCredits;
                   mainClass.semlist[2].setCreditsForSem();
                    /** 4th Semester **/
                   mainClass.semlist[3].min_core = panel4.core[0];
                   mainClass.semlist[3].max_core = panel4.core[1];
                   mainClass.semlist[3].min_free = panel4.free[0];
                   mainClass.semlist[3].max_free = panel4.free[1];
                   mainClass.semlist[3].min_liberal = panel4.la[0];
                   mainClass.semlist[3].max_liberal = panel4.la[1];
                   mainClass.semlist[3].maxcredits = panel4.MaxSemCredits;
                   mainClass.semlist[3].setCreditsForSem();
                   /** 5th Semester **/
                   mainClass.semlist[4].min_core = panel5.core[0];
                   mainClass.semlist[4].max_core = panel5.core[1];
                   mainClass.semlist[4].min_free = panel5.free[0];
                   mainClass.semlist[4].max_free = panel5.free[1];
                   mainClass.semlist[4].min_liberal = panel5.la[0];
                   mainClass.semlist[4].max_liberal = panel5.la[1];
                   mainClass.semlist[4].maxcredits = panel5.MaxSemCredits;
                   mainClass.semlist[4].setCreditsForSem();
                   /** 6th Semester **/
                   mainClass.semlist[5].min_core = panel6.core[0];
                   mainClass.semlist[5].max_core = panel6.core[1];
                   mainClass.semlist[5].min_free = panel6.free[0];
                   mainClass.semlist[5].max_free = panel6.free[1];
                   mainClass.semlist[5].min_liberal = panel6.la[0];
                   mainClass.semlist[5].max_liberal = panel6.la[1];
                   mainClass.semlist[5].maxcredits = panel6.MaxSemCredits;
                   mainClass.semlist[5].setCreditsForSem();
                   /** 7th Semester **/
                   mainClass.semlist[6].min_core = panel7.core[0];
                   mainClass.semlist[6].max_core = panel7.core[1];
                   mainClass.semlist[6].min_free = panel7.free[0];
                   mainClass.semlist[6].max_free = panel7.free[1];
                   mainClass.semlist[6].min_liberal = panel7.la[0];
                   mainClass.semlist[6].max_liberal = panel7.la[1];
                   mainClass.semlist[6].maxcredits = panel7.MaxSemCredits;
                   mainClass.semlist[6].setCreditsForSem();
                   /** 8th Semester **/
                   mainClass.semlist[7].min_core = panel8.core[0];
                   mainClass.semlist[7].max_core = panel8.core[1];
                   mainClass.semlist[7].min_free = panel8.free[0];
                   mainClass.semlist[7].max_free = panel8.free[1];
                   mainClass.semlist[7].min_liberal = panel8.la[0];
                   mainClass.semlist[7].max_liberal = panel8.la[1];
                   mainClass.semlist[7].maxcredits = panel8.MaxSemCredits;
                   mainClass.semlist[7].setCreditsForSem();
                   ImportSemester = true;
                   /*for(int i=0;i<8;i++) {
                       System.out.println("after all sem details"+mainClass.semlist[i].min_core+mainClass.semlist[i].max_core);
                       Sem_Lab[i] = new JLabel("Semester"+i+1+"\n"
                                                +"Core"+mainClass.semlist[i].min_core
                                                +"  "+mainClass.semlist[i].max_core+"\n"
                                                +"free"+mainClass.semlist[i].min_free
                                                +"  "+mainClass.semlist[i].max_free+"\n"
                                                +"liberal"+mainClass.semlist[i].min_liberal
                                                +"  "+mainClass.semlist[i].max_liberal);
                       NewrightPanel.add(Sem_Lab[i]);
                   }*/
                }
            } );
            
            JLabel min_max = new JLabel("              min     MAX");
            NewleftPanel.add(createPanelForComponent(tabbedPane, "Academic Semester Details"));
            NewleftPanel.add(BTechCredits);
            NewleftPanel.add(BTechCreditsList);
            NewleftPanel.add(Submit);
            //Create Instructions Label
            JPanel NewrightPanel =  createVerticalBoxPanel();
            ImageLabel SemesterGUI;
            try {
                SemesterGUI = new ImageLabel("images/SemesterGUI.JPG");
                NewrightPanel.add(createPanelForComponent(SemesterGUI, "     Instructions     "));
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            splitPane.setLeftComponent(NewleftPanel);
            splitPane.setRightComponent(NewrightPanel);
        }
        if ("DragLab".equals(e.getActionCommand())){
            if(DragLab.getState())
                mainClass.dragLab = true;
            else
                mainClass.dragLab = false;
        }
        if ("menuItem3".equals(e.getActionCommand())) {
            if(!ImportCourses){
                 errorMessage = "Cannot Proceed without Importing Courses Details from CSV file";
                 errorTitle = "Should Complete this Action";
                 showMessage(e.getSource());
                 return;
            }
            else if(!ImportSemester){
                errorMessage = "Cannot Proceed without Importing Semester Details from CSV file";
                errorTitle = "Should Complete this Action";
                showMessage(e.getSource());
                return;
            }
            JPanel NewleftPanel =  createVerticalBoxPanel();
            JPanel NewrightPanel =  createVerticalBoxPanel();
            // Deep Copy of Coutses list.
            // Else direct assignment would lead to Shalow copy.
            ArrayList <constraint> list = new ArrayList <constraint>();
            for(int i=0;i < mainClass.all_courses.size() ; i++){
                list.add(mainClass.all_courses.get(i));
            }
            //ArrayList <constraint> ReducedRepresentation = new ArrayList <constraint>();
            for(int i=0;i < list.size() ; i++){
                if(list.get(i).main_course){
                    for(int j=0; j < list.get(i).fractal_list.size(); j++){
                        int k = mainClass.SearchIndex(list.get(i).fractal_list.get(j).course_no, list);
                        list = mainClass.RemoveCoursefromList(list.get(i).fractal_list.get(j).course_no, list);
                        if(k < i)
                            i--;
                    }
                }
            }
            //System.out.println(list.size());
            processCourses = new ProcessCourses(list,mainClass);
            
            // Edit Jmenu
            JMenuItem undoButton = new JMenuItem("Undo");
            JMenuItem redoButton = new JMenuItem("Redo");
            undoAction = ProcessCourses.undoAction;
            redoAction = ProcessCourses.redoAction;
            undoButton.addActionListener(undoAction);
            redoButton.addActionListener(redoAction);
            JMenu editMenu = new JMenu("Edit");
            editMenu.add(undoButton);
            editMenu.add(redoButton);
         
            // CheckBox Menu Item for dragging Lab along with Course
            DragLab = new JCheckBoxMenuItem("Drag Lab Along With Course",false);
            DragLab.setActionCommand("DragLab");
            DragLab.addActionListener(this);
            editMenu.add(DragLab);
           
            // Disabling Import MenuItems
            menuItem1.setEnabled(false);
            menuItem2.setEnabled(false);
            menuItem5.setEnabled(false);
            //menuItem3.setEnabled(false);
            
            //Export Jmenu
            JMenu exportMenu = new JMenu("Export");
            JMenuItem exportButtonCSV = new JMenuItem("Export to CSV File");
            exportButtonCSV.setActionCommand("Export to CSV File");
            exportButtonCSV.addActionListener(this);
            JMenuItem exportButtonXLS = new JMenuItem("Export to XLS File");
            exportButtonXLS.setActionCommand("Export to XLS File");
            exportButtonXLS.addActionListener(this);
            exportMenu.add(exportButtonCSV);
            exportMenu.add(exportButtonXLS);
            
            // Adding to Menubar
            JMenuBar menuBar = frame.getJMenuBar();
            menuBar.add(editMenu);
            menuBar.add(exportMenu);
            frame.setJMenuBar(menuBar);
            // Assign the actions to keys
            splitPane.registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
            splitPane.registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
            NewleftPanel.add(processCourses);
            NewleftPanel.setBackground(Color.blue);
            //Create Instructions Label
            ImageLabel ProcessCourses;
            try {
                ProcessCourses = new ImageLabel("images/ProcessCourses.JPG");
                NewrightPanel.add(createPanelForComponent(ProcessCourses, "     Instructions     "));
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            splitPane.setLeftComponent(NewleftPanel);
            splitPane.setRightComponent(NewrightPanel);
        }
        if("Export to XLS File".equals(e.getActionCommand())){
            check_constraints();
            if(!check_constraints())    {
                errorTitle = "Constraint Violations";
                JTextArea textArea = new JTextArea(errorMessage);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);  
                textArea.setLineWrap(true);  
                textArea.setWrapStyleWord(true); 
                scrollPane.setPreferredSize( new Dimension( 1000, 500 ) );
                JOptionPane.showMessageDialog(null, scrollPane, errorTitle,  
                                       JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(processCourses != null){
                processCourses.Export_to_XLS();
            }
        }
        if ("Export to CSV File".equals(e.getActionCommand()))   {
            check_constraints();
            if(!check_constraints())    {
                errorTitle = "Constraint Violations";
                JTextArea textArea = new JTextArea(errorMessage);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);  
                textArea.setLineWrap(true);  
                textArea.setWrapStyleWord(true); 
                scrollPane.setPreferredSize( new Dimension( 1000, 500 ) );
                JOptionPane.showMessageDialog(null, scrollPane, errorTitle,  
                                       JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(processCourses != null){
                processCourses.Export_to_CSV();
            }
        }
        if ("menuItem2".equals(e.getActionCommand())) {
            JFileChooser openFile = new JFileChooser();
            openFile.showOpenDialog(null);
            if(openFile.getSelectedFile() == null)
                return;
            String fileName = openFile.getSelectedFile().toString();
            //String fileName = "CoursesCSVFile.csv";
            CSVReader reader = null;
            try {
                reader = new CSVReader(new FileReader(fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Create a table model.
            DefaultTableModel tm = new DefaultTableModel();
            try {
                // if the first line is the header
                String[] header = reader.readNext();
                for(int i = 0; i < header.length ; i++){
                        tm.addColumn(header[i]);
                }
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            // iterate over reader.readNext until it returns null
            String [] nextLine;
            try {
                while ((nextLine = reader.readNext()) != null) {
                    // nextLine[] is an array of values from the line
                    //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                    Vector<String> RowValues = new Vector<String>();
                    for(int i = 0; i < nextLine.length; i++){
                        RowValues.add(nextLine[i]);
                    }
                    tm.addRow(RowValues);
                    if(mainClass.setCourseDetails(RowValues) != true){
                        // Code for Error Dialog
                    }
                    ImportCourses = true;
                }
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            JPanel NewleftPanel =  createVerticalBoxPanel();
            JPanel NewrightPanel =  createVerticalBoxPanel();
            //Create table
            JTable table = new JTable(tm);
            // To make table UnEditable
            table.setEnabled(false);
            table.setForeground(Color.white);
            table.setBackground(new Color(  92,   51, 255));
            //Create the scroll pane and add the table to it.
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBackground(Color.white);
            NewleftPanel.add(createPanelForComponent(scrollPane, "List of Courses"));
            NewleftPanel.setBackground(Color.blue);
            //Create Instructions Label
            ImageLabel CoursesCSV;
            try {
                CoursesCSV = new ImageLabel("images/CoursesCSV.JPG");
                NewrightPanel.add(createPanelForComponent(CoursesCSV, "     Instructions     "));
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            splitPane.setLeftComponent(NewleftPanel);
            splitPane.setLeftComponent(NewleftPanel);
            splitPane.setRightComponent(NewrightPanel);
        }
        if ("menuItem5".equals(e.getActionCommand())) {
            JFileChooser openFile = new JFileChooser();
            openFile.showOpenDialog(null);
            if(openFile.getSelectedFile() == null)
                return;
            String fileName = openFile.getSelectedFile().toString();
            //String fileName = "CoursesCSVFile.csv";
            CSVReader reader = null;
            try {
                reader = new CSVReader(new FileReader(fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            // To change
            //Create a table model.
            DefaultTableModel tm = new DefaultTableModel();
            try {
                // if the first line is the header
                String[] header = reader.readNext();
                for(int i = 0; i < header.length ; i++){
                        tm.addColumn(header[i]);
                }
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            // iterate over reader.readNext until it returns null
            String [] nextLine;
            try {
                int semNo = 0;
                while ((nextLine = reader.readNext()) != null) {
                    // nextLine[] is an array of values from the line
                    //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                    if(nextLine[0].compareToIgnoreCase("Btech Total Credits") == 0){
                        mainClass.BtechCredits = Integer.valueOf(nextLine[1]);  
                        continue;
                    }
                    Vector<String> RowValues = new Vector<String>();
                    for(int i = 0; i < nextLine.length; i++){
                        RowValues.add(nextLine[i]);
                    }
                    tm.addRow(RowValues);
                    // Store Semester Details in mainClass
                    mainClass.semlist[semNo].min_core = Integer.valueOf(RowValues.get(1));
                    mainClass.semlist[semNo].max_core = Integer.valueOf(RowValues.get(2));
                    mainClass.semlist[semNo].min_free = Integer.valueOf(RowValues.get(3));
                    mainClass.semlist[semNo].max_free = Integer.valueOf(RowValues.get(4));
                    mainClass.semlist[semNo].min_liberal = Integer.valueOf(RowValues.get(5));
                    mainClass.semlist[semNo].max_liberal = Integer.valueOf(RowValues.get(6));
                    mainClass.semlist[semNo].maxcredits = Integer.valueOf(RowValues.get(7));
                    mainClass.semlist[semNo].setCreditsForSem();
                    semNo++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImportSemester = true;
            // To change
            JPanel NewleftPanel =  createVerticalBoxPanel();
            JPanel NewrightPanel =  createVerticalBoxPanel();
            //Create table
            JTable table = new JTable(tm);
            // To make table UnEditable
            table.setEnabled(false);
            table.setForeground(Color.white);
            table.setBackground(new Color(  92,   51, 255));
            //Create the scroll pane and add the table to it.
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBackground(Color.white);
            NewleftPanel.add(createPanelForComponent(scrollPane, "List of Each Semester Details"));
            NewleftPanel.setBackground(Color.blue);
            //Create Instructions Label
            ImageLabel SemesterCSV;
            try {
                SemesterCSV = new ImageLabel("images/SemesterCSV.JPG");
                NewrightPanel.add(createPanelForComponent(SemesterCSV, "     Instructions     "));
            } catch (IOException ex) {
                Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
            }
            splitPane.setLeftComponent(NewleftPanel);
            splitPane.setRightComponent(NewrightPanel);
        }
        
    }
    
    boolean constraints_check;
     public boolean check_constraints()  {
        constraints_check = true;
        errorMessage = "";
        int semester_number = 0;
        int currentBTechSelectedCredits = 0;
        for (semester_number = 0;semester_number < 8;semester_number++) {
            currentBTechSelectedCredits += mainClass.semlist[semester_number].totalcredits; 
            if(mainClass.semlist[semester_number].totalcredits < mainClass.semlist[semester_number].mincredits){
                constraints_check = false;
                    errorMessage += "\nSemester MinCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].mincredits
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits;
                }
            if(mainClass.semlist[semester_number].totalcredits > mainClass.semlist[semester_number].mincredits){
                    constraints_check = false;
                    errorMessage += "\nSemester MaxCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].maxcredits
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits;
                }
            if(mainClass.semlist[semester_number].totalcredits_core() < mainClass.semlist[semester_number].min_core){
                    constraints_check = false;
                    errorMessage += "\nCore MinCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].min_core
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_core();
                }
            if(mainClass.semlist[semester_number].totalcredits_core() > mainClass.semlist[semester_number].max_core){
                    constraints_check = false;
                    errorMessage += "\nCore MaxCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].max_core
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_core();
                }
            if(mainClass.semlist[semester_number].totalcredits_free() < mainClass.semlist[semester_number].min_free){
                    constraints_check = false;
                    errorMessage += "\nFree MinCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].min_free
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_free();
                }
            if(mainClass.semlist[semester_number].totalcredits_free() > mainClass.semlist[semester_number].max_free){
                    constraints_check = false;
                    errorMessage += "\nFree MaxCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].max_free
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_free();
                }
            if(mainClass.semlist[semester_number].totalcredits_liberal() > mainClass.semlist[semester_number].min_liberal){
                    constraints_check = false;
                    errorMessage += "\nLiberal MinCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].min_liberal
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_liberal();
                }
            if(mainClass.semlist[semester_number].totalcredits_liberal() > mainClass.semlist[semester_number].max_liberal){
                    constraints_check = false;
                    errorMessage += "\nLiberal MaxCredits criteria in Semester"+semester_number
                            +"   Required: "+mainClass.semlist[semester_number].max_liberal
                            +"   Current Selection: "+mainClass.semlist[semester_number].totalcredits_liberal();
                }
        }
        if(currentBTechSelectedCredits < mainClass.BtechCredits){
            constraints_check = false;
                    errorMessage = "\nTotal BTech Credits are not satisfied"
                            +"   Required: "+mainClass.BtechCredits
                            +"   Current Selection: "+currentBTechSelectedCredits + errorMessage;
        }
        errorMessage = "Cannot be exported due to the following error" + errorMessage;
        return constraints_check;
    }
     
    protected void showMessage(Object source) {
      if (source instanceof Component) {
        JOptionPane.showMessageDialog((Component) source, errorMessage,
            errorTitle, JOptionPane.WARNING_MESSAGE);
      } else {
        System.err.println(errorMessage);
      }
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws IOException {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
        //Create and set up the window.
        frame = new JFrame("Curriculum");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 500));
        //Create and set up the content pane.
        Import newContentPane = new Import();
        frame.setJMenuBar(newContentPane.createMenuBar());
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fontss
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                try {
                    createAndShowGUI();
                } catch (IOException ex) {
                    Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}