public class JA
{
  
 public static void main(String[] args)
 {
  int move = 0;
  int open = 0;
  int close = 0;
  int command = 1;
  int sleep = 1;
  
  
  
  while(true)
  {
  if(open == 1 && move == 1)
  {
    sleep = 0;
    move = 0;
    System.out.println("asdfasdasfd");
    
  }
  
  
  if(command == 1)
  {
   move = 1;
   sleep = 0;
   open = 1;
  }
  
  }
   
   
 }
}