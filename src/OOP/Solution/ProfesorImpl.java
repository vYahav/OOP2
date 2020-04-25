package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.function.Predicate;

public class ProfesorImpl implements Profesor{
    int ID;
    String name;
    ProfesorImpl(int id1,String name1){
        this.ID=id1;
        this.name=name1;
    }
    /**
     * @return the id of the profesor.
     * */
    public int getId(){
        return this.ID;
    }

    /**
     * the profesor favorites a casa de burrito
     *
     * @return the object to allow concatenation of function calls.
     * @param c - the casa de burrito being favored by the profesor
     * */
    public Profesor favorite(CasaDeBurrito c)
            throws UnratedFavoriteCasaDeBurritoException{
        return null;
    }

    /**
     * @return the profesor's favorite casas de burrito
     * */
    public Collection<CasaDeBurrito> favorites(){
        return null;
    }

    /**
     * adding a profesor as a friend
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor being "friend-ed"
     * */
    public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException{
        return null;
    }

    /**
     * @return the profesor's set of friends
     * */
    public Set<Profesor> getFriends(){
        return null;
    }

    /**
     * @return the profesor's set of friends, filtered by a predicate
     * @param p - the predicate for filtering
     * */
    public Set<Profesor> filteredFriends(Predicate<Profesor> p){
        return null;
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by a Comparator, and filtered by a predicate.
     * @param comp - a comparator for ordering
     * @param p - a predicate for filtering
     * */
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p){
        return null;
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by rating.
     * @param rLimit - the limit of rating under which casas de burrito will not be included.
     * */
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit){
        return null;
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by distance and then rating.
     * @param dLimit - the limit of distance above which casas de burrito will not be included.
     * */
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit){
        return null;
    }

    /**
     * @return the profesors's description as a string in the following format:
     * <format>
     * Profesor: <name>.
     * Id: <id>.
     * Favorites: <casaName1, casaName2, casaName3...>.
     * </format>
     * No newline at the end of the string.
     * Note: favorite casas de burrito are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * Profesor: Oren.
     * Id: 236703.
     * Favorites: BBB, Burger salon.
     * */

    public String toString(){
        return null;
    }

    @Override
    public int compareTo(Profesor profesor) {
        return 0;
    }
}
