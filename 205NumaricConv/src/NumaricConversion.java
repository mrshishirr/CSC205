import java.math.BigInteger;
import java.util.Scanner;

/**
 * This class is a simple Base Conversion calculator. 
 * <p>
 * Converts String of a number from one base (2-36) to another.
 * <p><p>
 * <b>Note: </b> No validation has been implemented for user input.
 */
public class NumaricConversion {
	private static Scanner scan;
	private static String inNum;
	private static int inBase;
	private static int radix;

	
	public static void main(String[] args) {

		scan = new Scanner(System.in);

		System.out.print("Enter a number: ");
		inNum = scan.nextLine();
		System.out.print("Enter the number's base: ");
		inBase = scan.nextInt();
		System.out.print("Enter the target radix: ");
		radix = scan.nextInt();

		System.out.println();
		System.out.println(baseConverter(inNum, inBase, radix));
		
	}

	/**
	 * @param inNum
	 *            the number to be converted
	 * @param inBase
	 *            the base of the provided number
	 * @param radix
	 *            the target base for conversion
	 * 
	 * @return A String containing the result.
	 *         <p>
	 *         i.e. <i>The number in base-<b>10</b> is <b>123456</b>
	 */
	private static String baseConverter(String inNum, int inBase, int radix) {
		
		BigInteger bInt = new BigInteger(inNum, inBase);		
		// The internal logic could be elaborated in this way -
		/*********************************************************************
		* 
		* int dVal = 0;
		* Character ch;
		* for(int i=0; i<inNum.length(); i++) {
		* 	ch = inNum.charAt(i);
		* 	dVal += Character.getNumericValue(ch)
		* 				* Math.pow(inBase, (inNum.length() - (i + 1)));
		* }
		* 
		*********************************************************************/
		
		String conv = bInt.toString(radix).toUpperCase();		
		// The internal logic could be elaborated in this way -
		/*********************************************************************
		 * 
		 * String calc = "";
		 * int divisor = radix, dividend = dVal, remainder, quotient;
		 * do
		 * {
		 * 		quotient = dividend / divisor;
		 * 		remainder = dividend % divisor;
		 * 
		 * 		if (remainder > 10)
		 * 			calc += Character.forDigit(remainder, radix);
		 * 		else calc += remainder;
		 * 		
		 * 		dividend = quotient;
		 * 
		 * } while(dividend >0);
		 * String result = new StringBuilder(calc).reverse().toString().toUpperCase();		 * 
		 * 
		 *********************************************************************/
		
		String solution = String.format("The number in base-%d is %s", radix, conv);
		return solution;

	}

}
