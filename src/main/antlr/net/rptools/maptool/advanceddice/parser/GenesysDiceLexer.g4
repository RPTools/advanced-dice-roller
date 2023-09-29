lexer grammar GenesysDiceLexer;



// Proficiency Dice
PROFICIENCY_DICE                 : PROFICIENCY_DICE_NAMES | YELLOW_DICE_NAMES;

// Ability Dice
ABILITY_DICE                     : ABILITY_DICE_NAMES | GREEN_DICE_NAMES;

// Boost Dice
BOOST_DICE                       : BOOST_DICE_NAMES | BLUE_DICE_NAMES;

// Setback Dice
SETBACK_DICE                     : SETBACK_DICE_NAMES | BLACK_DICE_NAMES;

// Challenge Dice
CHALLENGE_DICE                   : CHALLENGE_DICE_NAMES | RED_DICE_NAMES;

// Difficulty Dice
DIFFICULTY_DICE                  : DIFFICULTY_DICE_NAMES | PURPLE_DICE_NAMES;

// Force Dice
FORCE_DICE                       : FORCE_DICE_NAMES | WHITE_DICE_NAMES;

// Success
SUCCESS                          : 'suc' | 'success' | '*';

// Advantage
ADVANTAGE                        : 'adv' | 'advantage' | 'v';

// Triumph
TRIUMPH                          : 'tri' | 'triumph' | '!';

// Failure
FAILURE                          : 'fail' | 'failure' | '-';

// Threat
THREAT                           : 'thr' | 'thrt' | 'threat' | 't';

// Despair
DESPAIR                          : 'des' | 'despair' | '$';

// Light
LIGHT                            : 'light' | 'l' | 'lightforce' | 'lf' | 'lightpip' | 'ls';

// Dark
DARK                             : 'dark' | 'n' | 'darkforce' | 'df' | 'darkpip' | 'ds';





INTEGER_LITERAL                  : Digit+;

STRING_LITERAL                   : ( '\'' (~['] | EscapeSequence)* '\''  )
                                 | ( '"'  (~["] | EscapeSequence)* '"'   );

WS                               : [ \t\r\n\u000C]+ -> channel(HIDDEN);

// Misc
COMMA                            : ',';

LPAREN                           : '(';
RPAREN                           : ')';
GROUP_NAME                       : IDENTIFIER ':';
FUNCTION                         : '#' IDENTIFIER;


// Variables
PROPERTY                         : LBRACE '@' (~[}])+ RBRACE;
PROMPT                           : LBRACE '?' (~[}])+ RBRACE;
VARIABLE                         : LBRACE (~[?@]) (~[}])* RBRACE;


// Dice Types
fragment PROFICIENCY_DICE_NAMES   : 'pro' | 'prof' | 'proficiency';
fragment ABILITY_DICE_NAMES       : 'a' | 'ab' | 'abil' | 'ability';
fragment BOOST_DICE_NAMES         : 'boo' | 'bst' | 'boost';
fragment SETBACK_DICE_NAMES       : 'set' | 's' | 'sb' | 'setback';
fragment CHALLENGE_DICE_NAMES     : 'c' | 'ch' | 'challenge';
fragment DIFFICULTY_DICE_NAMES    : 'd' | 'diff' | 'difficulty';
fragment FORCE_DICE_NAMES         : 'f' | 'force';

// Dice Colors
fragment YELLOW_DICE_NAMES        : 'y' | 'yellow';
fragment GREEN_DICE_NAMES         : 'g' | 'green';
fragment BLUE_DICE_NAMES          : 'b' | 'blue';
fragment BLACK_DICE_NAMES         : 'blk' | 'k' | 'black';
fragment RED_DICE_NAMES           : 'r' | 'red';
fragment PURPLE_DICE_NAMES        : 'p' | 'purple';
fragment WHITE_DICE_NAMES         : 'w' | 'white';




fragment LBRACE                   : '{';
fragment RBRACE                   : '}';



fragment EscapeSequence          : '\\' [btnfr"'\\] ;





fragment IDENTIFIER              : Letter (Letter | Digit)*;

fragment Digit                   : '0'..'9';
fragment Letter                  : [a-zA-Z_] // Java letters below 0x7F
                                 | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above // 0x7F which are not a surrogate
                                 | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs // encodings for U+10000 to U+10FFFF
                                 ;