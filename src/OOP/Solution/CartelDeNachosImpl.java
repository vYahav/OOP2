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

        p=getProfesor(p.getId());
        ArrayList<CasaDeBurrito> restaurant_list=new ArrayList<>();
        List<Profesor> p_friends=p.getFriends().stream()
                .sorted(Comparator.comparingInt(Profesor::getId))
                .collect(Collectors.toList());

        Collection<CasaDeBurrito> curr_restaurants;
        for(Profesor friend : p_friends){ //Iterate through friends and get each fav restaurants sorted by rating
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

        p=getProfesor(p.getId());
        ArrayList<CasaDeBurrito> restaurant_list=new ArrayList<>();
        List<Profesor> p_friends=p.getFriends().stream()
                .sorted(Comparator.comparingInt(Profesor::getId))
                .collect(Collectors.toList());

        Collection<CasaDeBurrito> curr_restaurants;
        for(Profesor friend : p_friends){ //Iterate through friends and get each fav restaurants and sort by dist
            curr_restaurants=friend.favoritesByDist(Integer.MAX_VALUE);
            for(CasaDeBurrito rest : curr_restaurants){
                if(!restaurant_list.contains(rest)){
                    restaurant_list.add(rest);
                }
            }



        }
        return restaurant_list;

    }


    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException {
        //errors
        if (!profesors.contains(p)) throw new Profesor.ProfesorNotInSystemException();
        if (!resturants.contains(c)) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if (t < 0) throw new CartelDeNachos.ImpossibleConnectionException();

      /** BFS **/

        Set<Integer> visited = new LinkedHashSet<Integer>();//all the visited prof by id
        Queue<Profesor> queue = new LinkedList<Profesor>();
        queue.add(p);
        visited.add(p.getId());

        while (!queue.isEmpty()) {
            Profesor profesor = queue.poll();
            if (profesor.favorites().contains(c))
                return true;

            t--;
            if (t >=0) {
                for (Profesor pr_friend : profesor.getFriends()) {
                    if (!visited.contains(pr_friend.getId())) {
                        visited.add(pr_friend.getId());
                        queue.add(pr_friend);
                    }

                }
            }
        }

        return  false;
    }


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
                for (CasaDeBurrito casa : pr_friend.favorites()){ // all the favorite resturants of a given friend of a profesor
                    CasaWithScore old_casa = casas.putIfAbsent(casa.getId() , new CasaWithScore(casa,1));
                    if( old_casa != null) //the casa exists already
                        old_casa.score= old_casa.score + 1 ;
                }
            }
        }
        // now we have a map with id of casa and casascore.

        if(casas.isEmpty()) //in case the casa are empty
            return  new LinkedList<>();
        /** sorting and filtering the map to get the list **/
        Comparator<CasaWithScore> comparator = Comparator.comparingInt(c -> c.score);
        CasaWithScore c = casas.values().stream().max(comparator).get();
        int max = c.score;
        return casas.values().stream().filter( (ca)-> ca.score == max).map( (ca) ->  ca.casa.getId() )
                .sorted().collect(Collectors.toList());



    }


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
