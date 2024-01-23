//Daniel Dockins 4/23/2023
//Final Project Web-CAT
//https://introcs.cs.princeton.edu/java/43stack/ResizingArrayQueue.java.html
//https://www.cs.princeton.edu/courses/archive/spr17/cos126/precepts/Queue.java
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class PopcornQueue<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];
    private int size;
    private final Random random;
    // construct an empty popcorn queue
    public PopcornQueue() {
        size = 0;
        random = new Random();
    }
    // is the popcorn queue empty?
    public boolean empty() {
        return size == 0;
    }
    // return the number of items on the popcorn queue
    public int size() {
        return size;
    }
    // add the item
    public void insert(Item item) {
        if (item == null) throw new IllegalArgumentException("Cant insert null item");
        if (size == items.length) resize(items.length * 2);
        items[size++] = item;
    }
    // remove and return a random item
    public Item remove() {
        if (empty()) throw new NoSuchElementException("PopcornQueue empty");
        int randomIndex = random.nextInt(size);
        Item removed = items[randomIndex];
        items[randomIndex] = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return removed;
    }
    // return a random item (but do not remove it
    public Item sample() {
        if (empty()) throw new NoSuchElementException("PopcornQueue empty");
        int randomIndex = random.nextInt(size);
        return items[randomIndex];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new PopcornQueueIterator();
    }

    private void resize(int capacity) {
        Item[] resized = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(items, 0, resized, 0, size);
        items = resized;
    }

    private class PopcornQueueIterator implements Iterator<Item> {
        private int currentIndex;
        private final Item[] shuffledItems = (Item[]) new Object[size];

        public PopcornQueueIterator() {
            currentIndex = 0;
            System.arraycopy(items, 0, shuffledItems, 0, size);
            shuffle(shuffledItems);
        }

        public boolean hasNext() {
            return currentIndex < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No items");
            return shuffledItems[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove op not supported");
        }

        private void shuffle(Item[] array) {
            for (int i = 0; i < array.length; i++) {
                int randomIndex = random.nextInt(i + 1);
                Item temp = array[randomIndex];
                array[randomIndex] = array[i];
                array[i] = temp;
            }
        }
    }
    // unit testing
    public static void main(String[] args) {
        int n = 5;
        PopcornQueue<Integer> queue = new PopcornQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.insert(i);
        for (int a : queue) {
            for (int b : queue)
                System.out.print(a + "-" + b + " ");
            System.out.println();
        }
    }
}