package heap;

public class ReorderLogs {
    String[] heap;
    int maxHeapSize;
    int currentHeapSize;

    public String[] reorderLogFiles(String[] logs) {

        //Get heap size
        maxHeapSize = logs.length;

        //Intialize heap size
        currentHeapSize = 0;

        //Allocate heap
        heap = new String[maxHeapSize];

        String[] tail = new String[maxHeapSize];
        int y = 0;
        for (String s : logs) {
            int indexOfFirstSpace = s.indexOf(" ");
            String content = s.substring(indexOfFirstSpace + 1);
            int indexOfSecondSpace = content.indexOf(" ");
            String firstElement = indexOfSecondSpace == -1 ? content : content.substring(0, indexOfSecondSpace);

            if (isNumeric(firstElement)) {
                tail[y] = s;
                y++;
            } else {
                push(s);
            }
        }

        String[] result = new String[maxHeapSize];
        int i = 0;
        while (notEmpty()) {
            result[i] = pop();
            i++;
        }

        int z = 0;
        while (i < maxHeapSize && z < y) {
            result[i] = tail[z];
            i++;
            z++;
        }

        return result;
    }

    public static boolean isNumeric(String str) {
        boolean result = false;
        for (char c : str.toCharArray()) {
            boolean isDigit = Character.isDigit(c);
            result = isDigit;
            if (!isDigit) break;
        }
        return result;
    }


    private void printHeap() {
        for (String s : heap) {
            System.out.println(s);
        }
    }

    private boolean smallerThan(String a, String b) {
        int aIndexOfFirstSpace = a.indexOf(" ");
        int bIndexOfFirstSpace = b.indexOf(" ");

        String aContent = a.substring(aIndexOfFirstSpace + 1);
        String bContent = b.substring(bIndexOfFirstSpace + 1);

        boolean aIsLetters = !Character.isDigit(aContent.charAt(0));
        boolean bIsLetters = !Character.isDigit(bContent.charAt(0));

        int result = aContent.compareTo(bContent);
        if (result == 0) {
            String aIdentifier = a.substring(0, aIndexOfFirstSpace);
            String bIdentifier = b.substring(0, bIndexOfFirstSpace);
            return aIdentifier.compareTo(bIdentifier) < 0;
        } else {
            return result < 0;
        }
    }

    private void push(String newElement) {
        if (currentHeapSize < maxHeapSize) {
            heap[currentHeapSize] = newElement;
            currentHeapSize++;
            bubbleUp();
        } else {
            throw new IllegalStateException("Maximum size reached");
        }
    }

    private String pop() {
        String result;
        if (currentHeapSize > 0) {
            result = heap[0];
            heap[0] = heap[currentHeapSize - 1];
            currentHeapSize--;
            bubbleDown();
        } else {
            throw new IllegalStateException("Heap is empty");
        }
        return result;
    }

    private void bubbleUp() {
        int currentIndex = currentHeapSize - 1;
        while (hasParent(currentIndex) && smallerThan(heap[currentIndex], heap[parent(currentIndex)])) {
            swap(currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }

    private void bubbleDown() {
        int currentIndex = 0;
        while (hasLeftChild(currentIndex)) {
            int smallestChild = leftChild(currentIndex);

            if (hasRightChild(currentIndex) && smallerThan(heap[rightChild(currentIndex)], heap[smallestChild])) {
                smallestChild = rightChild(currentIndex);
            }

            if (smallerThan(heap[smallestChild], heap[currentIndex])) {
                swap(smallestChild, currentIndex);
            }

            currentIndex = smallestChild;
        }
    }

    private void swap(int from, int to) {
        String temp = heap[from];
        heap[from] = heap[to];
        heap[to] = temp;
    }

    private int parent(int child) {
        return (child - 1) / 2;
    }

    private int leftChild(int parent) {
        return parent * 2 + 1;
    }

    private int rightChild(int parent) {
        return parent * 2 + 2;
    }

    private boolean notEmpty() {
        return currentHeapSize > 0;
    }

    private boolean hasParent(int index) {
        return index > 0 && index < currentHeapSize && parent(index) < currentHeapSize;
    }

    private boolean hasLeftChild(int index) {
        return index >= 0 && index < currentHeapSize && leftChild(index) < currentHeapSize;
    }

    private boolean hasRightChild(int index) {
        return index >= 0 && index < currentHeapSize && rightChild(index) < currentHeapSize;
    }
}

