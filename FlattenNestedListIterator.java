// Time Complexity : O(n) where n = total no of integers across all lists
// Space Complexity : O(h)  where h = max depth of nested lists in recursion stacks
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Approach -
//   - Use a stack of iterators to traverse the nested list structure.
//   - When encountering a nested list, push its iterator onto the stack.
//   - When encountering an integer, store it as nextEl to be returned by next().
//   - Always advance the stack only when next() or hasNext() is called (lazy evaluation).
//   - Continue until the stack is empty and no integers are left.

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 */
interface NestedInteger {
     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     public boolean isInteger();
         // @return the single integer that this NestedInteger holds, if it holds a single integer
         // Return null if this NestedInteger holds a nested list
         public Integer getInteger();
         // @return the nested list that this NestedInteger holds, if it holds a nested list
         // Return empty list if this NestedInteger holds a single integer
         public List<NestedInteger> getList();
 }

public class FlattenNestedListIterator implements Iterator<Integer> {
    Stack<Iterator<NestedInteger>> st;
    // NestedInteger nextEl;
    Integer nextEl;

    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        this.st = new Stack<>();
        st.push(nestedList.iterator());
        process();
    }

    private void process() {
        nextEl = null;
        while(!st.isEmpty()) {
            if(!st.peek().hasNext()) {
                st.pop();
            } else {
                NestedInteger temp = st.peek().next();
                if(temp.isInteger()) {
                    nextEl = temp.getInteger();
                    break;
                } else {
                    st.push(temp.getList().iterator());
                }
            }
        }
    }

    @Override
    public Integer next() {
        Integer temp = nextEl;
        process();  //find next valid element for next processing
        return temp;
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;  //if valid nextEl then return true else false
    }

}
/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */