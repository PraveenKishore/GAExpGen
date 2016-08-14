# GAExpGen
Implementation of Genetic Algorithm to produce Math Expressions that results in a given number

**Programming Language:** Java

###Example:
If TARGET number is 100, <br>
Some possible expressions that result in 100 are: <br>
10 * 30 + 10 <br>
5 * 10 + 2 * 26 - 2 <br>

The program generates such math expressions using genetic algorithm.

###Classes:
* **Chromosome:** Represents a candidate/individual
* **Population:** Used for managing the population of Chromosomes
* **Main:** The Main class for driving the program
* **MathInterpreter:** For evaluating the math expressions

###Selection Algorithm Used: 
[Roulette Wheel selection](https://en.wikipedia.org/wiki/Fitness_proportionate_selection) is used in the program. Although any selection algorithm can be choosen.
