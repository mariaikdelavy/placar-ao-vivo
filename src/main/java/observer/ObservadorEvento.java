package observer;

import dominio.Evento;

// PADRÃO OBSERVER: todo "ouvinte" do jogo implementa esta interface.
public interface ObservadorEvento {
    void atualizar(Evento evento);
}