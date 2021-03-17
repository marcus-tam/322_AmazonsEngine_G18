
package ubc.cosc322;

import java.util.ArrayList;
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
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test("asssrgssss[0]", "argssss[1]");
    	
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
	List<Room> rooms = getGameClient().getRoomList();
	for (Room room : rooms){
		System.out.println(room);
	}
	System.out.println("Client Logged In");
	//	System.out.println(getGameClient().getRoomList());
//    getGameClient().joinRoom("Okanagan Lake");
	//System.out.println(userName);
	userName = gameClient.getUserName();
	
	if(gamegui != null) {
	gamegui.setRoomInformation(gameClient.getRoomList());
	}


    }


    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document.
    	
//		if(messageType.equals("cosc322.game-state.board")){
//			this.getGameGUI().setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
//		}

//		System.out.println(msgDetails); 
//		System.out.println((ArrayList<Integer>) msgDetails.get("game-state"));

//		if(messageType.equals(GameMessage.GAME_STATE_BOARD)){
//			
//		}
		if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
			System.out.println("GAME_ACTION_MOVE");
			this.getGameGUI().setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
			System.out.println("_________ END ___________");
			
		}
		if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			System.out.println("GAME_ACTION_START");
//	    	gameClient.sendTextMessage("HEHEHEHEHE");
			/*
			 * Extract player colour
			 */
			if (msgDetails.get("player-white").equals(gameClient.getUserName())){
				this.colour = 1;
				System.out.println(gameClient.getUserName() + " plays as White");
				ArrayList<Integer> queenPosCurrent = new ArrayList<>();
				queenPosCurrent.add(4);
				queenPosCurrent.add(10);
				ArrayList<Integer> queenPosNew = new ArrayList<>();
				queenPosNew.add(4);
				queenPosNew.add(2);
				ArrayList<Integer> arrowPos = new ArrayList<>();
				arrowPos.add(4);
				arrowPos.add(4);

				this.getGameClient().sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
				this.getGameGUI().setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
			}else {
				this.colour = 0;
				System.out.println(gameClient.getUserName() + " plays as Black");
				
				ArrayList<Integer> queenPosCurrent = new ArrayList<>();
				queenPosCurrent.add(7);
				queenPosCurrent.add(10);
				ArrayList<Integer> queenPosNew = new ArrayList<>();
				queenPosNew.add(7);
				queenPosNew.add(2);
				ArrayList<Integer> arrowPos = new ArrayList<>();
				arrowPos.add(7);
				arrowPos.add(4);
				System.out.println("BEGIN MOVE");
				this.getGameClient().sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
				System.out.println("FINISH MOVE");
				this.getGameGUI().updateGameState(queenPosCurrent, queenPosNew, arrowPos);

			}
			
			this.getGameGUI().updateGameState(msgDetails);
			System.out.println("_________ END ___________");

		}
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			System.out.println("GAME_STATE_BOARD");
			System.out.println(msgDetails);
			this.getGameGUI().setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
			System.out.println("_________ END ___________");
		}
		if(messageType.equals(GameMessage.GAME_STATE_JOIN)) {
			System.out.println("GAME_STATE_JOIN");
			this.getGameGUI().updateGameState(msgDetails);
			System.out.println("_________ END ___________");
		}
		if(messageType.equals(GameMessage.GAME_STATE_PLAYER_LOST)) {
			System.out.println("GAME_STATE_PLAYER_LOST");
			this.getGameGUI().updateGameState(msgDetails);
			System.out.println("_________ END ___________");
		}
		if(messageType.equals(GameMessage.GAME_TEXT_MESSAGE)) {
			System.out.println("GAME_TEXT_MESSAGE");
			this.getGameGUI().updateGameState(msgDetails);
			System.out.println("_________ END ___________");
		}
		if(messageType.equals(AmazonsGameMessage.ARROW_POS)) {
			System.out.println("ARROW_POS");
			System.out.println(msgDetails);
			this.getGameGUI().updateGameState(msgDetails);
			System.out.println("_________ END ___________");
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
