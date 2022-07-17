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

import java.util.Arrays;
import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {
  // DONE: Include a representation invariant (RI) for the fields below
  // DONE: Include a abstraction function (AF) for this ADT
  // RI: either true or false
  // AF(this) = true when it is a complement set, false otherwise
  private final boolean complement;
  // RI: points != null and points != infinity and points != NaN
  // AF(this) = points stored in the FiniteSet, represents points excluded
  // when complement is true, represents points contained with complement
  // is false.
  private final FiniteSet points;

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    // DONE: implement this
    this.complement = false;
    this.points = FiniteSet.of(vals);
  }

  // NOTE: feel free to create other constructors
  // constructor for complement set
  public SimpleSet(boolean complement, FiniteSet vals) {
    this.complement = !complement;
    this.points = vals;
  }

  @Override
  // returns whether this equals other
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;
    SimpleSet other = (SimpleSet) o;
    // DONE: replace this with a correct check
    // we are checking that both this and other have the same complement status
    // as well as the same points.
    return this.complement == other.complement && this.points.equals(other.points);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // DONE: implement this
    if (!this.complement) {
      return this.points.size();
    } else {
      return Float.POSITIVE_INFINITY;
    }
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    // DONE: document its invariant
    StringBuilder buf = new StringBuilder();
    if (this.complement) {
      if (this.points.size() == 0) {
        return "R";
      }
      buf.append("R \\ {");
    } else {
      buf.append("{");
    }

    int i = 0;
    // Inv: buf = ch(D[0]), ch(D[1]), ..., ch(D[i - 1])
    //      where D = this.points and n = this.points.size().
    while (i != this.points.size()) {
      buf.append(this.points.getPoints().get(i));
      i++;
      if (i != this.points.size()) {
        buf.append(", ");
      }
    }
    // After the loop, we have ch(D[0]), ch(D[1]), ..., ch(D[i - 1]) and i = n.
    // y plugging in the value of i in buf, we have:
    // buf = ch(D[0]), ch(D[1]), ..., ch(D[n - 1]), which implies the post condition.

    // Post: buf = ch(D[0]), ch(D[1]), ..., ch(D[n - 1])
    return buf + "}";
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // DONE: include sufficient comments to see why it is correct (hint: cases)
    // case 1: change this to R \ this (when an original set is passed in)
    // we want to change the representation of this from this to R \ this, and
    // keep the points contained within, hence by calling the new constructor that
    // takes in a boolean and values set, we will flip the boolean complement,
    // after which it will represent the complement set R \ this.

    // case 2: change R \ this to this (when a complement set is passed in)
    // we want to change the representation from R \ this to this without changing the
    // points it contains. By calling the new constructor that takes in a boolean and
    // its points, we are computing the complement of a complement set, R \ R \ this,
    // which then becomes the original set --> this.
    return new SimpleSet(this.complement, this.points);
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    // DONE: include sufficient comments to see why it is correct (hint: cases)
    FiniteSet output;
    if (!this.complement && !other.complement) {
      // case 1: neither this and other is complement set
      // when both sets are original sets (with size that is not infinity),
      // we will simply find the elements that appear in this and other.
      output = this.points.union(other.points);
    } else if (this.complement && other.complement) {
      // case 2: both this and other are complement of some sets
      // since a complement set represents all the points other than those
      // it stores, the union of two complement sets then will exclude the
      // elements that are stored in both complement sets. Therefore, we first
      // find the intersection of the elements these two sets store, and exclude
      // these intersecting elements by storing them and setting complement to be true.
      output = this.points.intersection(other.points);
    } else if (this.complement) {
      output = this.complement().points.difference(other.points);
    } else {
      output = other.complement().points.difference(this.points);
    }
    // case 3 and 4: this or other is a complement set but not both
    // when merging an infinite set (complement set) with a small set, we will then
    // check if the complement set excludes elements that are not contained in the
    // small set either by computing the points stored in the complement set but not
    // the small set. Because these excluded elements are not contained in either sets,
    // we will store them and set complement as true, meaning that the union set contains
    // all the elements but these stored ones.

    // In all cases 2, 3, and 4, we end up with a complement set, meaning we want to
    // store the boolean complement as true, since the constructor flips the boolean,
    // we will want to pass in the opposite of what we want to store, which then will
    // be false.
    // Since the only case where the boolean we want to store is false (where we'll
    // pass in "true") is when we unionize two non-complement sets, we can simply pass in the
    // boolean "!this.complement && !other.complement" which will only be true when we are
    // merging two non-complement sets. When it actually gets to the constructor, the boolean
    // will become false, representing a non-complement set.
    return new SimpleSet(!this.complement && !other.complement, output);
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersect other
   */
  public SimpleSet intersection(SimpleSet other) {
    // DONE: include sufficient comments to see why it is correct
    FiniteSet output;
    if (!this.complement && !other.complement) {
      // case 1: neither this and other is complement set
      // when both sets are original sets (with size that is not infinity),
      // we will simply find the intersection of the two sets. And the
      // intersection of two small sets (non-complement sets) will be
      // a non-complement set.
      output = this.points.intersection(other.points);
    } else if (this.complement && other.complement) {
      // case 2: both this and other are complement sets
      // since a complement set excludes the points it stores, these points will
      // never appear in the intersection of two complement sets. Hence we want
      // to find the union of the two complement sets and exclude them and store
      // the boolean complement as true (the intersection of two infinite sets
      // becomes one infinite set).
      output = this.points.union(other.points);
    } else if (!this.complement) {
      output = this.points.difference(other.points);
    } else {
      output = other.points.difference(this.points);
    }
    // case 3 and 4: this or other is a complement set but not both
    // when merging an infinite set (complement set) with a small set, the result will
    // be a small set, because at the maximum, the intersection can only be elements
    // from the small set. Therefore, we want to find the elements that appear in the
    // small set and subtract the elements that the complement set excludes, which is
    // "small set".difference(complement set). And we will store the complement boolean
    // as false.

    // The only case where we end up with a complement set is when we find the intersection
    // of two complement sets, in which case we want to pass in the opposite of true for the
    // constructor to flip the boolean, thus we pass in "!(this.complement && other.complement)"
    // so that all other cases will have complement stored as false.
    return new SimpleSet(!(this.complement && other.complement), output);
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    // DONE: include sufficient comments to see why it is correct
    FiniteSet output;
    // case 1 and 2: neither this and other is complement set, OR
    // both are complement sets.
    // When finding difference between two small sets, we simply call method
    // this.difference(other), but when both are complement sets, we want to
    // find elements other excludes but this does not exclude, which is the
    // same as finding elements this includes but other excludes.
    // Note: subtracting a small set from a small set and subtracting an infinite
    // set from an infinite set will both end up with a small set.
    if (this.complement && other.complement) {
      output = other.points.difference(this.points);
    } else if (!this.complement && !other.complement) {
      output = this.points.difference(other.points);
    } else {
      if (!this.complement) {
        // case 3: when this is a small set but other is an infinite set, and we want to find
        // elements in this but not in other, we can look at the elements that the complement
        // set excludes, because these excluded elements represents elements that the complement
        // set does not have. Hence, we will find the intersection of this and other, and we
        // end up with a small set.
        output = this.points.intersection(other.points);
      } else { // this.comp && !other.comp
        // case 4: when finding elements in an infinite set that are not in a small set, we
        // want to add the elements in the small set to the elements that the infinite set
        // already excludes, which can be done by unionizing the points stored in this and other,
        // and we end up with an infinite set.
        output = this.points.union(other.points);
      }
    }

    // The only case where we end up with an infinite set is when we are finding elements in an
    // infinite set that are not in a small set, which is when this is a complement set and other
    // is not. To account for the boolean flip of the constructor, we will then pass in the opposite
    // of (this.complement && !other.complement), so that all other cases will be stored as
    // non-complement sets.
    return new SimpleSet(!(this.complement && !other.complement), output);
  }

}
