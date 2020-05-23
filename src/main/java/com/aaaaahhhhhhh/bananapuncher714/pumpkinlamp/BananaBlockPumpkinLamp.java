package com.aaaaahhhhhhh.bananapuncher714.pumpkinlamp;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DBlock;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DBlockData;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DInfo;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DState;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.states.DStateBoolean;
import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.states.DStateEnum;

public class BananaBlockPumpkinLamp extends DBlock {
	protected static final DStateBoolean LIT = DStateBoolean.of( "lit" );
	protected static final DStateEnum< BlockFace > FACE = DStateEnum.of( "facing", BlockFace.class, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST );
	
	public BananaBlockPumpkinLamp() {
		super( new DInfo( NamespacedKey.minecraft( "pumpkin_lamp" ), Material.JACK_O_LANTERN.createBlockData() )
				.setExplosionResistance( 1.5f )
				.setCausesSuffocation( false ) );
	}

	@Override
	public BlockData getClientBlock( DBlockData data ) {
		BlockData craftData;
		if ( data.get( LIT ) ) {
			craftData = Material.JACK_O_LANTERN.createBlockData();
		} else {
			craftData = Material.CARVED_PUMPKIN.createBlockData();
		}
		
		if ( craftData instanceof Directional ) {
			Directional directional = ( Directional ) craftData;
			directional.setFacing( BlockFace.valueOf( data.get( FACE ).name() ) );
		}
		
		return craftData;
	}
	
	@Override
	public void onRegister() {
		DBlockData def = getDefaultBlockData();
		def.set( LIT, true );
		def.setAsDefault();
	}
	
	@Override
	public void attackBlock( DBlockData data, Location location, HumanEntity entity ) {
		location.getWorld().spawnParticle( Particle.SMOKE_NORMAL, location.add( .5, .5, .5 ), 40, .5, .5, .5, .01 );
	}
	
	@Override
	public void doPhysics( DBlockData data, Location location, Location otherBlock ) {
		boolean powered = location.getWorld().getBlockAt( location ).getBlockPower() > 0;
		boolean lit = data.get( LIT );
		
		if ( lit == powered ) {
			if ( lit ) {
				data.set( LIT, false );
				update( data, location, false );
			} else {
				tickLater( location, 4 );
			}
		}
	}

	@Override
	public void dropNaturally( DBlockData data, Location location, ItemStack item ) {
		location.getWorld().dropItem( location.clone().add( .5, .5, .5 ), PumpkinLamp.getPumpkinLampItem() );
	}
	
	@Override
	public void tick( DBlockData data, Location location, Random random ) {
		if ( !data.get( LIT ) && location.getWorld().getBlockAt( location ).getBlockPower() == 0 ) {
			data.set( LIT, true );
			update( data, location, false );
		}
	}
	
	@Override
	public DState< ? >[] getStates() {
		return new DState< ? >[] { LIT, FACE };
	}
}
