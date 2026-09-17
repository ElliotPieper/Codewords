import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
//functional test, not coverage
class SolverTest {

    int[][] grid1 = {{4, 20, 14, 19, 11, 20, 0, 23, 14, 18, 3, 15, 23, 19, 13},
                     {6, 0, 19, 0, 5, 0, 0, 0, 19, 0, 1, 0, 6, 0, 3},
                     {21, 9, 14, 18, 9, 4, 0, 18, 25, 6, 21, 12, 5, 9, 15},
                     {2, 0, 14, 0, 11, 0, 10, 0, 17, 0, 13, 0, 8, 0, 14},
                     {2, 20, 18, 14, 0, 16, 19, 14, 15, 0, 3, 22, 20, 12, 20},
                     {14, 0, 26, 0, 7, 0, 21, 0, 0, 0, 0, 0, 6, 0, 0},
                     {21, 4, 15, 14, 14, 0, 9, 18, 8, 21, 11, 18, 26, 14, 20},
                     {9, 0, 0, 0, 18, 0, 25, 0, 5, 0, 5, 0, 0, 0, 24},
                     {11, 18, 2, 20, 3, 3, 20, 20, 6, 0, 1, 22, 5, 5, 23},
                     {0, 0, 20, 0, 0, 0, 0, 0, 3, 0, 9, 0, 25, 0, 14},
                     {3, 15, 23, 20, 13, 0, 25, 22, 20, 7, 0, 16, 19, 4, 5},
                     {5, 0, 22, 0, 12, 0, 22, 0, 24, 0, 21, 0, 14, 0, 21},
                     {1, 18, 15, 7, 18, 6, 20, 6, 0, 18, 4, 6, 21, 7, 3},
                     {20, 0, 6, 0, 6, 0, 18, 0, 0, 0, 14, 0, 13, 0, 20},
                     {4, 20, 13, 17, 3, 5, 23, 13, 0, 8, 20, 13, 3, 20, 4}};
    int[][] grid2 = {{2, 26, 26, 13, 6, 14, 0, 20, 5, 10, 21, 7, 13, 10, 21},
                     {1, 0, 3, 0, 5, 0, 0, 0, 24, 0, 19, 0, 17, 0, 9},
                     {19, 3, 9, 13, 15, 14, 0, 25, 13, 19, 10, 10, 13, 5, 7},
                     {8, 0, 5, 0, 19, 0, 21, 0, 2, 0, 8, 0, 2, 0, 7},
                     {15, 5, 7, 22, 0, 12, 2, 2, 16, 0, 19, 10, 15, 8, 14},
                     {4, 0, 13, 0, 20, 0, 26, 0, 0, 0, 0, 0, 13, 0, 0},
                     {8, 13, 17, 21, 19, 0, 26, 5, 15, 13, 8, 13, 23, 5, 7},
                     {2, 0, 0, 0, 19, 0, 13, 0, 2, 0, 9, 0, 0, 0, 5},
                     {11, 5, 7, 7, 18, 5, 18, 19, 8, 0, 25, 5, 26, 13, 10},
                     {0, 0, 19, 0, 0, 0, 0, 0, 3, 0, 14, 0, 9, 0, 17},
                     {26, 23, 5, 16, 18, 0, 23, 8, 9, 24, 0, 5, 26, 18, 26},
                     {18, 0, 6, 0, 5, 0, 13, 0, 19, 0, 25, 0, 15, 0, 7},
                     {5, 7, 7, 9, 26, 13, 1, 19, 0, 5, 7, 22, 5, 7, 13},
                     {26, 0, 19, 0, 15, 0, 13, 0, 0, 0, 9, 0, 13, 0, 17},
                     {16, 2, 15, 4, 19, 8, 7, 14, 0, 25, 8, 2, 10, 12, 19}};

