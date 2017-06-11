package calcul;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by lion on 02/06/17.
 */
public class Functions<T> { // container for various static methods
    public static BigInteger add(BigInteger opLeft, BigInteger opRight){
        return opLeft.add(opRight);
    }
    public static BigInteger mul(BigInteger opLeft, BigInteger opRight){
        return opLeft.multiply(opRight);
    }
    public static BigInteger sub(BigInteger opLeft, BigInteger opRight){
        return opLeft.subtract(opRight);
    }
    public static BigInteger div(BigInteger opLeft, BigInteger opRight){
        return opLeft.divide(opRight);
    }
    //-----------------------------------------------------------------//
    public static BigDecimal add(BigDecimal opLeft, BigDecimal opRight){
        return opLeft.add(opRight);
    }
    public static BigDecimal sub(BigDecimal opLeft, BigDecimal opRight){
        return opLeft.subtract(opRight);
    }
    public static BigDecimal mul(BigDecimal opLeft, BigDecimal opRight){
        return opLeft.multiply(opRight);
    }
    public static BigDecimal div(BigDecimal opLeft, BigDecimal opRight){
        return opLeft.divide(opRight);
    }
    public static boolean not(Object op){
        return ! (Boolean.valueOf(String.valueOf(op)));
    }
    public static boolean or(Object opL, Object opR){
        return ( (Boolean.valueOf(String.valueOf(opL))) || (Boolean.valueOf(String.valueOf(opR))) );
    }
    public static boolean and(Object opL, Object opR){
        return ( (Boolean.valueOf(String.valueOf(opL))) && (Boolean.valueOf(String.valueOf(opR))) );
    }
    public static boolean imp(Object opL, Object opR){
        return (!(Boolean.valueOf(String.valueOf(opL))) || Boolean.valueOf(String.valueOf(opR)));
    }
}

