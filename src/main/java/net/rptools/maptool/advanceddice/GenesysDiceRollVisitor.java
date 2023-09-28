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
import java.util.function.ToIntFunction;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.AbilityDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.AdvantageContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.BoostDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.ChallengeDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.DarkContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.DespairContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.DifficultyDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.FailureContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.ForceDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.FunctionParamContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysFunctionContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysFunctionParamsContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysMultipleDiceResultsContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysMultipleRollContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysNumberDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysRollContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GenesysRollsContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GroupNameContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.GroupedGenesysRollContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.LightContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.NumberLiteralContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.ProficiencyDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.SetbackDiceContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.StartGenesysContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.StringLiteralContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.SuccessContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.ThreatContext;
import net.rptools.maptool.advanceddice.parser.GenesysDiceParser.TriumphContext;

/** Tree visitor for te syntax tree built by the parser. */
public class GenesysDiceRollVisitor
    extends net.rptools.maptool.advanceddice.parser.GenesysDiceParserBaseVisitor<
        GenesysDiceResultBuilder> {

  /** Function used to resolve variables. */
  private final ToIntFunction<String> variableResolver;
  /** Function used to resolve properties. */
  private final ToIntFunction<String> propertyResolver;

  /** Function used to resolve prompts. */
  private final ToIntFunction<String> promptResolver;

  /**
   * Constructor.
   *
   * @param variableResolver the function used to resolve variables.
   * @param propertyResolver the function used to resolve properties.
   * @param promptResolver the function used to resolve prompts.
   */
  public GenesysDiceRollVisitor(
      ToIntFunction<String> variableResolver,
      ToIntFunction<String> propertyResolver,
      ToIntFunction<String> promptResolver) {
    this.variableResolver = variableResolver;
    this.propertyResolver = propertyResolver;
    this.promptResolver = promptResolver;
  }

  @Override
  public GenesysDiceResultBuilder visitStartGenesys(StartGenesysContext ctx) {
    return visit(ctx.genesysRolls());
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysRoll(GenesysRollContext ctx) {
    var res = super.visitGenesysRoll(ctx);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitGroupName(GroupNameContext ctx) {
    var res = super.visitGroupName(ctx);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysNumberDice(GenesysNumberDiceContext ctx) {
    var res = super.visitGenesysNumberDice(ctx);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysRolls(GenesysRollsContext ctx) {
    var res = new ArrayList<GenesysDiceResultBuilder>();
    for (var roll : ctx.genesysRoll()) {
      res.add(visit(roll));
    }
    return new GenesysDiceResultBuilder().merge(res).setRollString(ctx.getText());
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysMultipleRoll(GenesysMultipleRollContext ctx) {
    var res = new ArrayList<GenesysDiceResultBuilder>();
    int count = getNumber(ctx.num);
    for (int i = 0; i < count; i++) {
      res.add(visit(ctx.genesysDiceType()));
    }
    return new GenesysDiceResultBuilder().merge(res).setRollString(ctx.getText());
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysMultipleDiceResults(
      GenesysMultipleDiceResultsContext ctx) {
    var res = new ArrayList<GenesysDiceResultBuilder>();
    int count = getNumber(ctx.num);
    for (int i = 0; i < count; i++) {
      res.add(visit(ctx.genesysDiceResults()));
    }
    return new GenesysDiceResultBuilder().merge(res).setRollString(ctx.getText());
  }

  @Override
  public GenesysDiceResultBuilder visitGroupedGenesysRoll(GroupedGenesysRollContext ctx) {
    var groupRes = new GenesysDiceResultBuilder();
    var res = visit(ctx.genesysRolls());
    return groupRes.addGroup(ctx.groupName().getText().replaceAll(":$", ""), res).setRollString(ctx.getText());
  }

  @Override
  public GenesysDiceResultBuilder visitProficiencyDice(ProficiencyDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.PROFICIENCY);
  }

  @Override
  public GenesysDiceResultBuilder visitChallengeDice(ChallengeDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.CHALLENGE);
  }

  @Override
  public GenesysDiceResultBuilder visitBoostDice(BoostDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.BOOST);
  }

  @Override
  public GenesysDiceResultBuilder visitSetbackDice(SetbackDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.SETBACK);
  }

  @Override
  public GenesysDiceResultBuilder visitAbilityDice(AbilityDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.ABILITY);
  }

  @Override
  public GenesysDiceResultBuilder visitDifficultyDice(DifficultyDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.DIFFICULTY);
  }

  @Override
  public GenesysDiceResultBuilder visitForceDice(ForceDiceContext ctx) {
    return new GenesysDiceResultBuilder()
        .setRollString(ctx.getText())
        .addResult(GenesysDiceType.FORCE);
  }

  @Override
  public GenesysDiceResultBuilder visitSuccess(SuccessContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.SUCCESS);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitFailure(FailureContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.FAILURE);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitTriumph(TriumphContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.TRIUMPH);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitDespair(DespairContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.DESPAIR);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitAdvantage(AdvantageContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.ADVANTAGE);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitThreat(ThreatContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.THREAT);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitLight(LightContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.LIGHT);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitDark(DarkContext ctx) {
    var res = new GenesysDiceResultBuilder();
    res.setRollString(ctx.getText());
    res.addResult(GenesysResultType.DARK);
    return res;
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysFunction(GenesysFunctionContext ctx) {
    return super.visitGenesysFunction(ctx);
  }

  @Override
  public GenesysDiceResultBuilder visitGenesysFunctionParams(GenesysFunctionParamsContext ctx) {
    return super.visitGenesysFunctionParams(ctx);
  }

  @Override
  public GenesysDiceResultBuilder visitFunctionParam(FunctionParamContext ctx) {
    return super.visitFunctionParam(ctx);
  }

  @Override
  public GenesysDiceResultBuilder visitStringLiteral(StringLiteralContext ctx) {
    return super.visitStringLiteral(ctx);
  }

  @Override
  public GenesysDiceResultBuilder visitNumberLiteral(NumberLiteralContext ctx) {
    return super.visitNumberLiteral(ctx);
  }

  /**
   * Returns the number represented by the specified context.
   *
   * @param num the context to get the number from.
   * @return the number represented by the specified context.
   */
  private int getNumber(GenesysNumberDiceContext num) {
    if (num.INTEGER_LITERAL() != null) {
      return Integer.parseInt(num.INTEGER_LITERAL().getText());
    } else if (num.VARIABLE() != null) {
      return variableResolver.applyAsInt(num.VARIABLE().getText());
    } else if (num.PROPERTY() != null) {
      return propertyResolver.applyAsInt(num.PROPERTY().getText());
    } else if (num.PROMPT() != null) {
      return promptResolver.applyAsInt(num.PROMPT().getText());
    } else {
      return 1; // TODO: CDW
    }
  }
}
