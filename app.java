package STALGCMMP;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        //get input
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try{
            Scanner txtFileReader = new Scanner(new File("./STALGCMMP/test.txt"), "UTF-8");            
            
            
            String rawStates = txtFileReader.nextLine();
            String rawInputAl = txtFileReader.nextLine();
            String firstMarker = txtFileReader.nextLine(); 
            String secondMarker = txtFileReader.nextLine();
            String rawStackAl = txtFileReader.nextLine();
            int numOfTransitions = txtFileReader.nextInt();

            txtFileReader.nextLine();
            ArrayList<Transition> transitions = new ArrayList<>();

            for(int i = 0; i < numOfTransitions; i++){
                String rawTransition = txtFileReader.nextLine();
                String[] transition = rawTransition.split(",");
    
                String currentState = transition[0];
                String inputSymbol = transition[1];
                String stackSymbolToPop = transition[2];
                String stackSymbolToPush = transition[3];
                String nextState = transition[4];
                transitions.add(new Transition(currentState, inputSymbol, stackSymbolToPop, stackSymbolToPush, nextState));
            }

            String initialState = txtFileReader.nextLine();
            String firstStackSymbol = txtFileReader.nextLine();
            String finalState = txtFileReader.nextLine();
            txtFileReader.close();

            String states[] = rawStates.split(",");
            String inputAlphabet[] = rawInputAl.split(",");
            String stackAlphabet[] = rawStackAl.split(",");

            PDA pda = new PDA(states, transitions, inputAlphabet, stackAlphabet, initialState, firstStackSymbol, finalState);
            
            pda.init("aabb");
            
            pda.nextStep();
            pda.nextStep();
            pda.nextStep();
            pda.nextStep();
            pda.nextStep();
            pda.nextStep();

        } catch (Exception e){
            System.out.println(e);
        }
        //get transitions
        // Transition t1 = new Transition("q0", 'Σ', 'Γ*', 'Γ*', 'q1');
    }
}
