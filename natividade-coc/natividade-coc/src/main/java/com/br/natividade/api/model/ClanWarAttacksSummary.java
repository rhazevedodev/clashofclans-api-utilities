package com.br.natividade.api.model;

import java.util.List;

public class ClanWarAttacksSummary {

    private List<String> jogadoresSemAtaquesDisponiveis;
    private List<String> jogadoresComUmAtaquesDisponiveis;
    private List<String> jogadoresComDoisAtaquesDisponiveis;

    public ClanWarAttacksSummary() {
    }

    public ClanWarAttacksSummary(List<String> jogadoresSemAtaquesDisponiveis, List<String> jogadoresComUmAtaquesDisponiveis, List<String> jogadoresComDoisAtaquesDisponiveis) {
        this.jogadoresSemAtaquesDisponiveis = jogadoresSemAtaquesDisponiveis;
        this.jogadoresComUmAtaquesDisponiveis = jogadoresComUmAtaquesDisponiveis;
        this.jogadoresComDoisAtaquesDisponiveis = jogadoresComDoisAtaquesDisponiveis;
    }

    public List<String> getJogadoresSemAtaquesDisponiveis() {
        return jogadoresSemAtaquesDisponiveis;
    }

    public void setJogadoresSemAtaquesDisponiveis(List<String> jogadoresSemAtaquesDisponiveis) {
        this.jogadoresSemAtaquesDisponiveis = jogadoresSemAtaquesDisponiveis;
    }

    public List<String> getJogadoresComUmAtaquesDisponiveis() {
        return jogadoresComUmAtaquesDisponiveis;
    }

    public void setJogadoresComUmAtaquesDisponiveis(List<String> jogadoresComUmAtaquesDisponiveis) {
        this.jogadoresComUmAtaquesDisponiveis = jogadoresComUmAtaquesDisponiveis;
    }

    public List<String> getJogadoresComDoisAtaquesDisponiveis() {
        return jogadoresComDoisAtaquesDisponiveis;
    }

    public void setJogadoresComDoisAtaquesDisponiveis(List<String> jogadoresComDoisAtaquesDisponiveis) {
        this.jogadoresComDoisAtaquesDisponiveis = jogadoresComDoisAtaquesDisponiveis;
    }
}
