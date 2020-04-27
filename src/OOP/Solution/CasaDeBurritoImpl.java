package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.stream.Collectors;

//------------ CasaDeBurritoImpl ------------//

public class CasaDeBurritoImpl implements CasaDeBurrito {
    int ID,distance,numberOfRates,sumOfRates;
    String name;
    Hashtable<Profesor,Integer> ratings;
    Set<String> menu;
    public CasaDeBurritoImpl(int id1, String name1, int dist1, Set<String> menu1){
        this.ID=id1;
        this.ratings = new Hashtable<Profesor,Integer>();
        this.distance=dist1;
        this.name=name1;
        this.menu=menu1;
        this.numberOfRates=0;
        this.sumOfRates=0;//test
    }
    /**
     * @return the id of the casa de burrito.
     * */
    public int getId(){
        return this.ID;
    }

    /**
     * @return the name of the casa de burrito.
     * */
    public String getName(){
        return this.name;
    }

    /**
     * @return the distance from the Technion.*/
    public int distance(){
        return this.distance;
    }

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    public boolean isRatedBy(Profesor p){
        if(this.ratings.containsKey(p)) return true;
        return false;
    }

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    public CasaDeBurrito rate(Profesor p, int r)
            throws RateRangeException{
        if(r<0 || r>5) throw new RateRangeException();
        if(!isRatedBy(p)){
            this.numberOfRates++;
        }else{
            this.sumOfRates=this.sumOfRates-this.ratings.get(p);
        }
        this.sumOfRates=this.sumOfRates+r;
        this.ratings.put(p,r);
        return this;
    }

    /**
     * @return the number of rating the CasaDeBurrito has received
     * */
    public int numberOfRates(){
        return this.numberOfRates;
    }

    /**
     * @return the CasaDeBurrito's average rating
     * */
    public double averageRating(){
        return (double)this.sumOfRates/(double)this.numberOfRates;
    }


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

    /**
     * @return the CasaDeBurrito's description as a string in the following format:
     * <format>
     * CasaDeBurrito: <name>.
     * Id: <id>.
     * Distance: <dist>.
     * Menu: <menuItem1, menuItem2, menuItem3...>.
     * </format>
     * No newline at the end of the string.
     * Note: Menu items are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * CasaDeBurrito: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     * */

    @Override
    public String toString(){
        String menuText="";
        List<String> menu_list=menu.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        for(String i:menu_list){
            menuText.concat(i+", ");
        }
        if (menuText.length() > 0 && menuText.charAt(menuText.length() - 1) == ' ') {
            menuText = menuText.substring(0, menuText.length() - 1);
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
