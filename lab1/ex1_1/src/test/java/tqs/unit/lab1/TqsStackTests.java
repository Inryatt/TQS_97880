
package tqs.unit.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;


class TqsStackTests {

	TqsStack<String> stack;

	@BeforeEach
	void init(){
		stack = new TqsStack<String>();
	}

	@Test
	@DisplayName("Stack Empty on Construction")
	void TqsStackEmpty(){
		assertTrue(stack.isEmpty());;
	}

	@Test
	@DisplayName("Stack has size 0 on Construction")
	void TqsStackSizeZero(){
		assertTrue(stack.size()==0);;
	}

	@Test
	@DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
	void NPushes(){
		Random rn = new Random();
		Integer n=rn.nextInt(9)+1;
		for (int i=0;i<n;i++){	
			stack.push("test");
		}
		assertFalse(stack.isEmpty());
		assertTrue(stack.size()==n);
	}
	
	@Test	
	@DisplayName("If one pushes x then pops, the value popped is x.")
	void push_and_pop(){		
		String n = "push_and_peek";
		stack.push(n);
		Object popped = stack.pop();
		assertEquals(popped, n);
	}

	@Test
	@DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
	void push_and_peek(){
		String n = "push_and_peek";
		stack.push(n);
		Integer sizeBefore = stack.size();
		Object peeked = stack.peek();
		Integer sizeAfter = stack.size();
		assertEquals(peeked, n);
		assertEquals(sizeBefore, sizeAfter);
	}

	@Test
	@DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
	void nPops(){
		Integer size = stack.size();
		for (int i=0;i<size;i++){
			stack.pop();
		}
		assertTrue(stack.isEmpty());
		assertTrue(stack.size()==0);
	}

	@Test
	@DisplayName("Popping from an empty stack does throw a NoSuchElementException")
	void popEmpty(){
		assertTrue(stack.isEmpty());
		assertThrows(NoSuchElementException.class,()->stack.pop());
	}

	@Test
	@DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
	void peekEmpty(){
		assertTrue(stack.isEmpty());
		assertThrows(NoSuchElementException.class,()->stack.peek());
	}

	@Test
	@DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
	void pushFull(){
		stack = new TqsStack<String>(5);
		for (int i=0;i<5;i++){
			stack.push("test");
		}
		assertTrue(stack.size()==5);
		assertThrows(IllegalStateException.class,()->stack.push("test"));
	}

	@AfterEach
	void teardown(){
		stack = null;
	}
}
