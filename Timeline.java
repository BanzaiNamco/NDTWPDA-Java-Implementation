package STALGCMMP;

import java.util.ArrayList;

public class Timeline {

    public int head;
    public String currState;
    public ArrayList<String> stack;

    public Timeline(String currState, ArrayList<String> currStack, int currHead) {
        this.currState = currState;
        this.stack = currStack;
        this.head = currHead;
    }

    public Timeline(Timeline timeline) {
        this.currState = timeline.currState;
        this.stack = timeline.stack;
        this.head = timeline.head;
    }

    public Timeline(Timeline timeline, Transition t) {
        this.currState = timeline.currState;
        this.stack = new ArrayList<>();
        for(String s : timeline.stack)
            this.stack.add(s);
        this.head = timeline.head;

        nextStep(t);
    }

    public void nextStep(Transition t) {
        this.currState = t.nextState;
        if(!t.popSymbol.equals("ε"))
            this.pop();
        
        if(!t.pushSymbol.equals("ε"))
            this.stack.add(t.pushSymbol);
        
        if(!t.inputSymbol.equals("ε"))
            this.head++;
    }

    public String peekStack(){
        if(this.stack.size() == 0)
            return "ε";
        return this.stack.get(this.stack.size() - 1);
    }

    public String pop(){
        return this.stack.remove(this.stack.size() - 1);
    }
}
