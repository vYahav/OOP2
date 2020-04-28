package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.stream.Collectors;

//------------ CasaDeBurritoImpl ------------//

public class CasaDeBurritoImpl implements CasaDeBurrito {
    int ID,distance,numberOfRates,sumOfRates;
    String name;
    Hashtable<Profesor,Integer> ratings; /**the key is Profesor hascode(which is id) and the Integer is the rating of the prof**/
     Set<String> menu;
    public CasaDeBurritoImpl(int id1, String name1, int dist1, Set<String> menu1){
        this.ID=id1;
        this.ratings = new Hashtable<Profesor,Integer>();
        this.distance=dist1;
        this.name=name1;
        this.menu=new HashSet<>(menu1);
        this.numberOfRates=0;
        this.sumOfRates=0;//test
    }


    public int getId(){
        return this.ID;
    }


    public String getName(){
        return this.name;
    }


    public int distance(){
        return this.distance;
    }


    public boolean isRatedBy(Profesor p){
        if(this.ratings.containsKey(p)) return true;
        return false;
    }


    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException{
        if(r<0 || r>5)
            throw new RateRangeException();

        if(!isRatedBy(p)){
            this.numberOfRates++;
        }else{
            this.sumOfRates=this.sumOfRates-this.ratings.get(p);
        }
        this.sumOfRates=this.sumOfRates+r;
        this.ratings.put(p,r);
        return this;
    }


    public int numberOfRates(){
        return this.numberOfRates;
    }


    public double averageRating(){
        if(this.numberOfRates==0) return 0;
        return (double)this.sumOfRates/(double)this.numberOfRates;
    }

/** equal implementation according to the contract **/
    protected boolean eq(Object o) {
        if (!(o instanceof CasaDeBurrito)) return false;
        CasaDeBurrito other=(CasaDeBurrito) o;
        return this.ID==other.getId();
    }

        @Override
    public boolean equals(Object o) {// לפי ה"חוזה" שבתרגול
        return ((this.eq(o)) && ((CasaDeBurritoImpl)o).eq(this));
    }
    @Override
    public int hashCode(){
        return this.ID;
    }


    @Override
    public String toString(){
        String menuText="";
        List<String> menu_list=menu.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        for(String i:menu_list){
            menuText= menuText+ (i+", ");
        }
        /** In case Menu_list is not empty we have to delete the last ','  **/
        if (menuText.length() > 0 && menuText.charAt(menuText.length() - 2) == ',') {
            menuText = menuText.substring(0, menuText.length() - 2);
        }

        return      "CasaDeBurrito: "+this.getName()+".\n"+
                    "Id: " + this.getId() + ".\n"+
                    "Distance: "+this.distance()+".\n"+
                    "Menu: "+menuText +".";
    }

    @Override
    public int compareTo(CasaDeBurrito c){
        int diff=this.ID - c.getId();
        return diff;
    }



}