    int[][] grid3 = new int[][]{{0, 24, 0, 4, 0, 1, 0, 25, 0, 11, 0, 4, 0, 11, 0},
                                {18, 22, 3, 12, 19, 5, 0, 19, 12, 2, 13, 26, 10, 25, 10},
                                {0, 23, 0, 10, 0, 20, 0, 14, 0, 25, 0, 12, 0, 11, 0},
                                {16, 5, 19, 23, 19, 25, 14, 5, 0, 21, 25, 2, 13, 26, 19},
                                {0, 8, 0, 5, 0, 13, 0, 2, 0, 21, 0, 0, 0, 24, 0},
                                {16, 12, 19, 9, 18, 5, 26, 19, 0, 24, 12, 16, 9, 26, 16},
                                {0, 2, 0, 4, 0, 0, 0, 26, 0, 5, 0, 18, 0, 0, 0},
                                {5, 3, 16, 12, 9, 18, 17, 0, 12, 1, 15, 26, 22, 2, 19},
                                {0, 0, 0, 24, 0, 25, 0, 13, 0, 0, 0, 9, 0, 5, 0},
                                {15, 5, 9, 24, 12, 11, 0, 24, 12, 13, 2, 26, 10, 10, 5},
                                {0, 20, 0, 0, 0, 18, 0, 12, 0, 25, 0, 11, 0, 25, 0},
                                {15, 26, 17, 14, 22, 24, 0, 20, 25, 13, 12, 2, 12, 11, 5},
                                {0, 23, 0, 24, 0, 12, 0, 25, 0, 12, 0, 12, 0, 19, 0},
                                {10, 5, 7, 22, 5, 19, 13, 5, 0, 1, 22, 16, 24, 5, 6},
                                {0, 1, 0, 6, 0, 1, 0, 2, 0, 12, 0, 18, 0, 1, 0}};
    int[][] grid4 = new int[][]{{12, 26, 22, 0, 0, 17, 6, 3, 4, 0, 6, 12, 4, 12, 0},
                                {1, 26, 26, 0, 21, 6, 14, 26, 14, 0, 3, 2, 2, 26, 20},
                                {6, 13, 26, 0, 13, 19, 6, 8, 6, 0, 26, 4, 2, 25, 9},
                                {14, 26, 17, 0, 6, 13, 21, 16, 3, 26, 8, 0, 13, 9, 26},
                                {7, 14, 19, 11, 11, 0, 0, 0, 0, 6, 20, 11, 0, 0, 0},
                                {0, 12, 13, 2, 26, 0, 13, 16, 8, 24, 0, 19, 14, 10, 12},
                                {0, 0, 0, 2, 14, 20, 19, 8, 6, 13, 0, 23, 15, 26, 26},
                                {4, 2, 26, 3, 0, 26, 13, 19, 20, 26, 0, 15, 2, 8, 26},
                                {6, 8, 23, 19, 0, 21, 6, 23, 19, 12, 23, 26, 0, 0, 0,},
                                {13, 26, 6, 8, 0, 6, 1, 26, 14, 0, 6, 14, 1, 12, 0},
                                {0, 0, 0, 24, 6, 23, 0, 0, 0, 0, 4, 12, 6, 13, 3},
                                {26, 6, 23, 0, 3, 26, 12, 12, 6, 24, 26, 0, 18, 2, 9},
                                {12, 5, 16, 6, 21, 0, 23, 6, 21, 2, 14, 0, 2, 22, 8},
                                {12, 16, 14, 24, 26, 0, 6, 23, 13, 6, 12, 0, 13, 26, 6},
                                {0, 6, 7, 6, 14, 0, 23, 26, 26, 20, 0, 0, 26, 14, 12}};

    HashSet<String> dictionary = JsonDictHash.get();

    @Test
    void solver() {
        HashMap<Integer, Character> specialKey = new HashMap<>();
        Solver.SolveState solveState = new Solver.SolveState(new State(grid1, specialKey), dictionary);
        for (int[] row : grid3) {
            assert row.length == 15;
        }
        HashMap<Integer, Character> key1 = new HashMap<>();
        key1.put(14, 'l');
        key1.put(18, 'a');
        assertEquals("""
                d  e  l  u  g  e  #  p  l  a  t  y  p  u  s
                r  #  u  #  o  #  #  #  u  #  w  #  r  #  t
                i  n  l  a  n  d  #  a  c  r  i  m  o  n  y
                z  #  l  #  g  #  q  #  k  #  s  #  v  #  l
                z  e  a  l  #  j  u  l  y  #  t  h  e  m  e
                l  #  b  #  f  #  i  #  #  #  #  #  r  #  #
                i  d  y  l  l  #  n  a  v  i  g  a  b  l  e
                n  #  #  #  a  #  c  #  o  #  o  #  #  #  x
                g  a  z  e  t  t  e  e  r  #  w  h  o  o  p
                #  #  e  #  #  #  #  #  t  #  n  #  c  #  l
                t  y  p  e  s  #  c  h  e  f  #  j  u  d  o
                o  #  h  #  m  #  h  #  x  #  i  #  l  #  i
                w  a  y  f  a  r  e  r  #  a  d  r  i  f  t
                e  #  r  #  r  #  a  #  #  #  l  #  s  #  e
                d  e  s  k  t  o  p  s  #  v  e  s  t  e  d""", Solver.solve(new State(grid1, key1), dictionary).toString());
        HashMap<Integer, Character> key2 = new HashMap<>();
        key2.put(26, 's');
        key2.put(18, 'p');
        assertEquals("""
                o  s  s  i  f  y  #  j  a  n  g  l  i  n  g
                v  #  q  #  a  #  #  #  x  #  e  #  d  #  u
                e  q  u  i  t  y  #  b  i  e  n  n  i  a  l
                r  #  a  #  e  #  g  #  o  #  r  #  o  #  l
                t  a  l  k  #  z  o  o  m  #  e  n  t  r  y
                h  #  i  #  j  #  s  #  #  #  #  #  i  #  #
                r  i  d  g  e  #  s  a  t  i  r  i  c  a  l
                o  #  #  #  e  #  i  #  o  #  u  #  #  #  a
                w  a  l  l  p  a  p  e  r  #  b  a  s  i  n
                #  #  e  #  #  #  #  #  q  #  y  #  u  #  d
                s  c  a  m  p  #  c  r  u  x  #  a  s  p  s
                p  #  f  #  a  #  i  #  e  #  b  #  t  #  l
                a  l  l  u  s  i  v  e  #  a  l  k  a  l  i
                s  #  e  #  t  #  i  #  #  #  u  #  i  #  d
                m  o  t  h  e  r  l  y  #  b  r  o  n  z  e""", Solver.solve(new State(grid2, key2), dictionary).toString());
        HashMap<Integer, Character> key3 = new HashMap<>();
        key3.put(16, 'p');
        key3.put(9, 't');
        key3.put(26, 'o');
        assertEquals("""
                #  l  #  b  #  d  #  i  #  g  #  b  #  g  #
                h  u  m  a  n  e  #  n  a  r  c  o  s  i  s
                #  k  #  s  #  v  #  f  #  i  #  a  #  g  #
                p  e  n  k  n  i  f  e  #  z  i  r  c  o  n
                #  w  #  e  #  c  #  r  #  z  #  #  #  l  #
                p  a  n  t  h  e  o  n  #  l  a  p  t  o  p
                #  r  #  b  #  #  #  o  #  e  #  h  #  #  #
                e  m  p  a  t  h  y  #  a  d  j  o  u  r  n
                #  #  #  l  #  i  #  c  #  #  #  t  #  e  #
                j  e  t  l  a  g  #  l  a  c  r  o  s  s  e
                #  v  #  #  #  h  #  a  #  i  #  g  #  i  #
                j  o  y  f  u  l  #  v  i  c  a  r  a  g  e
                #  k  #  l  #  a  #  i  #  a  #  a  #  n  #
                s  e  q  u  e  n  c  e  #  d  u  p  l  e  x
                #  d  #  x  #  d  #  r  #  a  #  h  #  d  #""", Solver.solve(new State(grid3, key3), dictionary).toString());
        Map<Integer, Character> key4= new HashMap<>();
        key4.put(8,'n');
        key4.put(19,'i');
        key4.put(23,'t');
        assertEquals("""
                s  e  w  #  #  v  a  m  p  #  a  s  p  s  #
                c  e  e  #  b  a  r  e  r  #  m  o  o  e  d
                a  l  e  #  l  i  a  n  a  #  e  p  o  x  y
                r  e  v  #  a  l  b  u  m  e  n  #  l  y  e
                f  r  i  z  z  #  #  #  #  a  d  z  #  #  #
                #  s  l  o  e  #  l  u  n  g  #  i  r  k  s
                #  #  #  o  r  d  i  n  a  l  #  t  h  e  e
                p  o  e  m  #  e  l  i  d  e  #  h  o  n  e
                a  n  t  i  #  b  a  t  i  s  t  e  #  #  #
                l  e  a  n  #  a  c  e  r  #  a  r  c  s  #
                #  #  #  g  a  t  #  #  #  #  p  s  a  l  m
                e  a  t  #  m  e  s  s  a  g  e  #  j  o  y
                s  q  u  a  b  #  t  a  b  o  r  #  o  w  n
                s  u  r  g  e  #  a  t  l  a  s  #  l  e  a
                #  a  f  a  r  #  t  e  e  d  #  #  e  r  s""",Solver.solve(new State(grid4,key4),dictionary).toString());
        HashMap<Character, Integer> mappings = new HashMap<>();
        mappings.put('0', 0);
        mappings.put('1', 1);
        mappings.put('2', 2);
        mappings.put('3', 3);
        System.out.println();
        System.out.println(solveState.dictOptions("001023", mappings));
    }
}