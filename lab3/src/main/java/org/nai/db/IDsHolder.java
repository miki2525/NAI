package org.nai.db;


import com.sun.jersey.api.NotFoundException;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IDsHolder assigns and holds IDs for userNames and movies
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class IDsHolder {

    private static final AtomicLong userId = new AtomicLong();
    private static final AtomicLong movieId = new AtomicLong();
    static final Map<String, Long> userMap = new CaseInsensitiveMap<>();
    static final Map<String, Long> movieMap = new CaseInsensitiveMap<>();


    public static String getUserById(long id) {
        Optional<String> userName = keys(userMap, id);
        return userName.orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    public static String getMovieById(long id) {
        Optional<String> movie = keys(movieMap, id);
        return movie.orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    public static long getIdForUser(String userName) {
        if (userMap.get(userName) != null) {
            return userMap.get(userName);
        } else {
            throw new RuntimeException("No such user in DB");
        }
    }

    public static long getIdForMovie(String movie) {
        if (movieMap.get(movie) != null) {
            return movieMap.get(movie);
        } else {
            throw new RuntimeException("No such movie in DB");
        }
    }

    static void assignUserId(String userName) {
        if (!userMap.containsKey(userName)) {
            userMap.put(userName, userId.incrementAndGet());
        }
    }

    static void assignMovieId(String movie) {
        if (!movieMap.containsKey(movie)) {
            movieMap.put(movie, movieId.incrementAndGet());
        }
    }

    private static <K, V> Optional<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

}
