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
package net.rptools.maptool.advanceddice;

/** Enumeration of the possible results of a die roll. */
public enum GenesysResultType {
  /** A single success. */
  SUCCESS(1, 0, 0, 0, 0, 0, 0, 0, "s", 1),
  /** A single failure. */
  FAILURE(0, 1, 0, 0, 0, 0, 0, 0, "f", 2),
  /** A single advantage. */
  ADVANTAGE(0, 0, 1, 0, 0, 0, 0, 0, "a", 3),
  /** A single threat. */
  THREAT(0, 0, 0, 1, 0, 0, 0, 0, "h", 4),
  /** A single triumph. */
  TRIUMPH(1, 0, 0, 0, 1, 0, 0, 0, "t", 5),
  /** A single despair. */
  DESPAIR(0, 1, 0, 0, 0, 1, 0, 0, "d", 6),
  /* A single light side point. */
  LIGHT(0, 0, 0, 0, 0, 0, 1, 0, "Z", 7),
  /** A single dark side point. */
  DARK(0, 0, 0, 0, 0, 0, 0, 1, "z", 8),
  /** No result. */
  NONE(0, 0, 0, 0, 0, 0, 0, 0, " ", 99),
  /** A single success and a single advantage. */
  SUCCESS_ADVANTAGE(1, 0, 1, 0, 0, 0, 0, 0, "sa", 99),
  /** Two Advantages. */
  ADVANTAGE_ADVANTAGE(0, 0, 2, 0, 0, 0, 0, 0, "aa", 99),
  /** Two Successes. */
  SUCCESS_SUCCESS(2, 0, 0, 0, 0, 0, 0, 0, "ss", 99),
  /* A single failure and a single threat. */
  FAILURE_THREAT(0, 1, 0, 1, 0, 0, 0, 0, "fh", 99),
  /** Two Failures. */
  FAILURE_FAILURE(0, 2, 0, 0, 0, 0, 0, 0, "ff", 99),
  /** Two Threats. */
  THREAT_THREAT(0, 0, 0, 2, 0, 0, 0, 0, "hh", 99),
  /** Two Light Side Points. */
  LIGHT_LIGHT(0, 0, 0, 0, 0, 0, 2, 0, "ZZ", 99),
  /** Two Dark Side Points. */
  DARK_DARK(0, 0, 0, 0, 0, 0, 0, 2, "zz", 99);

  /** The number of successes this result represents. */
  private final int success;
  /** The number of failures this result represents. */
  private final int failure;
  /** The number of advantages this result represents. */
  private final int advantage;
  /** The number of threats this result represents. */
  private final int threat;
  /** The number of triumphs this result represents. */
  private final int triumph;
  /** The number of despairs this result represents. */
  private final int despair;
  /** The number of light side points this result represents. */
  private final int light;
  /** The number of dark side points this result represents. */
  private final int dark;

  /** The sort order of the result when grouped. */
  private final int groupSort;

  /**
   * Constructor.
   *
   * @param success the number of successes this result represents.
   * @param failure the number of failures this result represents.
   * @param advantage the number of advantages this result represents.
   * @param threat the number of threats this result represents.
   * @param triumph the number of triumphs this result represents.
   * @param despair the number of despairs this result represents.
   * @param light the number of light side points this result represents.
   * @param dark the number of dark side points this result represents.
   * @param groupSort the sort order of the result when grouped.
   */
  GenesysResultType(
      int success,
      int failure,
      int advantage,
      int threat,
      int triumph,
      int despair,
      int light,
      int dark,
      String fontCharacters,
      int groupSort) {
    this.success = success;
    this.failure = failure;
    this.advantage = advantage;
    this.threat = threat;
    this.triumph = triumph;
    this.despair = despair;
    this.light = light;
    this.dark = dark;
    this.groupSort = groupSort;
  }

  /**
   * Get the number of successes this result represents.
   *
   * @return the number of successes this result represents.
   */
  public int getSuccess() {
    return success;
  }

  /**
   * Get the number of failures this result represents.
   *
   * @return the number of failures this result represents.
   */
  public int getFailure() {
    return failure;
  }

  /**
   * Get the number of advantages this result represents.
   *
   * @return the number of advantages this result represents.
   */
  public int getAdvantage() {
    return advantage;
  }

  /**
   * Get the number of threats this result represents.
   *
   * @return the number of threats this result represents.
   */
  public int getThreat() {
    return threat;
  }

  /**
   * Get the number of triumphs this result represents.
   *
   * @return the number of triumphs this result represents.
   */
  public int getTriumph() {
    return triumph;
  }

  /**
   * Get the number of despairs this result represents.
   *
   * @return the number of despairs this result represents.
   */
  public int getDespair() {
    return despair;
  }

  /**
   * Get the number of light side points this result represents.
   *
   * @return the number of light side points this result represents.
   */
  public int getLight() {
    return light;
  }

  /**
   * Get the number of dark side points this result represents.
   *
   * @return the number of dark side points this result represents.
   */
  public int getDark() {
    return dark;
  }

  /**
   * Get the font characters that represent this result.
   *
   * @return the font characters that represent this result.
   */
  public int getGroupSort() {
    return groupSort;
  }
}
