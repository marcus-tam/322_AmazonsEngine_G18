
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;

    private int colour = 2; //0 = black 1 = white
    
    // 0 = empty 1 = white queen 2 = black queen 3 = arrow
    private ArrayList<Integer> gameBoard;
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;

    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	userName = gameClient.getUserName();
    	
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}

    	//int[] test = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0};
    	//System.out.println(test.length);
    }


    @SuppressWarnings("unchecked")
	@Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {

		if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
			System.out.println("------------- GAME_ACTION_MOVE -------------");
			
			ArrayList<Integer> queenPosCurrent = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			ArrayList<Integer> queenPosNew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
			ArrayList<Integer> arrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
			
			System.out.println(queenPosCurrent);
			System.out.println(queenPosNew);
			System.out.println(arrowPos);
			
			gamegui.updateGameState(queenPosCurrent, queenPosNew, arrowPos);
			
			System.out.println("-----------------------------------------");
			
		}
		
		if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			System.out.println("------------- GAME_ACTION_START -------------");

			if (msgDetails.get("player-white").equals(gameClient.getUserName())){
				System.out.println("Playing as White");
				
				ArrayList<Integer> queenPosCurrent = new ArrayList<>();
				queenPosCurrent.add(4);
				queenPosCurrent.add(10);
				ArrayList<Integer> queenPosNew = new ArrayList<>();
				queenPosNew.add(4);
				queenPosNew.add(2);
				ArrayList<Integer> arrowPos = new ArrayList<>();
				arrowPos.add(4);
				arrowPos.add(4);
				
				gameClient.sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
				
				gamegui.updateGameState(queenPosCurrent, queenPosNew, arrowPos);
			}else {
				System.out.println("Playing as Black");
			}
			
			System.out.println("-----------------------------------------");

		}
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			System.out.println("------------- GAME_STATE_BOARD -------------");
			gameBoard = (ArrayList<Integer>)msgDetails.get("game-state");
			
			gamegui.setGameState(gameBoard);
			System.out.println("-----------------------------------------");
		}
		
    	return true;
    }
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
//		return null;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return this.gamegui;
	}
                  
	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}
	
	public void joinGameRoom() {
		
	}
 
}//end of class
