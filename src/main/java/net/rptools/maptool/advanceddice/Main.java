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

import net.rptools.maptool.advanceddice.parser.GenesysDiceLexer;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

  public static void main(String[] args) {
    var lexer =
        new GenesysDiceLexer(
            CharStreams.fromString("2y (boost: 2b (subboost: 4b)) ${?whats the value}f"));
    var tokens = new CommonTokenStream(lexer);
    var parser = new GenesysDiceParser(tokens);
    var tree = parser.startGenesys();
    var visitor = new GenesysDiceRollVisitor(s -> 1, s -> 1, s -> 1);
    GenesysDiceResultBuilder result = visitor.visit(tree);
    result.setRollString(lexer.getInputStream().toString());
    System.out.println(result);
  }
}
