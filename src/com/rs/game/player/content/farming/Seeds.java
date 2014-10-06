package com.rs.game.player.content.farming;

import com.rs.game.item.Item;

public class Seeds {
	
	/**
	 * @author Jake | Santa Hat @Rune-Server
	 */

	public enum Seed {
		
		/**
		 * Allotments
		 */
		POTATO(new Item(5318, 4), new int[] { 6, 7, 8, 9, 10 }, 1, new Item(1942, 4), new int[] { 8553, 8552, 8550, 8551 }, 60, 8, 9),
		/**
		 * Flower Patches
		 */
		MARIGOLD(new Item(5096, 4), new int[] { 8, 9, 10, 11, 12 }, 2, new Item(6010, 4), new int[] { 7848, 7847 }, 35, 9, 47),
		/**
		 * Herb Patches
		 */
		GUAM(new Item(5291, 1), new int[] { 4, 5, 6, 7, 8 }, 9, new Item(199, 4), new int[] { 8151, 8150 }, 60, 11, 13);
		
		
		private Item item;
		private int[] configValues;
		private int level;
		private Item produce;
		private int[] suitablePatches;
		private int time; //Time to grow (In Seconds)
		private int plantXP;
		private int harvestXP;
		
		Seed(Item item, int[] configValues, int level, Item produce, int[] suitablePatches, int time, int plantXP, int harvestXP) {
			this.item = item;
			this.configValues = configValues;
			this.level = level;
			this.produce = produce;
			this.suitablePatches = suitablePatches;
			this.time = time;
			this.plantXP = plantXP;
			this.harvestXP = harvestXP;
		}
		
		public Item getItem() {
			return item;
		}
		
		public int[] getConfigValues() {
			return configValues;
		}
		
		public int getLevel() {
			return level;
		}
		
		public Item getProduce() {
			return produce;
		}
		
		public int[] getSuitablePatch() {
			return suitablePatches;
		}
		
		public int getTime() {
			return time;
		}
		
		public int getPlantXP() {
			return plantXP;
		}
		
		public int getHarvestXP() {
			return harvestXP;
		}
	}
	
}