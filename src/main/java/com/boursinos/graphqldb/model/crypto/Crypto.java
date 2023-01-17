package com.boursinos.graphqldb.model.crypto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Crypto {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "crypto_id")
    private String cryptoId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

}
