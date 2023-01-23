package finalproject;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
   Array Implementation of a stack.
*/

public class ArrayStack
{
   private String [] s;  // Holds stack elements
	private int top;   // Stack top pointer
   
   /**
      Constructor.
      @param capacity The capacity of the stack.
   */
	
   public ArrayStack (int capacity)
   {
       s = new String[capacity];
       top = 0;
   }
   
   /**
      The empty method checks for an empty stack.
      @return true if stack is empty.
   */
   
   public boolean empty() 
   { 
       return top == 0; 
   }
   
   /**
      The push method pushes a value onto the stack.
      @param x The value to push onto the stack.
		@exception StackOverflowException When the 
		stack is full.
   */
   
   public void push(String x) 
   {
       if (top == s.length)  
           throw new StackOverFlowException();
       else
       {
          s[top] = x;
          top ++;           
       }         
   }
   
   /** 
      The pop method pops a value off the stack.
      @return The value popped.
		@exception EmptyStackException When the 
		stack is empty.
   */
   
   public String pop()
   {
       if (empty())
           throw new EmptyStackException();
       else
       {
          top--;
          return s[top];
       }
   }
   
   /** 
      The peek method returns the value at the
      top of the stack.
      @return value at top of the stack.
		@exception EmptyStackException When the 
		stack is empty.
   */
   
   String peek()
   {
       if (empty())
           throw new EmptyStackException();
       else
       {
           return s[top-1];
       }
   }
   
   String get(int x)
   {
       if (empty())
           throw new EmptyStackException();
       else
       {
           return s[x];
       }
   }
   
   @Override
   public String toString(){
       return Arrays.toString(s);
   }
}
