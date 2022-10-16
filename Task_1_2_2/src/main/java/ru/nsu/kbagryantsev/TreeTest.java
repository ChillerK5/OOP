package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@SuppressWarnings("CollectionAddedToSelf")
class TreeTest {
    /**
     * Tree for tests.
     */
    private Tree<Character> sample;

    Tree<Character> testTree() {
        Tree<Character> root = new Tree<>();
        root.add('A');
        Tree<Character> nodeB = root.add(root, 'B');
        Tree<Character> nodeC = root.add(root, 'C');
        nodeC.add('F');
        Tree<Character> nodeD = root.add(nodeB, 'D');
        nodeD.add('G');
        nodeD.add('H');
        Tree<Character> nodeE = root.add(nodeB, 'E');
        nodeE.add('I');
        nodeE.add('J');
        return root;
    }

    @Nested
    @DisplayName("add")
    class AddTest {
        @Test
        @DisplayName("Simple tree")
        void simple() {
            sample = new Tree<>();
            sample.add('A');
            Tree<Character> nodeB = sample.add(sample, 'B');
            Tree<Character> nodeC = sample.add(sample, 'C');
            Tree<Character> nodeD = sample.add(nodeB, 'D');
            Tree<Character> nodeE = sample.add(nodeB, 'E');
            nodeC.add('F');
            nodeD.add('G');
            nodeE.add('H');

            Assertions.assertEquals(sample.size(), 8);
            Assertions.assertEquals(nodeB.size(), 4);
            Assertions.assertEquals(nodeC.size(), 1);
            Assertions.assertEquals(nodeD.size(), 1);
            Assertions.assertEquals(nodeE.size(), 1);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertTrue(sample.isEmpty());
        }
    }

    @Nested
    @DisplayName("addAll")
    class AddAllTest {
        /**
         * Sample collection.
         */
        private ArrayList<Character> entities;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            entities = new ArrayList<>();
        }

        @Test
        @DisplayName("Add into root node")
        void root() {
            entities.add('R');
            entities.add('R');
            entities.add('R');
            Assertions.assertTrue(sample.addAll(entities));
            Assertions.assertEquals(sample.size(), 13);
        }

        @Test
        @DisplayName("Add into some node")
        void basic() {
            sample = new Tree<>();
            sample.add('A');
            Tree<Character> nodeB = sample.add(sample, 'B');
            nodeB.add('C');
            entities.add('R');
            entities.add('R');
            entities.add('R');
            Assertions.assertTrue(nodeB.addAll(entities));
            Assertions.assertEquals(sample.size(), 6);
        }

