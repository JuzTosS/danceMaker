package com.juztoss.dancemaker.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Kirill on 4/9/2016.
 */
public class SequenceGeneratorTest {

    @Test
    public void testGenerateNew() throws Exception {

        String testName = "Test name";
        List<DanceElement> emptyList = new ArrayList<>();
        SequenceGenerator generator = new SequenceGenerator();

        DanceSequence resultSequence = generator.generateNew(testName, 0, emptyList);
        assertNotEquals(resultSequence, null);
        assertEquals(resultSequence.getName(), testName);
        assertEquals(resultSequence.getElements().size(), 0);
        assertEquals(resultSequence.getId(), 0);
        assertEquals(resultSequence.getLength(), 0);
        assertEquals(resultSequence.isNew(), true);

    }
}