package com.siberhus.sms.commons;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 24, 2010
 * Time: 10:58:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnglishThaiUnicodeCodec {
	
	static final int THAI_FC = 3585;//Thai first character (char->int)
	static final int THAI_LC = 3675;//Thai last character (char->int)
	static final int ENG_FC = 32;//English first character (char->int)
	static final int ENG_LC = 126;//English last character (char->int)

	static final int THAI_FU = 0x01; //Thai first unicode (0x01)
	static final int ENG_FU = 0x20; //English first unicode (0x20)

	static final String THAI_UP = "0E"; //Thai unicode prefix
	static final String ENG_UP = "00"; //English unicode prefix

	public static String encode(String val) throws UnsupportedEncodingException {
		String result = "";
		for(int i=0;i<val.length();i++){
			int c = (int)val.charAt(i);
			if(c>=THAI_FC && c<=THAI_LC){
				int a = c-THAI_FC;
				String v = Integer.toHexString(THAI_FU+a);
				if(v.length()==1){
					v = "0"+v;
				}
				result += THAI_UP+v;
			}else if(c>=ENG_FC && c<=ENG_LC){
				System.out.println(c);
				int a = c-ENG_FC;
				System.out.println(ENG_FU+a);
				String v = Integer.toHexString(ENG_FU+a);
				result += ENG_UP+v;
			}else{
				throw new UnsupportedEncodingException("Cannot convert character: "+(char)c);
			}
		}
		return result;
	}

	public static String decode(String val){
		String result = "";
		for(int i=0;i<val.length();i+=4){
			String c = val.substring(i,i+4);
			if(c.startsWith(THAI_UP)){
				c = c.substring(2);
				
			}else if(c.startsWith(ENG_UP)){
				c = c.substring(2);

			}
		}
		return result;
	}

	public static boolean containsThaiChar(String val){
		for(int i=0;i<val.length();i++){
			int c = (int)val.charAt(i);
			if(c>=THAI_FC && c<=THAI_LC){
				return true;
			}
		}
		return false;
	}

	public static boolean containsEnglishChar(String val){
		for(int i=0;i<val.length();i++){
			int c = (int)val.charAt(i);
			if(c>=ENG_FC && c<=ENG_LC){
				return true;
			}
		}
		return false;
	}

	
}
