/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.classes;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author emerson
 */
public class Venda{
    private int Id;
    private Date Data_venda;
    private double Total;
    private double Total_final;
    private List<Item_venda> Itens;
    private Funcionario Atendente;
    private Cliente Consumidor;
    private Pagamento Tipo_paga;
    
    //----

    public Venda() {
        Id = 0;
        Data_venda = new Date();
        Total = 0;
        Total_final = 0;
        Itens = new LinkedList<Item_venda>();
        Atendente = Atendente;
        Consumidor = Consumidor;
        Tipo_paga = Tipo_paga;
    }
 
    //----

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Date getData_venda() {
        return Data_venda;
    }

    public void setData_venda(Date Data_venda) {
        Date Datalimite = new Date(1900,01,01);
        
        if(Data_venda.before(Datalimite)){
            System.out.print("A data é inválida!");
        }else{
            this.Data_venda = Data_venda;
        }
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }
    
    public double getTotal_final() {
        return Total_final;
    }
    
    public void setTotal_final(Pagamento Pa){
        Total_final = (Total+(Total * Pa.getJuros())); 
    }

    public List<Item_venda> getItens() {
        return Itens;
    }

    public void setItens(List<Item_venda> Itens) {
        this.Itens = Itens;
    }

    public Funcionario getAtendente() {
        return Atendente;
    }

    public void setAtendente(Funcionario Atendente) {
        this.Atendente = Atendente;
    }

    public Cliente getConsumidor() {
        return Consumidor;
    }

    public void setConsumidor(Cliente Consumidor) {
        this.Consumidor = Consumidor;
    }

    public Pagamento getTipo_paga() {
        return Tipo_paga;
    }

    public void setTipo_paga(Pagamento Tipo_paga) {
        this.Tipo_paga = Tipo_paga;
    }
    
    public void addItem(Item_venda Iv){
        if(!Itens.contains(Iv)){
            Itens.add(Iv);
            Total += Iv.getProduto().getValor_vend() * Iv.getQtd();
        }
    }
    
    public void removeItem(Item_venda Iv){
        if(Itens.contains(Iv)){
            Itens.remove(Iv);
            Total -= Iv.getProduto().getValor_vend() * Iv.getQtd();
        }
    }
    
    //----
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Data_venda, other.Data_venda)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Total) != Double.doubleToLongBits(other.Total)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Total_final) != Double.doubleToLongBits(other.Total_final)) {
            return false;
        }
        if (!Objects.equals(this.Itens, other.Itens)) {
            return false;
        }
        if (!Objects.equals(this.Atendente, other.Atendente)) {
            return false;
        }
        if (!Objects.equals(this.Consumidor, other.Consumidor)) {
            return false;
        }
        if (!Objects.equals(this.Tipo_paga, other.Tipo_paga)) {
            return false;
        }
        return true;
    }
    



    @Override
    public String toString() {
        return "Venda{" + "Id=" + Id + ", Data_venda=" + Data_venda + ", Total=" + Total + ", Itens=" + Itens + ", Atendente=" + Atendente + ", Consumidor=" + Consumidor + ", Tipo_paga=" + Tipo_paga + '}';
    }
}
