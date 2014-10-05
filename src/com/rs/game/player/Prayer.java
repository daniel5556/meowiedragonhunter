package com.rs.game.player;

import java.io.Serializable;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.clanwars.ClanWars.Rules;
import com.rs.utils.Utils;
import com.rs.Settings;

public class Prayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2082861520556582824L;

	private final static int[][] prayerLvls = {
		// normal prayer book
		{ 1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 35,
			37, 40, 43, 44, 45, 46, 49, 52, 60, 65, 70, 74, 77 },
			// ancient prayer book
			{ 50, 50, 52, 54, 56, 59, 62, 65, 68, 71, 74, 76, 78, 80, 82, 84,
				86, 89, 92, 95 } };

	private final static int[][][] closePrayers = { { // normal prayer book
		{ 0, 5, 13 }, // Skin prayers 0
		{ 1, 6, 14 }, // Strength prayers 1
		{ 2, 7, 15 }, // Attack prayers 2
		{ 3, 11, 20, 28 }, // Range prayers 3
		{ 4, 12, 21, 29 }, // Magic prayers 4
		{ 8, 9, 26 }, // Restore prayers 5
		{ 10 }, // Protect item prayers 6
		{ 17, 18, 19 }, // Protect prayers 7
		{ 16 }, // Other protect prayers 8
		{ 22, 23, 24 }, // Other special prayers 9
		{ 25, 27 } // Other prayers 10
	}, { // ancient prayer book
		{ 0 }, // Protect item prayers 0
		{ 1, 2, 3, 4 }, // sap prayers 1
		{ 5 }, // other prayers 2
		{ 7, 8, 9, 17, 18 }, // protect prayers 3
		{ 6 }, // other protect prayers 4
		{ 10, 11, 12, 13, 14, 15, 16 }, // leech prayers 5
		{ 19 }, // other prayers
	} };

	private final static int[] prayerSlotValues = { 1, 2, 4, 262144, 524288, 8,
		16, 32, 64, 128, 256, 1048576, 2097152, 512, 1024, 2048, 16777216,
		4096, 8192, 16384, 4194304, 8388608, 32768, 65536, 131072,
		33554432, 134217728, 67108864, 268435456 * 2, 268435456 };

	private final static double[][] prayerDrainRate = {
		{ 1.2, 1.2, 1.2, 1.2, 1.2, 0.6, 0.6, 0.6, 3.6, 1.8, 1.8, 0.6, 0.6, 0.3,
			0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.2, 0.6, 0.18,
			0.18, 0.24, 0.15, 0.2, 0.18 },
			{ 1.8, 0.24, 0.24, 0.24, 0.24, 1.8, 0.3, 0.3, 0.3, 0.3, 0.36, 0.36,
				0.36, 0.36, 0.36, 0.36, 0.36, 1.2, 0.2, 0.2 } };

	private transient Player player;
	private transient boolean[][] onPrayers;
	private transient boolean usingQuickPrayer;
	private transient int onPrayersCount;

	private boolean[][] quickPrayers;
	private int prayerpoints;
	private transient int[] leechBonuses;
	private boolean ancientcurses;
	private transient long[] nextDrain;
	private transient boolean boostedLeech;

	public double getMageMultiplier() {
		double value = 1.0 + (leechBonuses[4] / 100.0);
		if (usingPrayer(0, 4))
			value += 0.05;
		else if (usingPrayer(0, 12))
			value += 0.10;
		else if (usingPrayer(0, 21))
			value += 0.15;
		else if (usingPrayer(0, 29))
			value += 0.25;
		else if (usingPrayer(1, 12)) {
			double d = (10 + leechBonuses[9]);
			value += d / 100;
		}
		return value;
	}

	public double getRangeMultiplier() {
		double value = 1.0 + (leechBonuses[3] / 100.0);
		if (usingPrayer(0, 3))
			value += 0.05;
		else if (usingPrayer(0, 11))
			value += 0.10;
		else if (usingPrayer(0, 20))
			value += 0.15;
		else if (usingPrayer(0, 28))
			value += 0.25;
		else if (usingPrayer(1, 11)) {
			double d = (10 + leechBonuses[8]);
			value += d / 100;
		}
		return value;
	}

	public double getAttackMultiplier() {
		double value = 1.0 + (leechBonuses[0] / 100.0);
		if (usingPrayer(0, 2))
			value += 0.05;
		else if (usingPrayer(0, 7))
			value += 0.10;
		else if (usingPrayer(0, 15))
			value += 0.15;
		else if (usingPrayer(0, 25))
			value += 0.15;
		else if (usingPrayer(0, 27))
			value += 0.25;
		else if (usingPrayer(1, 10)) {
			double d = (10 + leechBonuses[5]);
			value += d / 100;
		}
		else if (usingPrayer(1, 19)) {
			double d = (15 + leechBonuses[10]);
			value += d / 100;
		}
		return value;
	}

	public double getStrengthMultiplier() {
		double value = 1.0 + (leechBonuses[2] / 100.0);
		if (usingPrayer(0, 1))
			value += 0.05;
		else if (usingPrayer(0, 6))
			value += 0.10;
		else if (usingPrayer(0, 14))
			value += 0.15;
		else if (usingPrayer(0, 25))
			value += 0.18;
		else if (usingPrayer(0, 27))
			value += 0.25;
		else if (usingPrayer(1, 14)) {
			double d = (10 + leechBonuses[7]);
			value += d / 100;
		}
		else if (usingPrayer(1, 19)) {
			double d = (23 + leechBonuses[12]);
			value += d / 100;
		}
		return value;
	}

	public double getDefenceMultiplier() {
		double value = 1.0 + (leechBonuses[1] / 100.0);
		if (usingPrayer(0, 0))
			value += 0.05;
		else if (usingPrayer(0, 5))
			value += 0.10;
		else if (usingPrayer(0, 13))
			value += 0.15;
		else if (usingPrayer(0, 25))
			value += 0.20;
		else if (usingPrayer(0, 27))
			value += 0.25;
		else if (usingPrayer(0, 28))
			value += 0.25;
		else if (usingPrayer(0, 29))
			value += 0.25;
		else if (usingPrayer(1, 13)) {
			double d = (10 + leechBonuses[6]);
			value += d / 100;
		}
		else if (usingPrayer(1, 19)) {
			double d = (15 + leechBonuses[11]);
			value += d / 100;
		}
		return value;
	}

	public boolean reachedMax(int bonus) {
		if (bonus >= 5 && bonus <= 9) {
			return leechBonuses[bonus] >= 10;
		}
		else if (bonus >= 0 && bonus <= 4) {
			return leechBonuses[bonus] <= -10;
		}
		else {
			return false;
		}
	}

	public void adjustLeechBonus(int bonus) {
		if (bonus >= 5 && bonus <= 9) {
			leechBonuses[bonus]++;
			if (bonus == 5) { // + attack
				adjustStat(0, leechBonuses[5]);
			} 
			else if (bonus == 6) { // + defence
				adjustStat(1, leechBonuses[6]);
			} 
			else if (bonus == 7) { // + strength
				adjustStat(2, leechBonuses[7]);
			} 
			else if (bonus == 8) { // + range
				adjustStat(4, leechBonuses[8]);
			}
			else if (bonus == 9) { // + magic
				adjustStat(6, leechBonuses[9]);
			}
		}
		else if (bonus >= 0 && bonus <= 4) {
			leechBonuses[bonus]--;
			if (bonus == 0) { // - attack
				adjustStat(0, leechBonuses[0]);
			} 
			else if (bonus == 1) { // - defence
				adjustStat(1, leechBonuses[1]);
			} 
			else if (bonus == 2) { // - strength
				adjustStat(2, leechBonuses[2]);
			} 
			else if (bonus == 3) { // - range
				adjustStat(4, leechBonuses[3]);
			}
			else if (bonus == 4) { // - magic
				adjustStat(6, leechBonuses[4]);
			}
		}
	}
	
	public void restoreIfLeeched() {
		for (int i=0; i<5; i++) {
			if (leechBonuses[i] < 0) {
				leechBonuses[i]++;
				if (i == 0) {
					adjustStat(0, leechBonuses[0]);
					if (leechBonuses[0] == 0 && leechBonuses[5] == 0) {
						player.getPackets().sendGameMessage("Your attack is no longer affected by sap or leech curses.");
					}
				}
				else if (i == 1) {
					adjustStat(1, leechBonuses[1]);
					if (leechBonuses[1] == 0 && leechBonuses[6] == 0) {
						player.getPackets().sendGameMessage("Your defence is no longer affected by sap or leech curses.");
					}
				}
				else if (i == 2) {
					adjustStat(2, leechBonuses[2]);
					if (leechBonuses[2] == 0 && leechBonuses[7] == 0) {
						player.getPackets().sendGameMessage("Your strength is no longer affected by sap or leech curses.");
					}
				}
				else if (i == 3) {
					adjustStat(4, leechBonuses[3]);
					if (leechBonuses[3] == 0 && leechBonuses[8] == 0) {
						player.getPackets().sendGameMessage("Your ranged is no longer affected by sap or leech curses.");
					}
				}
				else if (i == 4) {
					adjustStat(6, leechBonuses[4]);
					if (leechBonuses[4] == 0 && leechBonuses[9] == 0) {
						player.getPackets().sendGameMessage("Your magic is no longer affected by sap or leech curses.");
					}
				}
			}
		}
	}
	
	public void resetLeeched() {
		for (int i=0; i<5; i++) {
			leechBonuses[i] = 0;
		}
	}

	public void increaseTurmoilBonus(Player p2) {
		leechBonuses[10] = (int) ((100 * Math.floor(0.15 * p2.getSkills()
				.getLevelForXp(Skills.ATTACK))) / p2.getSkills().getLevelForXp(
						Skills.ATTACK));
		leechBonuses[11] = (int) ((100 * Math.floor(0.15 * p2.getSkills()
				.getLevelForXp(Skills.DEFENCE))) / p2.getSkills()
				.getLevelForXp(Skills.DEFENCE));
		leechBonuses[12] = (int) ((100 * Math.floor(0.1 * p2.getSkills()
				.getLevelForXp(Skills.STRENGTH))) / p2.getSkills()
				.getLevelForXp(Skills.STRENGTH));
		adjustStat(0, leechBonuses[10]);
		adjustStat(1, leechBonuses[11]);
		adjustStat(2, leechBonuses[12]);
	}

	public void adjustStat(int stat, int percentage) {
		player.getPackets().sendConfigByFile(6857 + stat, 30 + percentage);
	}

	public void closePrayers(int prayerId) {
		if (ancientcurses) {
			if (prayerId == 10) {
				if (leechBonuses[5] > 0) {					
					adjustStat(0, leechBonuses[0]);
					leechBonuses[5] = 0;
				}				
			} 
			else if (prayerId == 11) {
				if (leechBonuses[8] > 0) {					
					adjustStat(4, leechBonuses[3]);
					leechBonuses[8] = 0;
				}				
			} 
			else if (prayerId == 12) {
				if (leechBonuses[9] > 0) {				
					adjustStat(6, leechBonuses[4]);
					leechBonuses[9] = 0;
				}				
			} 
			else if (prayerId == 13) {
				if (leechBonuses[6] > 0) {
					adjustStat(1, leechBonuses[1]);
					leechBonuses[6] = 0;
				}				
			} 
			else if (prayerId == 14) {
				if (leechBonuses[7] > 0) {
					adjustStat(2, leechBonuses[2]);
					leechBonuses[7] = 0;
				}				
			} 
			else if (prayerId == 19) {
				leechBonuses[10] = 0;
				leechBonuses[11] = 0;
				leechBonuses[12] = 0;
				adjustStat(0, 0);
				adjustStat(1, 0);
				adjustStat(2, 0);
			}
		}
	}

	public int getPrayerHeadIcon() {
		if (onPrayersCount == 0)
			return -1;
		int value = -1;
		if (usingPrayer(0, 16))
			value += 8;
		if (usingPrayer(0, 17))
			value += 3;
		else if (usingPrayer(0, 18))
			value += 2;
		else if (usingPrayer(0, 19))
			value += 1;
		else if (usingPrayer(0, 22))
			value += 4;
		else if (usingPrayer(0, 23))
			value += 6;
		else if (usingPrayer(0, 24))
			value += 5;
		else if (usingPrayer(1, 6)) {
			value += 16;
			if (usingPrayer(1, 8))
				value += 2;
			else if (usingPrayer(1, 7))
				value += 3;
			else if (usingPrayer(1, 9))
				value += 1;
		} else if (usingPrayer(1, 7))
			value += 14;
		else if (usingPrayer(1, 8))
			value += 15;
		else if (usingPrayer(1, 9))
			value += 13;
		else if (usingPrayer(1, 17))
			value += 20;
		else if (usingPrayer(1, 18))
			value += 21;
		return value;
	}

	public void switchSettingQuickPrayer() {
		usingQuickPrayer = !usingQuickPrayer;
		player.getPackets().sendGlobalConfig(181, usingQuickPrayer ? 1 : 0);// activates
		// quick
		// choose
		unlockPrayerBookButtons();
		if (usingQuickPrayer) // switchs tab to prayer
			player.getPackets().sendGlobalConfig(168, 6);
	}

	public void switchQuickPrayers() {
		if (!checkPrayer())
			return;
		if (hasPrayersOn())
			closeAllPrayers();
		else {
			if (player.getCurrentFriendChat() != null) {
				ClanWars war = player.getCurrentFriendChat().getClanWars();
				if (war != null && war.get(Rules.NO_PRAYER) && (war.getFirstPlayers().contains(player) || war.getSecondPlayers().contains(player))) {
					player.getPackets().sendGameMessage("Prayer has been disabled during this war.");
					return;
				}
			}
			boolean hasOn = false;
			int index = 0;
			for (boolean prayer : quickPrayers[getPrayerBook()]) {
				if (prayer) {
					if (usePrayer(index))
						hasOn = true;
				}
				index++;
			}
			if (hasOn) {
				player.getPackets().sendGlobalConfig(182, 1);
				recalculatePrayer();
			}
		}
	}

	private void closePrayers(int[]... prayers) {
		for (int[] prayer : prayers)
			for (int prayerId : prayer)
				if (usingQuickPrayer)
					quickPrayers[getPrayerBook()][prayerId] = false;
				else {
					if (onPrayers[getPrayerBook()][prayerId])
						onPrayersCount--;
					onPrayers[getPrayerBook()][prayerId] = false;
					closePrayers(prayerId);

				}
	}

	public void switchPrayer(int prayerId) {
		if (!usingQuickPrayer)
			if (!checkPrayer())
				return;
		usePrayer(prayerId);
		recalculatePrayer();
	}

	private boolean usePrayer(int prayerId) {
		if (prayerId < 0 || prayerId >= prayerLvls[getPrayerBook()].length)
			return false;
		if (player.getSkills().getLevelForXp(5) < prayerLvls[this
		                                                     .getPrayerBook()][prayerId]) {
			player.getPackets().sendGameMessage(
					"You need a prayer level of at least "
							+ prayerLvls[getPrayerBook()][prayerId]
									+ " to use this prayer.");
			return false;
		}
		if (getPrayerBook() == 0 && prayerId == 25 || prayerId == 27) {
			if (player.getSkills().getLevelForXp(Skills.DEFENCE) < 70) {
				player.getPackets().sendGameMessage(
						"You need a defence level of at least 70 to use this prayer.");
				return false;
			}
		} else if (getPrayerBook() == 1) {
			if (player.getSkills().getLevelForXp(Skills.DEFENCE) < 30) {
				player.getPackets().sendGameMessage(
						"You need a defence level of at least 30 to use this prayer.");
				return false;
			}
		}
		if (player.getPrayerDelay() >= Utils.currentTimeMillis()) {
			player.getPackets()
			.sendGameMessage(
					"You are currently injured and cannot use protection prayers!");
			if (ancientcurses && prayerId >= 6 && prayerId <= 9)
				return false;
			else if (prayerId >= 16 && prayerId <= 19)
				return false;
		}
		if (player.getCurrentFriendChat() != null) {
			ClanWars war = player.getCurrentFriendChat().getClanWars();
			if (war != null && war.get(Rules.NO_PRAYER) && (war.getFirstPlayers().contains(player) || war.getSecondPlayers().contains(player))) {
				player.getPackets().sendGameMessage("Prayer has been disabled during this war.");
				return false;
			}
		}
		if (getPrayerBook() == 0 && prayerId >= 22 && Settings.FREEGAME) {
			player.getPackets().sendGameMessage("That prayer is members and the server is set to F2P gameplay.");
			return false;
		}
		if (!usingQuickPrayer) {
			if (onPrayers[getPrayerBook()][prayerId]) {
				onPrayers[getPrayerBook()][prayerId] = false;
				closePrayers(prayerId);
				onPrayersCount--;
				player.getAppearence().generateAppearenceData();
				player.getPackets().sendSound(2663, 0, 1);
				return true;
			}
		} else {
			if (quickPrayers[getPrayerBook()][prayerId]) {
				quickPrayers[getPrayerBook()][prayerId] = false;
				player.getPackets().sendSound(2663, 0, 1);
				return true;
			}
		}
		boolean needAppearenceGenerate = false;
		if (getPrayerBook() == 0) {
			switch (prayerId) {
			case 0:
			case 5:
			case 13:
				closePrayers(closePrayers[getPrayerBook()][0],
						closePrayers[getPrayerBook()][10]);
				break;
			case 1:
			case 6:
			case 14:
				closePrayers(closePrayers[getPrayerBook()][1],
						closePrayers[getPrayerBook()][3],
						closePrayers[getPrayerBook()][4],
						closePrayers[getPrayerBook()][10]);
				break;
			case 2:
			case 7:
			case 15:
				closePrayers(closePrayers[getPrayerBook()][2],
						closePrayers[getPrayerBook()][3],
						closePrayers[getPrayerBook()][4],
						closePrayers[getPrayerBook()][10]);
				break;
			case 3:
			case 11:
			case 20:
				closePrayers(closePrayers[getPrayerBook()][1],
						closePrayers[getPrayerBook()][2],
						closePrayers[getPrayerBook()][3],
						closePrayers[getPrayerBook()][10]);
				break;
			case 4:
			case 12:
			case 21:
				closePrayers(closePrayers[getPrayerBook()][1],
						closePrayers[getPrayerBook()][2],
						closePrayers[getPrayerBook()][4],
						closePrayers[getPrayerBook()][10]);
				break;
			case 8:
			case 9:
			case 26:
				closePrayers(closePrayers[getPrayerBook()][5]);
				break;
			case 10:
				closePrayers(closePrayers[getPrayerBook()][6]);
				break;
			case 17:
			case 18:
			case 19:
				closePrayers(closePrayers[getPrayerBook()][7],
						closePrayers[getPrayerBook()][9]);
				needAppearenceGenerate = true;
				break;
			case 16:
				closePrayers(closePrayers[getPrayerBook()][8],
						closePrayers[getPrayerBook()][9]);
				needAppearenceGenerate = true;
				break;
			case 22:
			case 23:
			case 24:
				closePrayers(closePrayers[getPrayerBook()][7],
						closePrayers[getPrayerBook()][8],
						closePrayers[getPrayerBook()][9]);
				needAppearenceGenerate = true;
				break;
			case 25:
			case 27:
			case 28:
			case 29:
				closePrayers(closePrayers[getPrayerBook()][0],
						closePrayers[getPrayerBook()][1],
						closePrayers[getPrayerBook()][2],
						closePrayers[getPrayerBook()][3],
						closePrayers[getPrayerBook()][4],
						closePrayers[getPrayerBook()][10]);
				break;
			default:
				return false;
			}
		} else {
			switch (prayerId) {
			case 0:
				if (!usingQuickPrayer) {
					player.setNextAnimation(new Animation(12567));
					player.setNextGraphics(new Graphics(2213));
				}
				closePrayers(closePrayers[getPrayerBook()][0]);
				break;
			case 1:
				player.setNextGraphics(new Graphics(2214));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][5],closePrayers[getPrayerBook()][6]);
				break;
			case 2:
				player.setNextGraphics(new Graphics(2217));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][5],closePrayers[getPrayerBook()][6]);
				break;
			case 3:
				player.setNextGraphics(new Graphics(2220));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][5],closePrayers[getPrayerBook()][6]);
				break;
			case 4:
				player.setNextGraphics(new Graphics(2220));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][5],closePrayers[getPrayerBook()][6]);
				break;
			case 5:
				if (!usingQuickPrayer) {
					player.setNextAnimation(new Animation(12589));
					player.setNextGraphics(new Graphics(2266));
				}
				closePrayers(closePrayers[getPrayerBook()][2]);
				break;
			case 7:
				player.setNextGraphics(new Graphics(2228));
				player.setNextAnimation(new Animation(12573));
				closePrayers(closePrayers[getPrayerBook()][3]);
				needAppearenceGenerate = true;
				break;
			case 8:
				player.setNextGraphics(new Graphics(2229));
				player.setNextAnimation(new Animation(12573));
				closePrayers(closePrayers[getPrayerBook()][3]);
				needAppearenceGenerate = true;
				break;
			case 9:
				player.setNextAnimation(new Animation(12573));
				player.setNextGraphics(new Graphics(2230));
				closePrayers(closePrayers[getPrayerBook()][3]);
				needAppearenceGenerate = true;
				break;
			case 17:
				player.setNextAnimation(new Animation(12583));
				closePrayers(closePrayers[getPrayerBook()][3]);
				needAppearenceGenerate = true;
				break;
			case 18:
				player.setNextGraphics(new Graphics(2255));
				player.setNextAnimation(new Animation(12584));
				closePrayers(closePrayers[getPrayerBook()][3]);
				needAppearenceGenerate = true;
				break;
			case 6:
				player.setNextGraphics(new Graphics(2227));
				player.setNextAnimation(new Animation(12573));
				closePrayers(closePrayers[getPrayerBook()][4]);
				needAppearenceGenerate = true;
				break;
			case 10:
				player.setNextGraphics(new Graphics(2214));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
				closePrayers[getPrayerBook()][6]);
				break;
			case 11:
				player.setNextGraphics(new Graphics(2217));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
				closePrayers[getPrayerBook()][6]);
				break;
			case 12:
				player.setNextGraphics(new Graphics(2220));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
				closePrayers[getPrayerBook()][6]);
				break;
			case 13:
				player.setNextGraphics(new Graphics(2214));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
				closePrayers[getPrayerBook()][6]);
				break;
			case 14:
				player.setNextGraphics(new Graphics(2214));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
					closePrayers[getPrayerBook()][6]);
				break;
			case 15:
			case 16:
				player.setNextGraphics(new Graphics(2223));
				player.setNextAnimation(new Animation(12569));
				closePrayers(closePrayers[getPrayerBook()][1],
				closePrayers[getPrayerBook()][6]);
				break;
			case 19:
				if (!usingQuickPrayer) {
					player.setNextAnimation(new Animation(12565));
					player.setNextGraphics(new Graphics(2226));
				}
				closePrayers(closePrayers[getPrayerBook()][1],
						closePrayers[getPrayerBook()][5],
						closePrayers[getPrayerBook()][6]);
				break;
			default:
				return false;
			}
		}
		if (!usingQuickPrayer) {
			onPrayers[getPrayerBook()][prayerId] = true;
			resetDrainPrayer(prayerId);
			onPrayersCount++;
			if (needAppearenceGenerate)
				player.getAppearence().generateAppearenceData();
		} else {
			quickPrayers[getPrayerBook()][prayerId] = true;
		}
		player.getPackets().sendSound(2662, 0, 1);
		return true;
	}

	public void processPrayer() {
		if (!hasPrayersOn())
			return;
		boostedLeech = false;
	}
	
	//600
	
	public void processPrayerDrain() {
		if (!hasPrayersOn())
			return;
		int prayerBook = getPrayerBook();
		long currentTime = Utils.currentTimeMillis();
		int drain = 0;
		int prayerPoints = player.getCombatDefinitions().getBonuses()[CombatDefinitions.PRAYER_BONUS];
		for (int index = 0; index < onPrayers[prayerBook].length; index++) {
			if(onPrayers[prayerBook][index]) {
				long drainTimer = nextDrain[index];
				if(drainTimer != 0 && drainTimer <= currentTime) {
					int rate = (int) ((prayerDrainRate[getPrayerBook()][index]*1000) + (prayerPoints * 50));
					int passedTime = (int) (currentTime - drainTimer);
					drain++;
					int count = 0;
					while(passedTime >= rate && count++ < 10) {
						drain++;
						passedTime -= rate;
					}
					nextDrain[index] = (currentTime + rate) - passedTime;
				}
			}
		}
		if(drain > 0) {
			drainPrayer(drain);
			if (!checkPrayer())
				closeAllPrayers();
		}
	}

	public void resetDrainPrayer(int index) {
		nextDrain[index] = (long) (Utils.currentTimeMillis() + (prayerDrainRate[getPrayerBook()][index]*1000)
				+ (player.getCombatDefinitions().getBonuses()[CombatDefinitions.PRAYER_BONUS] * 50));
	}

	public int getOnPrayersCount() {
		return onPrayersCount;
	}

	public void closeAllPrayers() {
		onPrayers = new boolean[][] { new boolean[30], new boolean[20] };
		leechBonuses = new int[13];
		onPrayersCount = 0;
		player.getPackets().sendGlobalConfig(182, 0);
		player.getPackets().sendConfig(ancientcurses ? 1582 : 1395, 0);
		player.getAppearence().generateAppearenceData();
		resetStatAdjustments();
	}

	public boolean hasPrayersOn() {
		return onPrayersCount > 0;
	}

	private boolean checkPrayer() {
		if (prayerpoints <= 0) {
			player.getPackets().sendSound(2672, 0, 1);
			player.getPackets().sendGameMessage(
					"Your prayer has run out you may recharge it at an altar.");
			return false;
		}
		return true;
	}

	private int getPrayerBook() {
		return ancientcurses == false ? 0 : 1;
	}

	private void recalculatePrayer() {
		int value = 0;
		int index = 0;
		for (boolean prayer : (!usingQuickPrayer ? onPrayers[getPrayerBook()]
				: quickPrayers[getPrayerBook()])) {
			if (prayer)
				value += ancientcurses ? Math.pow(2, index)
						: prayerSlotValues[index];
				index++;
		}
		player.getPackets().sendConfig(
				ancientcurses ? (usingQuickPrayer ? 1587 : 1582)
						: (usingQuickPrayer ? 1397 : 1395), value);
	}

	public void refresh() {
		player.getPackets().sendGlobalConfig(181, usingQuickPrayer ? 1 : 0);
		player.getPackets().sendConfig(1584, ancientcurses ? 1 : 0);
		unlockPrayerBookButtons();
	}

	public void resetStatAdjustments() {
		for (int i = 0; i < 5; i++)
			adjustStat(i, 0);
	}

	public void init() {
		player.getPackets().sendGlobalConfig(181, usingQuickPrayer ? 1 : 0);
		player.getPackets().sendConfig(1584, ancientcurses ? 1 : 0);
		resetStatAdjustments();
	}

	public void unlockPrayerBookButtons() {
		player.getPackets().sendUnlockIComponentOptionSlots(271,
				usingQuickPrayer ? 42 : 8, 0, 29, 0);
	}

	public void setPrayerBook(boolean ancientcurses) {
		closeAllPrayers();
		this.ancientcurses = ancientcurses;
		player.getInterfaceManager().sendPrayerBook();
		refresh();
	}

	public Prayer() {
		quickPrayers = new boolean[][] { new boolean[30], new boolean[20] };
		prayerpoints = 10;
	}

	public void setPlayer(Player player) {
		this.player = player;
		onPrayers = new boolean[][] { new boolean[30], new boolean[20] };
		nextDrain = new long[30];
		leechBonuses = new int[13];
	}

	public boolean isAncientCurses() {
		return ancientcurses;
	}

	public boolean usingPrayer(int book, int prayerId) {
		return onPrayers[book][prayerId];
	}

	public boolean isUsingQuickPrayer() {
		return usingQuickPrayer;
	}

	public boolean isBoostedLeech() {
		return boostedLeech;
	}

	public void setBoostedLeech(boolean boostedLeech) {
		this.boostedLeech = boostedLeech;
	}

	public int getPrayerpoints() {
		return prayerpoints;
	}

	public void setPrayerpoints(int prayerpoints) {
		this.prayerpoints = prayerpoints;
	}

	public void refreshPrayerPoints() {
		player.getPackets().sendConfig(2382, prayerpoints);
	}

	public void drainPrayerOnHalf() {
		if (prayerpoints > 0) {
			prayerpoints = prayerpoints / 2;
			refreshPrayerPoints();
		}
	}
	
	
	public boolean hasFullPrayerpoints() {
		return getPrayerpoints() >= player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
	}

	public void drainPrayer(int amount) {
		if ((prayerpoints - amount) >= 0)
			prayerpoints -= amount;
		else
			prayerpoints = 0;
		refreshPrayerPoints();
	}

	public void restorePrayer(int amount) {
		int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
		if ((prayerpoints + amount) <= maxPrayer)
			prayerpoints += amount;
		else
			prayerpoints = maxPrayer;
		refreshPrayerPoints();
	}

	public void reset() {
		closeAllPrayers();
		prayerpoints = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
		refreshPrayerPoints();
	}

}
