# Laboratory Report of Parsing

#### 151250209 朱晨乾

#### Software Institute， Nanjing University





### Catalog

   	1. Motivation/Aim
     2. Content description
      3. ideas/Methods
      4. Assumption
      5. Related FA description
      6. Description of important Data Structure
      7. Description of core Algorithms
      8. Use cases on running
      9. Problems occurred and related solutions
      10. Feelings and comments





### 1. Purpose

​	Use parsing method to do parsing on input sentence and output the result according to the user-defined grammar, in order to understand the process of parsing better.

### 2. Content description

​	The program is writted by Java. It reads a text file, use the lexical analyzer to get token sequence, and then do parsing. It use LL(1) method for top-down analyzing, and output the production sequence.

​	Here is concrete grammar defined:

   	1. General assignment statement. For example, i = 1*(1+1);(It only allows + and * to lower the complexity)
     2. if-else sentence. if(condition){statement}else{statement}
      3. while sentence. while(condition){statement}
      4. condition sentence. i==1||(j==2||k==3)(it only allows || to lower the complexity)

### 3. Ideas/Methods

   	1. Define a grammar for parsing.
     2. Do pretreatment on the grammar(Eliminate left recursion and ambiguity).
      3. structure prediction analysis table.
      4. Write code based on analysis table.
      5. The implementation of the code: analyze according to the input queue and the first element of the state stack. Do the match of terminal or the production of subitems of non-terminal.

### 4. Assumption

​	All of the variables in the program are displayed as id, and all of the constants are displayed as num.

​	In order to avoid ambiguity, else appears after if all the time.

###5. Related parsing process description 

​	Here is the user-defined grammar:

  0.  S -> id=E; 

1. S -> if(C){S}else{S} 
2. S -> while(C){S} 
3. E -> TE' 
4. E' -> +TE' 
5. E' -> ε 
6. T -> FT' 
7. T' -> *FT' 
8. T' -> ε 
9. F -> (E) 
10. F -> num 
11. F -> id 
12. C -> DC' 
13. C' -> ||DC' 
14. C' -> ε 
15. D -> (C) 
16. D -> id==num 

   ​prediction analysis table

|      | id   | =    | ;    | if   | (    | )    | {    | }    |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S    | 0    |      |      | 1    |      |      |      |      |
| E    | 3    |      |      |      | 3    |      |      |      |
| E'   |      |      | 5    |      |      | 5    |      |      |
| T    | 6    |      |      |      | 6    |      |      |      |
| T'   |      |      | 8    |      |      | 8    |      |      |
| F    | 11   |      |      |      | 9    |      |      |      |
| C    | 12   |      |      |      | 12   |      |      |      |
| C'   |      |      |      |      |      | 14   |      |      |
| D    | 16   |      |      |      | 15   |      |      |      |

|      | else | while | +    | *    | num  | \|\| | ==   | $    |
| ---- | ---- | ----- | ---- | ---- | ---- | ---- | ---- | ---- |
| S    |      | 2     |      |      |      |      |      |      |
| E    |      |       |      |      | 3    |      |      |      |
| E'   |      |       | 4    |      |      |      |      | 5    |
| T    |      |       |      |      | 6    |      |      |      |
| T'   |      |       | 8    | 7    |      |      |      | 8    |
| F    |      |       |      |      | 10   |      |      |      |
| C    |      |       |      |      |      |      |      |      |
| C'   |      |       |      |      |      |      | 13   | 14   |
| D    |      |       |      |      |      |      |      |      |

### 6. Description of important Data Structure

![important ds1][important ds1]

[important ds1]: 重要的数据结构1.png

![important ds2][important ds2]

[important ds2]: 重要的数据结构2.png

![important ds3][important ds3]

[important ds3]: 重要的数据结构3.png





### 7. Description of Core Algorithms

​	Main methods are as follows:

​	parse()——parse the body of program

​	generate()——structure subitem by non-terminal and put it into stack

​	output()——output the result into console and the output file

​	Here is parse() method

![the method of parse()][the method of parse()]

[the method of parse()]: 核心算法1.png

​	Here is generate() method

![the method of generate()1][the method of generate()1]

[the method of generate()1]: 核心算法2.png

![the method of generate()2][the method of generate()2]

[the method of generate()2]: 核心算法3.png

![the method of generate()3][the method of generate()3]

[the method of generate()3]: 核心算法4.png



Comments: important data structure contains the input queue and the state stack. First, put the token sequence into the queue, put the first non-terminal into the stack. Read the first element of the queue and the stack and do analyzing. If the top element of the stack is non-terminal, then call the method generate(), structure sub item and put the sub item into stack. Else if it is a terminal, match with the element of the queue. If they match, pop them. Loop until there is no output element. If it is failed to check the table or doesn't match terminal, output error.

### 8. Use case on running

input.txt:

![input][input]

[input]: input.png

output.txt:

![output1][output1]

[output1]: output.png

![output2][output2]

[output2]: output2.png

![output3][output3]

[output3]: output3.png





###9. Problems occurred and related solutions 



### 10. Feelings and comments

