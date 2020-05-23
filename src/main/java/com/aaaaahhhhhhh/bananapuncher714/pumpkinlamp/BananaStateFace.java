package com.aaaaahhhhhhh.bananapuncher714.pumpkinlamp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DState;

public class BananaStateFace extends DState< CardinalDirection > {
	public BananaStateFace( String id ) {
		super( id, CardinalDirection.class );
	}

	@Override
	public String convertToString( CardinalDirection value ) {
		return value.name();
	}

	@Override
	public Optional< CardinalDirection > getFrom( String value ) {
		try {
			return Optional.of( CardinalDirection.valueOf( value.toUpperCase() ) );
		} catch ( IllegalArgumentException exception ) {
			return Optional.empty();
		}
	}

	@Override
	public Collection< CardinalDirection > getValues() {
		return Arrays.asList( CardinalDirection.values() );
	}

}
