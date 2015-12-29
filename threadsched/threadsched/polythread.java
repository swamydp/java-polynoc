package threadsched;

public class polythread implements Runnable{

	public void run()
	{
		
	System.out.println("polythread:: id is " + Thread.currentThread().getId());	
	
		
		try {
			Thread.sleep(10000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		System.out.println("polythread:: id is " + Thread.currentThread().getId() + " woke up");	
	}
	
	public polythread()
	{
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
