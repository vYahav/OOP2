package OOP.Provided;

/**
 * Each instance of the CasaDeBurrito class has an id, a name, a distance
 * from the Technion, and holds a collection of menu items.
 * The id is unique for every CasaDeBurrito.
 * */
public interface CasaDeBurrito extends Comparable<CasaDeBurrito> {

    class CasaDeBurritoAlreadyInSystemException    extends Exception {}
    class CasaDeBurritoNotInSystemException        extends Exception {}
    class RateRangeException                       extends Exception {}

    /**
     * @return the id of the casa de burrito.
     * */
    int getId();

    /**
     * @return the name of the casa de burrito.
     * */
    String getName();

    /**
     * @return the distance from the Technion.*/
    int distance();

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    boolean isRatedBy(Profesor p);

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    CasaDeBurrito rate(Profesor p, int r)
            throws RateRangeException;

    /**
     * @return the number of rating the CasaDeBurrito has received
     * */
    int numberOfRates();

    /**
     * @return the CasaDeBurrito's average rating
     * */
    double averageRating();

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
    String toString();
}



//------------ CasaDeBurritoImpl ------------//

public class CasaDeBurritoImpl implements CasaDeBurrito{
    int ID,distance,numberOfRates,sumOfRates;
    String name;
    Hashtable<Profesor,Integer> ratings;
    Set<String> menu;
    CasaDeBurritoImpl(int id1, String name1, int dist1, Set<String> menu1){
        this.ratings = new Hashtable<Profesor,Integer>();
        this.distance=dist1;
        this.name=name1;
        this.menu=menu1;
        this.numberOfRates=0;
        this.sumOfRates=0;
    }
    /**
     * @return the id of the casa de burrito.
     * */
    int getId(){
        return this.ID;
    }

    /**
     * @return the name of the casa de burrito.
     * */
    String getName(){
        return this.name;
    }

    /**
     * @return the distance from the Technion.*/
    int distance(){
        return 0;
    }

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    boolean isRatedBy(Profesor p){
        if(this.ratings.containsKey(p)) return true
        return false;
    }

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    CasaDeBurrito rate(Profesor p, int r)
            throws RateRangeException{
        if(r<0 || r>5) throw RateRangeException;
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
    int numberOfRates(){
        return this.numberOfRates;
    }

    /**
     * @return the CasaDeBurrito's average rating
     * */
    double averageRating(){
        return this.sumOfRates/this.numberOfRates;
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
    boolean equals(Object o) {

    }
    String toString(){
        return null;
    }

    int compareTo(CasaDeBurrito c){

    }



}
