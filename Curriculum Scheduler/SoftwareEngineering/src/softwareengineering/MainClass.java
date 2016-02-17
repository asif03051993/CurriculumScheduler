/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softwareengineering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

 /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
public class MainClass {
    ArrayList <constraint> all_courses = new ArrayList <constraint> () ;
    //private final static String newline = "\n";
    public ArrayList <String> id_list = new ArrayList<String>(Arrays.asList("no prerequisite"));
    public ArrayList <constraint> core_courses = new ArrayList <constraint> () ;
    public ArrayList <constraint> free_courses = new ArrayList <constraint> () ;
    public ArrayList <constraint> la_courses = new ArrayList <constraint> () ;
    public semester[] semlist;
    public boolean dragLab;
    public int idno;
    public int BtechCredits;
    // Course No is unique for each course either it is a fractal or main course
    public MainClass(){
        dragLab = false;
        BtechCredits = 0;
        idno = 1;
        all_courses.clear();
        core_courses.clear();
        free_courses.clear();
        la_courses.clear();
        semlist = new semester[8];
        for(int i = 0; i < semlist.length; i++)
        {
            semlist[i] = new semester();
        }
    }
    public boolean setCourseDetails( Vector<String> CourseIn ){
        constraint temp = new constraint();
        temp.Sem_alloted = "";
        temp.course_name = CourseIn.get(0).toUpperCase();
        temp.course_no = CourseIn.get(1).toUpperCase();
        temp.credits = Integer.parseInt(CourseIn.get(3));
        temp.minsem = Integer.parseInt(CourseIn.get(6));
        temp.maxsem = Integer.parseInt(CourseIn.get(7));
        switch (CourseIn.get(2).toLowerCase()){
            case "true":
                   temp.main_course = false;
                   break;
            case "false":
                    temp.main_course = true;
                    if (all_courses.isEmpty()){
                        // Error Dialog has to be added
                        return false;
                    }
                    for(int j=7; j<CourseIn.size(); j++){
                       for(int i=0; i < all_courses.size() ;i++)
                          if(all_courses.get(i).course_no.compareTo(CourseIn.get(j).toUpperCase()) == 0)
                              temp.fractal_list.add(all_courses.get(i));
                    }
                    break;
        }
        if(CourseIn.get(5).toLowerCase().compareTo("nil") == 0 ){
            temp.has_prerequisite = false;   
        }
        else{
            temp.has_prerequisite = true;   
        }
        switch (CourseIn.get(4).toLowerCase()){
            case "core":
                   temp.type = "Core";
                   core_courses.add(temp);
                   break;
            case "free":
                    temp.type = "Free";
                    free_courses.add(temp);
                    break;
            case "la":
                   temp.type = "LA";
                   la_courses.add(temp);
                   break;
            default:
                  // Error Dialog has to be added
                  return false;
        }
        if(temp.has_prerequisite == true){
            if (all_courses.isEmpty()){
                // Error Dialog has to be added
                return false;
            }
            List<String> PreRequisites = Arrays.asList(CourseIn.get(5).toUpperCase().split("\\s*;\\s*"));
            for(int j=0; j < PreRequisites.size() ; j++){
                for(int i=0; i < all_courses.size() ;i++){
                    if(all_courses.get(i).course_no.compareTo(PreRequisites.get(j).toUpperCase()) == 0){
                        temp.parent.add(all_courses.get(i));
                        all_courses.get(i).child.add(temp);
                    }
                }
            }
        }
        all_courses.add(temp);
        return true;
    }
    private constraint SearchCourse(String courseNo){
        for(int i=0; i < all_courses.size() ;i++){
                if(all_courses.get(i).course_no.compareTo(courseNo.toUpperCase()) == 0){
                      return all_courses.get(i);
                }
            }
        return null;
    }
    
    public int SearchIndex(String courseNo,ArrayList <constraint> list){
        for(int i=0; i < list.size() ;i++){
                if(list.get(i).course_no.compareTo(courseNo.toUpperCase()) == 0){
                      return i;
                }
            }
        return -1;
    }
    
    public ArrayList <constraint>  RemoveCoursefromList (String courseNo, ArrayList <constraint> list){
        for(int i=0; i < list.size() ;i++){
                if(list.get(i).course_no.compareTo(courseNo.toUpperCase()) == 0){
                    list.remove(i);
                    return list;
                }
            }
        return null;
    }
}
