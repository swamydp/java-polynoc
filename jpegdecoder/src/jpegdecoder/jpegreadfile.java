package jpegdecoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class jpegreadfile {

	static String jpegfile = "C:\\DATA\\swamy\\research\\javacode\\samplejpg\\Linux-Tux-small.jpg";
	
	//prefix byte is 0xFF
	static final byte PREF = (byte) 0xFF;
	
	//start of image
	static final byte SOI = (byte) 0xD8;
	
	//start of frame 0
	static final byte SOF0 = (byte) 0xC0;
	
	//Application-specific
	static final byte APP0 = (byte) 0xE0;
	
	//DQT
	static final byte DQT = (byte) 0xDB;
	
	//DHT
	static final byte DHT = (byte) 0xC4;
	
	//SOS
	static final byte SOS = (byte) 0xDA;
	
	//BYTE STUFF
	static final byte STUFF = (byte) 0x00;
	
	//EOI
	static final byte EOI = (byte) 0xD9;
		
	static FileInputStream inputStream;
	
	static int dc_huf0;
	
	//look for prefix byte and return the next byte which
	//is the marker byte
	static byte scan_for_prefix()
	{
		byte[] buffer = new byte[1];
		
		try
		{
			
		// read fills buffer with data and returns
        // the number of bytes read 
        int total = 0;
        int nRead = 0;
        
        //search for the start of image
        while((nRead = inputStream.read(buffer)) != -1) {
          
        	 total += nRead;
        	 
        	//search for the prefix byte
            if(buffer[0] == PREF)
            {
            	break;
            	
            }
        }
        
        int temp_total = total;
        
        System.out.println("Found prefix byte after " + total + " bytes");
        
        //skip any more prefix bytes
        while((nRead = inputStream.read(buffer)) != -1) {
            
       	 total += nRead;
       	 
       	//search for the prefix byte
           if(buffer[0] != PREF)
           {
           	break;
           	
           }
        }
        
        System.out.println("Found " + (total - temp_total - 1) + " more prefix bytes");
        

        
		}
		catch(IOException ex) {
	        System.out.println( "dec_APP0::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
		
        return buffer[0];
	}
	
	static void dec_SOF0()
	{
		
		try
		{
			
		byte[] buffer = new byte[1];
		
		//SOF0 segment length - 2 bytes
		//first two bytes after the SOF0 marker
		//has the size in bytes of the information
		
		//higher byte
		inputStream.read(buffer);
		
		int size = buffer[0] << 8;
		//lower byte
		inputStream.read(buffer);
		
		//two byte size of the information after APP0 marker
		size = size | buffer[0];
		
		  System.out.println( "dec_SOF0:: SOF0 segment length size " + size);   
		  
		//data precision
		 //This is in bits/sample, usually 8 
		  
		 inputStream.read(buffer);
		 
		  System.out.println( "dec_SOF0:: SOF0 data precision " + buffer[0]); 
		  
		 //image height - 2 bytes
		  //higher byte
			inputStream.read(buffer);
			
			size = buffer[0] << 8;
			//lower byte
			inputStream.read(buffer);
			
			//two byte size of the information after APP0 marker
			size = size | buffer[0];
			
			System.out.println( "dec_SOF0:: SOF0 image height " + size);
      
	     //image width - 2 bytes
	     //higher byte
			inputStream.read(buffer);
					
			size = buffer[0] << 8;
					//lower byte
			inputStream.read(buffer);
					
			//two byte size of the information after APP0 marker
			size = size | buffer[0];
					
		    System.out.println( "dec_SOF0:: SOF0 image width " + size);
		    
		  // Number of components - 1 byte      
		  //Usually 1 = grey scaled, 3 = color YcbCr or YIQ, 4 = color CMYK 
		    
		    inputStream.read(buffer);
		    System.out.println( "dec_SOF0:: SOF0  Number of components " + buffer[0]); 
		    
		    int num_components = buffer[0];
		    
		  /*for each component: 3 bytes
     - component id (1 = Y, 2 = Cb, 3 = Cr, 4 = I, 5 = Q)
     - sampling factors (bit 0-3 vert., 4-7 hor.)
     - quantization table number
     */
		    
		    if(num_components == 1) //only Y component
		    {
		    	 inputStream.read(buffer);
				    System.out.println( "dec_SOF0:: SOF0  Y component id " + buffer[0]); 
				 
				 inputStream.read(buffer);
				 
				 int vert = buffer[0] & 0x0F;
				 int horz = (buffer[0] & 0xF0) >> 4 ;
			
				 System.out.println( "dec_SOF0:: SOF0  Y vert component " + vert);
				 System.out.println( "dec_SOF0:: SOF0  Y horz component " + horz);
				    
				 inputStream.read(buffer);
				    System.out.println( "dec_SOF0:: SOF0  quant table num " + buffer[0]); 
		    }
		    else if(num_components == 3) // Y, Cb, Cr components
		    {
		    	// Y component
		    	inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  Y component id " + buffer[0]); 
			 
			 inputStream.read(buffer);
			 
			 int vert = buffer[0] & 0x0F;
			 int horz = (buffer[0] & 0xF0) >> 4 ;
		
			 System.out.println( "dec_SOF0:: SOF0  Y vert component " + vert);
			 System.out.println( "dec_SOF0:: SOF0  Y horz component " + horz);
			    
			 inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  quant table num " + buffer[0]);
			 
			    // Cb component
			    inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  Cb component id " + buffer[0]); 
			 
			 inputStream.read(buffer);
			 
			  vert = buffer[0] & 0x0F;
			  horz = (buffer[0] & 0xF0) >> 4 ;
		
			 System.out.println( "dec_SOF0:: SOF0  Cb vert component " + vert);
			 System.out.println( "dec_SOF0:: SOF0  Cb horz component " + horz);
			    
			 inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  quant table num " + buffer[0]);
			    
			    // Cr component
			    inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  Cr component id " + buffer[0]); 
			 
			 inputStream.read(buffer);
			 
			vert = buffer[0] & 0x0F;
			horz = (buffer[0] & 0xF0) >> 4 ;
		
			 System.out.println( "dec_SOF0:: SOF0  Cr vert component " + vert);
			 System.out.println( "dec_SOF0:: SOF0  Cr horz component " + horz);
			    
			 inputStream.read(buffer);
			    System.out.println( "dec_SOF0:: SOF0  quant table num " + buffer[0]);   
		    	
		    }
		    else
		    {
		    	System.out.println( "dec_SOF0:: SOF0  Number of components " + buffer[0] + " not implemented ");
		    	//not implemented yet
				  System.exit(0);
		    }
		}
		catch(IOException ex) {
	        System.out.println( "dec_APP0::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
	
		
		
		
	}
	
	/*
	  Marker Identifier       2 bytes      0xff, 0xda identify SOS marker
 Length                       2 bytes      This must be equal to 6+2*(number of components in scan).
    Number of Components in scan  1 byte        This must be >= 1 and <=4 (otherwise error), usually 1 or 3
            Each component        2 bytes      For each component, read 2 bytes. It contains,
                                  1 byte   Component Id (1=Y, 2=Cb, 3=Cr, 4=I, 5=Q),
                                  1 byte   Huffman table to use : bit 0..3 : AC table (0..3) bit 4..7 : DC table (0..3)
         Ignorable Bytes          3 bytes      We have to skip 3 bytes.
	 */
	static void dec_SOS()
	{
		
		try
		{
			
		byte[] buffer = new byte[1];
		
		//DQT segment length - 2 bytes
		//first two bytes after the DQT marker
		//has the size in bytes of the information
		
		//higher byte
		inputStream.read(buffer);
		
		int size = buffer[0] << 8;
		//lower byte
		inputStream.read(buffer);
		
		//two byte size of the information after APP0 marker
		size = size | buffer[0];
		
		  System.out.println( "dec_SOS:: SOS segment length size " + size);   
		  
		inputStream.read(buffer);
		  System.out.println( "dec_SOS:: SOS num of component " + buffer[0]);   
		  
		//component details
		//Y
		 inputStream.read(buffer);
		  System.out.println( "dec_SOS:: Y component id " + buffer[0]);  
		  
		 inputStream.read(buffer); 
		 
		  int ac_table = buffer[0] & 0x0F;
		  int dc_table = (buffer[0] & 0xF0) >> 4;
		
		System.out.println( "dec_SOS:: ac table " + ac_table);  
		System.out.println( "dec_SOS:: dc table " + dc_table);  
		
		//Cb
		 inputStream.read(buffer);
		  System.out.println( "dec_SOS:: Cb component id " + buffer[0]);  
		  
		 inputStream.read(buffer); 
		 
		  ac_table = buffer[0] & 0x0F;
		  dc_table = (buffer[0] & 0xF0) >> 4;
		
		System.out.println( "dec_SOS:: ac table " + ac_table);  
		System.out.println( "dec_SOS:: dc table " + dc_table);  
		
		//Cr
		 inputStream.read(buffer);
		  System.out.println( "dec_SOS:: Cr component id " + buffer[0]);  
		  
		 inputStream.read(buffer); 
		 
		  ac_table = buffer[0] & 0x0F;
		  dc_table = (buffer[0] & 0xF0) >> 4;
		
		System.out.println( "dec_SOS:: ac table " + ac_table);  
		System.out.println( "dec_SOS:: dc table " + dc_table);  
		
		//ignore 3 byte
		inputStream.read(buffer);
		System.out.println( "dec_SOS:: ignored byte 1 " + buffer[0]);  
		inputStream.read(buffer);
		System.out.println( "dec_SOS:: ignored byte 2 " + buffer[0]);  
		inputStream.read(buffer);
		System.out.println( "dec_SOS:: ignored byte 3 " + buffer[0]);  
		
		
		}
		catch(IOException ex) {
	        System.out.println( "dec_SOS::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
	
		
		
		
	}
	
	
	//decode DQT
	static void dec_DQT()
	{
		
		try
		{
			
		byte[] buffer = new byte[1];
		
		//DQT segment length - 2 bytes
		//first two bytes after the DQT marker
		//has the size in bytes of the information
		
		//higher byte
		inputStream.read(buffer);
		
		int size = buffer[0] << 8;
		//lower byte
		inputStream.read(buffer);
		
		//two byte size of the information after APP0 marker
		size = size | buffer[0];
		
		  System.out.println( "dec_DQT:: DQT segment length size " + size);   
		  
		//precision - 4 bits and table id - 4 bits
		// 1 byte
		 //  bit 0..3: number of QT (0..3, otherwise error)
         // bit 4..7: precision of QT, 0 = 8 bit, otherwise 16 bit 
		  
		 inputStream.read(buffer);
		 
		 size = (int) (buffer[0] & 0x0F);
		 
		 System.out.println( "dec_DQT:: DQT table id " + size);   
		 
         size = (int) ((buffer[0] & 0xF0) >>  4) ;
		 
         size = (size == 0) ? (int) 1 : (int) 2;
         
		 System.out.println( "dec_DQT:: DQT precision " + size);   
		 
		 //read 64 bytes(8 bits per DQT value) or 64*2 bytes (16 bits per DQT value) depending on single or double precision
		 
		 System.out.println( "dec_DQT:: DQT table ");   
		 
		 for(int i=0 ; i < (64*size) ; i++)
			{
			 
			  if(size == 1) //single precision
			  {
				  
				  if((i % 8) == 0)
					 {
						 System.out.println(" ");
					 }
				  
				inputStream.read(buffer);
		
				 System.out.print( buffer[0] + " ");			 
					 
			  }
			  else //double precision
			  {
				  //not implemented yet
				  System.exit(0);
			  }
				  
			}
		 
		}
		catch(IOException ex) {
	        System.out.println( "dec_APP0::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
		
		
	}
	
	//decode DHT
	/*
	 DHT: Define Huffman Table:
~~~~~~~~~~~~~~~~~~~~~~~~~~

  - $ff, $c4 (DHT)
  - length (high byte, low byte)
  - HT information (1 byte):
     bit 0..3: number of HT (0..3, otherwise error)
     bit 4   : type of HT, 0 = DC table, 1 = AC table
     bit 5..7: not used, must be 0
  - 16 bytes: number of symbols with codes of length 1..16, the sum of these
    bytes is the total number of codes, which must be <= 256
  - n bytes: table containing the symbols in order of increasing code length
    (n = total number of codes)

 Remarks:
  - A single DHT segment may contain multiple HTs, each with its own
    information byte.

	 */
	
	static void dec_DHT(ArrayList<String> dht_codes, ArrayList<Integer> dht_symbols, int [] symbols_array)
	{
	
		try
		{
			
		byte[] buffer = new byte[1];
		
		//List<Integer> dht_symbols = new ArrayList<Integer>();
		//List<String> dht_codes = new ArrayList<String>();
		
		//SOF0 segment length - 2 bytes
		//first two bytes after the SOF0 marker
		//has the size in bytes of the information
		
		//higher byte
		inputStream.read(buffer);
		
		int size = buffer[0] << 8;
		//lower byte
		inputStream.read(buffer);
		
		//two byte size of the information after APP0 marker
		size = size | buffer[0];
		
		  System.out.println( "dec_DHT:: DHT segment length size " + size);   
		  
		 inputStream.read(buffer);  
		 
		 int num_ht = (buffer[0] & 0x0F);
		 int ht_type = (buffer[0] & 0x10) >> 4;
		
		System.out.println( "dec_DHT:: DHT num HT " + num_ht);   
		System.out.println( "dec_DHT:: DHT ht type " + ht_type);   
		
		//16 bytes - number of symbols with codes of length 1..16
		System.out.println( "dec_DHT:: DHT number of symbols "); 
		
		//int [] symbols_array = new int [16];
		
		for(int i=0 ; i < 16 ; i++)
		{
			 inputStream.read(buffer);  
			 
			 symbols_array[i] = buffer[0];
			 
			 System.out.print(buffer[0] + " ");
			 
		}
		
		System.out.println(" ");
		//DHT symbols
				System.out.println( "dec_DHT:: DHT symbols "); 
				
				/*
				 
				 00 (zero) 01 (one) 
				 Next code would be 10 (two) 
				 100 (four) 
				 Next code would be 101 (five) 
				 1010 (ten) 1011 (eleven) 
				 Next code would be 1100 (twelve) 
				 11000 (twenty four) 11001 (twenty five) 11010 (twenty six) 
				 Next code would be 11011 (twenty seven) 
				 110110 (fifty four) 110111 (fifty five) 111000 (fifty six) 111001 (fifty seven) 111010 (fifty eight) 
				 111011 (fifty nine) 111100 (sixty) 111101 (sixty one) 111110 (sixty two) 
				 
				 */
				
		for(int i=0 ; i < 16 ; i++)
		{
			System.out.print( "dec_DHT:: DHT row " + i + " num codes " + symbols_array[i] + "  "); 
			
			for(int j = 0; j < symbols_array[i]; j++)
			{
			 inputStream.read(buffer);  
			 
			 dht_symbols.add((int)buffer[0]);

			 System.out.print(buffer[0] + " ");
			 
			}
			 
			System.out.println(" ");
		}
		
		System.out.println("dht_symbols " + dht_symbols.size());
		
		 int hufcode = 0;
         int count = 0;
         
         StringBuffer hufcodestring = new StringBuffer();               

         //outer loop - 16 code classes
         for (int i = 0; i < 16; i++) {

             //inner loop - how many codes in each class ?
             for (int j = 0 ; j <  symbols_array[i] ; j++) {

                 //turn the binary number into a string
                 hufcodestring.append(Integer.toBinaryString(hufcode)); 
                 
                 //prepend 0s until the code string is the required length in this class
                 for(int n = hufcodestring.length(); n < i+1; n++) {
                     hufcodestring.insert(0, "0");
                 }
                 
               //  for(int k = 0 ; k < hufcodestring.length(); k++)
               // 	 System.out.print(hufcodestring.charAt(k));
                 
               //  System.out.println("");
                 
                 //put the created Huffman code in an array
                 dht_codes.add(hufcodestring.toString());
                 
                 hufcode++;
                 count ++;
                 
                 hufcodestring.delete(0, hufcodestring.length());
             }
             
            hufcode = hufcode << 1;

         }
			
         count = 0;
         
         for(int i=0 ; i < 16 ; i++)
 		{
 			System.out.print( "dec_DHT:: DHT row " + i + " codes " + "  "); 
 			
 			for(int j = 0; j < symbols_array[i]; j++)
 			{

 			 System.out.print(dht_codes.get(count).substring(0) + " ");
 			 
 			 count++;
 			}
 			 
 			System.out.println(" ");
 		}
         
         System.out.println("dht_codes " + dht_codes.size());
         
		}
		catch(IOException ex) {
	        System.out.println( "dec_DHT::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
		
		
		
		
	}
	
	
	//decode the APP0 marker here
	static void dec_APP0()
	{
		
		try
		{
			
		byte[] buffer = new byte[1];
		
		//length - 2 bytes
		//first two bytes after the APP0 marker
		//has the size in bytes of the information
		
		//higher byte
		inputStream.read(buffer);
		
		int size = buffer[0] << 8;
		//lower byte
		inputStream.read(buffer);
		
		//two byte size of the information after APP0 marker
		size = size | buffer[0];
		
		  System.out.println( "dec_APP0:: APP0 Info size " + size);   
		  
		//identifier - 5 bytes
		String identifier = "";
		
		for(int i=0 ; i < 5; i++)
		{
			inputStream.read(buffer);
			
			identifier += (char)buffer[0];
			
		}
		
		System.out.println( "dec_APP0:: APP0 Identifier " + identifier);   
		
		//version - 2 bytes
		
		//higher byte
		inputStream.read(buffer);
		System.out.print( "dec_APP0:: AAP0 Version " + buffer[0]);   		

		//lower byte
		inputStream.read(buffer);
		System.out.println( "." + buffer[0]);   	
		
		//units - 1 byte
		inputStream.read(buffer);
		System.out.println( "dec_APP0:: AAP0 units " + buffer[0]);
		
		//X density - 2bytes
		       //higher byte
				inputStream.read(buffer);
				
				size = buffer[0] << 8;
				//lower byte
				inputStream.read(buffer);
				
				size = size | buffer[0];
				
				  System.out.println( "dec_APP0:: APP0 X density " + size);  
				  
		//Y density - 2bytes
			    //higher byte
				inputStream.read(buffer);
				
				size = buffer[0] << 8;
				//lower byte
				inputStream.read(buffer);
					
				size = size | buffer[0];
					
				System.out.println( "dec_APP0:: APP0 Y density " + size);  		
				
		// X thumbnail
				inputStream.read(buffer);
				int Xthumbnail = buffer[0];
				
				System.out.println( "dec_APP0:: APP0 X thumbnail " + buffer[0]);  
				
		// Y thumbnail
				inputStream.read(buffer);
				int Ythumbnail = buffer[0];
				
				System.out.println( "dec_APP0:: APP0 Y thumbnail " + buffer[0]); 
			
				int n = Xthumbnail * Ythumbnail;
				
	    //RGB - 3 x n bytes (packed RGB values for thumbnail)
		// n = Xthumbnail X Ythumbnail
				System.out.println( "dec_APP0:: APP0 RGB Vals for thumbnail size " + (3*n)); 
				
				for(int i = 0; i < 3 * n ; i ++)
				{
					inputStream.read(buffer);
					System.out.print( buffer[0] + " "); 
				}
				
				System.out.println("");
				
		}
		catch(IOException ex) {
	        System.out.println( "dec_APP0::Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }
		
		
	}
	
	//convert 8 bit binary number to equivalent string representation
	static String binary_to_string(byte val)
	{
		int mask = 0x80;
		
		StringBuffer binarystring = new StringBuffer();       
		
		int val_to_int = (int) val & 0xFF;
		
		for(int i = 0 ; i < 8 ; i++)
			
		{
			//System.out.println(val_to_int + " " + mask + " " + (val & mask));
			
			if((val_to_int & mask) == 0x00)
			{
				
				binarystring.append("0");
			}		
			else
			{
				binarystring.append("1");	
			}
		
			mask = (mask >> 1) & 0xFF;
			
		}
		
		//System.out.print(binarystring.toString() + " ");
		return binarystring.toString();
		
	}
	
	static int valid_dc_code(ArrayList<String> dht_codes, ArrayList<Integer> dht_symbols, StringBuffer scanstring)
	{
		int i = 0, code_length;
		
		int found_valid_code = 0;
		
		String partial_code = "";
		
		int size_next_bits = 0;
		
		for(code_length = 1 ; code_length < 17 ; code_length++)
			
		{
		
			partial_code = partial_code + scanstring.charAt(code_length - 1);
		
		// i iterates through the list of huffman codes
			// note - each category can have many codes , hence, i does not correspond to a category
		for(i = 0 ; i < dht_codes.size() ; i ++)
		{
			
			if(dht_codes.get(i).equals(partial_code))
			{
				//System.out.println("valid_dc_code:: i " + i);
				
				found_valid_code = 1;
				break;
			}
		}
		
		if(found_valid_code == 1)
		{
			
			break;
		}
		
		}
		
		if(found_valid_code == 1)
		{
		System.out.println("valid_dc_code:: partial_code " + partial_code + " dht table index " + i + " code length  " + code_length);
		
		//delete the huf code which is found
				scanstring.delete(0, code_length);
		
		//get the symbol from dht symbol table
		System.out.println("valid_dc_code::	" + dht_symbols.get(i).byteValue() + " " + dht_symbols.get(i).intValue());
		
		size_next_bits = dht_symbols.get(i).byteValue();
		}
		else
		{
		System.out.println("valid_dc_code:: partial_code " + partial_code + " no valid code " );
		size_next_bits = 0;
		}
		
		return size_next_bits;
		
	}
	
	
	 static int get_dc_coeff(int size_next_bits, StringBuffer scanstring)
	 {
		
		 int dc_coeff = 0;
		 
		 String next_bits = scanstring.substring(0, size_next_bits);
		 
		 System.out.println("get_dc_coeff:: next_bits " + next_bits );
		 
		 scanstring.delete(0, size_next_bits);
		 
		 if(next_bits.charAt(0) == '0')
		 {
			//coefficient is negative 
			 for(int i = 0 ; i < next_bits.length(); i++)
			 {
				 if(next_bits.charAt(i) == '0')
				 {
					dc_coeff += Math.pow(2, ((next_bits.length() - 1) - i)) ;
				 }
			 }
			 
			 dc_coeff = -1 * dc_coeff;
		 }
		 else
		 { //coeff is positive
	
			 for(int i = 0 ; i < next_bits.length(); i++)
			 {
				 if(next_bits.charAt(i) == '1')
				 {
					dc_coeff += Math.pow(2, ((next_bits.length() - 1) - i)) ;
				 }
			 }
			 
		 }
		 
		 return dc_coeff;
	 }
	 
	 static void decode_marker(byte marker)
	 {
		 
		  // is the previous byte SOI ?	
         if(marker == SOI)
         {
       	  System.out.println("Found JPEG start of image - SOI");  
         }
         else if(marker == APP0)
         {
         	System.out.println("Found APP0 marker");  
         	
         	dec_APP0();
         	
         }
         else if(marker == DQT)
         {
         	System.out.println("Found DQT marker");  
         	
         	dec_DQT();
         	       	
         }
         else  if(marker == SOF0)
         {
             System.out.println("Found JPEG start of frame 0 - SOF0");  
              	  
         dec_SOF0();
         
         
         }
        
         else
         {
       	  System.out.println("decode_marker:: " + marker + " marker not found");
       	  
       	  System.exit(0);
       	  
         }
         
		 
		 
	 }
	 
	 
	 static void image_8X8()
	 {
		 
		 
	 }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			
			byte[] buffer = new byte[1];
			 int total = 0;
	            int nRead = 0;
	            
			inputStream = new FileInputStream(jpegfile);
			
			//look for SOI marker
			buffer[0] = scan_for_prefix();
			decode_marker(buffer[0]); 
            
			//APP0 marker
            buffer[0] = scan_for_prefix();
            decode_marker(buffer[0]);       
            
           //DQT marker - table 0
            buffer[0] = scan_for_prefix();
            decode_marker(buffer[0]); 
            
           //DQT marker - table 1
            buffer[0] = scan_for_prefix();
            decode_marker(buffer[0]); 
            
            //SOF marker
            buffer[0] = scan_for_prefix();
            decode_marker(buffer[0]);
            
            //System.out.println("Marker " + Integer.toHexString(buffer[0]));
                
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            ArrayList<String> dht_codes0 = new ArrayList<String>();
            ArrayList<Integer> dht_symbols0 = new ArrayList<Integer>();
            int [] symbols_array0 = new int[16];
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
            
            dec_DHT(dht_codes0, dht_symbols0, symbols_array0);
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            

            ArrayList<String> dht_codes1 = new ArrayList<String>();
            ArrayList<Integer> dht_symbols1 = new ArrayList<Integer>();
            int [] symbols_array1 = new int[16];
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                
            dec_DHT(dht_codes1, dht_symbols1, symbols_array1);
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                 	  
                ArrayList<String> dht_codes2 = new ArrayList<String>();
                ArrayList<Integer> dht_symbols2 = new ArrayList<Integer>();
                int [] symbols_array2 = new int[16];  
                
            dec_DHT(dht_codes2, dht_symbols2, symbols_array2);
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                
                ArrayList<String> dht_codes3 = new ArrayList<String>();
                ArrayList<Integer> dht_symbols3 = new ArrayList<Integer>();
                int [] symbols_array3 = new int[16];  
                
            dec_DHT(dht_codes3, dht_symbols3, symbols_array3);
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == SOS)
            {
                System.out.println("Found JPEG SOS marker");  
                 	  
            dec_SOS();
            
            
            }
            
         //   dec_scan_code();
            
            //now we are reading the huffman codes
            //we read 8 bits at each read
            inputStream.read(buffer);
            
			//convert the 8 bits read into a string
            String temp = binary_to_string(buffer[0]);
            
            //System.out.println(temp);
            
            //scanstring is the container for the huffman bit stream
            StringBuffer scanstring = new StringBuffer();
            
            //put the huffman bitstream into scanstring container
            scanstring.append(temp);
            
            //System.out.println(scanstring.toString());
            
            //read 8 more bits into the scan container
            //
          //  while(inputStream.read(buffer) > 0)
           // {
            inputStream.read(buffer);
            	 temp = binary_to_string(buffer[0]);
            	
            	 scanstring.append(temp); 
            	 
            	 System.out.println(scanstring.toString()); 
            	 
            	 //read size_next_bits from the stream
            	 int size_next_bits = valid_dc_code(dht_codes0, dht_symbols0, scanstring);
            	 
            	 System.out.println(scanstring.toString()); 
            	 
            	 //fill container with more bits
            	 if(scanstring.length() < 16) {
            		 inputStream.read(buffer);
            		 temp = binary_to_string(buffer[0]);
                 	
                	 scanstring.append(temp); 
                	 System.out.println(scanstring.toString()); 
            	 }
            	 
            	int dc_coeff = get_dc_coeff(size_next_bits, scanstring);
            	 
            	System.out.println("dc coeff  "  + dc_coeff);
            	
            	 System.out.println(scanstring.toString()); 
            	 
            	 //fill container with more bits
            	 if(scanstring.length() < 16) {
            		 inputStream.read(buffer);
            		 temp = binary_to_string(buffer[0]);
                 	
                	 scanstring.append(temp); 
                	 System.out.println(scanstring.toString()); 
            	 }
            	 
            	 //ac coeffs
            	 //read size_next_bits from the stream
            	 size_next_bits = valid_dc_code(dht_codes1, dht_symbols1, scanstring);
            	 
                 System.out.println(scanstring.toString()); 
            	 
            	 //fill container with more bits
            	 if(scanstring.length() < 16) {
            		 inputStream.read(buffer);
            		 temp = binary_to_string(buffer[0]);
                 	
                	 scanstring.append(temp); 
                	 System.out.println(scanstring.toString()); 
            	 }
            	 
            	 //read size_next_bits from the stream
            	 size_next_bits = valid_dc_code(dht_codes0, dht_symbols0, scanstring);
            	 
            	 System.out.println(scanstring.toString()); 
            	 
            	 //fill container with more bits
            	 if(scanstring.length() < 16) {
            		 inputStream.read(buffer);
            		 temp = binary_to_string(buffer[0]);
                 	
                	 scanstring.append(temp); 
                	 System.out.println(scanstring.toString()); 
            	 }
            	 
            	dc_coeff = get_dc_coeff(size_next_bits, scanstring);
            	 
             	System.out.println("dc coeff  "  + dc_coeff);
             	
             	 System.out.println(scanstring.toString()); 
             	 
             	 //fill container with more bits
            	 if(scanstring.length() < 16) {
            		 inputStream.read(buffer);
            		 temp = binary_to_string(buffer[0]);
                 	
                	 scanstring.append(temp); 
                	 System.out.println(scanstring.toString()); 
            	 }
            	 
            	 //ac coeffs
            	 //read size_next_bits from the stream
            	 size_next_bits = valid_dc_code(dht_codes1, dht_symbols1, scanstring);
            	 
                 System.out.println(scanstring.toString()); 
                 
            	 // System.out.print(scanstring.length() + " "); 
          //  }
            
           /* System.out.println("");
            
            String my = scanstring.substring(0, (scanstring.length()/64)*5);
            System.out.println(my);*/
            
            /*
            int pad_endof_string = 8 - ( scanstring.length() - (scanstring.length()/8) * 8);
            
            for(int i = 0 ;  i < pad_endof_string; i ++)
            {
            	scanstring.append("F");      
            }
            
            System.out.print(scanstring.length() + " ");  
            
            for(int i = 0 ;  i < (scanstring.length()/8) ; i ++)
            {
            	
            String t = scanstring.substring( (i * 8) , ( ((i + 1 )* 8) - 1) );
            
            System.out.print(t + " ");  
            }
            */
           // valid_dc_code()
        //   String temp = binary_to_string(buffer[0]);
           
         // System.out.println(temp);
            
          /*  
            while(true)
            {
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] != STUFF)
            	break;
            }
           
           */ 
            if(buffer[0] == EOI)
            {
                System.out.println("Found JPEG end of image marker");  
                 	     
            
            }
           /*else
                    {
                  	  System.out.println("Did not find JPEG start of frame 0" + Integer.toHexString(buffer[0]));
                  	  
                  	  System.exit(0);
                  	  
                    }
                
               */   
                  
                 
              
            // Always close files.
            inputStream.close();        

            System.out.println("Read " + total + " bytes");
			
		}
		catch(FileNotFoundException ex) {
			
	        System.out.println( "Unable to open file '" + jpegfile + "'");
	        
	    }
		
	    catch(IOException ex) {
	        System.out.println( "Error reading file '" + jpegfile + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	    }

	}

}
