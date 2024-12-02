package fr.istic.aco.editor.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CaretakerImpl<T> implements Caretaker<T> {
    private final List<Memento<T>> mementoList = new ArrayList<>();
    private int mementoIndex = -1;

    @Override
    public void addMemento(Memento<T> memento) {
        if (!mementoList.isEmpty() && mementoIndex < mementoList.size() - 1) {
            mementoList.subList(mementoIndex + 1, mementoList.size()).clear();
        }
        mementoList.add(memento);
        mementoIndex = mementoList.size() - 1;
    }

    @Override
    public Memento<T> getMemento(int index) {
        if (index <= -1 || index >= mementoList.size()) {
            throw new IndexOutOfBoundsException("Invalid list index: " + index);
        }
        return mementoList.get(index);
    }

    public boolean isFirstMemento() {
        return mementoIndex == 0;
    }

    public boolean isLastMemento() {
        return mementoIndex == mementoList.size() - 1;
    }

    public int getLastMementoIndex() {
        return mementoList.size() - 1;
    }

    public int getCurrentMementoIndex() {
        return mementoIndex;
    }

    public void setMementoIndex(int mementoIndex) {
        if(mementoIndex < 0) {
            throw new IndexOutOfBoundsException("A memento index cannot be set to a value less than zero.");
        } else if(mementoIndex > this.getLastMementoIndex()) {
            throw new IndexOutOfBoundsException("A memento index cannot be larger than the last memento index.");
        } else {
            this.mementoIndex = mementoIndex;
        }
    }

    public void incrementMementoIndex() {
        if (mementoIndex + 1 >= mementoList.size()) {
            throw new IndexOutOfBoundsException(
                    "A memento index cannot be set incremented to a value larger than the maximum number of mementos.");
        }
        mementoIndex++;
    }

    public void decrementMementoIndex() {
        if(mementoIndex - 1 < 0) {
            throw new IndexOutOfBoundsException("A memento index cannot be set to a value lower than 0.");
        }
        mementoIndex--;
    }
}
