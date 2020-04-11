package nearest_smaller_elements;

import common.Triple;
import common.Tuple;

import java.util.*;

public class NearestSmallestElements {
    public static void main(String[] args) {
        System.out.println(nearestSmallerElements(Arrays.asList(1, 3, 4, 2, 5, 3, 4, 2)));
    }

    public static <E> Stack<E> addToStack(E element, Stack<E> stack) {
        Stack<E> newStack = new Stack<>();
        newStack.addAll(stack);
        newStack.push(element);
        return newStack;
    }

    public static <E> List<E> addToList(E element, List<E> list) {
        List<E> newList = new ArrayList<>(list);
        newList.add(element);
        return newList;
    }

    public static <E> Tuple<E, Stack<E>> stackPop(Stack<E> stack) {
        Stack<E> newStack = new Stack<E>();
        newStack.addAll(stack);
        E element = newStack.pop();
        return Tuple.a(element, newStack);
    }

    public static List<Tuple<Integer, Integer>> nearestSmallerElements(List<Integer> elements) {
        Queue<Triple<Stack<Integer>, Integer, List<Tuple<Integer, Integer>>>> tailQueue = new LinkedList<>();
        tailQueue.add(Triple.a(new Stack<>(), 0, Collections.emptyList()));
        while (!tailQueue.isEmpty()) {
            Triple<Stack<Integer>, Integer, List<Tuple<Integer, Integer>>> qEntry = tailQueue.remove();
            Stack<Integer> nearestSmallestElementStack = qEntry.left();
            Integer currentListIndex = qEntry.middle();
            List<Tuple<Integer, Integer>> previousResult = qEntry.right();
            if (currentListIndex >= elements.size()) {
                return previousResult;
            } else {
                Integer currentListElement = elements.get(currentListIndex);
                if (nearestSmallestElementStack.empty()) {
                    tailQueue.add(Triple.a(addToStack(currentListElement, nearestSmallestElementStack), currentListIndex + 1, previousResult));
                } else {
                    Integer lastElementInStack = nearestSmallestElementStack.peek();
                    if (currentListElement > lastElementInStack) {
                        tailQueue.add(Triple.a(addToStack(currentListElement, nearestSmallestElementStack), currentListIndex + 1, addToList(Tuple.a(currentListIndex, lastElementInStack), previousResult)));
                    } else {
                        tailQueue.add(Triple.a(stackPop(nearestSmallestElementStack).right(), currentListIndex, previousResult));
                    }
                }
            }
        }
        throw new IllegalStateException("Should never execute loop without returning");
    }
}
