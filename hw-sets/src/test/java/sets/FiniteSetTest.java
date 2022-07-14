/*
 * Copyright (C) 2022 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Summer Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  /** Test creating sets. */
  @Test
  public void testCreation() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());         // empty
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());  // negative
  }

  // Some example sets used by the tests below.
  private static FiniteSet S0 = FiniteSet.of(new float[0]);
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});
  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});

  /** Test set equality. */
  @Test
  public void testEquals() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));

    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));

    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  /** Test set size. */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }

//  // TODO: Feel free to initialize (private static) FiniteSet objects here
//  //       if you plan to use them for the tests below.
//  private static FiniteSet S2 = FiniteSet.of(new float[] {-3, -1, 0, 1, 3});
//  private static FiniteSet S3 = FiniteSet.of(new float[] {-2, 2});
//  private static FiniteSet S4 = FiniteSet.of(new float[] {});
//  private static FiniteSet S5 = FiniteSet.of(new float[] {-2, 0});

  /** Tests forming the union of two finite sets. */
  @Test
  public void testUnion() {
    // DONE: implement this
    // this and other are completely different
    assertEquals(S2.union(S3), FiniteSet.of(new float[] {-3, -2, -1, 0, 1, 2, 3}));
    // this and other have some intersecting elements
    assertEquals(S2.union(S5), FiniteSet.of(new float[] {-3, -2, -1, 0, 1, 3}));
    // one of the sets (other) is empty
    assertEquals(S2.union(S4), FiniteSet.of(new float[] {-3, -1, 0, 1, 3}));
    // both sets are empty
    assertEquals(S4.union(S4), FiniteSet.of(new float[] {}));
  }

  /** Tests forming the intersection of two finite sets. */
  @Test
  public void testIntersection() {
    // TODO: implement this
    // this and other are completely different, expecting empty output
    assertEquals(S2.intersection(S3), FiniteSet.of(new float[] {}));
    // this and other have some intersecting elements
    assertEquals(S3.intersection(S5), FiniteSet.of(new float[] {-2}));
    // one of the sets (other) is empty
    assertEquals(S2.intersection(S4), FiniteSet.of(new float[] {}));
    // both sets are empty
    assertEquals(S4.intersection(S4), FiniteSet.of(new float[] {}));
  }

  // TODO: Feel free to initialize (private static) FiniteSet objects here
  //       if you plan to use them for the tests below.
  private static FiniteSet S2 = FiniteSet.of(new float[] {-3, -1, 0, 1, 3});
  private static FiniteSet S22 = FiniteSet.of(new float[] {-1, 1}); // subset of S2
  private static FiniteSet S3 = FiniteSet.of(new float[] {-2, 2});
  private static FiniteSet S4 = FiniteSet.of(new float[] {});
  private static FiniteSet S5 = FiniteSet.of(new float[] {-2, 0});

  /** Tests forming the difference of two finite sets. */
  @Test
  public void testDifference() {
    // TODO: implement this
    // no intersections, expecting to return all elements in this(?)
    assertEquals(S2.intersection(S3), FiniteSet.of(new float[] {-3, -1, 0, 1, 3}));
    // some intersections, expecting elements in this but not in other
    //    do we care about points in other that is not in this?
    assertEquals(S3.intersection(S5), FiniteSet.of(new float[] {2}));
    // this is a subset of other
    assertEquals(S22.intersection(S2), FiniteSet.of(new float[] {}));
    // other is a subset of this
    assertEquals(S2.intersection(S22), FiniteSet.of(new float[] {-3, 0, 3}));
    // this is an empty set
    assertEquals(S4.intersection(S2), FiniteSet.of(new float[] {-3, 0, 3}));
    // other is an empty set
    assertEquals(S2.intersection(S4), FiniteSet.of(new float[] {-3, 0, 3}));
    // both sets are empty
    assertEquals(S4.intersection(S4), FiniteSet.of(new float[] {}));
  }

}
