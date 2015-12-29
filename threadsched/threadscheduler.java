package threadsched;

import java.util.*;

public class threadscheduler implements Runnable{

	//number of threads created for this polymorphic scheduler
	public static final int NUMTHREADS = 5;
	
	//Ready queue of scheduler - Ready to run thread are held here
	public Queue <polythread> Poly_Ready_Queue;
	
	//Run queue of scheduler - Ready to run thread are held here
	public Queue <polythread> Poly_Run_Queue;
	
	Thread t;
	
	public int interrupt_number;
	
	public int iter_number;
	
	public threadscheduler()
	{
		interrupt_number = 0;
		iter_number = 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public void run()
	{
		
		System.out.println("Threadscheduler:: id is " + Thread.currentThread().getId());	
		
		while(true)
		{
			if(interrupt_number > 5)
				break;
			
			if(Thread.currentThread().isInterrupted())
			{
				interrupt_number ++;
				
			 System.out.println("Thread sched:: interrupted" + interrupt_number);
			}
			
			iter_number++;
			
			 System.out.println("Thread sched:: iter number" + iter_number);
		}
/*
		//Create the poly threads here
		polythread [] Poly_Threads = new polythread [NUMTHREADS];
		
		for(int i = 0; i < NUMTHREADS;  i++)
			Poly_Threads[i] = new polythread();
		
		//create the ready queue
		Poly_Ready_Queue = new LinkedList<polythread>();
		
		//create the run queue
		Poly_Run_Queue = new LinkedList<polythread>();
				
		//System.out.println("Queue size is " + Poly_Run_Queue.size());	
		
		//add the ready threads into the queue - initially all threads ready
		for(int i = 0 ; i < Poly_Threads.length ; i ++)
		{
			Poly_Ready_Queue.add(Poly_Threads[i]);
					
		}
		
		//System.out.println("Queue size after adding threads " + Poly_Run_Queue.size());	
		
		//scan queue and run each thread
		while(!Poly_Ready_Queue.isEmpty())
		{
			
		//	System.out.println("Loop iter " + Poly_Run_Queue.size());	
			polythread curthread = Poly_Ready_Queue.remove();
			
			t = new Thread(curthread);
		
			t.start();
			
			//make threads execute serially
			try{
				t.join();		
			}catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			
			
			Poly_Run_Queue.add(curthread);
		}
			
		*/
	}

}

/*
//scan queue and run each thread
while(!Poly_Run_Queue.isEmpty())
{
	
//	System.out.println("Loop iter " + Poly_Run_Queue.size());	
	polythread curthread = Poly_Run_Queue.remove();
	
	t = new Thread(curthread);

	t.start();
	
}*/

/*
try
{
t.join();
}
catch(InterruptedException e){
	
	System.out.println("My id is " + Thread.currentThread().getId());	
}*/