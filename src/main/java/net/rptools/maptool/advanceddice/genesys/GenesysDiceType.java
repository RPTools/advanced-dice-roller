/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.advanceddice.genesys;

import java.util.List;
import net.rptools.maptool.advanceddice.DiceRoller;

/** Enumeration of the possible dice types. */
public enum GenesysDiceType {
  /** The boost die. */
  BOOST(
      0,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.NONE,
          GenesysResultType.SUCCESS,
          GenesysResultType.SUCCESS_ADVANTAGE,
          GenesysResultType.ADVANTAGE_ADVANTAGE,
          GenesysResultType.ADVANTAGE)),

  /** The setback die. */
  SETBACK(
      1,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.NONE,
          GenesysResultType.FAILURE,
          GenesysResultType.FAILURE,
          GenesysResultType.THREAT,
          GenesysResultType.THREAT)),
  /** The ability die. */
  ABILITY(
      2,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.SUCCESS,
          GenesysResultType.SUCCESS,
          GenesysResultType.SUCCESS_SUCCESS,
          GenesysResultType.ADVANTAGE,
          GenesysResultType.ADVANTAGE,
          GenesysResultType.SUCCESS_ADVANTAGE,
          GenesysResultType.ADVANTAGE_ADVANTAGE)),
  /** The difficulty die. */
  DIFFICULTY(
      3,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.FAILURE,
          GenesysResultType.FAILURE_FAILURE,
          GenesysResultType.THREAT,
          GenesysResultType.THREAT,
          GenesysResultType.THREAT,
          GenesysResultType.THREAT_THREAT,
          GenesysResultType.FAILURE_THREAT)),
  /** The proficiency die. */
  PROFICIENCY(
      4,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.SUCCESS,
          GenesysResultType.SUCCESS,
          GenesysResultType.SUCCESS_SUCCESS,
          GenesysResultType.SUCCESS_SUCCESS,
          GenesysResultType.ADVANTAGE,
          GenesysResultType.SUCCESS_ADVANTAGE,
          GenesysResultType.SUCCESS_ADVANTAGE,
          GenesysResultType.SUCCESS_ADVANTAGE,
          GenesysResultType.ADVANTAGE_ADVANTAGE,
          GenesysResultType.ADVANTAGE_ADVANTAGE,
          GenesysResultType.TRIUMPH)),
  /* The challenge die. */
  CHALLENGE(
      5,
      List.of(
          GenesysResultType.NONE,
          GenesysResultType.FAILURE,
          GenesysResultType.FAILURE,
          GenesysResultType.FAILURE_FAILURE,
          GenesysResultType.FAILURE_FAILURE,
          GenesysResultType.THREAT,
          GenesysResultType.THREAT,
          GenesysResultType.FAILURE_THREAT,
          GenesysResultType.FAILURE_THREAT,
          GenesysResultType.THREAT_THREAT,
          GenesysResultType.THREAT_THREAT,
          GenesysResultType.DESPAIR)),
  /** The force die. */
  FORCE(
      6,
      List.of(
          GenesysResultType.DARK,
          GenesysResultType.DARK,
          GenesysResultType.DARK,
          GenesysResultType.DARK,
          GenesysResultType.DARK,
          GenesysResultType.DARK,
          GenesysResultType.DARK_DARK,
          GenesysResultType.LIGHT,
          GenesysResultType.LIGHT,
          GenesysResultType.LIGHT_LIGHT,
          GenesysResultType.LIGHT_LIGHT,
          GenesysResultType.LIGHT_LIGHT)),
  /** Not a dice, used for added success, fail etc. */
  NONE(99, List.of());

  /** The sides of the die. */
  private final List<GenesysResultType> sides;

  /** The sort order of the die when grouped, */
  private final int groupSort;

  /**
   * Constructor.
   *
   * @param groupSort the sort order of the die when grouped.
   * @param sides the sides of the die.
   */
  GenesysDiceType(int groupSort, List<GenesysResultType> sides) {
    this.sides = sides;
    this.groupSort = groupSort;
  }

  /**
   * Get the number of sides on this die.
   *
   * @return the number of sides on this die.
   */
  public int getSides() {
    return sides.size();
  }

  /**
   * Roll the die.
   *
   * @return the result of the roll.
   */
  public GenesysResultType roll() {
    return getSide(new DiceRoller().rollDice(sides.size()) - 1);
  }

  /**
   * Get the result of a roll of the die.
   *
   * @param side the side of the die to get the result for.
   * @return the result of the roll.
   */
  public GenesysResultType getSide(int side) {
    return sides.get(side);
  }

  /**
   * Get the sort order of the die when grouped.
   *
   * @return the sort order of the die when grouped.
   */
  public int getGroupSort() {
    return groupSort;
  }
}
