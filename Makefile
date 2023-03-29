all:
	-mkdir Compiled
	-rm Compiled/* -rf
	javac Yahtzee/*.java Yahtzee/YahtzeeCore/*.java Yahtzee/YahtzeeGUIComponents/*.java Yahtzee/YahtzeeWindows/*.java  -d Compiled && java -classpath Compiled/ Yahtzee.Main
test:
	-mkdir Compiled
	-rm Compiled/* -rf
	javac Yahtzee/*.java Yahtzee/YahtzeeCore/*.java Yahtzee/YahtzeeGUIComponents/*.java Yahtzee/YahtzeeWindows/*.java  -d Compiled && java -classpath Compiled/ Yahtzee.Main $(t)
auto:
	-mkdir Compiled
	-rm Compiled/* -rf
	javac Yahtzee/*.java Yahtzee/YahtzeeCore/*.java Yahtzee/YahtzeeGUIComponents/*.java Yahtzee/YahtzeeWindows/*.java  -d Compiled && java -classpath Compiled/ Yahtzee.Main "test" $(t)

