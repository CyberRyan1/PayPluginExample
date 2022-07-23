package com.github.cyberryan1.paypluginexample.utils;

import java.text.DecimalFormat;

public class Utils {

    public static String getFormattedNumber( double x ) {
        DecimalFormat format = new DecimalFormat( "#.00" );
        format.setGroupingUsed( true );
        format.setGroupingSize( 3 );
        format.setDecimalSeparatorAlwaysShown( true );
        return format.format( x );
    }
}