package pl.napierala.cinemacodechallenge.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * A single showing of a movie.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_showing")
public class MovieShowingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cinema_id", foreignKey = @ForeignKey(name = "movie_showing_cinema_id_fkey"))
    private CinemaEntity cinema;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "movie_showing_movie_id_fkey"))
    private MovieEntity movie;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "room", nullable = false)
    private String room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieShowingEntity that = (MovieShowingEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cinema != null ? !cinema.equals(that.cinema) : that.cinema != null) return false;
        if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        return room != null ? room.equals(that.room) : that.room == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cinema != null ? cinema.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieShowingEntity{" +
                "id=" + id +
                ", cinema=" + cinema +
                ", movie=" + movie +
                ", dateTime=" + dateTime +
                ", room='" + room + '\'' +
                '}';
    }
}