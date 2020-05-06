package kellerAutomat;
/** 
 * @author R.N
 *
 * Generic Stack Class
 * 
 * @param <T>
 */
public class Stack<T> {
    private Object[] stack;
    
    /**
     * Constructor initializes object Array with length 0
     */
    public Stack() {
        stack = new Object[0];
    }
    
    /**
     * adds object in at the top/end of the stack
     * @param in
     */
    @SuppressWarnings("unchecked")
    public void push(T in) {
        T[] temp = (T[]) new Object[stack.length + 1];
        for (int i = 0; i < stack.length; i++) {
            temp[i] = (T) stack[i];
        }
        temp[temp.length - 1] = in;
        stack = temp;
    }
    
    /**
     * removes the object at the top/end of the stack if the stack is empty null is returned
     * @return
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        } else {
            T[] temp = (T[]) new Object[stack.length - 1];
            for (int i = 0; i < stack.length - 1; i++) {
                temp[i] = (T) stack[i];
            }
            T val = (T) stack[stack.length - 1];
            stack = temp;
            return val;
        }
    }
    
    /**
     * checks if stack is empty
     * @return
     */
    public boolean isEmpty() {
        return stack.length <= 0;
    }
    
    @Override
    public String toString() {
        String s = "[";
        for (Object o : stack) {
            s += o+", ";
        }
        if (s.length() > 2) {
            s = s.substring(0,s.length()-2);
        }
        s += "]";
        return s;
    }
    
}
