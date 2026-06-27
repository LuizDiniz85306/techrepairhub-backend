package com.luiz.techrepairhub.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "garantias")
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "item_pedido_id", nullable = false)
    private ItemPedido itemPedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Column(name = "meses_garantia", nullable = false)
    private Integer mesesGarantia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusGarantia status = StatusGarantia.ATIVA;

    public Garantia() {
    }

    public Garantia(Pedido pedido, ItemPedido itemPedido) {
        this.pedido = pedido;
        this.itemPedido = itemPedido;
        this.produto = itemPedido.getProduto();
        this.cliente = pedido.getCliente();
        this.dataInicio = pedido.getDataPedido();
        this.mesesGarantia = itemPedido.getProduto().getGarantiaMeses();
        this.dataFim = this.dataInicio.plusMonths(this.mesesGarantia);
        this.status = StatusGarantia.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(Integer mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    public StatusGarantia getStatus() {
        return status;
    }

    public void setStatus(StatusGarantia status) {
        this.status = status;
    }

    public boolean estaExpirada() {
        return LocalDateTime.now().isAfter(dataFim);
    }
}