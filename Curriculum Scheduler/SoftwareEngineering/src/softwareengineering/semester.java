/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareengineering;
import java.util.ArrayList;
 /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
public class semester {
    
    ArrayList <constraint> free_courseslist;
    ArrayList <constraint> core_courseslist;
    ArrayList <constraint> liberal_courseslist;
    public semester(){
        free_courseslist = new ArrayList <constraint>();
        free_courseslist.clear();
        core_courseslist = new ArrayList <constraint>();
        core_courseslist.clear();
        liberal_courseslist = new ArrayList <constraint>();
        liberal_courseslist.clear();
    }
    
    // current credits of sem and no of core,free and liberal courses in sem
    int totalcredits,free,core,liberal;
    
    // min and max credits of each course type and sem
    int min_free,max_free,min_core,max_core,min_liberal,max_liberal;
    int mincredits,maxcredits;
    
    // Functions to set free,core,liberal and total credits
    //Functions to return total credits of each type and for overall sem
    public int totalcredits_free(){
        if(free_courseslist.isEmpty()){
            free = 0;
            return 0;
        }
        int sum = 0;
        for(int i = 0; i < free_courseslist.size(); i++){
            sum += free_courseslist.get(i).credits;
        }
        free  = free_courseslist.size();
        return sum;
    }
    public int totalcredits_core(){
        if(core_courseslist.isEmpty()){
            core = 0;
            return 0;
        }
        int sum = 0;
        for(int i = 0; i < core_courseslist.size(); i++){
            sum += core_courseslist.get(i).credits;
        }
        core = core_courseslist.size();
        return sum;
    }
    public int totalcredits_liberal(){
        if(liberal_courseslist.isEmpty()){
            liberal  = 0;
            return 0;
        }
        int sum = 0;
        for(int i = 0; i < liberal_courseslist.size(); i++){
            sum += liberal_courseslist.get(i).credits;
        }
        liberal = liberal_courseslist.size();
        return sum;
    }
    public int set_totalcredits(){
        totalcredits = totalcredits_core()+totalcredits_free()+totalcredits_liberal();
        return totalcredits;
    }
    public void setLimits(int a,int b,int c, int d,int e,int f)
    {   
        min_core = a;
        max_core = d;
        min_free = b;
        max_free = e;
        min_liberal = c;
        max_liberal = f;
        mincredits = a + b + c;
        maxcredits = d + e + f;
    }   
    public void setCreditsForSem()
    {   
        mincredits = min_core + min_free + min_liberal;
        maxcredits = max_core + max_free + max_liberal;
        System.out.println(mincredits);
        System.out.println(maxcredits);
    }  
}
