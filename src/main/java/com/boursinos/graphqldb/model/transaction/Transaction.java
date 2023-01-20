package com.boursinos.graphqldb.model.transaction;

import com.boursinos.graphqldb.model.client.Client;
import com.boursinos.graphqldb.model.crypto.Crypto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Client client;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "trade_type")
    private TradeType tradeType;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crypto_id", referencedColumnName = "crypto_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Crypto crypto;

    @Column
    private double amount;

    @Column
    private double totalValue;

    public Transaction(Client client, TradeType tradeType, Crypto crypto, double amount, double totalValue) {
        this.client = client;
        this.tradeType = tradeType;
        this.crypto = crypto;
        this.amount = amount;
        this.totalValue = totalValue;
    }
}
