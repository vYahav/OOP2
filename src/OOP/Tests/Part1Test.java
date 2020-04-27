package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CartelDeNachos;
import OOP.Provided.CartelDeNachos.ImpossibleConnectionException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.*;


public class Part1Test {



    @Test
    public void TestRate() throws RateRangeException {
        Set<String> menu1 = new HashSet<>();
        menu1.add("McBurrito");
        menu1.add("McQueso");
        menu1.add("McNachos");
        menu1.add("McGuacamole");
        CasaDeBurritoImpl network = new CasaDeBurritoImpl(1, "case1", 1, menu1);
        Profesor p1 = new ProfesorImpl(10, "p1");
        Profesor p2 = new ProfesorImpl(11, "p2");
        Profesor p3 = new ProfesorImpl(10, "p3");

        try {
            network.rate(p1, 6);
        } catch (RateRangeException e) {
        }

        try {
            network.rate(p1, -1);
        } catch (RateRangeException e) {
        }


        assertFalse(network.isRatedBy(p1));

        network.rate(p1, 5);
        assertTrue(network.isRatedBy(p1));
        assertTrue(network.isRatedBy(p3));

        network.rate(p2, 3);
        assertTrue(network.isRatedBy(p2));
        assertTrue(network.isRatedBy(p1));

        network.rate(p1, 3);
        assertTrue(network.isRatedBy(p1));

    }

    @Test
    public void TestNumberAndAvarage() throws RateRangeException {
        Set<String> menu1 = new HashSet<>();
        menu1.add("McBurrito");
        menu1.add("McQueso");
        menu1.add("McNachos");
        menu1.add("McGuacamole");
        CasaDeBurritoImpl network = new CasaDeBurritoImpl(1, "case1", 1, menu1);
        Profesor p1 = new ProfesorImpl(10, "p1");
        Profesor p2 = new ProfesorImpl(11, "p2");
        Profesor p3 = new ProfesorImpl(10, "p3");
        assertEquals( 0, network.numberOfRates() ,0 );
        assertEquals( 0, network.averageRating() ,0 );

        network.rate(p1, 5);
        assertEquals( 1, network.numberOfRates() ,0 );
        assertEquals( 5, network.averageRating() ,0 );

        network.rate(p2, 2);
        assertEquals( 2, network.numberOfRates() ,0 );
        assertEquals( 3.5, network.averageRating() ,0 );


        network.rate(p2, 3);
        assertEquals( 2, network.numberOfRates() ,0 );
        assertEquals( 4, network.averageRating() ,0 );

        network.rate(p3, 2);
        assertEquals( 2, network.numberOfRates() ,0 );
        assertEquals( 2.5, network.averageRating() ,0 );



    }
}