package ru.netology.repository;

import ru.netology.model.Post;
import java.util.Optional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
  public class PostRepository {

  private AtomicLong id = new AtomicLong(0);
  private Map<Long, Post> map = new ConcurrentHashMap<>();


  public Map<Long, Post> all() {
    return map;
  }

  public Optional<Post> getById(long id) {
    return Optional.of(map.get(id));
  }

  public synchronized Post save(Post post) {
    if (map.containsKey(post.getId())) {
      map.put(post.getId(), post);
    } else if (post.getId() == 0) {
      long newId = id.incrementAndGet();
      map.put(newId, post);
    }
    return post;
  }

  public void removeById(long id) {
  }
}
