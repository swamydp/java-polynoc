package threadsched;

//Timer interrupt class
//generate an interrupt every 
public class PolyTimer implements Runnable {

     Thread Polysched;
	 int stop_timer;
	 
	public PolyTimer()
	{
		stop_timer = 0;
		Polysched = null;
	}
	
	public PolyTimer(Thread T)
	{
		stop_timer = 0;
		Polysched = T;
		
	}
	
	public void stop_timer_now(int stop_now)
	{
		
		stop_timer = stop_now;
		
	}
	
	public void run()
	{
		while(!Thread.currentThread().isInterrupted())
		{
			
		 try
		 {
		
			
			Thread.sleep(1);
			
			Polysched.interrupt();
			
		 }catch (InterruptedException e) {
            //e.printStackTrace();
			 System.out.println("PolyTimer:: sleep threw exception"); 
			 Thread.currentThread().interrupt();
        }
		
			
			stop_timer ++;
			
			System.out.println("PolyTimer: " + stop_timer);
		
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
