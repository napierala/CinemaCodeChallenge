package pl.napierala.cinemacodechallenge.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_price")
public class TicketPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cinema_id", foreignKey = @ForeignKey(name = "ticket_price_cinema_id_fkey"))
    private CinemaEntity cinema;

    /**
     * A description - for example: normal ticket, discounted for students etc.
     */
    @NotEmpty
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The price in cents meaning that $10.50 will be 1050 as it is in cents.
     * The concept of cents is just a name as it could be grosz in PLN, pennies for the pound or euro cents.
     */
    @NotNull
    @Min(value = 0)
    @Column(name = "price_in_cents", nullable = false)
    private Integer priceInCents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketPriceEntity that = (TicketPriceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cinema != null ? !cinema.equals(that.cinema) : that.cinema != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return priceInCents != null ? priceInCents.equals(that.priceInCents) : that.priceInCents == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cinema != null ? cinema.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (priceInCents != null ? priceInCents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketPriceEntity{" +
                "id=" + id +
                ", cinema=" + cinema +
                ", description='" + description + '\'' +
                ", priceInCents=" + priceInCents +
                '}';
    }
}