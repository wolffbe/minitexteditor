package fr.istic.aco.editor.memento;

public interface Originator<T> {
    Memento<T> saveState();
    void restoreState(Memento<T> memento);
}