package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor{
    int ID;
    String name;
    HashSet<CasaDeBurrito> rated_resturants,favorite_resturants;
    HashSet<Profesor> friends;

    public ProfesorImpl(int id1, String name1){
        this.ID=id1;
        this.name=name1;
        rated_resturants=new HashSet<>();
        favorite_resturants=new HashSet<>();
        friends=new HashSet<>();
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
        if(!this.rated_resturants.contains(c)) throw new UnratedFavoriteCasaDeBurritoException();
        favorite_resturants.add(c);
        return this;
    }

    /**
     * @return the profesor's favorite casas de burrito
     * */
    public Collection<CasaDeBurrito> favorites(){
        return new HashSet<>(favorite_resturants);
    }

    /**
     * adding a profesor as a friend
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor being "friend-ed"
     * */
    public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException{
        if(this.equals(p)) throw new SameProfesorException();
        if(friends.contains(p)) throw new ConnectionAlreadyExistsException();
        friends.add(p);
        return this;
    }

    /**
     * @return the profesor's set of friends
     * */
    public Set<Profesor> getFriends(){
        return new HashSet<>(friends);
    }

    /**
     * @return the profesor's set of friends, filtered by a predicate
     * @param p - the predicate for filtering
     * */
    public Set<Profesor> filteredFriends(Predicate<Profesor> p){
        return this.friends.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * @return the profesor's favorite casas de burrito,
     * ordered by a Comparator, and filtered by a predicate.
     * @param comp - a comparator for ordering
     * @param p - a predicate for filtering
     * */
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p){
        return this.favorite_resturants.stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by rating.
     * @param rLimit - the limit of rating under which casas de burrito will not be included.
     * */
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit){
        return this.favorite_resturants.stream()
                .filter(s->(s.averageRating()>rLimit))
                .sorted((s1,s2)-> (int)(s1.averageRating()-s2.averageRating()))
                .collect(Collectors.toList());
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by distance and then rating.
     * @param dLimit - the limit of distance above which casas de burrito will not be included.
     * */
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit){
        return this.favorite_resturants.stream()
                .filter(s->(s.distance()>dLimit))
                .sorted((s1,s2)-> (int)(s1.averageRating()-s2.averageRating()))
                .sorted(Comparator.comparingInt(CasaDeBurrito::distance))
                .collect(Collectors.toList());
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
        List<CasaDeBurrito> favorites_list=favorite_resturants.stream()
                .sorted(Comparator.comparing(CasaDeBurrito::getName))
                .collect(Collectors.toList());
        String favs="";
        for(CasaDeBurrito i:favorites_list){
            favs.concat(i.getName()+", ");
        }
        if (favs.length() > 0 && favs.charAt(favs.length() - 1) == ' ') {
            favs = favs.substring(0, favs.length() - 1);
        }
        return "Profesor: "+this.name+".\n"
                +"Id: "+ this.ID + ".\n"
                +"Favorites: "+ favs +".";
    }

    @Override
    public int compareTo(Profesor profesor) {
        int diff=this.ID - profesor.getId();
        return diff;
    }
}
