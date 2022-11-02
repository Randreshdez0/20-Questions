
/**
 *  3/11/22
 *	CS 145
 *	Lab 6: 20 Questions
 * 	Ricardo Hernandez, Addison Gez, Isaac Orloff
 * 
 * 	This program plays a yes/no guessing game called "20 Questions." 
 *  Each round the computer attempts to get to the object the user 
 *  is thinking of with different questions. Eventually the computer 
 *  will come to its own conclusion and attempt to guess the user's object.
 */
import java.util.*;
import java.io.*;

public class QuestionTree {
    private QuestionNode root;
    private UserInterface ui;
    private int totalGames;
    private int gamesWon;

    /**
     * Initializes a new Question Tree.
     * @param ui
     */
    public QuestionTree(UserInterface ui) {
        // check if null
        if (ui == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        this.ui = ui;
        this.root = new QuestionNode("computer");
        this.totalGames = 0;
        this.gamesWon = 0;
    }
    /**
     * Plays one complete guessing game with the user,
     * asking yes/no question until reaching an answer object to guess
     */
    public void play() {
        totalGames++;
        root = play(root);
    }

    /**
     * Takes QuestionNode then plays game and returns new QuestionNode
     * @param 	root
     * @return  new QuestionNode
     */
    private QuestionNode play(QuestionNode root) {
        String response;
        if (root.left != null && root.right != null) {
            ui.println(root.data);
            // question
            response = ui.nextLine();
            if (response.equals("yes") || response.equals("y")) {
                root.left = play(root.left);
            } else if (response.equals("no") || response.equals("n")) {
                root.right = play(root.right);
            }
        } else {
            ui.println("Would your object happen to be " + root.data + "?");
            response = ui.nextLine();
            if (response.equals("yes") || response.equals("y")) {
                ui.println("I win!");
                gamesWon++;
            } else if (response.equals("no") || response.equals("n")) {
                ui.println("I lose. What is your object?");
                String newObjectText = ui.nextLine();
                ui.println("Type a yes/no question to distinguish your item " + "from " + root.data + ":");
                String questionText = ui.nextLine();
                ui.println("And what is the answer for your object?");
                boolean answerText = ui.nextBoolean();
                QuestionNode newQuestion = new
                QuestionNode(questionText);
                QuestionNode newAnswer = new
                QuestionNode(newObjectText);
                QuestionNode oldRoot = root;
                // yes response
                if (answerText) {
                    newQuestion.left = newAnswer;
                    newQuestion.right = oldRoot;
                }
                // no response
                else {
                    newQuestion.left = oldRoot;
                    newQuestion.right = newAnswer;
                }
                root = newQuestion;
            }
        }
        return root;
    }

    /**
     * Stores the current tree state to an output file represented by the given PrintStream
     * @param output
     */
    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        save(root, output);
    }
    /**
     * Stores the current tree state to an output file represented by the given PrintStream
     * @param root
     * @param output
     */
    private void save(QuestionNode root, PrintStream output) {
        if (root.left == null && root.right == null) {
            output.println("A:" + root.data);
        } else {
            output.println("Q:" + root.data);
            save(root.left, output);
            save(root.right, output);
        }
    }
    /**
     * Replaces the current tree by reading another tree from a file.
     * @param input
     */
    public void load(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        this.root = load(input, input.nextLine());
    }
    /**
     * Replaces the current tree by reading another tree from a file.
     * @param input
     * @param dataIn
     * @return QuestionNode
     */
    public QuestionNode load(Scanner input, String dataIn) {
        String[] splitData = dataIn.split(":", 2);
        if (splitData[0].equals("A")) {
            return new QuestionNode(splitData[1]);
        } else {
            return new QuestionNode(splitData[1], load(input, input.nextLine()),
                load(input, input.nextLine()));
        }
    }
    /**
     * Returns the total games during the current execution of the program
     * @return totalGames
     */
    public int totalGames() {

        return totalGames;
    }
    /**
     * Returns the total games won by the computer during 
     * the current execution of the program
     * @return gamesWon
     */
    public int gamesWon() {

        return gamesWon;
    }

}