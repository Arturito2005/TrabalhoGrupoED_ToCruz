package TestStacks;

import Exceptions.EmptyCollectionException;
import Interfaces.Stack.StackADT;
import Stacks.LinkedStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLinkedStack {
    private StackADT<String> stack;

    @BeforeEach
    public void setUp() {
        this.stack = new LinkedStack<>();
    }

    @Test
    public void testPopStack() {
        String excepted = "Pop";
        this.stack.push("Pop");
        assertEquals(excepted, this.stack.pop(), "Como a stack não estava vazia foi feito o pop do elemento que ela possuia, nesta caso Pop");
    }

    @Test
    public void testPopEmptyStackException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> stack.pop());
    }

    @Test
    public void testPeekStack() {
        String excepted = "Pop";
        this.stack.push("Pop");
        assertEquals(excepted, this.stack.peek(), "Como a stack não estava vazia foi feito o peek do elemento que ela possuia, nesta caso Pop");
    }

    @Test
    public void testPeekEmptyStackException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> stack.peek());
    }
}
