package tqs.unit.lab1;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<E> {

	ArrayList<E> stack;
	Integer bound;
	public TqsStack(){
		stack = new ArrayList<E>();
		bound = null;
	}
	public TqsStack(Integer bound){
		stack = new ArrayList<E>();
		this.bound = bound;
	}
	public void push(E item){
		if (bound!=null){
			if (stack.size()==bound){
				throw new IllegalStateException();
			}
			else
				stack.add(item);
		}
		else
			stack.add(item);
		
		
	}

	public E pop(){
		if (stack.size()==0){
			throw new NoSuchElementException();
		}
		return stack.remove(stack.size()-1);
	}

	public E peek(){
		if (stack.size()==0){
			throw new NoSuchElementException();
		}
		return stack.get(stack.size()-1);
	}

	public Integer size(){
		return stack.size();
	}

	public Boolean isEmpty(){

		return stack.isEmpty();
	}

}
