package fr.istic.aco.editor.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CaretakerImpl<T> implements Caretaker<T> {
    private final List<Memento<T>> mementoList = new ArrayList<>();

    @Override
    public void addMemento(Memento<T> memento) {
        mementoList.add(memento);
    }

    @Override
    public Memento<T> getMemento(int index) {
        if (index < 0 || index >= mementoList.size()) {
            throw new IndexOutOfBoundsException("Invalid memento index: " + index);
        }
        return mementoList.get(index);
    }

    public int getNextMementoIndex() {
        return mementoList.size();
    }

    public int getLastMementoIndex() {
        return mementoList.size() - 1;
    }
}
