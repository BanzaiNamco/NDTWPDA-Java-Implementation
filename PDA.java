package STALGCMMP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PDA {
    
    // Variables to store TWPDA machine definition
    public String input;
    public ArrayList<Transition> transitions;
    public String firstStackSymbol;
    public String initialState;
    // public String finalState;
    public ArrayList<String> finalStates;
    public String startMarker;
    public String endMarker;

    public String tape;

    public Queue<Timeline> timelines = new LinkedList<>();

    public PDA( ArrayList<Transition> transitions, 
                String initialState,
                String firstStackSymbol, 
                // String finalState ,
                String[] finalStates,
                String startMarker,
                String endMarker) {
        {
        this.transitions = transitions;
        this.initialState = initialState;
        this.firstStackSymbol = firstStackSymbol;
        this.finalStates = new ArrayList<>(Arrays.asList(finalStates));
        this.startMarker = startMarker;
        this.endMarker = endMarker;
        }
    }

    public PDA( PDA pda) {
        transitions = pda.transitions;
        initialState = pda.initialState;
        firstStackSymbol = pda.firstStackSymbol;
        finalStates = pda.finalStates;
        startMarker = pda.startMarker;
        endMarker = pda.endMarker;
    }
    
    public void init(String input) {
        this.input = input;
        this.tape = startMarker + input + endMarker;
        ArrayList<String> stack = new ArrayList<>();
        stack.add(firstStackSymbol);

        // Create the original timeline 
        Timeline timeline = new Timeline(initialState, stack, 0);

        // Do a reboot of the timelines
        timelines.clear();

        // Add the original timeline to the timelines
        timelines.add(timeline);
    }

    // Return 0 if timelines still exist but none have been accepted
    // Return 1 if a timeline has been accepted
    // Return -1 if no timelines are left
    public int nextStep() {
        // System.out.println("\n------------------------------------\n");
        Queue<Timeline> newTimelines = new LinkedList<>();
        // For each active timeline
        while(!timelines.isEmpty()) {
            // Get the current timeline
            Timeline timeline = timelines.remove();
            
            // Get all possible transitions of current timeline
            ArrayList<Transition> possibleTransitions = getPossibleTransitions(timeline);
            
            // System.out.println("\nPossible Transitions: " + possibleTransitions.size());

            // if(timeline.head < input.length())
            //     System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + String.valueOf(tape.charAt(timeline.head)) + " " + timeline.stack);
            // else            
            //     System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + timeline.head + " " + timeline.stack);
            
            if(tape.charAt(timeline.head) == 'ε')
                timeline.head++;
            // System.out.println("Head: " + timeline.head);
            // System.out.println(tape);

            // For each possible transition
            for(Transition t : possibleTransitions) {
                // System.out.println("Transition: " + t.currentState + " " + t.inputSymbol + " " + t.popSymbol + " " + t.pushSymbol + " " + t.nextState + " " + t.direction);

                // Create a new timeline that implements the transition
                Timeline newTimeline = new Timeline(timeline, t, tape.length());
                // System.out.println("New Timeline: " + newTimeline.currState + " " + newTimeline.head + " " + newTimeline.stack);

                // Check if timeline is accepted (reached final state, stack is empty, and input has been fully read)
                // if(newTimeline.currState.equals(finalState) && newTimeline.stack.isEmpty() && (newTimeline.head == tape.length()-1)) {
                if(finalStates.contains(newTimeline.currState) && newTimeline.stack.isEmpty() && (newTimeline.head == tape.length()-1)) {
                    // Return 1 if input is accepted
                    // System.out.println(newTimeline.head);
                    // System.out.println("Input accepted");
                    timelines.clear();
                    timelines.add(newTimeline);
                    return 1;
                }

                //if the next state = current state, that implies that the transition is a loop and the machine should halt
                Boolean halt = timeline.head == newTimeline.head && timeline.stack.equals(newTimeline.stack) && timeline.currState.equals(newTimeline.currState);
                if(!halt){
                    // Add the new timeline to the temp variable
                    newTimelines.add(newTimeline);
                }

            }
        }
        
        if(newTimelines.size() == 0) {
            // If there are no timelines left, then the input is not accepted
            // System.out.println("Input not accepted");
            return -1;
        }

        // Add the new timelines to timelines
        timelines.addAll(newTimelines);

        // System.out.println("\nGo to next step");
        return 0;
    }

    private ArrayList<Transition> getPossibleTransitions(Timeline timeline) {
        //get all possible transitions from the current state
        ArrayList<Transition> possibleTransitions = new ArrayList<>();
        for(Transition t : transitions) {
           if(tape.charAt(timeline.head) == 'ε'){
                if(t.isTransition(  timeline.currState, 
                                    String.valueOf(tape.charAt(timeline.head)), 
                                    timeline.peekStack()) ||
                                    t.isTransition(  timeline.currState,
                                    String.valueOf(tape.charAt(timeline.head+1)),
                                    timeline.peekStack())){
                    possibleTransitions.add(t);
                }
            }else{
                if(t.isTransition(  timeline.currState, 
                                    String.valueOf(tape.charAt(timeline.head)), 
                                    timeline.peekStack())){
                    possibleTransitions.add(t);
                }
            }
        }
        return possibleTransitions;
    }
}
