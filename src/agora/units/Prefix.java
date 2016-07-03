package agora.units;

import java.util.HashMap;

public class Prefix {

    public static final int YOTTA = 24;
    public static final int ZETTA = 21;
    public static final int EXA = 18;
    public static final int PETA = 15;
    public static final int TERA = 12;
    public static final int GIGA = 9;
    public static final int MEGA = 6;
    public static final int KILO = 3;
    public static final int HECTO = 2;
    public static final int DECA = 1;
    public static final int DECI = -1;
    public static final int CENTI = -2;
    public static final int MILLI = -3;
    public static final int MICRO = -6;
    public static final int NANO = -9;
    public static final int PICO = -12;
    public static final int FEMPTO = -15;
    public static final int ATTO = -18;
    public static final int ZEPTO = -21;
    public static final int YOCTO = -24;

    public static final HashMap< String , Integer > PREFIXES;
    static {
        
        PREFIXES = new HashMap<>();
        
        PREFIXES.put( "Y" , YOTTA );
        PREFIXES.put( "Z" , ZETTA );
        PREFIXES.put( "E" , EXA );
        PREFIXES.put( "P" , PETA );
        PREFIXES.put( "T" , TERA );
        PREFIXES.put( "G" , GIGA );
        PREFIXES.put( "M" , MEGA );
        PREFIXES.put( "k" , KILO );
        PREFIXES.put( "h" , HECTO );
        PREFIXES.put( "da" , DECA );
        PREFIXES.put( "d" , DECI );
        PREFIXES.put( "c" , CENTI );
        PREFIXES.put( "m" , MILLI );
        PREFIXES.put( "\\mu" , MICRO );
        PREFIXES.put( "n" , NANO );
        PREFIXES.put( "p" , PICO );
        PREFIXES.put( "f" , FEMPTO );
        PREFIXES.put( "a" , ATTO );
        PREFIXES.put( "z" , ZEPTO );
        PREFIXES.put( "y" , YOCTO );
        
    }
    
}
