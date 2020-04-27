package OOP.Solution;
import OOP.Provided.*;
import java.util.*;

public class CartelDeNachosImpl implements CartelDeNachos{
    Hashtable<Integer,Profesor> profesors;//key=id, value= Profesor object
    Hashtable<Integer,CasaDeBurrito> resturants; //key=id, value= CasaDeBurrito object


    public CartelDeNachosImpl(){
        profesors = new Hashtable<>();
        resturants = new Hashtable<>();

    }



    /**
     * add a profesor to the cartel.
     *
     * @param id - the id of the profesor
     * @param name - the name of the profesor
     * @return the Profesor added
     * */
    public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{
        if(profesors.containsKey(id)) throw new Profesor.ProfesorAlreadyInSystemException();
        Profesor prof=new ProfesorImpl(id,name);
        profesors.put(id,prof);
        return profesors.get(id);
    }

    /**
     * add a casa de burrito to the cartel
     * @param id - the id of the casa de burrito
     * @param name - the name of the casa de burrito
     * @param dist - the distance of the casa de burrito from the Technion
     * @param menu - the set of menu items of the casa de burrito
     * @return the CasaDeBurrito added
     * */
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        if(resturants.containsKey(id)) throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        CasaDeBurrito rest=new CasaDeBurritoImpl(id,name,dist,menu);
        resturants.put(id,rest);
        return resturants.get(id);
    }

    /**
     * @return a collection of all profesores in the cartel
     * */
    public Collection<Profesor> registeredProfesores(){
        Set<Profesor> newSet=new HashSet<>(profesors.values());
        return newSet;
    }

    /**
     * @return a collection of all casas de burrito in the cartel
     * */
    public Collection<CasaDeBurrito> registeredCasasDeBurrito(){
        Set<CasaDeBurrito> newSet=new HashSet<>(resturants.values());
        return newSet;
    }

    /**
     * @return the profesor in the cartel by the id given
     * @param id - the id of the profesor to look for in the cartel
     * */
    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException{

        if(profesors.containsKey(id)) {
            return profesors.get(id);
        }
        else{
            throw new Profesor.ProfesorNotInSystemException();
        }
    }

    /**
     * @return the casa de burrito in the cartel by the id given
     * @param id - the id of the casa de burrito to look for in the cartel
     * */
    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{

        if(resturants.containsKey(id)) {
            return resturants.get(id);
        }
        else{
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
    }

    /**
     * add a connection of friendship between the two profesores received.
     * friendship is a symmetric relation!
     *
     * @return the object to allow concatenation of function calls.
     * @param p1 - the first profesor
     * @param p2 - the second profesor
     * */
    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException{
        int id1=p1.getId();
        int id2=p2.getId();
        if(id1==id2) throw new Profesor.SameProfesorException();
        if(!profesors.containsKey(id1) || !profesors.containsKey(id2)) throw new Profesor.ProfesorNotInSystemException();
        if(profesors.get(id1).getFriends().contains(p2) || profesors.get(id2).getFriends().contains(p1)){
            throw new Profesor.ConnectionAlreadyExistsException();
        }
        profesors.get(id1).addFriend(p2);
        profesors.get(id2).addFriend(p1);

        return this;
    }

    /**
     * returns a collection of casas de burrito favored by the friends of the received profesor,
     * ordered by rating
     *
     * @param p - the profesor whom in relation to him, favored casas de burrito by his friends are considered
     * */
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        return null;
    }

    /**
     * returns a collection of casas de burrito favored by the friends of the received profesor,
     * ordered by distance from the Technion
     *
     * @param p - the profesor whom in relation to him, favored casas de burrito by his friends are considered
     * */
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        return null;
    }

    /**
     * check whether the casa de burrito received is t-recommended by the received profesor (definition in the PDF)
     *
     * @param p - the profesor (dis)recommending the casa de burrito
     * @param c - the casa de burrito being (dis)recommended
     * @param t - the limit in the t-recommendation
     *
     * @return whether s t-recommends r
     * */
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException{
        return true;
    }

    /**
     * @return a list of the most popular casas-de-burrito's ids in the cartel.
     * */
    public List<Integer> getMostPopularRestaurantsIds(){
        return null;
    }

    /**
     * @return the cartel's description as a string in the following format:
     * <format>
     * Registered profesores: <profesorId1, profesorId2, profesorId3...>.
     * Registered casas de burrito: <casaId1, casaId2, casaId3...>.
     * Profesores:
     * <profesor1Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * <profesor2Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * ...
     * End profesores.
     * </format>
     * No newline at the end of the string.
     * Note: profesores, casas de burrito and friends' ids are ordered by natural integer order, asc.
     *
     * Example:
     *
     * Registered profesores: 1, 236703, 555555.
     * Registered casas de burrito: 12, 13.
     * Profesores:
     * 1 -> [236703, 555555555].
     * 236703 -> [1].
     * 555555 -> [1].
     * End profesores.
     * */
    @Override
    public String toString(){
        return null;
    }
}
