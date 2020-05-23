package com.aaaaahhhhhhh.bananapuncher714.pumpkinlamp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import com.aaaaahhhhhhh.bananapuncher714.dimensional.block.library.api.DState;

public class BananaStateBoolean extends DState< Boolean > {
    public BananaStateBoolean( String id ) {
        super( id, Boolean.class );
    }

    @Override
    public String convertToString( Boolean value ) {
        return Boolean.toString( value );
    }

    @Override
    public Optional< Boolean > getFrom( String value ) {
        if ( value.equalsIgnoreCase( "true" ) || value.equalsIgnoreCase( "false" ) ) {
            return Optional.of( Boolean.valueOf( value.toUpperCase() ) );
        }
        return Optional.empty();
    }

    @Override
    public Collection< Boolean > getValues() {
        return Arrays.asList( true, false );
    }
}
