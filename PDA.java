package STALGCMMP;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PDA {
    
    //this section is for the PDA machine definition
    public String input;
    public String states[];
    public ArrayList<Transition> transitions;
    public String inputAlphabet[];
    public String stackAlphabet[];
    public String initialState;
    public String firstStackSymbol;
    // public ArrayList<String> finalStates;
    public String finalState;
    
    //stuff we need to keep track of during execution
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
                String finalState)
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
    
    public void init(String input){
        this.input = input;
        ArrayList<String> stack = new ArrayList<>();
        stack.add(firstStackSymbol);

        //create the original timeline 
        Timeline timeline = new Timeline(initialState, stack, 0);

        //do a reboot of the timelines
        timelines.clear();

        //add the original timeline to the timelines
        timelines.add(timeline);

    }

    //return 0 if timelines still exist but none have been accepted
    //return 1 if a timeline has been accepted
    //return -1 if no timelines exist
    public int nextStep(){
        System.out.println("\n------------------------------------\n");
        Queue<Timeline> newTimelines = new LinkedList<>();
       
        //for each active timeline
        while(!timelines.isEmpty()){
            //get the current timeline
            Timeline timeline = timelines.remove();
            
            //get all possible transitions
            ArrayList<Transition> possibleTransitions = getPossibleTransitions(timeline);
            
            System.out.println("Possible Transitions: " + possibleTransitions.size());
            if(timeline.head < input.length())
                System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + String.valueOf(input.charAt(timeline.head)) + " " + timeline.stack);
            else            
                System.out.println("\n\nCurrent Timeline: " + timeline.currState + " " + timeline.head + " " + timeline.stack);
            System.out.println("Head: " + timeline.head);
            System.out.println(input);

            //for each possible transition
            for(Transition t : possibleTransitions){
                System.out.println("Transition: " + t.currentState + " " + t.inputSymbol + " " + t.popSymbol + " " + t.pushSymbol + " " + t.nextState);
                //create a new timeline that implements the transition
                Timeline newTimeline = new Timeline(timeline, t);
                System.out.println("New Timeline: " + newTimeline.currState + " " + newTimeline.head + " " + newTimeline.stack);
                //check if timeline has reached perfection
                if(newTimeline.currState.equals(finalState) &&
                    newTimeline.head == input.length() &&
                    newTimeline.stack.isEmpty()){
                        //if it has, then the input is accepted
                        System.out.println("Input accepted");
                        return 1;
                    }
                //add the new timeline to the temp variable
                newTimelines.add(newTimeline);
            }
        }
        
        if(newTimelines.size() == 0){
            //if there are no timelines left, then the input is not accepted
            System.out.println("Input not accepted");
            return -1;
        }

        
        //add the new timelines to the timelines
        timelines.addAll(newTimelines);
        // System.out.println("\n\ntimelines:");
        // for(Timeline t : timelines){
        //     System.out.println(t.currState + " " + t.head + " " + t.stack);
        // }
        System.out.println("\nGo to next step");
        return 0;
    }

    private ArrayList<Transition> getPossibleTransitions(Timeline timeline){
        //get all possible transitions from the current state
        ArrayList<Transition> possibleTransitions = new ArrayList<>();
        for(Transition t : transitions){
            if(timeline.head == input.length()){
                if(t.isTransition(  timeline.currState, 
                                    "ε", 
                                    timeline.peekStack())){
                    possibleTransitions.add(t);
                }
            }
            else{
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
