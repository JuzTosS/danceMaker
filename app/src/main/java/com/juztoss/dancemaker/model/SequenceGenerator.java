package com.juztoss.dancemaker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kirill on 3/20/2016.
 */
public class SequenceGenerator {

    private static List<DanceElement> getElementsInOutType(List<DanceElement> elements, int type) {
        ArrayList<DanceElement> result = new ArrayList<>();
        for (DanceElement el : elements) {
            if ((el.getInOut() & type) > 0)
                result.add(el);
        }

        return result;
    }

    private static int getRandomOutType(int types) {
        List<Integer> outTypes = new ArrayList<>();

        if ((types & DanceElement.OUT_HELLO) > 0)
            outTypes.add(DanceElement.OUT_HELLO);
        if ((types & DanceElement.OUT_LEFT) > 0)
            outTypes.add(DanceElement.OUT_LEFT);
        if ((types & DanceElement.OUT_BOTH) > 0)
            outTypes.add(DanceElement.OUT_BOTH);

        if (outTypes.size() == 0)
            return 0;

        int elementIndex = (int) (Math.random() * outTypes.size());
        return outTypes.get(elementIndex);
    }

    private static DanceElement getRandom(List<DanceElement> elements) {
        if(elements.size() <= 0)
            return null;

        int elementIndex = (int) (Math.random() * elements.size());
        return elements.get(elementIndex);
    }

    public static DanceSequence generateNew(String name, int length, List<DanceElement> allElements) {

        Map<Integer, List<DanceElement>> elementsByType = new HashMap<>();
        elementsByType.put(DanceElement.IN_HELLO, getElementsInOutType(allElements, DanceElement.IN_HELLO));
        elementsByType.put(DanceElement.IN_LEFT, getElementsInOutType(allElements, DanceElement.IN_LEFT));
        elementsByType.put(DanceElement.IN_BOTH, getElementsInOutType(allElements, DanceElement.IN_BOTH));
        elementsByType.put(DanceElement.OUT_HELLO, getElementsInOutType(allElements, DanceElement.OUT_HELLO));
        elementsByType.put(DanceElement.OUT_LEFT, getElementsInOutType(allElements, DanceElement.OUT_LEFT));
        elementsByType.put(DanceElement.OUT_BOTH, getElementsInOutType(allElements, DanceElement.OUT_BOTH));

        DanceSequence seq = new DanceSequence(name, new ArrayList<DanceElement>());

        DanceElement lastEl = null;

        while (seq.getLength() < length) {
            if(lastEl == null)
                lastEl = getRandom(allElements);

            int elementTypes = getRandomOutType(lastEl.getInOut());

            int type = getRandomOutType(elementTypes);
            if (type > 0) {
                lastEl = getRandom(elementsByType.get(type));
            }

            if (hasOutTypes(elementsByType, lastEl))
                seq.getElements().add(lastEl);
            else {
                remove(elementsByType, lastEl);
                lastEl = null;
            }
        }
        return seq;
    }

    private static void remove(Map<Integer, List<DanceElement>> elementsByType, DanceElement el) {
        for (Map.Entry<Integer, List<DanceElement>> entry : elementsByType.entrySet()) {
            int index = entry.getValue().indexOf(el);
            if (index >= 0)
                entry.getValue().remove(index);
        }
    }

    private static boolean hasOutTypes(Map<Integer, List<DanceElement>> elementsByType, DanceElement el) {
        if (elementsByType.get(DanceElement.IN_HELLO).size() > 0 && (el.getInOut() & DanceElement.OUT_HELLO) > 0)
            return true;

        if (elementsByType.get(DanceElement.IN_LEFT).size() > 0 && (el.getInOut() & DanceElement.OUT_LEFT) > 0)
            return true;

        if (elementsByType.get(DanceElement.IN_BOTH).size() > 0 && (el.getInOut() & DanceElement.OUT_BOTH) > 0)
            return true;

        return false;
    }
}
