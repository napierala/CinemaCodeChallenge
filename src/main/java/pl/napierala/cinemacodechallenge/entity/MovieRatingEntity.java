package pl.napierala.cinemacodechallenge.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_rating")
public class MovieRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "movie_rating_movie_id_fkey"))
    private MovieEntity movie;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "movie_rating_user_id_fkey"))
    private UserEntity user;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRatingEntity that = (MovieRatingEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return rating != null ? rating.equals(that.rating) : that.rating == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieRatingEntity{" +
                "id=" + id +
                ", movie=" + movie +
                ", user=" + user +
                ", rating=" + rating +
                '}';
    }
}