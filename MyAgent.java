
package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;


public class MyAgent extends BasicMarioAIAgent implements Agent
{

	public MyAgent()
	{
		super("MyAgent");
		reset();
	}

	// Does (row, col) contain an enemy?   
	public boolean hasEnemy(int row, int col) {
		return enemies[row][col] != 0;
	}

	// Is (row, col) empty?   
	public boolean isEmpty(int row, int col) {
		return (levelScene[row][col] == 0);
	}

        /* Does(row, col) contain a coin? defined coins in agent.java
        public boolean hasCoin (int row, int col){
	    return coins[row][col] !=0;
	}
	*/ 
	/* Is (row, col)  a pit? defined pit in basic agent.java
        public boolean isPit (int row, int col){
	    return pits[row][col] !=0;
	}
	*/ 

	// Display Mario's view of the world
	public void printObservation() {
		System.out.println("**********OBSERVATIONS**************");
		for (int i = 0; i < mergedObservation.length; i++) {
			for (int j = 0; j < mergedObservation[0].length; j++) {
				if (i == mergedObservation.length / 2 && j == mergedObservation.length / 2) {
					System.out.print("M ");
				}
				else if (hasEnemy(i, j)) {
					System.out.print("E ");
				}
				else if (!isEmpty(i, j)) {
					System.out.print("B ");
				}
				/*
				else if (hasCoins(i,j)){
					System.out.print("C ");
					}*/
				/*
				else if (hasCoins(i,j)){
					System.out.print("P ");
					}*/
			}
			System.out.println();
		}
		System.out.println("************************");
	}

	// Actually perform an action by setting a slot in the action array to be true
	public boolean[] getAction()
	{
		action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
		action[Mario.KEY_RIGHT] = true;
		//jump when enemies on the right
		if (hasEnemy(10,9) &&isMarioAbleToJump){
		    action[Mario.KEY_JUMP]=true;
		}
		//move to the left when enemy is above
		action[Mario.KEY_LEFT]=hasEnemy(9,10);
		//stay still when enemy is below
		action[Mario.KEY_RIGHT]=!hasEnemy(9,8);
		//if there is a pit
		//printObservation();
		return action;
	}

	// Do the processing necessary to make decisions in getAction
	public void integrateObservation(Environment environment)
	{
		super.integrateObservation(environment);
    	levelScene = environment.getLevelSceneObservationZ(2);
	}

	// Clear out old actions by creating a new action array
	public void reset()
	{
		action = new boolean[Environment.numberOfKeys];
	}
}
