parser grammar GenesysDiceParser;



options { tokenVocab=GenesysDiceLexer; }

startGenesys                  : genesysRolls EOF
                              ;

// Genesys Dice
genesysRolls                  : (genesysRoll COMMA?)*
                              ;

genesysRoll                   : genesysMultipleRoll
                              | genesysDiceType
                              | genesysMultipleDiceResults
                              | genesysDiceResults
                              | groupedGenesysRoll
                              | genesysFunction
                              ;

genesysMultipleRoll           : num=genesysNumberDice genesysDiceType
                              ;

genesysMultipleDiceResults    : num=genesysNumberDice genesysDiceResults
                              ;

groupedGenesysRoll            : LPAREN groupName? genesysRolls RPAREN
                              ;

groupName                     : GROUP_NAME
                              ;

genesysDiceType               : PROFICIENCY_DICE                                # ProficiencyDice
                              | CHALLENGE_DICE                                  # ChallengeDice
                              | BOOST_DICE                                      # BoostDice
                              | SETBACK_DICE                                    # SetbackDice
                              | ABILITY_DICE                                    # AbilityDice
                              | DIFFICULTY_DICE                                 # DifficultyDice
                              | FORCE_DICE                                      # ForceDice
                              ;

genesysDiceResults            : SUCCESS                                         # Success
                              | FAILURE                                         # Failure
                              | TRIUMPH                                         # Triumph
                              | DESPAIR                                         # Despair
                              | ADVANTAGE                                       # Advantage
                              | THREAT                                          # Threat
                              | LIGHT                                           # Light
                              | DARK                                            # Dark
                              ;

genesysNumberDice             : INTEGER_LITERAL
                              | PROPERTY
                              | VARIABLE
                              | PROMPT
                              ;

genesysFunction               : func=FUNCTION LPAREN genesysFunctionParams RPAREN
                              ;

genesysFunctionParams         : genesysFunctionParam (COMMA genesysFunctionParam)*
                              ;

genesysFunctionParam          : genesysFunction                                # FunctionParam
                              | STRING_LITERAL                                 # StringLiteral
                              | INTEGER_LITERAL                                # NumberLiteral
                              ;



