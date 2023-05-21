package ru.netology.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.controller.PostController;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class MainServlet extends HttpServlet {

  private final static String POST_PATH = "/api/posts";
  private final static String POST_PATH_REGEX = "/api/posts/\\d+";
  private final PostController controller;

  @Autowired
  public MainServlet(PostController controller){
    this.controller = controller;
  }

  @Override
  public void init() {
    // Код инициализации контекста уже не требуется,
    // так как контекст создается с использованием AnnotationConfigApplicationContext
    // и аннотации @Component у класса MainServlet.
    // Вместо инициализации сервиса и репозитория здесь,
    // они будут внедрены в контроллер через автосвязывание (Autowired).
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    if (path.equals(POST_PATH)) {
      controller.save(req.getReader(), resp);
    }else  {
      super.doPost(req,resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    if (path.equals(POST_PATH)) {
      controller.all(resp);
      return;
    } else if (path.matches(POST_PATH_REGEX)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.getById(id, resp);
      return;
    }
    super.doGet(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
    if (path.matches(POST_PATH_REGEX)) {
      controller.removeById(id, resp);
      return;
    }
    super.doDelete(req, resp);
  }

}

