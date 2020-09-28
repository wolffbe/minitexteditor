package fr.istic.aco.editor.memento;

public interface Caretaker<T> {
    void addMemento(Memento<T> memento);
    Memento<T> getMemento(int index);
}
