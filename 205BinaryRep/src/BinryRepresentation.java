import java.util.Scanner;

public class BinryRepresentation {


	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter a number: ");
		String bNum = scan.nextLine();
		
		System.out.println();
		System.out.println();
		
		System.out.println("Unsigned: " + valueofUnsignedMag(bNum));
		System.out.println("1's Comp: " + valueof1sComplement(bNum));
		System.out.println("2's Comp: " + valueof2sComplement(bNum));
		System.out.println("Q(4.4): " + valueofQ4by4(bNum));
		System.out.println("S-Exp-Sig: " + valueofSExpSig(bNum));
			
	}

	private static double valueofSExpSig(String bin) {
		double val = 0;
		int bias = (7/2);
		
		int unbiasedExp = bin2dec(bin.substring(1, 4));
		int exp = unbiasedExp - bias;
		int sig = bin2dec(bin.substring(4));
		
		if (unbiasedExp == 0 && sig == 0)
			 return 0;
		else {
			val = Math.pow(10, exp) * Double.parseDouble("1." + dec2bin(sig));
			
			String bVal = String.valueOf(val);
			int posDot = bVal.indexOf('.');
			
			String intPart = bVal.substring(0, posDot);
			int intVal = bin2dec(intPart);
			
			String fracPart = bVal.substring(posDot + 1);
			float  fracVal =  binFrac2Dec(fracPart);
			
			if (bin.charAt(0) == 48)
				return intVal + fracVal;
			else 
				return -1 * (intVal + fracVal);			
		}
	}

	private static float valueofQ4by4(String bin) {
		
		if (bin.charAt(0) == 48) {			
			String intPart = bin.substring(0, 4);
			int intVal = bin2dec(intPart);
			
			String fracPart = bin.substring(4);
			float  fracVal =  binFrac2Dec(fracPart);
			
			return intVal + fracVal;
		}
		
		else {			
			
			String part = bin.substring(1);
			String num = numFlipper(part);
			int val = bin2dec(num)+1;
			String unsignedVal = String.format("%1$8s", dec2bin(val)).replace(' ', '0');
			
			String intPart = unsignedVal.substring(0, 4);
			int intVal = bin2dec(intPart);
			
			String fracPart = unsignedVal.substring(4);
			float  fracVal =  binFrac2Dec(fracPart);
			
			return -1 * (intVal + fracVal);
		}
		
	}

	private static int valueof2sComplement(String bin) {
//		return valueof1sComplement(bin) - 1;
		
		if (bin.charAt(0) == 48)
			return bin2dec(bin);
		else {
			String mag = bin.substring(1);
			String comp = numFlipper(mag);
			int compMag = bin2dec(comp);
			return ((-1 * compMag) - 1);
		}
	}

	private static int valueof1sComplement(String bin) {
		if (bin.charAt(0) == 48)
			return bin2dec(bin);
		else {
			String mag = bin.substring(1);
			String comp = numFlipper(mag);
			int compMag = bin2dec(comp);
			return (-1 * compMag);
		}
	}

	private static int valueofUnsignedMag(String bin) {
		return bin2dec(bin);
	}
	
	private static int bin2dec(String bin) {
		int dVal = 0;
		for(int i=0; i<bin.length(); i++) {
			int val = bin.charAt(i) - 48;
			dVal += val * Math.pow(2, (bin.length() - (i + 1)));
		}
		return dVal;
	}
	
	private static float binFrac2Dec(String bin) {
		float dVal = 0;
		for(int i=0; i<bin.length(); i++) {
			int val = bin.charAt(i) - 48; // Character.getNumaricValue()
			dVal += val * Math.pow(2, - (i + 1));
		}
		return dVal;
		
	}
	
	private static String numFlipper(String bin) {
		String s = "";
		for(int i=0; i<bin.length(); i++) {
			if (bin.charAt(i) == '0') s += "1";
			else s += "0";
		}
		return s;
	}
	
	private static String dec2bin(int dec) {
		String calc = "";
		int radix = 2;
		
		int divisor = radix, dividend = dec, remainder, quotient;
		do {
			quotient = dividend / divisor;
			remainder = dividend % divisor;
//			if (remainder > 10)
//				calc += Character.forDigit(remainder, radix);
//			else
				calc += remainder;
			dividend = quotient;

		} while (dividend > 0);
		String result = new StringBuilder(calc).reverse().toString().toUpperCase();
		return result;
	}
}	