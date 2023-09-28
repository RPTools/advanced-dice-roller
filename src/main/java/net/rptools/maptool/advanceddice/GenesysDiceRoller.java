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

import java.util.function.ToIntFunction;
import net.rptools.maptool.advanceddice.parser.GenesysDiceLexer;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/** Class to roll Genesys dice. */
public class GenesysDiceRoller {

  /**
   * Roll the given dice string using genesys/starwars dice roll parser.
   * @param rollString the string to roll.
   * @param variableSupplier the supplier to use for variable values.
   * @param propertySupplier the supplier to use for property values.
   * @param promptSupplier the supplier to use for prompt values.
   * @return the result of the roll.
   */
  public GenesysDiceResult roll(String rollString, ToIntFunction<String> variableSupplier,
      ToIntFunction<String> propertySupplier, ToIntFunction<String> promptSupplier) {
    var lexer = new GenesysDiceLexer(CharStreams.fromString(rollString));
    var tokens = new CommonTokenStream(lexer);
    var parser = new GenesysDiceParser(tokens);
    var tree = parser.startGenesys();
    var visitor = new GenesysDiceRollVisitor(variableSupplier, propertySupplier, promptSupplier);
    var result = visitor.visit(tree);
    result.setRollString(rollString);
    return result.build();
  }
}
