package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CartelDeNachos;
import OOP.Provided.CartelDeNachos.ImpossibleConnectionException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Solution.*;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class Part1Test2 {
    @Test
    public void ProfesorTest() {
        Profesor s1 = null, s2 = null,s3=null,s4=null,s5=null,s6=null;

        s1 = new ProfesorImpl(100, "Kobe");
        s2 = new ProfesorImpl(200, "Bryant");
        s3 = new ProfesorImpl(300, "Lebron");
        s4 = new ProfesorImpl(400, "James");
        s5 = new ProfesorImpl(500, "Bibi");
        s6 = new ProfesorImpl(600, "Ganz");

        Set<String> menu1 = new HashSet<>(), menu2 = new HashSet<>();
        menu1.add("Hamburger");
        menu1.add("Fries");
        menu2.add("Steak");
        menu2.add("Fries");
        menu2.add("Orange Juice");
        CasaDeBurrito r1 = null, r2 = null, r3 = null;

        r1 = new CasaDeBurritoImpl(10, "BBB", 12, menu1);
        r2 = new CasaDeBurritoImpl(12, "Bob's place", 5, menu2);
        r3 = new CasaDeBurritoImpl(14, "Ben's hut", 1, menu1);


        // addFriend() test
        try {
           s1.addFriend(s3);
           s2.addFriend(s4);
           s5.addFriend(s6);
           s6.addFriend(s1);
        } catch (SameProfesorException e) {
            fail();
        }
        catch(ConnectionAlreadyExistsException e2){
            fail();
        }
        // getFriends() test
        Set<Profesor> set1=s6.getFriends();
        Set<Profesor> set2=s6.getFriends();
        set1.add(s5);
        Set<Profesor> set3=s6.getFriends();

        assertTrue(set3.equals(set2));

        //compareTo(Profesor profesor) test
        assertTrue(s1.compareTo(s2)<0);
        assertTrue(s5.compareTo(s5)==0);
        assertTrue(s5.compareTo(s4)>0);

        //equals(Object o) test
        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(s6));

    }
}
