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
import net.rptools.maptool.advanceddice.GenesysDiceResult.Result;

/**
 * Builder class for creating {@link GenesysDiceResult} objects.
 */
public class GenesysDiceResultBuilder {

  /** The string that represents the roll. */
  private String rollString = "";
  /** The capture groups in the roll. */
  private final Map<String, GenesysDiceResultBuilder> groups = new HashMap<>();
  /** The rolls that occurred. */
  private final List<GenesysDiceResult.Result> rolls = new ArrayList<>();

  /**
   * Sets the roll string.
   * @param rollString the roll string.
   * @return this builder.
   */
  public GenesysDiceResultBuilder setRollString(String rollString) {
    this.rollString = rollString;
    return this;
  }

  /**
   * Adds a capture group to the result.
   * @param name the name of the group.
   * @param result the result of the group.
   * @return this builder.
   */
  public GenesysDiceResultBuilder addGroup(String name, GenesysDiceResultBuilder result) {
    // We want to add the group AND add the rolls to the main list
    groups.put(name, result);
    rolls.addAll(result.rolls);
    return this;
  }

  /**
   * Adds a result to the roll (not from a roll).
   * @param result the result to add.
   * @return this builder.
   */
  public GenesysDiceResultBuilder addResult(GenesysResultType result) {
    rolls.add(new Result(GenesysDiceType.NONE, result));
    return this;
  }

  /**
   * Adds a result to the roll for the specified dice type.
   * @param diceType the dice type.
   * @param result the result to add.
   * @return this builder.
   */
  public GenesysDiceResultBuilder addResult(GenesysDiceType diceType, GenesysResultType result) {
    rolls.add(new Result(diceType, result));
    return this;
  }

  /**
   * "Rolls" the specified dice type and adds the result to the roll.
   * @param diceType the dice type.
   * @return this builder.
   */
  public GenesysDiceResultBuilder addResult(GenesysDiceType diceType) {
    rolls.add(new Result(diceType, diceType.roll()));
    return this;
  }

  /**
   * Merges the specified result into this one.
   * @param result the result to merge into this one.
   * @return this builder.
   */
  public GenesysDiceResultBuilder merge(GenesysDiceResultBuilder result) {
    if (result == null) {
      return this;
    }

    rollString += result.rollString;
    groups.putAll(result.groups);
    rolls.addAll(result.rolls);

    return this;
  }

  /**
   * Merges the specified results into this one.
   * @param result the results to merge into this one.
   * @return this builder.
   */
  public GenesysDiceResultBuilder merge(List<GenesysDiceResultBuilder> result) {
    if (result == null) {
      return this;
    }

    for (var r : result) {
      merge(r);
    }

    return this;
  }

  /**
   * Builds a {@link GenesysDiceResult} from this builder.
   * @return the result
   */
  public GenesysDiceResult build() {
    var builtGroups = new HashMap<String, GenesysDiceResult>();
    for (var group : groups.entrySet()) {
      builtGroups.put(group.getKey(), group.getValue().build());
    }
    return new GenesysDiceResult(rollString, rolls, builtGroups);
  }
}
