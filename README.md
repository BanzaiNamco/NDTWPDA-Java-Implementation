# STALGCM-MP

Machine format is as follows: 

A,B,C   // States
a,b     // Input alphabet
$       // Start marker
@       // End marker
Z,a     // Stack symbols
4       // Number of transitions
A,a,ε,A,a,1    // Current state, input, pop symbol, destination state, push symbol, direction to move input head (1: right, -1: left, 0: stay)
A,ε,ε,A,ε,1    // USE ε for lambda (A,λ,λ) -> (A,λ,1) = (A,ε,ε) -> (A,ε,1)
B,b,a,A,ε,1    // (B,b,a) -> (A,λ,1)
B,@,Z,C,ε,0    // (B,@,Z) -> (C,λ,0)
A    // Initial state
Z    // Initial stack symbol
C,A    // Final states

How to run the machine:
1. Edit machine.txt with the appropriate NDTWPDA of your choice
2. Make sure that machine.txt is in the same directory as the jar file
3. Run the app.jar file
4. Type down your input in the input field on the top center of the program
5. Click the "set input" button
6. Click either the "auto" button or the "next" button
7. If you clicked the "auto" button, the machine will run until it halts or until the input has been accepted/rejected
8. If you clicked the "next" button, the machine will run one step at a time
9. The machine will be colored red or green depending on whether the input has been accepted or rejected

Notes to remember when writing the machine:
1. The program does not check for errors in the machine, so make sure that the machine is correct
2. The program will automatically add the start and end markers to the input string, so do not include them in the input string
3. As stated in the paper, the machine starts with its head at the start marker so make sure to include a transition for that