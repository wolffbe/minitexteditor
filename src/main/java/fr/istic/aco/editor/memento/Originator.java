package fr.istic.aco.editor.memento;

public interface Originator {
    MementoImpl saveState();
    void restoreState(MementoImpl memento);
}