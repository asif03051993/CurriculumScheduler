/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
 /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
 
package softwareengineering;

import java.util.ArrayList;

public class constraint extends course {
  int minsem,maxsem;
  
  // Conditions for fractal or normal course
  boolean main_course;
  ArrayList <constraint> fractal_list = new ArrayList <constraint> ();
  
  // Prerequisite Conditions
  // If no prerequisite, then there is no parent node
  boolean  has_prerequisite; 
  ArrayList <constraint> parent = new ArrayList <constraint> ();     // parent(prerequisites) of current node
  
  // Descendant children
  // If Arraylist Child is empty then there are no children
  ArrayList <constraint> child = new ArrayList <constraint> () ; 
  public constraint(){
      course_name = "dummy";
      fractal_list.clear();
      parent.clear();
      child.clear();
  }
  public String toString() {
            return course_name;
  }
}
