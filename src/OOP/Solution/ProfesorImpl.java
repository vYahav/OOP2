package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor{
    int ID;
    String name;
    HashSet<CasaDeBurrito> favorite_resturants;
    HashSet<Profesor> friends;

    public ProfesorImpl(int id1, String name1){
        this.ID=id1;
        this.name=name1;
        favorite_resturants=new HashSet<>();
        friends=new HashSet<>();
    }

    public int getId(){  return this.ID; }


    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException{
        if(!c.isRatedBy(this)) throw new UnratedFavoriteCasaDeBurritoException();
        favorite_resturants.add(c);
        return this;
    }


    public Collection<CasaDeBurrito> favorites(){
        return new HashSet<>(favorite_resturants);
    }


    public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException{
        if(this.equals(p)) throw new SameProfesorException();
        if(friends.contains(p)) throw new ConnectionAlreadyExistsException();
        friends.add(p);
        return this;
    }


    public Set<Profesor> getFriends(){
        return new HashSet<>(friends);
    }


    public Set<Profesor> filteredFriends(Predicate<Profesor> p){
        return this.friends.stream().filter(p).collect(Collectors.toSet());
    }



    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p){
        return this.favorite_resturants.stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

/** Compare by the  natural Order of double, but from down to up **/
 int double_comp (CasaDeBurrito c1 ,CasaDeBurrito c2){
        double res =c2.averageRating() - c1.averageRating() ;
      if(res > 0)  return 1;
      if (res == 0) return 0;
      else return -1; }

    public Collection<CasaDeBurrito> favoritesByRating(int rLimit){
      /** we must convert to list first in order to make sorted stable **/
      List<CasaDeBurrito> list = new ArrayList<>(favorite_resturants);
        return list.stream()
                .filter(s->(s.averageRating()>=rLimit))
                .sorted(Comparator.comparingInt(CasaDeBurrito::getId))
                .sorted(Comparator.comparingInt(CasaDeBurrito::distance))
                .sorted( this::double_comp )
                .collect(Collectors.toList());
    }



    public Collection<CasaDeBurrito> favoritesByDist(int dLimit){
        /** we must convert to list first in order to make sorted stable **/
        List<CasaDeBurrito> list = new ArrayList<>(favorite_resturants);
        return list.stream()
                .filter(s->(s.distance()<=dLimit))
                .sorted((s1,s2)-> s1.getId()-s2.getId())
                .sorted(this::double_comp)
                .sorted(Comparator.comparingInt(CasaDeBurrito::distance))
                .collect(Collectors.toList());
    }



    public String toString(){
        List<CasaDeBurrito> favorites_list=favorite_resturants.stream()
                .sorted(Comparator.comparing(CasaDeBurrito::getName))
                .collect(Collectors.toList());
        String favs="";
        for(CasaDeBurrito i:favorites_list){
            favs= favs + (i.getName()+", ");
        }
        /** In case Menu_list is not empty we have to delete the last ','  **/
        if (favs.length() > 0 && favs.charAt(favs.length() - 2) == ',') {
            favs = favs.substring(0, favs.length() - 2);
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


    protected boolean eq(Object o) {
        if (!(o instanceof Profesor)) return false;
        Profesor other=(Profesor) o;
        return this.ID==other.getId();
    }

    @Override
    public boolean equals(Object o) {// לפי ה"חוזה" שבתרגול
        return ((this.eq(o)) && ((ProfesorImpl)o).eq(this));
    }
    @Override
    public int hashCode(){
        return this.ID;
    }


}
