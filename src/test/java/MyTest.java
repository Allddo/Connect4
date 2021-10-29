import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

class  MyTest {

    String color;

    @Test
    void ifValidTest1() {
        ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();
        ArrayList<String> inner = new ArrayList<String>();
        ArrayList<String> inner2 = new ArrayList<String>();

        inner.add("blue");
        inner.add("blue");
        inner.add("blue");
        inner.add("blue");
        inner.add("red");
        inner.add("grey");
        outer.add(inner);

        inner2.add("grey");
        inner2.add("grey");
        inner2.add("grey");

        outer.add(inner2);

        color = "blue";
        int count = 0;

        for (int i = 0; i < outer.size(); i++) {
            for (int j = 0; j < outer.get(i).size(); j++) {
                if(outer.get(i).get(j) == color) {
                    count++;
                }
            }
        assertEquals(4, count);
        }
    }
}