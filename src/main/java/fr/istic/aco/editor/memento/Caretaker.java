package fr.istic.aco.editor.memento;

public interface Caretaker {
    void addMemento(MementoImpl memento);
    MementoImpl getMemento(int index);
    Integer getMementoLastItemIndex();
}