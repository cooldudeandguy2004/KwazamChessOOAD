package main;

//Defines Command interface part of Command design pattern
public interface Command {
    /*Executes the command's predefined action.
    Implementations will contain the specific logic for operations like saving or loading a game.*/
    void execute();

}
