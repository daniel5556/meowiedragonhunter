package com.rs.game.player.content.custom;

import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.WorldTile;


public class PlayerPanel {

	public static void handleButtons(Player player, int componentId) {
		if (componentId == 2) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 4) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 6) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 8) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 10) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 12) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
		else if (componentId == 14) {
			player.getPackets().sendGameMessage("The power of Zaros compels you!",true);
			return;
		}
	}
}