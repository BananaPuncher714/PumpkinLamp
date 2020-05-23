package com.aaaaahhhhhhh.bananapuncher714.pumpkinlamp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.DimensionalBlocks;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DBlockData;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.util.NBTEditor;

public class PumpkinLamp extends JavaPlugin implements Listener {
	public static final BananaBlockPumpkinLamp PUMPKIN_LANTERN = new BananaBlockPumpkinLamp();

	private static final Object[] KEYS = new String[] { "com", "aaaaahhhhhhh", "bananapuncher714", "pumpkinlamp", "islamp" };
	
	@Override
	public void onEnable() {
		DimensionalBlocks.register( PUMPKIN_LANTERN );

		Bukkit.getPluginManager().registerEvents( this, this );
		
		Bukkit.getScheduler().runTask( this, this::addRecipe );
	}
	
	@EventHandler( priority = EventPriority.LOWEST )
	private void onEvent( BlockPlaceEvent event ) {
		ItemStack item = event.getItemInHand();
		
		if ( NBTEditor.getByte( item, KEYS ) == 1 ) {
			Block block = event.getBlock();
			BlockData data = block.getBlockData();

			if ( data.getMaterial() == Material.JACK_O_LANTERN && data instanceof Directional ) {
				Directional directional = ( Directional ) data;
				BlockFace face = directional.getFacing();

				DBlockData dData = PUMPKIN_LANTERN.getDefaultBlockData();
				dData.set( BananaBlockPumpkinLamp.FACE, CardinalDirection.valueOf( face.name() ) );
				dData.set( BananaBlockPumpkinLamp.LIT, block.getBlockPower() == 0 );
				DimensionalBlocks.setDBlockDataAt( dData, block.getLocation() );
			}
		}
	}
	
	@EventHandler
	private void onEvent( PrepareItemCraftEvent event ) {
		for ( ItemStack item : event.getInventory().getMatrix() ) {
			if ( item != null && NBTEditor.getByte( item, KEYS ) == 1 ) {
				event.getInventory().setResult( new ItemStack( Material.AIR ) );
			}
		}
	}
	
	private void addRecipe() {
		NamespacedKey key = new NamespacedKey( this, "pumpkin_lamp" );
		
		ShapelessRecipe recipe = new ShapelessRecipe( key, getPumpkinLampItem() );
		recipe.addIngredient( Material.JACK_O_LANTERN );
		recipe.addIngredient( Material.REDSTONE_LAMP );
		
		Bukkit.addRecipe( recipe );
	}
	
	public static ItemStack getPumpkinLampItem() {
		ItemStack item = new ItemStack( Material.JACK_O_LANTERN );
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName( ChatColor.GOLD + "Pumpkin Lamp" );
		meta.setLore( Arrays.asList( ChatColor.GRAY + "Turns off when powered with redstone" ) );
		item.setItemMeta( meta );
		
		return NBTEditor.set( item, ( byte ) 1, KEYS );
	}
}
