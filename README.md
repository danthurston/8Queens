## A backtracking solution to the classic 8 Queens problem. 

This Java application solves the 8 queens puzzle through a backtracking approach.
The 8 queens puzzle is the challenge of positioning 8 queens on an 8x8 chessboard so that no queens are threatened. That is, they are positioned so that none of the 8 queens share the same row, column, or diagonal.

This approach utilizes backtracking to find all possible positions for any selected square on the chessboard which the user can iterate through.

## Data Structures
A Stack consisting of JToggleButton ‘locations’ was used to store the queen positions being considered at any one time. A Stack is ideal as it operates a LIFO (Last-In, First-Out) structure which works perfectly with the backtracking algorithm. This is because when backtracking needs to occur, the most recently added queen can be removed from the top of the stack with minimal code. 

Once a stack is full of 8-positions, the stack is added to an ArrayList (stackList). This builds up a collection of all the stacks as ‘NextQueen’ runs. This is the ideal data structure for this task as it is dynamically resized, so does not waste memory and the stacks within can be grabbed from any point in the list.

## Algorithm
For this task a backtracking algorithm was used to find all 8 queens and add them to the Stack repeatedly until all solutions are found. This is a type of recursive algorithm that works by iterating through a problem and making a decision at each stage until it either succeeds or fails. If the algorithm reaches a dead-end then it works backwards until it finds a stage that it can make a different choice that may solve the problem.

## Future Work
To improve the time complexity of the program, a ’Branch and Bound’ algorithm could have been used. This differs from backtracking in that after it builds a partial solution, it figures out there is no solution as it will hit a dead end, whereas backtracking runs until it hits that dead end. Another algorithmic option was the greedy method which has a large speed advantage over the backtracking algorithm. However, it would only be able to find the first solution and not all the possible outcomes without some reengineering.
