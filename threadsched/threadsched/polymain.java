package threadsched;



public class polymain {

 static threadscheduler polysched;

 static threadscheduler Polysched;
 
 static Thread Polysched_thread;
 
 static PolyTimer Timer_interrupt;
 
 static Thread Timer_interrupt_thread;
 
 //static polythread p1;
 
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//polysched = new threadscheduler();
		
		//(new Thread(polysched)).start();
		

		//(new Thread(new polythread())).start();

		Polysched  = new threadscheduler();
		
		Thread Polysched_thread = new Thread(Polysched);
		
		Timer_interrupt = new PolyTimer(Polysched_thread);
		
		Timer_interrupt_thread = new Thread(Timer_interrupt);
		
		Polysched_thread.start();
		
		
		Timer_interrupt_thread.start();
		
		try
		{
		Thread.sleep(10);
		
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		Timer_interrupt_thread.interrupt();
	}

	 
}
