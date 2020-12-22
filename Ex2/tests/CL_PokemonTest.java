package tests;

import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CL_PokemonTest {

    @Test
    void init_from_json() {
        String json  = "{\"Pokemon\":{\"type\":1, \"pos\":\"1.0,2.7,3.3\" ,\"value\":5.0}}";
        CL_Pokemon pokemon1 = CL_Pokemon.init_from_json(json);
        CL_Pokemon pokemon2 = new CL_Pokemon(new Point3D(-1.0,2.7, 3.3),-1,5,0,null);
        String s1 = pokemon1.toString();
        String s2 = pokemon2.toString();
        assertNotEquals(s1, s2);
        pokemon2 = new CL_Pokemon(new Point3D(1.0,2.7, 3.3),1,5,0,null);
        s2 = pokemon2.toString();
        assertEquals(s1,s2);
    }

    @Test
    void getLocation() {
        CL_Pokemon pokemon1 = new CL_Pokemon(new Point3D(-1.0,2.7, 3.3),-1,5,0,null);
        assertEquals(new Point3D(-1.0,2.7, 3.3), pokemon1.getLocation());
    }

    @Test
    void getValue() {
        CL_Pokemon pokemon1 = new CL_Pokemon(new Point3D(-1.0,2.7, 3.3),-1,5,0,null);
        assertEquals(5, pokemon1.getValue());
    }
}