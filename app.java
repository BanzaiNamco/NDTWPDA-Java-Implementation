package STALGCMMP;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import STALGCMMP.GUI.Controller;

public class app {
    public static void main(String[] args) {

        // Read machine definition file
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            Scanner txtFileReader = new Scanner(new File("./STALGCMMP/csg.txt"), "UTF-8");
            
            String rawStates = txtFileReader.nextLine();
            String rawInputAl = txtFileReader.nextLine();
            String firstMarker = txtFileReader.nextLine(); 
            String secondMarker = txtFileReader.nextLine();
            String rawStackAl = txtFileReader.nextLine();
            int numOfTransitions = txtFileReader.nextInt();

            txtFileReader.nextLine();
            ArrayList<Transition> transitions = new ArrayList<>();

            for(int i = 0; i < numOfTransitions; i++) {
                String rawTransition = txtFileReader.nextLine();
                String[] transition = rawTransition.split(",");
    
                String currentState = transition[0];
                String inputSymbol = transition[1];
                String stackSymbolToPop = transition[2];
                String stackSymbolToPush = transition[3];
                String nextState = transition[4];
                String direction = transition[5];
                transitions.add(new Transition(currentState, inputSymbol, stackSymbolToPop, stackSymbolToPush, nextState, direction));
            }

            String initialState = txtFileReader.nextLine();
            String firstStackSymbol = txtFileReader.nextLine();
            String rawFinalStates = txtFileReader.nextLine();
            txtFileReader.close();

            String finalStates[] = rawFinalStates.split(",");
            String states[] = rawStates.split(",");
            String inputAlphabet[] = rawInputAl.split(",");
            String stackAlphabet[] = rawStackAl.split(",");

            PDA pda = new PDA(transitions, initialState, firstStackSymbol, finalStates, firstMarker, secondMarker);
            
            Controller controller = new Controller(pda);

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
