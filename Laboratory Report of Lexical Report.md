# Laboratory Report of Lexical Analyzer 

#### 151250209 朱晨乾

#### Software Institute，Nanjing University







### Catalog

1. Purpose
2. Content description
3. Method
4. Hypothesis
5. Description of finite automata
6. Key data structures
7. Kernel algorithms
8. Screenshots
9. Problems and feelings








### 1. Purpose

​	Writing a program of lexical analyzer. We can do lexical analyzing on sentences so that we can understand the process of lexical analyzing better.

### 2. Content description

​	The program is writted by Java. It reads a text file and does lexical analyzing on the content of the file. The program generally realizes the lexical parsing on Java programs. It can recognize reserved words, operators, boundary characters, annotations, variable names(the priority of reserved words is higher than variable names). The output format is the sequence of token which looks like <token-name code, word symbols>, and the program can do exception handling on unrecognized character, integer variables which is overflowing and oversized files.

### 3. Method

 	1. Write regular expressions for the word symbols needed to be recognized.
	2. Constructs the NFA corresponding to regular expressions.
	3. Merge all NFA and simplify it into DFA.
	4. Write programs based on DFA.
	5. The specific implementation of the code: read an input character first, determine its possible categories, and then read the next . If it has been identified, it will be  added to the output linklist. The redundant characters need to be returned, otherwise the next character will be read.

### 4. Hypothesis

​	Assume that the input Java program is right, which means it contains valid reserved words and operators.

###5. Description of finite automata

![Description of finite automata][Description of finite automata]

[Description of finite automata]: C:\Users\朱晨乾\IdeaProjects\Compilers\有限自动机描述.jpg

### 6. Key data structures

![Key data structure 1][Key data structure 1]

[Key data structure 1]: C:\Users\朱晨乾\IdeaProjects\Compilers\重要数据结构1.jpg

![Key data structure 2][Key data structure 2]

[Key data structure 2]: C:\Users\朱晨乾\IdeaProjects\Compilers\重要数据结构2.jpg

![Key data structure 3][Key data structure 3]

[Key data structure 3]: C:\Users\朱晨乾\IdeaProjects\Compilers\重要数据结构3.jpg



![Key data structures 4][Key data structure 4]

[Key data structure 4]: C:\Users\朱晨乾\IdeaProjects\Compilers\重要数据结构4.jpg

### 7.  Kernel algorithms

​	Main Method of the program:

​	getInput()——read text file.

![getInput][getInput]

[getInput]: C:\Users\朱晨乾\IdeaProjects\Compilers\getInput.jpg

​	output()——output the result to the console and output file.

![output][output]

[output]: C:\Users\朱晨乾\IdeaProjects\Compilers\output.jpg

​	scanner()——scan input for analysis. This method includes the kernel algorithm.

![scanner][scanner]

[scanner]: C:\Users\朱晨乾\IdeaProjects\Compilers\scanner.jpg

​	The Scanner () method contains the kernel algorithm for lexical analysis, and only one word symbol can be recognized at a time, so the program analyzes the entire input by calling the scanner () method repeatedly.
​	By reading the first character type, the possible type of the next word symbol is predicted. When reading English characters, they may be reserved or variable name (type A); when reading numbers,  they are constants (positive) (type B); when reading other characters (type C), may be operators or boundary characters or annotations; if it is a ' - ' character, and the word behind is number, then they form a negative number. In addition, it is a newline character or undefined character.
​	Type A: Continue reading. When reading one character every time, judge whether it belongs to a reserved word. If it is, then output it directly (because the priority of reserved word is higher than the variable name). Otherwise, read until it is not an English character. At this point, it is necessary to return to the previous one and output the variable name.
​	Type B: Read until the number is not the same, also need to return one, and output the positive number.
​	Type C: if a single character can determine the type, output it directly; or continue to read the next one until it can determine the type; if a number is behind the minus, read it according to type B and output the negative number finally. Similarly, the redundant characters need to be returned.

​	Here is the corresponding table of token-name code and word symbols: 

| word sympols | token-name code | word sympols           | token-name code |
| ------------ | --------------- | ---------------------- | --------------- |
| class        | 1               | =                      | 30              |
| public       | 2               | ==                     | 31              |
| protected    | 3               | &                      | 32              |
| private      | 4               | &&                     | 33              |
| void         | 5               | \|                     | 34              |
| static       | 6               | \|\|                   | 35              |
| int          | 7               | !                      | 36              |
| char         | 8               | !=                     | 37              |
| float        | 9               | <                      | 38              |
| double       | 10              | <=                     | 39              |
| String       | 11              | >                      | 40              |
| if           | 12              | >=                     | 41              |
| else         | 13              | //                     | 42              |
| do           | 14              | /*                     | 43              |
| while        | 15              | */                     | 44              |
| try          | 16              | (                      | 45              |
| catch        | 17              | )                      | 46              |
| switch       | 18              | [                      | 47              |
| case         | 19              | ]                      | 48              |
| for          | 20              | {                      | 49              |
|              | 21              | }                      | 50              |
| +            | 22              | ,                      | 51              |
| +=           | 23              | :                      | 52              |
| -            | 24              | ;                      | 53              |
| -=           | 25              | '                      | 54              |
| *            | 26              | "                      | 55              |
| *=           | 27              | letter(letter\|digit)* | 56              |
| /            | 28              | digitdigit             | 57              |
| /=           | 29              |                        |                 |

### 8. Screenshots

input.txt

![input.txt][inputtxt]

[inputtxt]: C:\Users\朱晨乾\IdeaProjects\Compilers\inputtxt.jpg

output.txt

![output.txt][outputtxt1]



[outputtxt1]: C:\Users\朱晨乾\IdeaProjects\Compilers\outputtxt1.jpg

![output.txt][outputtxt2]

[outputtxt2]: C:\Users\朱晨乾\IdeaProjects\Compilers\outputtxt2.jpg

![output.txt][outputtxt3]

[outputtxt3]: C:\Users\朱晨乾\IdeaProjects\Compilers\outputtxt3.jpg

### 9. Problems and feelings

​	Thinking about how to design of corresponding table of token-name code and word symbols wastes a lot of time. In addition, there are many reserved words which I didn't add to data structures. As a result of this, many programs cannot be analyzed by this lexical analyzer.

​	What's more, I think the quality of my code is not good. When I rewrite some codes in order to improve quality, faults will be found when the program is running. And code redundancy appears.

​	There is a long way to design a satisfying lexical analyzer.