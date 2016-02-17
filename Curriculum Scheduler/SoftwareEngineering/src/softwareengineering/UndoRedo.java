/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softwareengineering;

import java.util.Stack;

 /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
public class UndoRedo {
   public Stack <Actions> Undo;
   public Stack <Actions> Redo;
   UndoRedo(){
       Undo = new Stack <Actions> ();
       Redo = new Stack <Actions> ();
       Undo.clear();
       Redo.clear();
   }
}
class Actions{
    String CourseNo;
    //String LabNo;
    int SemNo;
    Actions(String No,int sem){
        CourseNo = No;
        //LabNo = LNo;
        SemNo = sem;
    }
}
