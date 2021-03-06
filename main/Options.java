/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Erik
 */
public class Options {
    public static boolean gameOver = false;
    public static boolean animateBackground = false;
    public static boolean playMusic = false;
    public static boolean playSounds = false;
    public static float score = 0;
    public static String username = System.getProperty("user.name").replaceAll(" ", "").replaceAll("=", "").replaceAll(":", "").replaceAll(";", "");
    public static String WelcomeBox = 
            "(press SPACE to continue)\n"
            + "(press CTRL to skip)\n\n"
            + "Okay Commander, can you here me?\n"
            + "Houston is speaking. Just to re-check your mission goals: "
            + "Some may say, that you are the last hope of mankind. "
            + "The rising temperature forces us to leave our lovely home called Earth, "
            + "and to search for new shelters.\n"
            + "Scientists agreed, that in sector Omega-13QD, "
            + "where you will arrive in a few moments, "
            + "a habitable planet could exist. "
            + "It's your mission to find it.\n"
            + "But we have a tiny problem:\n"
            + "Our monitors here say, that you will have to pass a giant asteroid field, "
            + "and the autopilot systems stopped responding 2 hours ago. Seems like you have to navigate manually.\n\n"
            + "Just in case you forgot:\n"
            + "Press and hold SPACE to rise your spaceship\n"
            + "Press CTRL to fly around obstacles\n"
            + "Maybe you can even find some useful things over there.\n\n"
            + "Good luck Commander, we're counting on you!\n"
            + "Housten, Over!";
}
