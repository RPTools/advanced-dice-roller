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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Class the represents the result of a Genesys dice roll. */
public class GenesysDiceResult {

  /**
   * Record to hold dice type and result.
   *
   * @param diceType the type of dice.
   * @param resultType the result of the dice.
   */
  public record Result(GenesysDiceType diceType, GenesysResultType resultType) {}

  /** The String that describes the dice that were rolled. */
  private final String rollString;
  /** The number of success rolled includes triumph. */
  private final int successCount;
  /** The number of failures rolled, includes dispair. */
  private final int failureCount;
  /** The number of advantages rolled. */
  private final int advantageCount;
  /** The number of threats rolled. */
  private final int threatCount;
  /** The number of triumphs rolled. */
  private final int triumphCount;
  /** The number of despairs rolled. */
  private final int despairCount;
  /** The number of light force points rolled. */
  private final int lightCount;
  /** The number of dark force points rolled. */
  private final int darkCount;
  /** Results of capture groups in the roll. */
  private final Map<String, GenesysDiceResult> groups;
  /** The rolls that occurred. */
  private final List<Result> rolls;

  /** The results that were rolled for each dice type. */
  private final Map<GenesysDiceType, List<GenesysResultType>> diceResults = new HashMap<>();

  /**
   * Constructor.
   *
   * @param rollString The string representing the roll.
   * @param rolls The rolls that occurred.
   * @param groups the capture groups and their results.
   */
  GenesysDiceResult(String rollString, List<Result> rolls, Map<String, GenesysDiceResult> groups) {
    this.rollString = rollString;

    int successCount = 0;
    int failureCount = 0;
    int advantageCount = 0;
    int threatCount = 0;
    int triumphCount = 0;
    int despairCount = 0;
    int lightCount = 0;
    int darkCount = 0;

    for (var roll : rolls) {
      successCount += roll.resultType().getSuccess();
      failureCount += roll.resultType().getFailure();
      advantageCount += roll.resultType().getAdvantage();
      threatCount += roll.resultType().getThreat();
      triumphCount += roll.resultType().getTriumph();
      despairCount += roll.resultType().getDespair();
      lightCount += roll.resultType().getLight();
      darkCount += roll.resultType().getDark();
    }

    this.successCount = successCount;
    this.failureCount = failureCount;
    this.advantageCount = advantageCount;
    this.threatCount = threatCount;
    this.triumphCount = triumphCount;
    this.despairCount = despairCount;
    this.lightCount = lightCount;
    this.darkCount = darkCount;
    this.groups = Map.copyOf(groups);
    this.rolls = List.copyOf(rolls);

    for (var roll : rolls) {
      diceResults.computeIfAbsent(roll.diceType(), k -> new ArrayList<>()).add(roll.resultType());
    }
  }

  /**
   * Constructor.
   *
   * @param rollString The string representing the roll.
   * @param rolls The rolls that occurred.
   */
  GenesysDiceResult(String rollString, List<Result> rolls) {
    this(rollString, rolls, Map.of());
  }

  /**
   * Returns the string representing the roll.
   *
   * @return the string representing the roll.
   */
  public String getRollString() {
    return rollString;
  }

  /**
   * Returns the number of successes rolled.
   *
   * @return the number of successes rolled.
   */
  public int getSuccessCount() {
    return successCount;
  }

  /**
   * Returns the number of failures rolled.
   *
   * @return the number of failures rolled.
   */
  public int getFailureCount() {
    return failureCount;
  }

  /**
   * Returns the number of advantages rolled.
   *
   * @return the number of advantages rolled.
   */
  public int getAdvantageCount() {
    return advantageCount;
  }

  /**
   * Returns the number of threats rolled.
   *
   * @return the number of threats rolled.
   */
  public int getThreatCount() {
    return threatCount;
  }

  /**
   * Returns the number of triumphs rolled.
   *
   * @return the number of triumphs rolled.
   */
  public int getTriumphCount() {
    return triumphCount;
  }

  /**
   * Returns the number of despairs rolled.
   *
   * @return the number of despairs rolled.
   */
  public int getDespairCount() {
    return despairCount;
  }

  /**
   * Returns the number of light force pips rolled.
   *
   * @return the number of light force pips rolled.
   */
  public int getLightCount() {
    return lightCount;
  }

  /**
   * Returns the number of dark force pips rolled.
   *
   * @return the number of dark force pips rolled.
   */
  public int getDarkCount() {
    return darkCount;
  }

  /**
   * Returns the capture group names. This will only return the top level capture groups, it will
   * not return the name of inner capture groups.
   *
   * @return the capture group names.
   */
  public Set<String> getGroupNames() {
    return groups.keySet();
  }

  /**
   * Returns the result for the specified capture group, if the capture group does not exist then
   * this will return {@code null}.
   *
   * @param name The name of the capture group.
   * @return the results of the capture group.
   */
  public GenesysDiceResult getGroup(String name) {
    return groups.get(name);
  }

  /**
   * Returns all the roll results.
   *
   * @return the roll results.
   */
  public List<Result> getRolls() {
    return rolls;
  }

  /**
   * Returns the results rolled for the specified dice type.
   *
   * @param diceType the dice type to get the results of.
   * @return the results.
   */
  public List<GenesysResultType> getDiceResults(GenesysDiceType diceType) {
    return diceResults.getOrDefault(diceType, List.of());
  }

  /**
   * Returns the number of result type rolled.
   *
   * @param resultType the result type to get the number of times the result was rolled.
   * @return the number of times the result was rolled.
   */
  public long getNumberOfResult(GenesysResultType resultType) {
    return rolls.stream().filter(r -> r.resultType == resultType).count();
  }
}
