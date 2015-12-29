package jpegdecoder;

import java.io.*;

public class jpegreadfile {

	static String jpegfile = "C:\\DATA\\swamy\\research\\javacode\\samplejpg\\image1.jpg";
	
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
	
	//look for prefix byte and return the next byte whic
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
		inputStream.read(buffer);
		inputStream.read(buffer);
		
		
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
	
	static void dec_DHT()
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
		
		  System.out.println( "dec_DHT:: DHT segment length size " + size);   
		  
		 inputStream.read(buffer);  
		 
		 int num_ht = (buffer[0] & 0x0F);
		 int ht_type = (buffer[0] & 0x10) >> 4;
		
		System.out.println( "dec_DHT:: DHT num HT " + num_ht);   
		System.out.println( "dec_DHT:: DHT ht type " + ht_type);   
		
		//16 bytes - number of symbols with codes of length 1..16
		System.out.println( "dec_DHT:: DHT number of symbols "); 
		
		int [][] symbols_array = new int [16][1];
		
		for(int i=0 ; i < 16 ; i++)
		{
			 inputStream.read(buffer);  
			 
			 symbols_array[i][0] = buffer[0];
			 
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
			System.out.print( "dec_DHT:: DHT row " + i + " num codes " + symbols_array[i][0] + "  "); 
			
			for(int j = 0; j < symbols_array[i][0]; j++)
			{
			 inputStream.read(buffer);  
			 
			 System.out.print(buffer[0] + " ");
			}
			 
			System.out.println(" ");
		}
		
	
		
			
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			
			byte[] buffer = new byte[1];
			 int total = 0;
	            int nRead = 0;
	            
			inputStream = new FileInputStream(jpegfile);
			
			buffer[0] = scan_for_prefix();
			
                // is the previous byte SOI ?	
                  if(buffer[0] == SOI)
                  {
                	  System.out.println("Found JPEG start of image");  
                  }
                  else
                  {
                	  System.out.println("Did not find JPEG start of image");
                	  
                	  System.exit(0);
                	  
                  }
       
                 
             buffer[0] = scan_for_prefix();
                  
            //we break from above while
            //check buffer to see what marker was hit in the while loop above
            if(buffer[0] == APP0)
            {
            	System.out.println("Found APP0 marker");  
            	
            	dec_APP0();
            	
            }
           
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DQT)
            {
            	System.out.println("Found DQT marker");  
            	
            	dec_DQT();
            	
            	
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DQT)
            {
            	System.out.println("Found DQT marker");  
            	
            	dec_DQT();
            	
            	
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
           
            if(buffer[0] == SOF0)
             {
                 System.out.println("Found JPEG start of frame 0");  
                  	  
             dec_SOF0();
             
             
             }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                 	  
            dec_DHT();
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                 	  
            dec_DHT();
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                 	  
            dec_DHT();
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == DHT)
            {
                System.out.println("Found JPEG DHT marker");  
                 	  
            dec_DHT();
            
            
            }
            
            buffer[0] = scan_for_prefix();
            
            System.out.println("Marker " + Integer.toHexString(buffer[0]));
            
            if(buffer[0] == SOS)
            {
                System.out.println("Found JPEG SOS marker");  
                 	  
            dec_SOS();
            
            
            }
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
