package STALGCMMP;

public class Transition {
    
    //state transitions for PDA
    public final String currentState;
    public final String inputSymbol;
    public final String popSymbol;
    public final String pushSymbol;
    public final String nextState;
    public final String direction;

    //(Q, Σ, Γ, δ, q0, Z0, F)
    //δ = (Q, Σ, Γ*) -> P(Q, Γ*)

    public Transition(  String currentState, 
                        String inputSymbol, 
                        String stackSymbolToPop, 
                        String stackSymbolToPush, 
                        String nextState,
                        String direction ) {
        this.currentState = currentState;
        this.nextState = nextState;
        this.inputSymbol = inputSymbol;
        this.popSymbol = stackSymbolToPop;
        this.pushSymbol = stackSymbolToPush;
        this.direction = direction;
    }

    public Boolean isTransition(String currentState, String inputSymbol, String topOftheStack) {
        Boolean correctState = this.currentState.equals(currentState);
        Boolean correctInput = this.inputSymbol.equals(inputSymbol) || this.inputSymbol.equals("ε");
        Boolean correctPop = this.popSymbol.equals(topOftheStack) || this.popSymbol.equals("ε");
        if(!topOftheStack.equals("ε"))
            return correctState && correctInput && correctPop;
        return false;    
    }
}