package STALGCMMP;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PDA {
    
    // Variables to store TWPDA machine definition
    public String input;
    public String states[];
    public ArrayList<Transition> transitions;
    public String inputAlphabet[];
    public String stackAlphabet[];
    public String initialState;
    public String firstStackSymbol;
    // public ArrayList<String> finalStates;
    public String finalState;
    
    // Variables we need to keep track of during execution
    // public int head;
    // private String currentState;
    // public Stack<String> stack;
    Queue<Timeline> timelines = new LinkedList<>();

    public PDA( String states[],
                ArrayList<Transition> transitions, 
                String inputAlphabet[], 
                String stackAlphabet[], 
                String initialState,
                String firstStackSymbol, 
                String finalState )
            //  ArrayList<String> finalStates)
        {
        // head = 0;
        this.states = states;
        this.transitions = transitions;
        this.inputAlphabet = inputAlphabet;
        this.stackAlphabet = stackAlphabet;
        this.initialState = initialState;
        this.firstStackSymbol = firstStackSymbol;
        this.finalState = finalState;
    }
    
    public void init(String input) {
        this.input = input;
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
        System.out.println("\n------------------------------------\n");
        Queue<Timeline> newTimelines = new LinkedList<>();
       
        // For each active timeline
        while(!timelines.isEmpty()) {
            // Get the current timeline
            Timeline timeline = timelines.remove();
            
            // Get all possible transitions of current timeline
            ArrayList<Transition> possibleTransitions = getPossibleTransitions(timeline);
            
            System.out.println("Possible Transitions: " + possibleTransitions.size());

            if(timeline.head < input.length())
                System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + String.valueOf(input.charAt(timeline.head)) + " " + timeline.stack);
            else            
                System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + timeline.head + " " + timeline.stack);
            
            System.out.println("Head: " + timeline.head);
            System.out.println(input);

            // For each possible transition
            for(Transition t : possibleTransitions) {
                System.out.println("Transition: " + t.currentState + " " + t.inputSymbol + " " + t.popSymbol + " " + t.pushSymbol + " " + t.nextState);

                // Create a new timeline that implements the transition
                Timeline newTimeline = new Timeline(timeline, t);
                System.out.println("New Timeline: " + newTimeline.currState + " " + newTimeline.head + " " + newTimeline.stack);

                // Check if timeline is accepted (reached final state, stack is empty, and input has been fully read)
                if(newTimeline.currState.equals(finalState) && newTimeline.stack.isEmpty() && (newTimeline.head == input.length())) {
                    // Return 1 if input is accepted
                    System.out.println("Input accepted");
                    return 1;
                }

                // Add the new timeline to the temp variable
                newTimelines.add(newTimeline);
            }
        }
        
        if(newTimelines.size() == 0) {
            // If there are no timelines left, then the input is not accepted
            System.out.println("Input not accepted");
            return -1;
        }

        // Add the new timelines to timelines
        timelines.addAll(newTimelines);

        System.out.println("\nGo to next step");
        return 0;
    }

    private ArrayList<Transition> getPossibleTransitions(Timeline timeline) {
        //get all possible transitions from the current state
        ArrayList<Transition> possibleTransitions = new ArrayList<>();
        for(Transition t : transitions) {
            if(timeline.head == input.length()){
                if(t.isTransition(  timeline.currState, 
                                    "ε", 
                                    timeline.peekStack())){
                    possibleTransitions.add(t);
                }
            } else {
                if(t.isTransition(  timeline.currState, 
                                    String.valueOf(input.charAt(timeline.head)), 
                                    timeline.peekStack())){
                    possibleTransitions.add(t);
                }
            }
        }
        
        return possibleTransitions;
    }
}
