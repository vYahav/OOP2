package OOP.Solution;
import OOP.Provided.*;
import java.util.*;
import java.util.stream.Collectors;

public class CartelDeNachosImpl implements CartelDeNachos{
    Hashtable<Integer,Profesor> profesors;//key=id, value= Profesor object
    Hashtable<Integer,CasaDeBurrito> resturants; //key=id, value= CasaDeBurrito object


    public CartelDeNachosImpl(){
        profesors = new Hashtable<>();
        resturants = new Hashtable<>();

    }




    public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{
        if(profesors.containsKey(id)) throw new Profesor.ProfesorAlreadyInSystemException();
        Profesor prof=new ProfesorImpl(id,name);
        profesors.put(id,prof);
        return profesors.get(id);
    }


    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        if(resturants.containsKey(id)) throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        CasaDeBurrito rest=new CasaDeBurritoImpl(id,name,dist,menu);
        resturants.put(id,rest);
        return resturants.get(id);
    }


    public Collection<Profesor> registeredProfesores(){
        Set<Profesor> newSet=new HashSet<>(profesors.values());
        return newSet;
    }


    public Collection<CasaDeBurrito> registeredCasasDeBurrito(){
        Set<CasaDeBurrito> newSet=new HashSet<>(resturants.values());
        return newSet;
    }


    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException{

        if(profesors.containsKey(id)) {
            return profesors.get(id);
        }
        else{
            throw new Profesor.ProfesorNotInSystemException();
        }
    }


    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{

        if(resturants.containsKey(id)) {
            return resturants.get(id);
        }
        else{
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
    }


    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException{

        int id1=p1.getId();
        int id2=p2.getId();
        if(!profesors.containsKey(id1) || !profesors.containsKey(id2)) throw new Profesor.ProfesorNotInSystemException();
        if(id1==id2) throw new Profesor.SameProfesorException();
        if(profesors.get(id1).getFriends().contains(p2) || profesors.get(id2).getFriends().contains(p1)){
            throw new Profesor.ConnectionAlreadyExistsException();
        }

        profesors.get(id1).addFriend(p2);
        profesors.get(id2).addFriend(p1);
        return this;
    }


    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesors.containsKey(p.getId()))
            throw new Profesor.ProfesorNotInSystemException();

        ArrayList<CasaDeBurrito> restaurant_list=new ArrayList<>();
        List<Profesor> p_friends=p.getFriends().stream()
                .sorted((s1,s2)-> s2.getId()-s1.getId())
                .collect(Collectors.toList());

        Collection<CasaDeBurrito> curr_restaurants;
        for(Profesor friend : p_friends){
            curr_restaurants=friend.favoritesByRating(0);
            for(CasaDeBurrito rest : curr_restaurants){
                if(!restaurant_list.contains(rest)){
                    restaurant_list.add(rest);
                }
            }



        }
        return restaurant_list;
    }

    /**
     * returns a collection of casas de burrito favored by the friends of the received profesor,
     * ordered by distance from the Technion
     *
     * @param p - the profesor whom in relation to him, favored casas de burrito by his friends are considered
     * */
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesors.containsKey(p.getId())) {
            throw new Profesor.ProfesorNotInSystemException();
        }

        ArrayList<CasaDeBurrito> restaurant_list=new ArrayList<>();
        List<Profesor> p_friends=p.getFriends().stream()
                .sorted((s1,s2)-> s2.getId()-s1.getId())
                .collect(Collectors.toList());

        Collection<CasaDeBurrito> curr_restaurants;
        for(Profesor friend : p_friends){
            curr_restaurants=friend.favoritesByDist(0);
            for(CasaDeBurrito rest : curr_restaurants){
                if(!restaurant_list.contains(rest)){
                    restaurant_list.add(rest);
                }
            }



        }
        return restaurant_list;

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
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException {
        //errors
        if (!profesors.contains(p)) throw new Profesor.ProfesorNotInSystemException();
        if (!resturants.contains(c)) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if (t < 0) throw new CartelDeNachos.ImpossibleConnectionException();

        //good

        Set<Integer> visited = new LinkedHashSet<Integer>();
        Queue<Profesor> queue = new LinkedList<Profesor>();
        queue.add(p);
        visited.add(p.getId());

        while (!queue.isEmpty()) {
            Profesor profesor = queue.poll();
            if (profesor.favorites().contains(c)) return true;
            t--;
            if (t >= 0) {
                for (Profesor pr_friend : p.getFriends()) {
                    if (!visited.contains(pr_friend.getId())) {
                        visited.add(pr_friend.getId());
                        queue.add(pr_friend);
                    }
                }
            }
        }
        return  false;
    }

    /**
     * @return a list of the most popular casas-de-burrito's ids in the cartel.
     * */
    public List<Integer> getMostPopularRestaurantsIds() {
        class CasaWithScore  {
            CasaDeBurrito casa;
            int score ;
            public   CasaWithScore(CasaDeBurrito casa,int score){
                this.casa = casa;
                this.score = score;
            }
        }

        Map<Integer,CasaWithScore> casas = new HashMap<>();


        for (Profesor profesor : this.profesors.values()) { //all the profesor
            for (Profesor pr_friend : profesor.getFriends()){ // all the friends of a given profesor
                for (CasaDeBurrito casa : profesor.favorites()){ // all the favorite resturants of a given proffesor
                    CasaWithScore old_casa = casas.putIfAbsent(casa.getId() , new CasaWithScore(casa,0));
                    if( old_casa != null) //the casa exists already
                        old_casa.score= old_casa.score + 1 ;
                }
            }
        }
        // now we have a map with id of casa and casascore.

        if(casas.isEmpty()) //in case the casa are empty
            return  new LinkedList<>();;
        Comparator<CasaWithScore> comparator = Comparator.comparingInt(c -> c.score);
        CasaWithScore c = casas.values().stream().max(comparator).get();
        int max = c.score;
        return casas.values().stream().filter( (ca)-> ca.score == max).map( (ca) ->  ca.casa.getId() )
                .sorted().collect(Collectors.toList());



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
        //Start string
        String s = "";

        //Registered profesores
        s = s + ("Registered profesores: ");

        ArrayList<Profesor> profs = new ArrayList<>(profesors.values());
        profs.sort(Comparator.comparingInt(Profesor::getId));
        for(Profesor friend : profs){
            s= s + (friend.getId()+", ");
        }
        if (s.length() > 0 && s.charAt(s.length() - 2) == ',') {
            s= s.substring(0, s.length() - 2);
        }
        s = s + (".\n");

        //Registered casas de burrito
        s = s + ("Registered casas de burrito: ");

        ArrayList<CasaDeBurrito> casot = new ArrayList<>(resturants.values());
        casot.sort(Comparator.comparingInt(CasaDeBurrito::getId));
        for(CasaDeBurrito r : casot){
            s= s + (r.getId()+", ");
        }
        if (s.length() > 0 && s.charAt(s.length() - 2) == ',') {
            s = s.substring(0, s.length() - 2);
        }
        s= s + (".\n");

        //Profesores
        s= s + ("Profesores:\n");
        for(Profesor friend : profs){
            s= s + (friend.getId()+" -> [");
            ArrayList<Profesor> friend_friends = new ArrayList<>(friend.getFriends());
            friend_friends.sort(Comparator.comparingInt(Profesor::getId));

            for(Profesor f_friend : friend_friends){
                s= s + (f_friend.getId()+", ");
            }
            if (s.length() > 0 && s.charAt(s.length() - 2) == ',') {
                s = s.substring(0, s.length() - 2);
            }
            s= s + ("].\n");
        }
        s= s + ("End profesores.");

        return s;
    }
}