        @Test
        @DisplayName("Add into an empty tree")
        void present() {
            sample = new Tree<>();
            entities.add('R');
            entities.add('R');
            entities.add('R');
            Assertions.assertTrue(sample.addAll(entities));
            Assertions.assertEquals(sample.size(), 3);
        }
    }

    @Nested
    @DisplayName("bfs")
    class BfsTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Simple tree")
        void simple() {
            Iterator<Character> dfs = sample.bfs();
            Assertions.assertEquals(dfs.next(), 'A');
            Assertions.assertEquals(dfs.next(), 'B');
            Assertions.assertEquals(dfs.next(), 'C');
            Assertions.assertEquals(dfs.next(), 'D');
            Assertions.assertEquals(dfs.next(), 'E');
            Assertions.assertEquals(dfs.next(), 'F');
            Assertions.assertEquals(dfs.next(), 'G');
            Assertions.assertEquals(dfs.next(), 'H');
            Assertions.assertEquals(dfs.next(), 'I');
            Assertions.assertEquals(dfs.next(), 'J');
            Assertions.assertThrowsExactly(NoSuchElementException.class,
                    dfs::next);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertThrowsExactly(NullPointerException.class,
                    () -> sample.bfs());
        }
    }

    @Nested
    @DisplayName("clear")
    class ClearTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @SuppressWarnings("ConstantConditions")
        @Test
        @DisplayName("Simple tree")
        void simple() {
            sample.clear();
            Assertions.assertTrue(sample.isEmpty());
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertTrue(sample.isEmpty());
        }
    }

    @Nested
    @DisplayName("contains")
    class ContainsTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Element is contained")
        void success() {
            Assertions.assertTrue(sample.contains('J'));
        }

        @Test
        @DisplayName("Element is not contained")
        void failure() {
            Assertions.assertFalse(sample.contains('M'));
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertFalse(sample.contains('A'));
        }
    }

    @Nested
    @DisplayName("containsAll")
    class ContainsAllTest {
        /**
         * Sample collection.
         */
        private ArrayList<Character> entities;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            entities = new ArrayList<>();
        }

        @Test
        @DisplayName("All elements are present")
        void present() {
            entities.add('A');
            entities.add('E');
            entities.add('F');
            Assertions.assertTrue(sample.containsAll(entities));
        }

        @Test
        @DisplayName("Some of the elements are not present")
        void notPresent() {
            entities.add('A');
            entities.add('E');
            entities.add('K');
            Assertions.assertFalse(sample.containsAll(entities));
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertThrowsExactly(NullPointerException.class,
                    () -> sample.containsAll(entities));
        }
    }

    @Nested
    @DisplayName("dfs")
    class DfsTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Simple tree")
        void simple() {
            Iterator<Character> dfs = sample.dfs();
            Assertions.assertEquals(dfs.next(), 'A');
            Assertions.assertEquals(dfs.next(), 'B');
            Assertions.assertEquals(dfs.next(), 'D');
            Assertions.assertEquals(dfs.next(), 'G');
            Assertions.assertEquals(dfs.next(), 'H');
            Assertions.assertEquals(dfs.next(), 'E');
            Assertions.assertEquals(dfs.next(), 'I');
            Assertions.assertEquals(dfs.next(), 'J');
            Assertions.assertEquals(dfs.next(), 'C');
            Assertions.assertEquals(dfs.next(), 'F');
            Assertions.assertThrowsExactly(NoSuchElementException.class,
                    dfs::next);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertThrowsExactly(NullPointerException.class,
                    () -> sample.dfs());
        }
    }

    @Nested
    @DisplayName("isEmpty")
    class EmptyTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Simple tree")
        void simple() {
            Assertions.assertFalse(sample.isEmpty());
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertTrue(sample.isEmpty());
        }
    }

    @Nested
    @DisplayName("iterator")
    class IteratorTest {
        /**
         * Iterator over a sample tree.
         */
        private Iterator<Character> iterator;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            iterator = sample.iterator();
        }

        @Test
        @Tag("next")
        @DisplayName("Simple tree")
        void simple() {
            Assertions.assertEquals(iterator.next(), 'A');
            Assertions.assertEquals(iterator.next(), 'B');
            Assertions.assertEquals(iterator.next(), 'C');
            Assertions.assertEquals(iterator.next(), 'D');
            Assertions.assertEquals(iterator.next(), 'E');
            Assertions.assertEquals(iterator.next(), 'F');
            Assertions.assertEquals(iterator.next(), 'G');
            Assertions.assertEquals(iterator.next(), 'H');
            Assertions.assertEquals(iterator.next(), 'I');
            Assertions.assertEquals(iterator.next(), 'J');
            Assertions.assertThrowsExactly(NoSuchElementException.class,
                    iterator::next);
        }

        @Test
        @Tag("next")
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertThrowsExactly(NullPointerException.class,
                    () -> sample.iterator());
        }

        @Test
        @Tag("hasNext")
        @DisplayName("Next element exists")
        void success() {
            Assertions.assertTrue(iterator.hasNext());
        }

        @Test
        @Tag("hasNext")
        @DisplayName("Next element does not exist")
        void failure() {
            sample = new Tree<>();
            sample.add('A');
            iterator = sample.iterator();
            iterator.next();
            Assertions.assertFalse(iterator.hasNext());
        }
    }

    @Nested
    @DisplayName("remove")
    class RemoveTest {
        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Element exists")
        void exists() {
            boolean check = sample.remove('E');
            Assertions.assertTrue(check);
            Iterator<Character> iterator = sample.iterator();
            Assertions.assertEquals(iterator.next(), 'A');
            Assertions.assertEquals(iterator.next(), 'B');
            Assertions.assertEquals(iterator.next(), 'C');
            Assertions.assertEquals(iterator.next(), 'D');
            Assertions.assertEquals(iterator.next(), 'F');
            Assertions.assertEquals(iterator.next(), 'G');
            Assertions.assertEquals(iterator.next(), 'H');
            Assertions.assertEquals(sample.size(), 7);
        }

        @Test
        @DisplayName("Element does not exist")
        void notExists() {
            boolean check = sample.remove('K');
            Assertions.assertFalse(check);
            Assertions.assertEquals(sample.size(), 10);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Assertions.assertTrue(sample.remove('A'));
        }

        @Test
        @DisplayName("Root is to be removed")
        void root() {
            sample.remove('A');
            Assertions.assertTrue(sample.isEmpty());
        }

        @Test
        @DisplayName("Element has duplicates around the tree")
        void duplicates() {
            sample = new Tree<>();
            sample.add('A');
            sample.add('E');
            Tree<Character> nodeB = sample.add(sample, 'B');
            sample.add('E');
            nodeB.add('E');
            nodeB.add('D');
            nodeB.add('E');
            Tree<Character> nodeC = sample.add(sample, 'C');
            nodeC.add('F');
            nodeC.add('E');
            nodeC.add('G');

            boolean check = sample.remove('E');
            Assertions.assertTrue(check);
            Iterator<Character> iterator = sample.iterator();
            Assertions.assertEquals(iterator.next(), 'A');
            Assertions.assertEquals(iterator.next(), 'B');
            Assertions.assertEquals(iterator.next(), 'C');
            Assertions.assertEquals(iterator.next(), 'D');
            Assertions.assertEquals(iterator.next(), 'F');
            Assertions.assertEquals(iterator.next(), 'G');
            Assertions.assertEquals(sample.size(), 6);
        }
    }

    @Nested
    @DisplayName("removeAll")
    class RemoveAllTest {
        /**
         * Sample collection.
         */
        private ArrayList<Character> entities;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            entities = new ArrayList<>();
        }

        @Test
        @DisplayName("All elements are trivially removed")
        void trivial() {
            entities.add('D');
            entities.add('E');
            entities.add('F');
            Assertions.assertTrue(sample.removeAll((entities)));
            Assertions.assertEquals(sample.size(), 3);
        }

        @Test
        @DisplayName("One of the elements is the root")
        void root() {
            entities.add('A');
            entities.add('E');
            entities.add('K');
            Assertions.assertTrue(sample.removeAll((entities)));
            Assertions.assertTrue(sample.isEmpty());
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            entities.add('A');
            entities.add('E');
            entities.add('K');
            Assertions.assertTrue(sample.removeAll((entities)));
            Assertions.assertTrue(sample.isEmpty());
        }
    }

    @Nested
    @DisplayName("retainAll")
    class RetainAllTest {
        /**
         * Sample collection.
         */
        private ArrayList<Character> entities;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            entities = new ArrayList<>();
        }

        @Test
        @DisplayName("Collection has all main parts of a tree")
        void trivial() {
            entities.add('A');
            entities.add('B');
            entities.add('C');
            Assertions.assertTrue(sample.retainAll((entities)));
            Assertions.assertEquals(sample.size(), 3);
        }

        @Test
        @DisplayName("One of the missing elements is the root")
        void root() {
            entities.add('B');
            entities.add('C');
            entities.add('D');
            Assertions.assertTrue(sample.retainAll((entities)));
            Assertions.assertTrue(sample.isEmpty());
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            entities.add('B');
            entities.add('C');
            entities.add('D');
            Assertions.assertTrue(sample.retainAll((entities)));
            Assertions.assertTrue(sample.isEmpty());
        }
    }

    @Nested
    @DisplayName("size")
    class SizeTest {

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
        }

        @Test
        @DisplayName("Simple tree")
        void simple() {
            Assertions.assertEquals(sample.size(), 10);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();

            Assertions.assertEquals(sample.size(), 0);
        }
    }


    @Nested
    @DisplayName("toArray")
    class ToArrayTest {
        /**
         * Array to compare with.
         */
        private Object[] sampleArray;

        @BeforeEach
        void initialiseTree() {
            sample = testTree();
            sampleArray = new Object[]{
                'A', 'B', 'C',
                'D', 'E', 'F',
                'G', 'H', 'I', 'J'};
        }

        @Test
        @DisplayName("Simple tree")
        void simple() {
            Object[] treeArray = sample.toArray();
            Assertions.assertArrayEquals(treeArray, sampleArray);
        }

        @Test
        @DisplayName("Empty tree")
        void empty() {
            sample = new Tree<>();
            Object[] treeArray = sample.toArray();
            Assertions.assertArrayEquals(treeArray, new Object[]{});
        }
    }

    @Nested
    @DisplayName("toArray(1)")
    class ToArray2Test {
        @Test
        @DisplayName("Unsupported")
        void unsupported() {
            sample = new Tree<>();
            Assertions.assertThrowsExactly(UnsupportedOperationException.class,
                    () -> sample.toArray(new Character[1]));
        }
    }
}
