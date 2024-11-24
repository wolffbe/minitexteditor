package fr.istic.aco.editor.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CaretakerImpl implements Caretaker {
    private final List<MementoImpl> mementoList = new ArrayList<>();

    @Override
    public void addMemento(MementoImpl memento) {
        mementoList.add(memento);
    }

    @Override
    public MementoImpl getMemento(int index) {
        return mementoList.get(index);
    }

    @Override
    public Integer getMementoLastItemIndex() {
        return mementoList.size() - 1;
    }
}